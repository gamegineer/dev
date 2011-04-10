/*
 * AbstractService.java
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
 * Created on Mar 25, 2011 at 11:33:11 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;

/**
 * Superclass for all implementations of {@link IService}.
 */
@ThreadSafe
abstract class AbstractService
    implements IService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instance lock. */
    private final Object lock_;

    /** The network table associated with the service. */
    private final NetworkTable networkTable_;

    /** The next available message tag. */
    @GuardedBy( "getLock()" )
    private int nextTag_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractService} class.
     * 
     * @param networkTable
     *        The network table associated with the service; must not be {@code
     *        null}.
     */
    AbstractService(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        lock_ = new Object();
        networkTable_ = networkTable;
        nextTag_ = getInitialMessageTag();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the initial message tag for the service.
     * 
     * @return The initial message tag for the service.
     */
    private static int getInitialMessageTag()
    {
        final Random rng = new Random( System.currentTimeMillis() );
        return IMessage.MIN_TAG + rng.nextInt( IMessage.MAX_TAG - IMessage.MIN_TAG );
    }

    /**
     * Gets the instance lock for the service.
     * 
     * @return The instance lock for the service; never {@code null}.
     */
    /* @NonNull */
    final Object getLock()
    {
        return lock_;
    }

    /**
     * Gets the network table associated with the service.
     * 
     * @return The network table associated with the service; never {@code null}
     *         .
     */
    /* @NonNull */
    final NetworkTable getNetworkTable()
    {
        return networkTable_;
    }

    /**
     * Gets the next available message tag for the service.
     * 
     * @return The next available message tag for the service.
     */
    @GuardedBy( "getLock()" )
    final int getNextMessageTag()
    {
        assert Thread.holdsLock( getLock() );

        final int tag = nextTag_;
        if( ++nextTag_ > IMessage.MAX_TAG )
        {
            nextTag_ = IMessage.MIN_TAG;
        }

        return tag;
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

        // TODO: should handle correlation of all response message tags in base class

        try
        {
            final IMessage message = messageEnvelope.getBodyAsMessage();
            synchronized( getLock() )
            {
                if( !messageReceivedInternal( context, message ) )
                {
                    Loggers.getDefaultLogger().warning( Messages.AbstractService_messageReceived_unknownMessage( messageEnvelope ) );
                }
            }
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractService_messageReceived_deserializationError( messageEnvelope ), e );
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractService_messageReceived_deserializationError( messageEnvelope ), e );
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
     */
    @GuardedBy( "getLock()" )
    boolean messageReceivedInternal(
        /* @NonNull */
        final IServiceContext context,
        /* @NonNull */
        final IMessage message )
    {
        assert context != null;
        assert message != null;
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
     */
    @GuardedBy( "getLock()" )
    void peerStoppedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assert context != null;
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
     */
    @GuardedBy( "getLock()" )
    void startedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assert context != null;
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
     */
    @GuardedBy( "getLock()" )
    void stoppedInternal(
        /* @NonNull */
        final IServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        // do nothing
    }
}
