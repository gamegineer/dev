/*
 * NodeLayerRunner.java
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
 * Created on Nov 5, 2011 at 8:10:26 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.net.ITableNetworkConfiguration;

/**
 * Ensures a block of code is executed on a node layer thread.
 */
@ThreadSafe
public final class NodeLayerRunner
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node that manages the node layer. */
    private final AbstractNode<?> node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NodeLayerRunner} class.
     * 
     * @param node
     *        The node that manages the node layer; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    public NodeLayerRunner(
        /* @NonNull */
        final AbstractNode<?> node )
    {
        assertArgumentNotNull( node, "node" ); //$NON-NLS-1$

        node_ = node;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Synchronously connects the associated table network node to the table
     * network using the specified configuration.
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     */
    public void connect(
        /* @NonNull */
        final ITableNetworkConfiguration configuration )
        throws Exception
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        final Future<Void> future = run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return node_.beginConnect( configuration );
            }
        } );

        node_.endConnect( future );
    }

    /**
     * Synchronously disconnects the associated table network node from the
     * table network.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void disconnect()
        throws Exception
    {
        final Future<Void> future = run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return node_.beginDisconnect();
            }
        } );

        node_.endDisconnect( future );
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting no checked exceptions will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be {@code
     *        null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     */
    /* @Nullable */
    public <V> V run(
        /* @NonNull */
        final Callable<V> task )
        throws Exception
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        return run( task, Collections.<Class<? extends Exception>>emptyList() );
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting the specified checked exception will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be {@code
     *        null}.
     * @param exceptionType
     *        The checked exception expected type expected to be thrown; must
     *        not be {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code task} or {@code exceptionType} is {@code null}.
     */
    /* @Nullable */
    public <V> V run(
        /* @NonNull */
        final Callable<V> task,
        /* @NonNull */
        final Class<? extends Exception> exceptionType )
        throws Exception
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$
        assertArgumentNotNull( exceptionType, "exceptionType" ); //$NON-NLS-1$

        return run( task, Collections.<Class<? extends Exception>>singletonList( exceptionType ) );
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting any of the specified checked exceptions will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be {@code
     *        null}.
     * @param exceptionTypes
     *        The collection of checked exception types expected to be thrown;
     *        must not be {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.IllegalArgumentException
     *         If {@code exceptionTypes} contains a {@code null} element.
     * @throws java.lang.NullPointerException
     *         If {@code task} or {@code exceptionTypes} is {@code null}.
     */
    /* @Nullable */
    public <V> V run(
        /* @NonNull */
        final Callable<V> task,
        /* @NonNull */
        final Collection<Class<? extends Exception>> exceptionTypes )
        throws Exception
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$
        assertArgumentNotNull( exceptionTypes, "exceptionTypes" ); //$NON-NLS-1$
        assertArgumentLegal( !exceptionTypes.contains( null ), "exceptionTypes" ); //$NON-NLS-1$

        try
        {
            return node_.getNodeLayer().syncExec( task );
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            for( final Class<? extends Exception> exceptionType : exceptionTypes )
            {
                if( exceptionType == cause.getClass() )
                {
                    throw exceptionType.cast( cause );
                }
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting no result and no checked exceptions will be thrown.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be {@code
     *        null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     */
    /* @Nullable */
    public void run(
        /* @NonNull */
        final Runnable task )
        throws Exception
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        try
        {
            node_.getNodeLayer().syncExec( task );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }
}
