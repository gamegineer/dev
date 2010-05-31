/*
 * DebugTest.java
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
 * Created on Feb 29, 2008 at 11:00:58 PM.
 */

package org.gamegineer.common.core.runtime;

import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.common.core.runtime.Debug}
 * class.
 */
public final class DebugTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DebugTest} class.
     */
    public DebugTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code trace(String, String,Exception)} method throws an
     * exception when passed a {@code null} exception.
     */
    @Test( expected = NullPointerException.class )
    public void testTraceMessageAndException_Exception_Null()
    {
        Debug.trace( "option", "message", null ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
