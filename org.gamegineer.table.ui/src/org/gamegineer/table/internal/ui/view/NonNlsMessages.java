/*
 * NonNlsMessages.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Oct 8, 2009 at 11:37:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.io.File;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- Cursors ----------------------------------------------------------

    /** Failed to create the system invalid cursor. */
    public static String Cursors_createInvalidCursor_failed;

    /** The name of the grab cursor. */
    public static String Cursors_grab_name;

    /** The name of the hand cursor. */
    public static String Cursors_hand_name;

    // --- MainFrame --------------------------------------------------------

    /** An error occurred while opening the table. */
    public static String MainFrame_openTable_error;

    /** An error occurred while saving the table. */
    public static String MainFrame_saveTable_error;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- MainFrame --------------------------------------------------------

    /**
     * Gets the formatted message that indicates an error occurred while opening
     * the table.
     * 
     * @param file
     *        The table file; must not be {@code null}.
     * 
     * @return The formatted message that indicates an error occurred while
     *         opening the table; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_openTable_error(
        /* @NonNull */
        final File file )
    {
        return bind( MainFrame_openTable_error, file.getAbsolutePath() );
    }

    /**
     * Gets the formatted message that indicates an error occurred while saving
     * the table.
     * 
     * @param file
     *        The table file; must not be {@code null}.
     * 
     * @return The formatted message that indicates an error occurred while
     *         saving the table; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_saveTable_error(
        /* @NonNull */
        final File file )
    {
        return bind( MainFrame_saveTable_error, file.getAbsolutePath() );
    }
}
