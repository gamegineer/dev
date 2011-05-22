/*
 * AbstractRemoteTableProxy.java
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
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.ITableProxy;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;

/**
 * Superclass for all remote table proxies.
 */
@ThreadSafe
public abstract class AbstractRemoteTableProxy
    implements IRemoteTableProxyController, IService, ITableProxy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The error that caused the remote table proxy to be closed or {@code null}
     * if the remote table proxy was closed normally.
     */
    @GuardedBy( "getLock()" )
    private TableNetworkError closeError_;

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

    /** The local table network node. */
    private final ITableNetworkNode node_;

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
     * Initializes a new instance of the {@code AbstractRemoteTableProxy} class.
     * 
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    protected AbstractRemoteTableProxy(
        /* @NonNull */
        final ITableNetworkNode node )
    {
        assertArgumentNotNull( node, "node" ); //$NON-NLS-1$

        closeError_ = null;
        correlatedMessageHandlers_ = new HashMap<Integer, IMessageHandler>();
        lock_ = new Object();
        nextId_ = getInitialMessageId();
        node_ = node;
        playerName_ = null;
        serviceContext_ = null;
        uncorrelatedMessageHandlers_ = new IdentityHashMap<Class<? extends IMessage>, IMessageHandler>();

        registerUncorrelatedMessageHandler( ErrorMessage.class, new ErrorMessageHandler( this ) );
    }

    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#bind(java.lang.String)
     */
    @Override
    public final void bind(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableProxy_closed );
        assertStateLegal( playerName_ == null, Messages.AbstractRemoteTableProxy_bound );
        assert Thread.holdsLock( getLock() );

        playerName_ = playerName;
        node_.addTableProxy( this );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#close(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void close(
        final TableNetworkError error )
    {
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableProxy_closed );
        assert Thread.holdsLock( getLock() );

        closeError_ = error;
        serviceContext_.stopService();
    }

    /**
     * Invoked when the remote table proxy has been closed.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * Subclasses may override but the superclass version must be called.
     * </p>
     * 
     * @param error
     *        The error that caused the remote table proxy to be closed or
     *        {@code null} if the remote table proxy was closed normally.
     */
    @GuardedBy( "getLock()" )
    protected void closed(
        /* @Nullable */
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            synchronized( node_.getLock() )
            {
                node_.removeTableProxy( this );
            }

            playerName_ = null;
        }
    }

    /**
     * Extracts the message from the specified message envelope.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @return The message extracted from the specified message envelope or
     *         {@code null} if the message envelope contains an unknown message.
     */
    /* @Nullable */
    private static IMessage extractMessage(
        /* @NonNull */
        final MessageEnvelope messageEnvelope )
    {
        assert messageEnvelope != null;

        try
        {
            return messageEnvelope.getBodyAsMessage();
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractRemoteTableProxy_extractMessage_deserializationError( messageEnvelope ), e );
            return null;
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractRemoteTableProxy_extractMessage_deserializationError( messageEnvelope ), e );
            return null;
        }
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
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#getLock()
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
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#getNode()
     */
    public final ITableNetworkNode getNode()
    {
        return node_;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#getPlayerName()
     */
    @Override
    public final String getPlayerName()
    {
        synchronized( getLock() )
        {
            assertStateLegal( playerName_ != null, Messages.AbstractRemoteTableProxy_playerNotAuthenticated );
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

        synchronized( getLock() )
        {
            final IMessage message = extractMessage( messageEnvelope );
            if( message != null )
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
                    synchronized( node_.getLock() )
                    {
                        messageHandler.handleMessage( message );
                    }
                }
                else
                {
                    Loggers.getDefaultLogger().warning( Messages.AbstractRemoteTableProxy_messageReceived_unhandledMessage( message ) );
                    if( !(message instanceof ErrorMessage) )
                    {
                        sendErrorMessage( TableNetworkError.UNHANDLED_MESSAGE, message.getId() );
                    }
                }
            }
            else
            {
                sendErrorMessage( TableNetworkError.UNKNOWN_MESSAGE, messageEnvelope.getId() );
            }
        }
    }

    /**
     * Invoked when the remote table proxy has been opened.
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
            close( TableNetworkError.UNEXPECTED_PEER_TERMINATION );
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
            assertArgumentLegal( !uncorrelatedMessageHandlers_.containsKey( type ), "type", Messages.AbstractRemoteTableProxy_registerUncorrelatedMessageHandler_messageTypeRegistered ); //$NON-NLS-1$
            uncorrelatedMessageHandlers_.put( type, messageHandler );
        }
    }

    /**
     * Sends an error message to the remote peer with the specified attributes.
     * 
     * @param error
     *        The error that occurred; must not be {@code null}.
     * @param correlationId
     *        The message correlation identifier.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code correlationId} is less than {@link IMessage#MINIMUM_ID}
     *         or greater than {@link IMessage#MAXIMUM_ID} or not equal to
     *         {@link IMessage#NULL_CORRELATION_ID} .
     */
    @GuardedBy( "getLock()" )
    private void sendErrorMessage(
        /* @NonNull */
        final TableNetworkError error,
        final int correlationId )
    {
        assert error != null;
        assert Thread.holdsLock( getLock() );

        final ErrorMessage message = new ErrorMessage();
        message.setCorrelationId( correlationId );
        message.setError( error );
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController#sendMessage(org.gamegineer.table.internal.net.transport.IMessage, org.gamegineer.table.internal.net.common.IRemoteTableProxyController.IMessageHandler)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public final boolean sendMessage(
        final IMessage message,
        final IMessageHandler messageHandler )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableProxy_closed );
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
     * @see org.gamegineer.table.internal.net.transport.IService#stopped(java.lang.Exception)
     */
    @Override
    public final void stopped(
        final Exception exception )
    {
        synchronized( getLock() )
        {
            // Do not overwrite the original error that caused the remote table proxy to be closed
            if( (exception != null) && (closeError_ == null) )
            {
                closeError_ = TableNetworkError.TRANSPORT_ERROR;
            }

            closed( closeError_ );
            serviceContext_ = null;
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all implementations of {@ink
     * org.gamegineer.table.internal.net.common.IRemoteTableProxyController.
     * IMessageHandler}.
     * 
     * @param <T>
     *        The type of the remote table proxy control interface.
     */
    @Immutable
    public static abstract class AbstractMessageHandler<T extends IRemoteTableProxyController>
        implements IMessageHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The control interface for the remote table proxy associated with the
         * message handler.
         */
        private final T remoteTableProxyController_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteTableProxyController
         *        The control interface for the remote table proxy associated
         *        with the message handler; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableProxyController} is {@code null}.
         */
        protected AbstractMessageHandler(
            /* @NonNull */
            final T remoteTableProxyController )
        {
            assertArgumentNotNull( remoteTableProxyController, "remoteTableProxyController" ); //$NON-NLS-1$

            remoteTableProxyController_ = remoteTableProxyController;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the control interface for the remote table proxy associated with
         * the message handler.
         * 
         * @return The control interface for the remote table proxy associated
         *         with the message handler.
         */
        /* @NonNull */
        protected final T getRemoteTableProxyController()
        {
            return remoteTableProxyController_;
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
         * peer remote table proxy indicating the message is unsupported.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.common.IRemoteTableProxyController.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.transport.IMessage)
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
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractMessageHandler_handleMessage_unexpectedError, e );
                }
            }
            catch( final NoSuchMethodException e )
            {
                Loggers.getDefaultLogger().severe( Messages.AbstractMessageHandler_messageReceived_unexpectedMessage( this, message ) );

                final ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCorrelationId( message.getId() );
                errorMessage.setError( TableNetworkError.UNEXPECTED_MESSAGE );
                getRemoteTableProxyController().sendMessage( errorMessage, null );

                handleUnexpectedMessage();
            }
        }

        /**
         * Invoked when the handler receives an unexpected message.
         * 
         * <p>
         * This implementation does nothing.
         * </p>
         */
        protected void handleUnexpectedMessage()
        {
            // do nothing
        }
    }

    /**
     * A message handler for the {@link ErrorMessage} class.
     */
    @Immutable
    private static final class ErrorMessageHandler
        extends AbstractMessageHandler<IRemoteTableProxyController>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ErrorMessageHandler} class.
         * 
         * @param remoteTableProxyController
         *        The control interface for the remote table proxy associated
         *        with the message handler; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableProxyController} is {@code null}.
         */
        ErrorMessageHandler(
            /* @NonNull */
            final IRemoteTableProxyController remoteTableProxyController )
        {
            super( remoteTableProxyController );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Handles an {@code ErrorMessage} message.
         * 
         * @param message
         *        The message; must not be {@code null}.
         */
        @SuppressWarnings( "unused" )
        private void handleMessage(
            /* @NonNull */
            final ErrorMessage message )
        {
            assert message != null;

            Loggers.getDefaultLogger().warning( Messages.ErrorMessageHandler_handleMessage_errorReceived( message.getError() ) );
        }
    }
}