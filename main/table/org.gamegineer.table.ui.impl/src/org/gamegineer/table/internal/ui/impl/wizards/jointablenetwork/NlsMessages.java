/*
 * NlsMessages.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jul 22, 2011 at 11:36:37 PM.
 */

package org.gamegineer.table.internal.ui.impl.wizards.jointablenetwork;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.net.TableNetworkError;

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

    // --- JoinTableNetworkWizard -------------------------------------------

    /** The connecting task description. */
    public static String JoinTableNetworkWizard_description_connecting;

    /** Could not connect due to an authentication failure. */
    public static String JoinTableNetworkWizard_finish_error_authenticationFailed;

    /** Could not connect due to a duplicate player name. */
    public static String JoinTableNetworkWizard_finish_error_duplicatePlayerName;

    /** Could not connect due to a generic error. */
    public static String JoinTableNetworkWizard_finish_error_generic;

    /** Could not connect due to a transport error. */
    public static String JoinTableNetworkWizard_finish_error_transportError;

    /** Interrupted while waiting for the finish task to complete. */
    public static String JoinTableNetworkWizard_finish_interrupted;

    /** The wizard title. */
    public static String JoinTableNetworkWizard_title;

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
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- JoinTableNetworkWizard -------------------------------------------

    /**
     * Gets the formatted message for the finish task execution error message.
     * 
     * @param error
     *        The error that caused the finish task execution error; must not be
     *        {@code null}.
     * 
     * @return The formatted message for the finish task execution error
     *         message; never {@code null}.
     */
    /* @NonNull */
    static String JoinTableNetworkWizard_finish_error(
        /* @NonNull */
        final TableNetworkError error )
    {
        switch( error )
        {
            case AUTHENTICATION_FAILED:
                return JoinTableNetworkWizard_finish_error_authenticationFailed;

            case DUPLICATE_PLAYER_NAME:
                return JoinTableNetworkWizard_finish_error_duplicatePlayerName;

            case TRANSPORT_ERROR:
                return JoinTableNetworkWizard_finish_error_transportError;

            default:
                return bind( JoinTableNetworkWizard_finish_error_generic, error );
        }
    }
}
