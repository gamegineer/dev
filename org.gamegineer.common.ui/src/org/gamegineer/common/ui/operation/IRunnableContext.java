/*
 * IRunnableContext.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Sep 20, 2010 at 10:28:40 PM.
 */

package org.gamegineer.common.ui.operation;

import java.util.concurrent.ExecutionException;

/**
 * A context in which a potentially long-running operation can be executed.
 * 
 * <p>
 * A runnable context is responsible for displaying the progress made by the
 * operation, as well as offering the ability to cancel the operation.
 * </p>
 */
public interface IRunnableContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Runs the specified operation in the context.
     * 
     * @param runnable
     *        The operation to run; must not be {@code null}.
     * @param isCancellable
     *        {@code true} if the operation is cancellable; otherwise {@code
     *        false}.
     * @param fork
     *        {@code true} to run the operation in a separate thread; {@code
     *        false} to run the operation on the current thread.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting for the
     *         operation.
     * @throws java.lang.NullPointerException
     *         If {@code runnable} is {@code null}.
     * @throws java.util.concurrent.CancellationException
     *         If the operation was cancelled.
     * @throws java.util.concurrent.ExecutionException
     *         If the operation threw an exception.
     */
    public void run(
        /* @NonNull */
        IRunnableWithProgress runnable,
        boolean isCancellable,
        boolean fork )
        throws InterruptedException, ExecutionException;
}
