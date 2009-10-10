/*
 * Messages.java
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
 * Created on Oct 8, 2009 at 11:37:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.event.KeyEvent;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Version;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.view.Messages"; //$NON-NLS-1$

    // --- AboutDialog ------------------------------------------------------

    /** The About dialog message. */
    public static String AboutDialog_message;

    /** The About dialog title. */
    public static String AboutDialog_title;

    // --- ExitAction -------------------------------------------------------

    /** The exit action mnemonic. */
    public static String ExitAction_mnemonic;

    /** The exit action text. */
    public static String ExitAction_text;

    // --- MainFrame --------------------------------------------------------

    /** The frame title. */
    public static String MainFrame_title;

    // --- MenuBarView ------------------------------------------------------

    /** The File menu mnemonic. */
    public static String MenuBarView_file_mnemonic;

    /** The File menu text. */
    public static String MenuBarView_file_text;

    /** The Help menu mnemonic. */
    public static String MenuBarView_help_mnemonic;

    /** The Help menu text. */
    public static String MenuBarView_help_text;

    // --- Messages ---------------------------------------------------------

    /** The virtual key name is unknown. */
    public static String Messages_toMnemonic_unknownVirtualKeyName;

    // --- OpenAboutDialogAction --------------------------------------------

    /** The open about dialog action mnemonic. */
    public static String OpenAboutDialogAction_mnemonic;

    /** The open about dialog action text. */
    public static String OpenAboutDialogAction_text;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- AboutDialog ------------------------------------------------------

    /**
     * Gets the formatted message for the About dialog message.
     * 
     * @param version
     *        The application version; must not be {@code null}.
     * 
     * @return The formatted message for the About dialog message; never {@code
     *         null}.
     */
    /* @NonNull */
    static String AboutDialog_message(
        /* @NonNull */
        final Version version )
    {
        return bind( AboutDialog_message, version.toString() );
    }

    // --- Messages ---------------------------------------------------------

    /**
     * Converts the specified virtual key name to its corresponding virtual key
     * code.
     * 
     * @param virtualKeyName
     *        The name of a virtual key code constant from the {@code
     *        java.awt.event.KeyEvent} class, not including the {@code VK_}
     *        prefix.
     * 
     * @return The virtual key code associated with the specified virtual key
     *         name.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the virtual key name is unknown.
     */
    static int toMnemonic(
        /* @NonNull */
        final String virtualKeyName )
    {
        final String fieldName = "VK_" + virtualKeyName; //$NON-NLS-1$
        try
        {
            return KeyEvent.class.getField( fieldName ).getInt( null );
        }
        catch( final NoSuchFieldException e )
        {
            throw new IllegalArgumentException( bind( Messages_toMnemonic_unknownVirtualKeyName, fieldName ), e );
        }
        catch( final IllegalAccessException e )
        {
            throw new IllegalArgumentException( bind( Messages_toMnemonic_unknownVirtualKeyName, fieldName ), e );
        }
    }
}
