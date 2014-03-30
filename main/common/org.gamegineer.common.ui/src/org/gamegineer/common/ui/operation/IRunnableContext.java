/*
 * IRunnableContext.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
     * Executes the specified task asynchronously.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     */
    public void executeTask(
        RunnableTask<?, ?> task );
}
