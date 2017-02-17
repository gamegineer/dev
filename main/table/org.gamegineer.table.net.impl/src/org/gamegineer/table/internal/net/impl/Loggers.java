/*
 * Loggers.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Nov 15, 2013 at 8:22:41 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.util.logging.Logger;
import net.jcip.annotations.ThreadSafe;

/**
 * Manages the loggers used by the bundle.
 */
@ThreadSafe
public final class Loggers
    extends org.gamegineer.common.core.runtime.Loggers
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Loggers} class.
     */
    private Loggers()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default logger for the bundle.
     * 
     * @return The default logger for the bundle.
     */
    public static Logger getDefaultLogger()
    {
        return getLogger( Activator.getDefault().getBundleContext().getBundle(), null );
    }
}
