/*
 * LoggingServiceConstants.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Jun 8, 2010 at 10:42:33 PM.
 */

package org.gamegineer.common.core.logging;

import net.jcip.annotations.ThreadSafe;

/**
 * Defines useful constants for use by the Logging Service and its clients.
 */
@ThreadSafe
public final class LoggingServiceConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the component factory instance name property. */
    public static final String PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME = "org.gamegineer.common.core.logging.instanceName"; //$NON-NLS-1$

    /** The name of the component factory logging properties property. */
    public static final String PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES = "org.gamegineer.common.core.logging.loggingProperties"; //$NON-NLS-1$

    /** The name of the component factory type name property. */
    public static final String PROPERTY_COMPONENT_FACTORY_TYPE_NAME = "org.gamegineer.common.core.logging.typeName"; //$NON-NLS-1$

    /** The name of the handler encoding property. */
    public static final String PROPERTY_HANDLER_ENCODING = "encoding"; //$NON-NLS-1$

    /** The name of the handler filter property. */
    public static final String PROPERTY_HANDLER_FILTER = "filter"; //$NON-NLS-1$

    /** The name of the handler formatter property. */
    public static final String PROPERTY_HANDLER_FORMATTER = "formatter"; //$NON-NLS-1$

    /** The name of the handler level property. */
    public static final String PROPERTY_HANDLER_LEVEL = "level"; //$NON-NLS-1$

    /** The name of the logger filter property. */
    public static final String PROPERTY_LOGGER_FILTER = "filter"; //$NON-NLS-1$

    /** The name of the logger handlers property. */
    public static final String PROPERTY_LOGGER_HANDLERS = "handlers"; //$NON-NLS-1$

    /** The name of the logger level property. */
    public static final String PROPERTY_LOGGER_LEVEL = "level"; //$NON-NLS-1$

    /** The name of the logger use parent handlers property. */
    public static final String PROPERTY_LOGGER_USE_PARENT_HANDLERS = "useParentHandlers"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingServiceConstants} class.
     */
    private LoggingServiceConstants()
    {
    }
}
