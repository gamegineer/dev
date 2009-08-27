/*
 * Loggers.java
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
 * Created on Feb 23, 2009 at 9:34:52 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import java.util.logging.Logger;
import org.gamegineer.common.core.services.logging.ILoggingService;
import org.osgi.framework.Bundle;

/**
 * Manages the loggers used by the bundle.
 */
public final class Loggers
    extends org.gamegineer.common.core.runtime.Loggers
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default logger for the bundle. */
    public static final Logger DEFAULT;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Loggers} class.
     */
    static
    {
        final Bundle bundle = Activator.getDefault().getBundleContext().getBundle();
        final ILoggingService loggingService = getLoggingService();
        DEFAULT = loggingService.getLogger( bundle );
    }

    /**
     * Initializes a new instance of the {@code Loggers} class.
     */
    private Loggers()
    {
        super();
    }
}
