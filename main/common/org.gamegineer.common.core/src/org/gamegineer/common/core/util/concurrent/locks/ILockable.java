/*
 * ILockable.java
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
 * Created on May 27, 2008 at 10:17:14 PM.
 */

package org.gamegineer.common.core.util.concurrent.locks;

import java.util.concurrent.locks.Lock;

/**
 * Indicates an object supports a synchronization lock.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ILockable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the lock for the object.
     * 
     * @return The lock for the object; never {@code null}.
     */
    public Lock getLock();
}
