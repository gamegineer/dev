/*
 * TableNetworkExceptionTest.java
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
 * Created on Apr 29, 2011 at 8:52:20 PM.
 */

package org.gamegineer.table.net;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.net.TableNetworkException} class.
 */
public final class TableNetworkExceptionTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkExceptionTest}
     * class.
     */
    public TableNetworkExceptionTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code TableNetworkException(TableNetworkError)} constructor
     * throws an exception when passed a {@code null} error.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromError_Error_Null()
    {
        new TableNetworkException( null );
    }

    /**
     * Ensures the {@code TableNetworkException(TableNetworkError, String)}
     * constructor throws an exception when passed a {@code null} error.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromErrorAndMessage_Error_Null()
    {
        new TableNetworkException( null, "message" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code TableNetworkException(TableNetworkError, Throwable)}
     * constructor throws an exception when passed a {@code null} error.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromErrorAndCause_Error_Null()
    {
        new TableNetworkException( null, new Exception() );
    }

    /**
     * Ensures the {@code TableNetworkException(TableNetworkError, String,
     * Throwable)} constructor throws an exception when passed a {@code null}
     * error.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromErrorAndMessageAndCause_Error_Null()
    {
        new TableNetworkException( null, "message", new Exception() ); //$NON-NLS-1$
    }
}
