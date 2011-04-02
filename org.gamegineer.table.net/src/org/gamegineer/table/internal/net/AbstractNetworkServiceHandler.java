/*
 * AbstractNetworkServiceHandler.java
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

/**
 * Superclass for all implementations of {@link INetworkServiceHandler}.
 */
@ThreadSafe
abstract class AbstractNetworkServiceHandler
    implements INetworkServiceHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instance lock. */
    private final Object lock_;

    /** The network table associated with the service handler. */
    private final NetworkTable networkTable_;

    /** The next available message tag. */
    @GuardedBy( "getLock()" )
    private int nextTag_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkServiceHandler}
     * class.
     * 
     * @param networkTable
     *        The network table associated with the service handler; must not be
     *        {@code null}.
     */
    AbstractNetworkServiceHandler(
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
     * Gets the initial message tag for the service handler.
     * 
     * @return The initial message tag for the service handler.
     */
    private static int getInitialMessageTag()
    {
        final Random rng = new Random( System.currentTimeMillis() );
        return AbstractMessage.MIN_TAG + rng.nextInt( AbstractMessage.MAX_TAG - AbstractMessage.MIN_TAG );
    }

    /**
     * Gets the instance lock for the service handler.
     * 
     * @return The instance lock for the service handler; never {@code null}.
     */
    /* @NonNull */
    final Object getLock()
    {
        return lock_;
    }

    /**
     * Gets the network table associated with the service handler.
     * 
     * @return The network table associated with the service handler; never
     *         {@code null}.
     */
    /* @NonNull */
    final NetworkTable getNetworkTable()
    {
        return networkTable_;
    }

    /**
     * Gets the next available message tag for the service handler.
     * 
     * @return The next available message tag for the service handler.
     */
    @GuardedBy( "getLock()" )
    final int getNextMessageTag()
    {
        assert Thread.holdsLock( getLock() );

        final int tag = nextTag_;
        if( ++nextTag_ > AbstractMessage.MAX_TAG )
        {
            nextTag_ = AbstractMessage.MIN_TAG;
        }

        return tag;
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#messageReceived(org.gamegineer.table.internal.net.INetworkServiceContext, org.gamegineer.table.internal.net.MessageEnvelope)
     */
    @Override
    public final void messageReceived(
        final INetworkServiceContext context,
        final MessageEnvelope messageEnvelope )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( messageEnvelope, "messageEnvelope" ); //$NON-NLS-1$

        // TODO: should handle correlation of all response message tags in base class

        try
        {
            final AbstractMessage message = messageEnvelope.getBodyAsMessage();
            synchronized( getLock() )
            {
                if( !messageReceivedInternal( context, message ) )
                {
                    Loggers.getDefaultLogger().warning( Messages.AbstractNetworkServiceHandler_messageReceived_unknownMessage( messageEnvelope ) );
                }
            }
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractNetworkServiceHandler_messageReceived_deserializationError( messageEnvelope ), e );
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.AbstractNetworkServiceHandler_messageReceived_deserializationError( messageEnvelope ), e );
        }
    }

    /**
     * Invoked when a message has been received from the peer service handler.
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
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return {@code true} if the message was handled by the service handler;
     *         otherwise {@code false}.
     */
    @GuardedBy( "getLock()" )
    boolean messageReceivedInternal(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final AbstractMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#peerStopped(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public final void peerStopped(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            peerStoppedInternal( context );
        }
    }

    /**
     * Invoked when the peer service handler has stopped.
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
     *        The network service context; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void peerStoppedInternal(
        /* @NonNull */
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#started(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public final void started(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            startedInternal( context );
        }
    }

    /**
     * Invoked when the service handler has started.
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
     *        The network service context; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void startedInternal(
        /* @NonNull */
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandler#stopped(org.gamegineer.table.internal.net.INetworkServiceContext)
     */
    @Override
    public final void stopped(
        final INetworkServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            stoppedInternal( context );
        }
    }

    /**
     * Invoked when the service handler has stopped.
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
     *        The network service context; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void stoppedInternal(
        /* @NonNull */
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        // do nothing
    }
}
