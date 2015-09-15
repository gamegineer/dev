/*
 * NodeLayerRunner.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.net.TableNetworkConfiguration;

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
    private final AbstractNode<@NonNull ?> node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NodeLayerRunner} class.
     * 
     * @param node
     *        The node that manages the node layer; must not be {@code null}.
     */
    public NodeLayerRunner(
        final AbstractNode<@NonNull ?> node )
    {
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
     */
    public void connect(
        final TableNetworkConfiguration configuration )
        throws Exception
    {
        final Future<@Nullable Void> future = run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<@Nullable Void> call()
            {
                return node_.beginConnect( configuration );
            }
        } );
        assert future != null;

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
        final Future<@Nullable Void> future = run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<@Nullable Void> call()
            {
                return node_.beginDisconnect();
            }
        } );
        assert future != null;

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
     *        The task to execute on the node layer thread; must not be
     *        {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Nullable
    public <V> V run(
        final Callable<V> task )
        throws Exception
    {
        return run( task, nonNull( Collections.<Class<? extends Exception>>emptyList() ) );
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting the specified checked exception will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be
     *        {@code null}.
     * @param exceptionType
     *        The checked exception expected type expected to be thrown; must
     *        not be {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Nullable
    public <V> V run(
        final Callable<V> task,
        final Class<? extends Exception> exceptionType )
        throws Exception
    {
        return run( task, nonNull( Collections.<Class<? extends Exception>>singletonList( exceptionType ) ) );
    }

    /**
     * Synchronously executes the specified task on the node layer thread
     * expecting any of the specified checked exceptions will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the node layer thread; must not be
     *        {@code null}.
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
     */
    @Nullable
    public <V> V run(
        final Callable<V> task,
        final Collection<Class<? extends Exception>> exceptionTypes )
        throws Exception
    {
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
     *        The task to execute on the node layer thread; must not be
     *        {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void run(
        final Runnable task )
        throws Exception
    {
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
