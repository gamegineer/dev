/*
 * Messages.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 8, 2010 at 9:52:13 PM.
 */

package org.gamegineer.table.internal.ui.wizards.joinnetworktable;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

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

    // --- JoinNetworkTableWizard -------------------------------------------

    /** The wizard title. */
    public static String JoinNetworkTableWizard_title;

    // --- MainPage ---------------------------------------------------------

    /** The page description. */
    public static String MainPage_description;

    /** The host name label widget mnemonic. */
    public static String MainPage_hostNameLabel_mnemonic;

    /** The host name label widget text. */
    public static String MainPage_hostNameLabel_text;

    /** The password label widget mnemonic. */
    public static String MainPage_passwordLabel_mnemonic;

    /** The password label widget text. */
    public static String MainPage_passwordLabel_text;

    /** The player name label widget mnemonic. */
    public static String MainPage_playerNameLabel_mnemonic;

    /** The player name label widget text. */
    public static String MainPage_playerNameLabel_text;

    /** The port value is illegal. */
    public static String MainPage_port_illegal;

    /** The port label widget mnemonic. */
    public static String MainPage_portLabel_mnemonic;

    /** The port label widget text. */
    public static String MainPage_portLabel_text;

    /** The page title. */
    public static String MainPage_title;

    // --- Model ------------------------------------------------------------

    /** The host name is empty. */
    public static String Model_hostName_empty;

    /** The player name is empty. */
    public static String Model_playerName_empty;

    /** The port is out of range. */
    public static String Model_port_outOfRange;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }
}
