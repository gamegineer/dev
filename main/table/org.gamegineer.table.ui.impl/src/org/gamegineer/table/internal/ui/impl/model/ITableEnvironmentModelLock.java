/*
 * ITableEnvironmentModelLock.java
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
 * Created on Jun 6, 2013 at 11:47:00 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.concurrent.locks.Lock;

/**
 * A table environment model lock.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableEnvironmentModelLock
    extends Lock
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this lock is held by the current thread.
     * 
     * @return {@code true} if this lock is held by the current thread;
     *         otherwise {@code false}.
     */
    public boolean isHeldByCurrentThread();
}
