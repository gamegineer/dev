/*
 * NlsMessages.java
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
 * Created on Jul 22, 2011 at 11:09:26 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import java.io.File;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class NlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- JFileChooser -----------------------------------------------------

    /** The Confirm Overwrite File dialog message. */
    public static String JFileChooser_confirmOverwriteFile_message;

    /** The Confirm Overwrite File dialog title. */
    public static String JFileChooser_confirmOverwriteFile_title;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NlsMessages.class.getName(), NlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NlsMessages} class.
     */
    private NlsMessages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- JFileChooser -----------------------------------------------------

    /**
     * Gets the formatted message for the Confirm Overwrite File dialog message.
     * 
     * @param file
     *        The file to be overwritten; must not be {@code null}.
     * 
     * @return The formatted message for the Confirm Overwrite File dialog
     *         message; never {@code null}.
     */
    /* @NonNull */
    static String JFileChooser_confirmOverwriteFile_message(
        /* @NonNull */
        final File file )
    {
        return bind( JFileChooser_confirmOverwriteFile_message, file.getName() );
    }
}
