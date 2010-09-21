/*
 * IRunnableWithProgress.java
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
 * Created on Sep 20, 2010 at 10:31:02 PM.
 */

package org.gamegineer.common.ui.operation;

import java.util.concurrent.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A potentially long-running operation that can report its progress and be
 * cancelled.
 */
public interface IRunnableWithProgress
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Runs the operation.
     * 
     * @param progressMonitor
     *        The progress monitor used to display progress and receive
     *        cancellation requests; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting for the
     *         operation.
     * @throws java.lang.NullPointerException
     *         If {@code progressMonitor} is {@code null}.
     * @throws java.util.concurrent.CancellationException
     *         If the operation was cancelled.
     * @throws java.util.concurrent.ExecutionException
     *         If the operation threw an exception.
     */
    public void run(
        /* @NonNull */
        IProgressMonitor progressMonitor )
        throws InterruptedException, ExecutionException;
}
