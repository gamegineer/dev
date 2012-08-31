/*
 * LockUtilsTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 30, 2012 at 9:04:20 PM.
 */

package org.gamegineer.common.core.util.concurrent.locks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of the
 * {@link org.gamegineer.common.core.util.concurrent.locks.LockUtils} class.
 */
public final class LockUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LockUtilsTest} class.
     */
    public LockUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code isHeldByCurrentThread} method throws an exception when
     * passed an illegal lock.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testIsHeldByCurrentThread_Lock_Illegal()
    {
        LockUtils.isHeldByCurrentThread( EasyMock.createMock( Lock.class ) );
    }

    /**
     * Ensures the {@code isHeldByCurrentThread} method throws an exception when
     * passed a {@code null} lock.
     */
    @Test( expected = NullPointerException.class )
    public void testIsHeldByCurrentThread_Lock_Null()
    {
        LockUtils.isHeldByCurrentThread( null );
    }

    /**
     * Ensures the {@code isHeldByCurrentThread} method correctly indicates a
     * {@link ReentrantLock} is held by the current thread.
     */
    @Test
    public void testIsHeldByCurrentThread_ReentrantLock_Held()
    {
        final Lock lock = new ReentrantLock();

        lock.lock();
        try
        {
            assertTrue( LockUtils.isHeldByCurrentThread( lock ) );
        }
        finally
        {
            lock.unlock();
        }
    }

    /**
     * Ensures the {@code isHeldByCurrentThread} method correctly indicates a
     * {@link ReentrantLock} is not held by the current thread.
     */
    @Test
    public void testIsHeldByCurrentThread_ReentrantLock_NotHeld()
    {
        final Lock lock = new ReentrantLock();

        assertFalse( LockUtils.isHeldByCurrentThread( lock ) );
    }
}
