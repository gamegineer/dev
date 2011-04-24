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
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * Superclass for all implementations of {@ink
 * org.gamegineer.table.internal.net.ITableGateway}.
 */
@ThreadSafe
public abstract class AbstractRemoteTableGateway
    implements IRemoteTableGateway, IService
{
    // ======================================================================
    // Fields
    // ======================================================================

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
    private final Map<Class<? extends IMessage>, IMessageHandler<?, ?>> uncorrelatedMessageHandlers_;


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

        lock_ = new Object();
        nextId_ = getInitialMessageId();
        playerName_ = null;
        serviceContext_ = null;
        tableGatewayContext_ = tableGatewayContext;
        uncorrelatedMessageHandlers_ = new IdentityHashMap<Class<? extends IMessage>, IMessageHandler<?, ?>>();
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
        final Random rng = new Random( System.currentTimeMillis() );
        return IMessage.MINIMUM_ID + rng.nextInt( IMessage.MAXIMUM_ID - IMessage.MINIMUM_ID );
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
    @SuppressWarnings( "unchecked" )
    public final void messageReceived(
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$

        // TODO: should handle correlation of all response message tags in this (base) class

        try
        {
            final IMessage message = messageEnvelope.getBodyAsMessage();
            synchronized( getLock() )
            {
                final IMessageHandler messageHandler = uncorrelatedMessageHandlers_.get( message.getClass() );
                if( messageHandler != null )
                {
                    messageHandler.handleMessage( this, message );
                }
                else
                {
                    Loggers.getDefaultLogger().warning( Messages.AbstractRemoteTableGateway_messageReceived_unknownMessage( messageEnvelope ) );
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
            closed();
        }
    }

    /**
     * Registers the specified message handler to handle uncorrelated messages
     * of the specified type.
     * 
     * @param <MessageType>
     *        The type of the message handled by the message handler.
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
    protected final <MessageType extends IMessage> void registerUncorrelatedMessageHandler(
        /* @NonNull */
        final Class<MessageType> type,
        /* @NonNull */
        final IMessageHandler<?, MessageType> messageHandler )
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
     * @see org.gamegineer.table.internal.net.common.IRemoteTableGateway#sendMessage(org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    public final boolean sendMessage(
        /* @NonNull */
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableGateway_networkDisconnected );
        assert Thread.holdsLock( getLock() );

        message.setId( getNextMessageId() );
        return serviceContext_.sendMessage( message );
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
}
