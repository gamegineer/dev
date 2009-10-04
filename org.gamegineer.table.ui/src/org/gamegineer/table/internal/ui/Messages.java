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
 * Created on Sep 19, 2009 at 12:12:47 AM.
 */

package org.gamegineer.table.internal.ui;

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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.Messages"; //$NON-NLS-1$

    // --- Messages ---------------------------------------------------------

    /** The virtual key name is unknown. */
    public static String Messages_toMnemonic_unknownVirtualKeyName;


    // --- TableFrame -------------------------------------------------------

    /** The About box message. */
    public static String TableFrame_about_message;

    /** The About box title. */
    public static String TableFrame_about_title;

    /** The File menu mnemonic. */
    public static String TableFrame_menu_file_mnemonic;

    /** The File menu text. */
    public static String TableFrame_menu_file_text;

    /** The File-Exit menu item mnemonic. */
    public static String TableFrame_menu_file_exit_mnemonic;

    /** The File-Exit menu item text. */
    public static String TableFrame_menu_file_exit_text;

    /** The Help menu mnemonic. */
    public static String TableFrame_menu_help_mnemonic;

    /** The Help menu text. */
    public static String TableFrame_menu_help_text;

    /** The Help-About menu item mnemonic. */
    public static String TableFrame_menu_help_about_mnemonic;

    /** The Help-About menu item text. */
    public static String TableFrame_menu_help_about_text;

    /** The frame title. */
    public static String TableFrame_title;

    // --- TableFrameRunner -------------------------------------------------

    /** The frame window could not be closed. */
    public static String TableFrameRunner_closeFrameWorker_error;

    /** The frame window could not be created. */
    public static String TableFrameRunner_createAndShowFrameWorker_error;

    /** The runner is already running or has already finished. */
    public static String TableFrameRunner_state_notPristine;


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

    /**
     * Gets the formatted message for the About box message.
     * 
     * @param version
     *        The application version; must not be {@code null}.
     * 
     * @return The formatted message for the About box message; never {@code
     *         null}.
     */
    /* @NonNull */
    static String TableFrame_about_message(
        /* @NonNull */
        final Version version )
    {
        return bind( TableFrame_about_message, version.toString() );
    }

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
