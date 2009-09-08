/*
 * SystemDisplay.java
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
 * Created on Sep 27, 2008 at 9:41:57 PM.
 */

package org.gamegineer.client.internal.ui.console.displays;

import java.io.Console;
import java.io.PrintWriter;
import java.io.Reader;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.IDisplay;

/**
 * Implementation of {@link org.gamegineer.client.ui.console.IDisplay} that uses
 * the system console.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class SystemDisplay
    implements IDisplay
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The system console to which all behavior will be delegated. */
    private final Console console_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SystemDisplay} class.
     * 
     * @param console
     *        The system console to which all behavior will be delegated; must
     *        not be {@code null}.
     */
    private SystemDisplay(
        /* @NonNull */
        final Console console )
    {
        assert console != null;

        console_ = console;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code SystemDisplay} class.
     * 
     * @return A new system display or {@code null} if the system console is not
     *         available.
     */
    /* @Nullable */
    public static SystemDisplay createSystemDisplay()
    {
        final Console console = System.console();
        if( console == null )
        {
            return null;
        }

        return new SystemDisplay( console );
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#flush()
     */
    public void flush()
    {
        console_.flush();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#format(java.lang.String, java.lang.Object[])
     */
    public IDisplay format(
        final String format,
        final Object... args )
    {
        console_.format( format, args );
        return this;
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getReader()
     */
    public Reader getReader()
    {
        return console_.reader();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#getWriter()
     */
    public PrintWriter getWriter()
    {
        return console_.writer();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine()
     */
    public String readLine()
    {
        return console_.readLine();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readLine(java.lang.String, java.lang.Object[])
     */
    public String readLine(
        final String format,
        final Object... args )
    {
        return console_.readLine( format, args );
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine()
     */
    public char[] readSecureLine()
    {
        return console_.readPassword();
    }

    /*
     * @see org.gamegineer.client.ui.console.IDisplay#readSecureLine(java.lang.String, java.lang.Object[])
     */
    public char[] readSecureLine(
        final String format,
        final Object... args )
    {
        return console_.readPassword( format, args );
    }
}
