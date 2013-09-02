/*
 * INodeLayer.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Nov 17, 2011 at 10:21:09 PM.
 */

package org.gamegineer.table.internal.net.node;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The node layer.
 * 
 * <p>
 * All the methods of this interface may be called from any thread.
 * </p>
 */
public interface INodeLayer
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asynchronously executes the specified task on the node layer thread.
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the task; never {@code null}
     *         .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    /* @NonNull */
    public <T> Future<T> asyncExec(
        /* @NonNull */
        Callable<T> task );

    /**
     * Asynchronously executes the specified task on the node layer thread.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the task; never {@code null}
     *         .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    /* @NonNull */
    public Future<?> asyncExec(
        /* @NonNull */
        Runnable task );

    /**
     * Disposes of the resources managed by the node layer.
     */
    public void dispose();

    /**
     * Indicates the current thread is the node layer thread.
     * 
     * @return {@code true} if the current thread is the node layer thread;
     *         otherwise {@code false}.
     */
    public boolean isNodeLayerThread();

    /**
     * Indicates the specified thread is the node layer thread.
     * 
     * @param thread
     *        The thread; must not be {@code null}.
     * 
     * @return {@code true} if the specified thread is the node layer thread;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code thread} is {@code null}.
     */
    public boolean isNodeLayerThread(
        /* @NonNull */
        Thread thread );

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return The return value of the task; may be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    /* @Nullable */
    public <T> T syncExec(
        /* @NonNull */
        Callable<T> task )
        throws ExecutionException, InterruptedException;

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.lang.NullPointerException
     *         If {@code task} is {@code null}.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    public void syncExec(
        /* @NonNull */
        Runnable task )
        throws ExecutionException, InterruptedException;
}
