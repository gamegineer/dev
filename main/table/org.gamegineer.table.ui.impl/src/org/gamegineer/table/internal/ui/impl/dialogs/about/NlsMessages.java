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
 * Created on Mar 30, 2013 at 8:46:31 PM.
 */

package org.gamegineer.table.internal.ui.impl.dialogs.about;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.internal.ui.impl.Branding;

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

    // --- AboutDialog ------------------------------------------------------

    /** The license button mnemonic. */
    public static String AboutDialog_licenseButton_mnemonic;

    /** The license button text. */
    public static String AboutDialog_licenseButton_text;

    /** The message label text. */
    public static String AboutDialog_messageLabel_text;

    /** The dialog title. */
    public static String AboutDialog_title;

    // --- LicenseDialog ----------------------------------------------------

    /** The license text area text. */
    public static String LicenseDialog_licenseTextArea_text;

    /** The dialog title. */
    public static String LicenseDialog_title;


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

    // --- AboutDialog ------------------------------------------------------

    /**
     * Gets the formatted message for the message label text.
     * 
     * @return The formatted message for the message label text; never
     *         {@code null}.
     */
    /* @NonNull */
    static String AboutDialog_messageLabel_text()
    {
        return bind( AboutDialog_messageLabel_text, Branding.getName(), Branding.getVersion() );
    }

    /**
     * Gets the formatted message for the dialog title.
     * 
     * @return The formatted message for the dialog title; never {@code null}.
     */
    /* @NonNull */
    static String AboutDialog_title()
    {
        return bind( AboutDialog_title, Branding.getName() );
    }
}
