/*
 * TaskUtilsTest.java
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
 * Created on Jun 10, 2008 at 10:16:37 PM.
 */

package org.gamegineer.common.core.util.concurrent;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of the
 * {@link org.gamegineer.common.core.util.concurrent.TaskUtils} class.
 */
public final class TaskUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TaskUtilsTest} class.
     */
    public TaskUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link TaskUtils#launderThrowable} method throws an exception
     * when passed a checked exception.
     */
    @Test( expected = IllegalStateException.class )
    public void testLaunderThrowable_Throwable_Checked()
    {
        TaskUtils.launderThrowable( new InstantiationException() );
    }

    /**
     * Ensures the {@link TaskUtils#launderThrowable} method throws an exception
     * when passed an error.
     */
    @Test( expected = Error.class )
    public void testLaunderThrowable_Throwable_Error()
    {
        TaskUtils.launderThrowable( new AssertionError() );
    }

    /**
     * Ensures the {@link TaskUtils#launderThrowable} method throws an exception
     * when passed a {@code null} cause.
     */
    @Test( expected = IllegalStateException.class )
    public void testLaunderThrowable_Throwable_Null()
    {
        TaskUtils.launderThrowable( null );
    }

    /**
     * Ensures the {@link TaskUtils#launderThrowable} method returns the correct
     * value when passed an unchecked exception.
     */
    @Test
    public void testLaunderThrowable_Throwable_Unchecked()
    {
        assertTrue( TaskUtils.launderThrowable( new UnsupportedOperationException() ) instanceof UnsupportedOperationException );
    }
}
