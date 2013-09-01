/*
 * IReadWriteLockable.java
 * Copyright 2008-2009 Gamegineer contributors and others.
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
 * Created on May 27, 2008 at 10:17:22 PM.
 */

package org.gamegineer.common.core.util.concurrent.locks;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Indicates an object supports a synchronization lock for read operations and a
 * separate synchronization lock for write operations.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IReadWriteLockable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the read-write lock for the object.
     * 
     * @return The read-write lock for the object; never {@code null}.
     */
    /* @NonNull */
    public ReadWriteLock getReadWriteLock();
}
