/*
 * LoggerConfiguration.java
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
 * Created on May 15, 2008 at 11:05:20 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.gamegineer.common.internal.core.impl.Debug;
import org.osgi.service.component.ComponentException;

/**
 * The configuration for a logger.
 */
@Immutable
final class LoggerConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the logger associated with the configuration. */
    private final String name_;

    /** The logging properties. */
    private final Map<String, String> properties_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggerConfiguration} class.
     * 
     * @param name
     *        The name of the logger associated with the configuration.
     * @param properties
     *        The logging properties.
     */
    LoggerConfiguration(
        final String name,
        final Map<String, String> properties )
    {
        name_ = name;
        properties_ = new HashMap<>( properties );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the value of the {@code filter} property.
     * 
     * @param defaultValue
     *        The default value to use if the property has not been configured.
     * 
     * @return The value of the {@code filter} property.
     * 
     * @see java.util.logging.Logger#getFilter()
     */
    @Nullable Filter getFilter(
        final @Nullable Filter defaultValue )
    {
        final String value = LoggingProperties.getProperty( properties_, name_, LoggingServiceConstants.PROPERTY_LOGGER_FILTER );
        if( value != null )
        {
            try
            {
                return AbstractLoggingComponentFactory.createNamedLoggingComponent( nonNull( Filter.class ), value, properties_ );
            }
            catch( final IllegalArgumentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Illegal filter name specified for logger '%1$s'", name_ ), e ); //$NON-NLS-1$
            }
            catch( final ComponentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to create filter '%1$s' for logger '%2$s'", value, name_ ), e ); //$NON-NLS-1$
            }
        }

        return defaultValue;
    }

    /**
     * Gets the value of the {@code handlers} property.
     * 
     * @return The value of the {@code handlers} property.
     * 
     * @see java.util.logging.Logger#getHandlers()
     */
    List<Handler> getHandlers()
    {
        final List<Handler> handlers = new ArrayList<>();
        final String value = LoggingProperties.getProperty( properties_, name_, LoggingServiceConstants.PROPERTY_LOGGER_HANDLERS );
        if( value != null )
        {
            for( final String name : value.split( "[,\\s]+" ) ) //$NON-NLS-1$
            {
                try
                {
                    handlers.add( AbstractLoggingComponentFactory.<@NonNull Handler>createNamedLoggingComponent( nonNull( Handler.class ), name, properties_ ) );
                }
                catch( final IllegalArgumentException e )
                {
                    Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Illegal handler name specified for logger '%1$s'", name_ ), e ); //$NON-NLS-1$
                }
                catch( final ComponentException e )
                {
                    Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to create handler '%1$s' for logger '%2$s'", name, name_ ), e ); //$NON-NLS-1$
                }
            }
        }

        return handlers;
    }

    /**
     * Gets the value of the {@code level} property.
     * 
     * @param defaultValue
     *        The default value to use if the property has not been configured.
     * 
     * @return The value of the {@code level} property.
     * 
     * @see java.util.logging.Logger#getLevel()
     */
    @Nullable Level getLevel(
        final @Nullable Level defaultValue )
    {
        try
        {
            final String value = LoggingProperties.getProperty( properties_, name_, LoggingServiceConstants.PROPERTY_LOGGER_LEVEL );
            if( value != null )
            {
                return Level.parse( value );
            }
        }
        catch( final IllegalArgumentException e )
        {
            Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to parse level for logger '%1$s'", name_ ), e ); //$NON-NLS-1$
        }

        return defaultValue;
    }

    /**
     * Gets the value of the {@code useParentHandlers} property.
     * 
     * @param defaultValue
     *        The default value to use if the property has not been configured.
     * 
     * @return The value of the {@code useParentHandlers} property.
     * 
     * @see java.util.logging.Logger#getUseParentHandlers()
     */
    boolean getUseParentHandlers(
        final boolean defaultValue )
    {
        final String value = LoggingProperties.getProperty( properties_, name_, LoggingServiceConstants.PROPERTY_LOGGER_USE_PARENT_HANDLERS );
        if( value != null )
        {
            return Boolean.parseBoolean( value );
        }

        return defaultValue;
    }
}
