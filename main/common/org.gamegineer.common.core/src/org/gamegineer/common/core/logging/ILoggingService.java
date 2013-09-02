/*
 * ILoggingService.java
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
 * Created on May 14, 2008 at 10:18:33 PM.
 */

package org.gamegineer.common.core.logging;

import java.util.logging.Logger;
import org.osgi.framework.Bundle;

/**
 * A service used to manage the Java core logging facilities in an OSGi
 * environment.
 * 
 * <p>
 * The purpose of this service is to provide individual bundles in the OSGi
 * environment the ability to configure the Java core logging facilities in an
 * isolated manner. The standard logging configuration mechanism is not suitable
 * for OSGi for the following reasons:
 * </p>
 * 
 * <ul>
 * <li>The standard logging configuration is centralized in a single location.
 * Thus, the configuration for a logger associated with a bundle must be located
 * outside the bundle.</li>
 * <li>The standard logging configuration relies heavily on the system class
 * loader. Thus, any object that is instantiated while processing the
 * configuration must be loaded through the system class loader. This is not
 * compatible with the isolation of bundle class loaders in an OSGi environment.
 * </li>
 * </ul>
 * 
 * <p>
 * It is sufficient to use the Java core logging facilities <i>without</i> this
 * service in the following cases:
 * </p>
 * 
 * <ul>
 * <li>It is acceptable to configure all loggers in a single location and your
 * logger configurations only require classes located on the system classpath.</li>
 * <li>You configure all your loggers programatically.</li>
 * </ul>
 * 
 * <p>
 * Each bundle may provide a file named <i>logging.properties</i> at the root of
 * the bundle's location to configure its loggers, handlers, formatters, and
 * filters.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ILoggingService
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default logger for the specified bundle.
     * 
     * @param bundle
     *        The bundle; must not be {@code null}.
     * 
     * @return The default logger for the specified bundle; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code bundle} is {@code null}.
     */
    /* @NonNull */
    public Logger getLogger(
        /* @NonNull */
        Bundle bundle );

    /**
     * Gets the named logger for the specified bundle.
     * 
     * @param bundle
     *        The bundle; must not be {@code null}.
     * @param name
     *        The logger name. Hierarchical names are represented as a sequence
     *        of dot-separated name fragments. If {@code null} or an empty
     *        string, the default logger for the bundle will be returned.
     * 
     * @return The named logger for the specified bundle; never {@code null}.
     */
    /* @NonNull */
    public Logger getLogger(
        /* @NonNull */
        Bundle bundle,
        /* @Nullable */
        String name );
}
