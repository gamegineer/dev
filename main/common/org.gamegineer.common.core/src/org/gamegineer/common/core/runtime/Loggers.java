/*
 * Loggers.java
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
 * Created on Sep 16, 2008 at 11:41:49 PM.
 */

package org.gamegineer.common.core.runtime;

import java.util.logging.Logger;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.internal.core.Activator;
import org.osgi.framework.Bundle;

/**
 * A collection of methods useful for defining a class that manages the loggers
 * used by a bundle.
 * 
 * <p>
 * This class is intended to be extended by clients. Typically, a client will
 * write their own bundle- or package-specific version of this class and simply
 * inherit the static methods it provides. For example, a client-specific
 * version might include methods that represent the loggers available in the
 * bundle.
 * </p>
 */
@ThreadSafe
public abstract class Loggers
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Loggers} class.
     */
    protected Loggers()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the named logger for the specified bundle.
     * 
     * @param bundle
     *        The bundle; must not be {@code null}.
     * @param name
     *        The logger name. If {@code null} or an empty string, the default
     *        logger for the bundle will be returned.
     * 
     * @return The named logger for the specified bundle; never {@code null}.
     */
    protected static Logger getLogger(
        final Bundle bundle,
        @Nullable
        final String name )
    {
        final ILoggingService loggingService = Activator.getDefault().getLoggingService();
        if( loggingService == null )
        {
            return Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
        }

        return loggingService.getLogger( bundle, name );
    }
}
