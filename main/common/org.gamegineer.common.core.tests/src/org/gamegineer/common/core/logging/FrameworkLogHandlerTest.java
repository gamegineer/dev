/*
 * FrameworkLogHandlerTest.java
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
 * Created on Jun 11, 2010 at 3:17:05 PM.
 */

package org.gamegineer.common.core.logging;

import org.junit.Test;

/**
 * A fixture for testing the {@link FrameworkLogHandler} class.
 */
public final class FrameworkLogHandlerTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FrameworkLogHandlerTest} class.
     */
    public FrameworkLogHandlerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link FrameworkLogHandler#FrameworkLogHandler} constructor
     * throws an exception when passed a {@code null} framework log.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_FrameworkLog_Null()
    {
        new FrameworkLogHandler( null );
    }
}
