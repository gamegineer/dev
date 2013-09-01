/*
 * ITableEnvironmentContext.java
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
 * Created on May 25, 2013 at 8:58:11 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.GuardedBy;

/**
 * The execution context for a table environment.
 * 
 * <p>
 * Provides operations that allow a table environment to interact with the
 * application.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableEnvironmentContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Fires the specified event notification according to the policy of this
     * context.
     * 
     * <p>
     * Implementors must ensure event notifications are executed in the same
     * order in which they were submitted. No attempt must be made to run them
     * in parallel.
     * </p>
     * 
     * @param eventNotification
     *        The event notification; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this method is not invoked while the table environment lock is
     *         held.
     * @throws java.lang.NullPointerException
     *         If {@code eventNotification} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public void fireEventNotification(
        /* @NonNull */
        Runnable eventNotification );

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    public ITableEnvironmentLock getLock();
}
