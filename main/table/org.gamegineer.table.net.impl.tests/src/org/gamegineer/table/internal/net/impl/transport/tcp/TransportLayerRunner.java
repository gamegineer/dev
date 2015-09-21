/*
 * TransportLayerRunner.java
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
 * Created on Oct 14, 2011 at 7:48:15 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.TaskUtils;

/**
 * Ensures a block of code is executed on a transport layer thread.
 * 
 * <p>
 * All methods of this class are expected to not be called on the transport
 * layer thread.
 * </p>
 */
@ThreadSafe
final class TransportLayerRunner
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer. */
    private final AbstractTransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TransportLayerRunner} class.
     * 
     * @param transportLayer
     *        The transport layer; must not be {@code null}.
     */
    TransportLayerRunner(
        final AbstractTransportLayer transportLayer )
    {
        transportLayer_ = transportLayer;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Synchronously closes the associated transport layer.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    void close()
        throws Exception
    {
        final Future<@Nullable Void> future = run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<@Nullable Void> call()
            {
                return transportLayer_.beginClose();
            }
        } );
        assert future != null;

        transportLayer_.endClose( future );
    }

    /**
     * Synchronously opens the associated transport layer.
     * 
     * @param hostName
     *        The host name; must not be {@code null}.
     * @param port
     *        The port.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    void open(
        final String hostName,
        final int port )
        throws Exception
    {
        final Future<@Nullable Void> future = run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<@Nullable Void> call()
            {
                return transportLayer_.beginOpen( hostName, port );
            }
        } );
        assert future != null;

        transportLayer_.endOpen( future );
    }

    /**
     * Synchronously executes the specified task on the transport layer thread
     * expecting no checked exceptions will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the transport layer thread; must not be
     *        {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    <V> V run(
        final Callable<V> task )
        throws Exception
    {
        return run( task, Collections.<@NonNull Class<? extends Exception>>emptyList() );
    }

    /**
     * Synchronously executes the specified task on the transport layer thread
     * expecting the specified checked exception will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the transport layer thread; must not be
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
    <V> V run(
        final Callable<V> task,
        final Class<? extends Exception> exceptionType )
        throws Exception
    {
        return run( task, Collections.singletonList( exceptionType ) );
    }

    /**
     * Synchronously executes the specified task on the transport layer thread
     * expecting any of the specified checked exceptions will be thrown.
     * 
     * @param <V>
     *        The type of the task result.
     * 
     * @param task
     *        The task to execute on the transport layer thread; must not be
     *        {@code null}.
     * @param exceptionTypes
     *        The collection of checked exception types expected to be thrown;
     *        must not be {@code null}.
     * 
     * @return The task result; may be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    <V> V run(
        final Callable<V> task,
        final Collection<Class<? extends Exception>> exceptionTypes )
        throws Exception
    {
        try
        {
            return transportLayer_.syncExec( task );
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
}
