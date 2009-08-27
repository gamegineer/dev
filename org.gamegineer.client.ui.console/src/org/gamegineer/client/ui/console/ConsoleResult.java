/*
 * ConsoleResult.java
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
 * Created on Oct 10, 2008 at 10:57:07 PM.
 */

package org.gamegineer.client.ui.console;

/**
 * Enumerates the possible console results.
 */
public enum ConsoleResult
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /** Indicates the console terminated normally. */
    OK( 0 ),

    /** Indicates the console terminated with an error. */
    FAIL( 1 );


    // ======================================================================
    // Fields
    // ======================================================================

    /** The exit code associated with this result. */
    private final int m_exitCode;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleResult} class.
     * 
     * @param exitCode
     *        The exit code associated with this result.
     */
    private ConsoleResult(
        final int exitCode )
    {
        m_exitCode = exitCode;
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
        return m_exitCode;
    }
}
