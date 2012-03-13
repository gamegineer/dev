/*
 * NonNlsMessages.java
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
 * Created on Jan 6, 2012 at 10:30:50 PM.
 */

package org.gamegineer.common.internal.ui.help;

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

    // --- HelpSetProviderProxy ---------------------------------------------

    /** The actual object is not available. */
    public static String HelpSetProviderProxy_getActualHelpSetProvider_actualObjectNotAvailable;

    /** The proxy has been disposed. */
    public static String HelpSetProviderProxy_getActualHelpSetProvider_proxyDisposed;

    // --- HelpSystem -------------------------------------------------------

    /** The master help set is missing. */
    public static String HelpSystem_activate_masterHelpSetMissing;

    /** Failed to add the help set. */
    public static String HelpSystem_addHelpSet_failed;

    /** The branding service is already bound. */
    public static String HelpSystem_bindBranding_bound;

    /** The help broker is not available. */
    public static String HelpSystem_displayHelp_helpBrokerNotAvailable;

    /** Failed to remove the help set. */
    public static String HelpSystem_removeHelpSet_failed;

    /** Failed to set the main window icons. */
    public static String HelpSystem_setMainWindowIcons_failed;

    /** The branding service is not bound. */
    public static String HelpSystem_unbindBranding_notBound;


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
    }
}
