/*
 * DebugTest.java
 * Copyright 2008 Gamegineer.org
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
     * Ensures the {@code trace(Exception)} method throws an exception if the
     * exception is {@code null}.
     */
    @Test( expected = NullPointerException.class )
    public void testTraceException_Exception_Null()
    {
        final Exception exception = null;
        Debug.trace( exception );
    }

    /**
     * Ensures the {@code trace(String)} method does not throw an exception if
     * the message is {@code null}.
     */
    @Test
    public void testTraceMessage_Message_Null()
    {
        final String message = null;
        Debug.trace( message );
    }

    /**
     * Ensures the {@code trace(String,Exception)} method throws an exception if
     * the exception is {@code null}.
     */
    @Test( expected = NullPointerException.class )
    public void testTraceMessageAndException_Exception_Null()
    {
        Debug.trace( "message", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code trace(String,Exception)} method does not throw an
     * exception if the message is {@code null}.
     */
    @Test
    public void testTraceMessageAndException_Message_Null()
    {
        Debug.trace( null, new Exception() );
    }
}
