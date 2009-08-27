/*
 * IDisplay.java
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
 * Created on Sep 26, 2008 at 11:15:56 PM.
 */

package org.gamegineer.client.ui.console;

import java.io.Flushable;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * Represents a character-based console display device.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IDisplay
    extends Flushable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Flushes the display and forces any buffered output to be written
     * immediately.
     */
    public void flush();

    /**
     * Writes a formatted string to this display's output stream using the
     * specified format string and arguments.
     * 
     * @param format
     *        A format string; must not be {@code null}.
     * @param args
     *        The arguments referenced by the format specifiers in the format
     *        string; may be {@code null}.
     * 
     * @return This display; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code format} is {@code null}.
     * @throws java.util.IllegalFormatException
     *         If the format string contains an illegal syntax, a format
     *         specifier is incompatible with the given arguments, insufficient
     *         arguments were received for the given format string, or other
     *         illegal conditions.
     */
    /* @NonNull */
    public IDisplay format(
        /* @NonNull */
        String format,
        /* @Nullable */
        Object... args );

    /**
     * Gets the unique reader associated with this display.
     * 
     * @return The unique reader associated with this display; never
     *         {@code null}.
     */
    /* @NonNull */
    public Reader getReader();

    /**
     * Gets the unique print writer associated with this display.
     * 
     * @return The unique print writer associated with this display; never
     *         {@code null}.
     */
    /* @NonNull */
    public PrintWriter getWriter();

    /**
     * Reads a single line of text from this display.
     * 
     * @return A string containing the line read from this display not including
     *         any line-termination characters, or {@code null} if the end of
     *         stream has been reached.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     */
    /* @Nullable */
    public String readLine();

    /**
     * Displays a formatted prompt, then reads a single line of text from this
     * display.
     * 
     * @param format
     *        A format string; must not be {@code null}.
     * @param args
     *        The arguments referenced by the format specifiers in the format
     *        string; may be {@code null}.
     * 
     * @return A string containing the line read from this display not including
     *         any line-termination characters, or {@code null} if the end of
     *         stream has been reached.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code format} is {@code null}.
     * @throws java.util.IllegalFormatException
     *         If the format string contains an illegal syntax, a format
     *         specifier is incompatible with the given arguments, insufficient
     *         arguments were received for the given format string, or other
     *         illegal conditions.
     */
    /* @Nullable */
    public String readLine(
        /* @NonNull */
        String format,
        /* @Nullable */
        Object... args );

    /**
     * Reads a single line of text from this display with echoing disabled.
     * 
     * @return A character array containing the line read from this display not
     *         including any line-termination characters, or {@code null} if the
     *         end of stream has been reached.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     */
    /* @Nullable */
    public char[] readSecureLine();

    /**
     * Displays a formatted prompt, then reads a single line of text from this
     * display with echoing disabled.
     * 
     * @param format
     *        A format string; must not be {@code null}.
     * @param args
     *        The arguments referenced by the format specifiers in the format
     *        string; may be {@code null}.
     * 
     * @return A character array containing the line read from this display not
     *         including any line-termination characters, or {@code null} if the
     *         end of stream has been reached.
     * 
     * @throws java.io.IOError
     *         If an I/O error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code format} is {@code null}.
     * @throws java.util.IllegalFormatException
     *         If the format string contains an illegal syntax, a format
     *         specifier is incompatible with the given arguments, insufficient
     *         arguments were received for the given format string, or other
     *         illegal conditions.
     */
    /* @Nullable */
    public char[] readSecureLine(
        /* @NonNull */
        String format,
        /* @Nullable */
        Object... args );
}
