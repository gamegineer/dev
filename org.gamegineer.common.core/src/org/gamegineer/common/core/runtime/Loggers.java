/*
 * Loggers.java
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
 * Created on Sep 16, 2008 at 11:41:49 PM.
 */

package org.gamegineer.common.core.runtime;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.logging.Logger;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.services.logging.ILoggingService;
import org.gamegineer.common.internal.core.Services;
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
    // Fields
    // ======================================================================

    /**
     * The default logging service used when no platform logging service is
     * available. This logging service always uses the global logger regardless
     * of which bundle and logger name are specified.
     */
    private static final ILoggingService DEFAULT_LOGGING_SERVICE = new ILoggingService()
    {
        @Override
        public Logger getLogger(
            final Bundle bundle )
        {
            return getLogger( bundle, null );
        }

        @Override
        public Logger getLogger(
            final Bundle bundle,
            @SuppressWarnings( "unused" )
            final String name )
        {
            assertArgumentNotNull( bundle, "bundle" ); //$NON-NLS-1$

            return Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Loggers} class.
     */
    protected Loggers()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the platform logging service.
     * 
     * @return The platform logging service; never {@code null}.
     */
    /* @NonNull */
    protected static ILoggingService getLoggingService()
    {
        final ILoggingService loggingService = Services.getLoggingService();
        return (loggingService != null) ? loggingService : DEFAULT_LOGGING_SERVICE;
    }
}
