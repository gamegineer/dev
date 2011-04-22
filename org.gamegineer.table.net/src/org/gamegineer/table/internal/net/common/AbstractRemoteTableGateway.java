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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGateway;
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
    implements ITableGateway, IService
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
     * The network service context or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private IServiceContext serviceContext_;

    /** The table gateway context. */
    private final ITableGatewayContext tableGatewayContext_;


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
        serviceContext_ = null;
        tableGatewayContext_ = tableGatewayContext;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

    /**
     * Gets the instance lock for the object.
     * 
     * @return The instance lock for the object; never {@code null}.
     */
    /* @NonNull */
    protected final Object getLock()
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

    /**
     * Gets the network service context.
     * 
     * @return The network service context; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    protected final IServiceContext getServiceContext()
    {
        assertStateLegal( serviceContext_ != null, Messages.AbstractRemoteTableGateway_networkDisconnected );
        assert Thread.holdsLock( getLock() );

        return serviceContext_;
    }

    /**
     * Gets the table gateway context.
     * 
     * @return The table gateway context; never {@code null}.
     */
    /* @NonNull */
    protected final ITableGatewayContext getTableGatewayContext()
    {
        return tableGatewayContext_;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#messageReceived(org.gamegineer.table.internal.net.transport.MessageEnvelope)
     */
    @Override
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
                if( !messageReceivedInternal( message ) )
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
     * Invoked when a message has been received from the peer service.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * This implementation does nothing and always returns {@code false}.
     * </p>
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return {@code true} if the message was handled by the service; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected boolean messageReceivedInternal(
        /* @NonNull */
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#peerStopped()
     */
    @Override
    public final void peerStopped()
    {
        synchronized( getLock() )
        {
            peerStoppedInternal();
        }
    }

    /**
     * Invoked when the peer service has stopped.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void peerStoppedInternal()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Sends the specified message to the service peer.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return {@code true} if the message was sent successfully; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected final boolean sendMessage(
        /* @NonNull */
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        message.setId( getNextMessageId() );
        return getServiceContext().sendMessage( message );
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
            startedInternal();
        }
    }

    /**
     * Invoked when the service has started.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void startedInternal()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Stops the network service.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     */
    @GuardedBy( "getLock()" )
    protected final void stop()
    {
        assert Thread.holdsLock( getLock() );

        getServiceContext().stopService();
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#stopped()
     */
    @Override
    public final void stopped()
    {
        synchronized( getLock() )
        {
            stoppedInternal();
            serviceContext_ = null;
        }
    }

    /**
     * Invoked when the service has stopped.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void stoppedInternal()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }
}
