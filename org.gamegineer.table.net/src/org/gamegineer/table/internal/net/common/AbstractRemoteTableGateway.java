/*
 * AbstractRemoteTableGateway.java
 * Copyright 2008-2011 Gamegineer.org
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Apr 14, 2011 at 10:43:19 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * Superclass for all implementations of {@ink
 * org.gamegineer.table.internal.net.common.IRemoteTableGateway}.
 */
@ThreadSafe
public abstract class AbstractRemoteTableGateway
    implements IRemoteTableGateway, IService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of message handlers for correlated messages. The key is
     * the message (request) identifier. The value is the message handler.
     */
    @GuardedBy( "getLock()" )
    private final Map<Integer, IMessageHandler> correlatedMessageHandlers_;

    /** The instance lock. */
    private final Object lock_;

    /** The next available message identifier. */
    @GuardedBy( "getLock()" )
    private int nextId_;

    /**
     * The name of the remote player or {@code null} if the player has not yet
     * been authenticated.
     */
    @GuardedBy( "getLock()" )
    private String playerName_;

    /**
     * The network service context or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private IServiceContext serviceContext_;

    /** The table gateway context. */
    private final ITableGatewayContext tableGatewayContext_;

    /**
     * The collection of message handlers for uncorrelated messages. The key is
     * the message type. The value is the message handler.
     */
    @GuardedBy( "getLock()" )
    private final Map<Class<? extends IMessage>, IMessageHandler> uncorrelatedMessageHandlers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteTableGateway}
     * class.
     * 
     * @param tableGatewayContext
     *        The table gateway context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGatewayContext} is {@code null}.
     */
    protected AbstractRemoteTableGateway(
        /* @NonNull */
        final ITableGatewayContext tableGatewayContext )
    {
        assertArgumentNotNull( tableGatewayContext, "tableGatewayContext" ); //$NON-NLS-1$

        correlatedMessageHandlers_ = new HashMap<Integer, IMessageHandler>();
        lock_ = new Object();
        nextId_ = getInitialMessageId();
        playerName_ = null;
        serviceContext_ = null;
        tableGatewayContext_ = tableGatewayContext;
        uncorrelatedMessageHandlers_ = new IdentityHashMap<Class<? extends IMessage>, IMessageHandler>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#close()
     */
    @Override
    public final void close()
    {
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableGateway_networkDisconnected );
        assert Thread.holdsLock( getLock() );

        serviceContext_.stopService();
    }

    /**
     * Invoked when the table gateway has been closed.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * Subclasses may override but the superclass version must be called.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void closed()
    {
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            tableGatewayContext_.removeTableGateway( this );
            playerName_ = null;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#getContext()
     */
    public final ITableGatewayContext getContext()
    {
        return tableGatewayContext_;
    }

    /**
     * Gets the initial message identifier.
     * 
     * @return The initial message identifier.
     */
    private static int getInitialMessageId()
    {
        return IMessage.MINIMUM_ID + (int)(Math.random() * (IMessage.MAXIMUM_ID - IMessage.MINIMUM_ID));
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#getLock()
     */
    public final Object getLock()
    {
        return lock_;
    }

    /**
     * Gets the next available message identifier.
     * 
     * @return The next available message identifier.
     */
    @GuardedBy( "getLock()" )
    private int getNextMessageId()
    {
        assert Thread.holdsLock( getLock() );

        final int id = nextId_;
        if( ++nextId_ > IMessage.MAXIMUM_ID )
        {
            nextId_ = IMessage.MINIMUM_ID;
        }

        return id;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#getPlayerName()
     */
    @Override
    public final String getPlayerName()
    {
        synchronized( getLock() )
        {
            assertStateLegal( playerName_ != null, Messages.AbstractRemoteTableGateway_playerNotAuthenticated );
            return playerName_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#messageReceived(org.gamegineer.table.internal.net.transport.MessageEnvelope)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public final void messageReceived(
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$

        try
        {
            final IMessage message = messageEnvelope.getBodyAsMessage();
            synchronized( getLock() )
            {
                final IMessageHandler messageHandler;
                if( message.getCorrelationId() != IMessage.NULL_CORRELATION_ID )
                {
                    messageHandler = correlatedMessageHandlers_.remove( message.getCorrelationId() );
                }
                else
                {
                    messageHandler = uncorrelatedMessageHandlers_.get( message.getClass() );
                }

                if( messageHandler != null )
                {
                    messageHandler.handleMessage( message );
                }
                else
                {
                    // TODO: Add support for default message handlers. For example, may send back
                    // an error message correlated to the unknown message to indicate to the peer
                    // that message is not supported...
                    Loggers.getDefaultLogger().warning( Messages.AbstractRemoteTableGateway_messageReceived_unsupportedMessage( messageEnvelope ) );
                }
            }
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractRemoteTableGateway_messageReceived_deserializationError( messageEnvelope ), e );
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractRemoteTableGateway_messageReceived_deserializationError( messageEnvelope ), e );
        }
    }

    /**
     * Invoked when the table gateway has been opened.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * Subclasses may override but the superclass version must be called.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void opened()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#peerStopped()
     */
    @Override
    public final void peerStopped()
    {
        synchronized( getLock() )
        {
            close();
        }
    }

    /**
     * Registers the specified message handler to handle uncorrelated messages
     * of the specified type.
     * 
     * @param type
     *        The message type; must not be {@code null}.
     * @param messageHandler
     *        The message handler; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an uncorrelated message handler is already registered for the
     *         specified message type.
     * @throws java.lang.NullPointerException
     *         If {@code type} or {@code messageHandler} is {@code null}.
     */
    protected final void registerUncorrelatedMessageHandler(
        /* @NonNull */
        final Class<? extends IMessage> type,
        /* @NonNull */
        final IMessageHandler messageHandler )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$
        assertArgumentNotNull( messageHandler, "messageHandler" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            assertArgumentLegal( !uncorrelatedMessageHandlers_.containsKey( type ), "type", Messages.AbstractRemoteTableGateway_registerUncorrelatedMessageHandler_messageTypeRegistered ); //$NON-NLS-1$
            uncorrelatedMessageHandlers_.put( type, messageHandler );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#sendMessage(org.gamegineer.table.internal.net.transport.IMessage, org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public final boolean sendMessage(
        final IMessage message,
        final IMessageHandler messageHandler )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableGateway_networkDisconnected );
        assert Thread.holdsLock( getLock() );

        message.setId( getNextMessageId() );
        final boolean wasSent = serviceContext_.sendMessage( message );
        if( wasSent && (messageHandler != null) )
        {
            correlatedMessageHandlers_.put( message.getId(), messageHandler );
        }

        return wasSent;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#setPlayerName(java.lang.String)
     */
    @Override
    public final void setPlayerName(
        final String playerName )
    {
        assert Thread.holdsLock( getLock() );

        playerName_ = playerName;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#started(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public final void started(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            serviceContext_ = context;
            opened();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#stopped()
     */
    @Override
    public final void stopped()
    {
        synchronized( getLock() )
        {
            closed();
            serviceContext_ = null;
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all implementations of {@ink
     * org.gamegineer.table.internal.net.common.IRemoteTableGateway.
     * IMessageHandler}.
     * 
     * @param <T>
     *        The type of the remote table gateway.
     */
    @Immutable
    public static abstract class AbstractMessageHandler<T extends IRemoteTableGateway>
        implements IMessageHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The remote table gateway associated with the message handler. */
        private final T remoteTableGateway_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteTableGateway
         *        The remote table gateway associated with the message handler;
         *        must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableGateway} is {@code null}.
         */
        protected AbstractMessageHandler(
            /* @NonNull */
            final T remoteTableGateway )
        {
            assertArgumentNotNull( remoteTableGateway, "remoteTableGateway" ); //$NON-NLS-1$

            remoteTableGateway_ = remoteTableGateway;
        }


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Gets the remote table gateway associated with the message handler.
         * 
         * @return The remote table gateway associated with the message handler.
         */
        /* @NonNull */
        protected final T getRemoteTableGateway()
        {
            return remoteTableGateway_;
        }

        /**
         * This method dispatches the incoming message via reflection. It
         * searches for a method with the following signature:
         * 
         * <p>
         * <code>void handleMessage(<i>&lt;concrete message type&gt;</i>)</code>
         * </p>
         * 
         * <p>
         * If such a method is not found, an error message will be sent to the
         * peer gateway indicating the message is unsupported.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.transport.IMessage)
         */
        @Override
        public final void handleMessage(
            final IMessage message )
        {
            assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

            try
            {
                final Method method = getClass().getDeclaredMethod( "handleMessage", message.getClass() ); //$NON-NLS-1$
                try
                {
                    method.setAccessible( true );
                    method.invoke( this, message );
                }
                catch( final Exception e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractRemoteTableGateway_handleMessage_unexpectedError, e );
                }
            }
            catch( final NoSuchMethodException e )
            {
                // TODO: log unsupported message
                // TODO: send error response before invoking template method
                handleUnsupportedMessage();
            }
        }

        /**
         * Invoked when the handler receives an unsupported message.
         * 
         * <p>
         * This implementation does nothing.
         * </p>
         */
        protected void handleUnsupportedMessage()
        {
            // do nothing
        }
    }
}
