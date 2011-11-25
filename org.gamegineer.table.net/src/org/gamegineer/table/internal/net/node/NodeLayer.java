/*
 * NodeLayer.java
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
 * Created on Nov 24, 2011 at 8:48:31 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;

/**
 * Implementation of {@link INodeLayer}.
 */
@ThreadSafe
final class NodeLayer
    implements INodeLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The node layer executor service. */
    private final ExecutorService executorService_;

    /** A reference to the node layer thread. */
    private final AtomicReference<Thread> nodeLayerThreadRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NodeLayer} class.
     */
    NodeLayer()
    {
        executorService_ = createExecutorService();
        nodeLayerThreadRef_ = new AtomicReference<Thread>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#asyncExec(java.util.concurrent.Callable)
     */
    @Override
    public <T> Future<T> asyncExec(
        final Callable<T> task )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        final String playerName = ThreadPlayer.getPlayerName();
        return executorService_.submit( new Callable<T>()
        {
            @Override
            public T call()
                throws Exception
            {
                ThreadPlayer.setPlayerName( playerName );
                try
                {
                    return task.call();
                }
                finally
                {
                    ThreadPlayer.setPlayerName( null );
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#asyncExec(java.lang.Runnable)
     */
    @Override
    public Future<?> asyncExec(
        final Runnable task )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        final String playerName = ThreadPlayer.getPlayerName();
        return executorService_.submit( new Runnable()
        {
            @Override
            public void run()
            {
                ThreadPlayer.setPlayerName( playerName );
                try
                {
                    task.run();
                }
                finally
                {
                    ThreadPlayer.setPlayerName( null );
                }
            }
        } );
    }

    /**
     * Creates the node layer executor service.
     * 
     * @return The node layer executor service; never {@code null}.
     */
    /* @NonNull */
    private ExecutorService createExecutorService()
    {
        return Executors.newSingleThreadExecutor( new ThreadFactory()
        {
            @Override
            public Thread newThread(
                final Runnable r )
            {
                return new Thread( r, NonNlsMessages.NodeLayer_thread_name )
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void run()
                    {
                        nodeLayerThreadRef_.set( Thread.currentThread() );
                        try
                        {
                            super.run();
                        }
                        finally
                        {
                            nodeLayerThreadRef_.set( null );
                        }
                    }
                };
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#dispose()
     */
    @Override
    public void dispose()
    {
        executorService_.shutdown();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#isNodeLayerThread()
     */
    @Override
    public boolean isNodeLayerThread()
    {
        return Thread.currentThread() == nodeLayerThreadRef_.get();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#syncExec(java.util.concurrent.Callable)
     */
    @Override
    public <T> T syncExec(
        final Callable<T> task )
        throws ExecutionException, InterruptedException
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        if( isNodeLayerThread() )
        {
            try
            {
                return task.call();
            }
            catch( final Exception e )
            {
                throw new ExecutionException( e );
            }
        }

        return asyncExec( task ).get();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeLayer#syncExec(java.lang.Runnable)
     */
    @Override
    public void syncExec(
        final Runnable task )
        throws ExecutionException, InterruptedException
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        if( isNodeLayerThread() )
        {
            try
            {
                task.run();
            }
            catch( final Exception e )
            {
                throw new ExecutionException( e );
            }
        }
        else
        {
            asyncExec( task ).get();
        }
    }
}
