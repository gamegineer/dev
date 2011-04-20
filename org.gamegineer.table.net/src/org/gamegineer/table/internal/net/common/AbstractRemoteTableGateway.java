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

    /** The next available message tag. */
    @GuardedBy( "getLock()" )
    private int nextTag_;

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
        nextTag_ = getInitialMessageTag();
        tableGatewayContext_ = tableGatewayContext;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the initial message tag.
     * 
     * @return The initial message tag.
     */
    private static int getInitialMessageTag()
    {
        final Random rng = new Random( System.currentTimeMillis() );
        return IMessage.MINIMUM_TAG + rng.nextInt( IMessage.MAXIMUM_TAG - IMessage.MINIMUM_TAG );
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
     * Gets the next available message tag.
     * 
     * @return The next available message tag.
     */
    @GuardedBy( "getLock()" )
    protected final int getNextMessageTag()
    {
        assert Thread.holdsLock( getLock() );

        final int tag = nextTag_;
        if( ++nextTag_ > IMessage.MAXIMUM_TAG )
        {
            nextTag_ = IMessage.MINIMUM_TAG;
        }

        return tag;
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
     * @see org.gamegineer.table.internal.net.transport.IService#messageReceived(org.gamegineer.table.internal.net.transport.IServiceContext, org.gamegineer.table.internal.net.transport.MessageEnvelope)
     */
    @Override
    public final void messageReceived(
        final IServiceContext context,
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$

        // TODO: should handle correlation of all response message tags in this (base) class

        try
        {
            final IMessage message = messageEnvelope.getBodyAsMessage();
            synchronized( getLock() )
            {
                if( !messageReceivedInternal( context, message ) )
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
     * @param context
     *        The service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return {@code true} if the message was handled by the service; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected boolean messageReceivedInternal(
        /* @NonNull */
        final IServiceContext context,
        /* @NonNull */
        final IMessage message )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#peerStopped(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public final void peerStopped(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            peerStoppedInternal( context );
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
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void peerStoppedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
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
            startedInternal( context );
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
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void startedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IService#stopped(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    public final void stopped(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            stoppedInternal( context );
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
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void stoppedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }
}
