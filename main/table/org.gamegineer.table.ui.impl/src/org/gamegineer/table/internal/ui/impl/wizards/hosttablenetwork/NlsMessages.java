/*
 * NlsMessages.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jul 22, 2011 at 11:30:42 PM.
 */

package org.gamegineer.table.internal.ui.impl.wizards.hosttablenetwork;

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

    // --- HostTableNetworkWizard -------------------------------------------

    /** The connecting task description. */
    public static String HostTableNetworkWizard_description_connecting = ""; //$NON-NLS-1$

    /** Could not connect due to a generic error. */
    public static String HostTableNetworkWizard_finish_error_generic = ""; //$NON-NLS-1$

    /** Could not connect due to a transport error. */
    public static String HostTableNetworkWizard_finish_error_transportError = ""; //$NON-NLS-1$

    /** Interrupted while waiting for the finish task to complete. */
    public static String HostTableNetworkWizard_finish_interrupted = ""; //$NON-NLS-1$

    /** The wizard title. */
    public static String HostTableNetworkWizard_title = ""; //$NON-NLS-1$

    // --- MainPage ---------------------------------------------------------

    /** The confirm password label widget mnemonic. */
    public static String MainPage_confirmPasswordLabel_mnemonic = ""; //$NON-NLS-1$

    /** The confirm password label widget text. */
    public static String MainPage_confirmPasswordLabel_text = ""; //$NON-NLS-1$

    /** The page description. */
    public static String MainPage_description = ""; //$NON-NLS-1$

    /** The password label widget mnemonic. */
    public static String MainPage_passwordLabel_mnemonic = ""; //$NON-NLS-1$

    /** The password label widget text. */
    public static String MainPage_passwordLabel_text = ""; //$NON-NLS-1$

    /** The player name label widget mnemonic. */
    public static String MainPage_playerNameLabel_mnemonic = ""; //$NON-NLS-1$

    /** The player name label widget text. */
    public static String MainPage_playerNameLabel_text = ""; //$NON-NLS-1$

    /** The port value is illegal. */
    public static String MainPage_port_illegal = ""; //$NON-NLS-1$

    /** The port label widget mnemonic. */
    public static String MainPage_portLabel_mnemonic = ""; //$NON-NLS-1$

    /** The port label widget text. */
    public static String MainPage_portLabel_text = ""; //$NON-NLS-1$

    /** The page title. */
    public static String MainPage_title = ""; //$NON-NLS-1$

    // --- Model ------------------------------------------------------------

    /** The passwords do not match. */
    public static String Model_password_unconfirmed = ""; //$NON-NLS-1$

    /** The player name is empty. */
    public static String Model_playerName_empty = ""; //$NON-NLS-1$

    /** The port is out of range. */
    public static String Model_port_outOfRange = ""; //$NON-NLS-1$


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

    // --- HostTableNetworkWizard -------------------------------------------

    /**
     * Gets the formatted message for the finish task execution error message.
     * 
     * @param error
     *        The error that caused the finish task execution error.
     * 
     * @return The formatted message for the finish task execution error
     *         message.
     */
    static String HostTableNetworkWizard_finish_error(
        final TableNetworkError error )
    {
        switch( error )
        {
            case TRANSPORT_ERROR:
                return HostTableNetworkWizard_finish_error_transportError;

            default:
                return bind( HostTableNetworkWizard_finish_error_generic, error );
        }
    }
}
