/*
 * LoggersTest.java
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
 * Created on Jun 15, 2010 at 10:37:40 PM.
 */

package org.gamegineer.common.core.runtime;

import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.common.core.runtime.Loggers}
 * class.
 */
public final class LoggersTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggersTest} class.
     */
    public LoggersTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getLogger} method throws an exception when passed a
     * {@code null} bundle.
     */
    @Test( expected = NullPointerException.class )
    public void testGetLogger_Bundle_Null()
    {
        Loggers.getLogger( null, "" ); //$NON-NLS-1$
    }
}
