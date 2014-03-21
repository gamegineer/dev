/*
 * LockUtils.java
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
 * Created on Aug 30, 2012 at 9:00:45 PM.
 */

package org.gamegineer.common.core.util.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of methods useful for working with locks.
 */
@ThreadSafe
public final class LockUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LockUtils} class.
     */
    private LockUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified lock is held by the current thread.
     * 
     * @param lock
     *        The lock; must not be {@code null}.
     * 
     * @return {@code true} if the specified lock is held by the current thread;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code lock} does not support an interface for determining if
     *         it is held by the current thread.
     */
    public static boolean isHeldByCurrentThread(
        final Lock lock )
    {
        if( lock instanceof ReentrantLock )
        {
            return ((ReentrantLock)lock).isHeldByCurrentThread();
        }

        throw new IllegalArgumentException( NonNlsMessages.LockUtils_isLockHeldByCurrentThread_unsupportedLockType );
    }
}
