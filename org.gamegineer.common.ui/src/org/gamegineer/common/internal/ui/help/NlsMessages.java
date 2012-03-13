/*
 * NlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 21, 2012 at 10:20:31 PM.
 */

package org.gamegineer.common.internal.ui.help;

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

    // --- HelpSystem -------------------------------------------------------

    /** The master help set title. */
    public static String HelpSystem_masterHelpSet_title;


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

    // --- HelpSystem -------------------------------------------------------

    /**
     * Gets the formatted message for the master help set title.
     * 
     * @param name
     *        The application name; must not be {@code null}.
     * 
     * @return The formatted message for the master help set title; never
     *         {@code null}.
     */
    /* @NonNull */
    static String HelpSystem_masterHelpSet_title(
        /* @NonNull */
        final String name )
    {
        return bind( HelpSystem_masterHelpSet_title, name );
    }
}
