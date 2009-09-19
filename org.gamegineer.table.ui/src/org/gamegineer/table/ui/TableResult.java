/*
 * TableResult.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 18, 2009 at 9:22:38 PM.
 */

package org.gamegineer.table.ui;

/**
 * Enumerates the possible table results.
 */
public enum TableResult
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /** Indicates the table terminated normally. */
    OK( 0 ),

    /** Indicates the table terminated with an error. */
    FAIL( 1 );


    // ======================================================================
    // Fields
    // ======================================================================

    /** The exit code associated with this result. */
    private final int exitCode_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableResult} class.
     * 
     * @param exitCode
     *        The exit code associated with this result.
     */
    private TableResult(
        final int exitCode )
    {
        exitCode_ = exitCode;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the exit code associated with this result.
     * 
     * <p>
     * The returned exit code is intended to be passed to the
     * {@link System#exit(int)} method.
     * </p>
     * 
     * @return The exit code associated with this result.
     */
    public int getExitCode()
    {
        return exitCode_;
    }
}
