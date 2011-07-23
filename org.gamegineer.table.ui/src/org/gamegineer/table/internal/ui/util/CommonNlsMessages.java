/*
 * CommonNlsMessages.java
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
 * Created on Nov 24, 2010 at 8:22:58 PM.
 */

package org.gamegineer.table.internal.ui.util;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the application.
 */
@ThreadSafe
public final class CommonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- Common -----------------------------------------------------------

    /** The application name. */
    public static String Common_application_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( CommonNlsMessages.class.getName(), CommonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code CommonNlsMessages} class.
     */
    private CommonNlsMessages()
    {
        super();
    }
}
