/*
 * LoggerConfiguration.java
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
 * Created on May 15, 2008 at 11:05:20 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.internal.core.Debug;

/**
 * The configuration for a logger.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class LoggerConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the logger associated with the configuration. */
    private final String name_;

    /** The logging properties which contains the configuration. */
    private final LoggingProperties props_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggerConfiguration} class.
     * 
     * @param props
     *        The logging properties which contains the configuration; must not
     *        be {@code null}.
     * @param name
     *        The name of the logger associated with the configuration; must not
     *        be {@code null}.
     */
    LoggerConfiguration(
        /* @NonNull */
        final LoggingProperties props,
        /* @NonNull */
        final String name )
    {
        assert props != null;
        assert name != null;

        props_ = props;
        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the value of the {@code filter} property.
     * 
     * @param defaultValue
     *        The default value to use if the property has not been configured;
     *        may be {@code null}.
     * 
     * @return The value of the {@code filter} property; may be {@code null}.
     * 
     * @see java.util.logging.Logger#getFilter()
     */
    /* @Nullable */
    Filter getFilter(
        /* @Nullable */
        final Filter defaultValue )
    {
        final String value = props_.getProperty( name_, "filter" ); //$NON-NLS-1$
        if( value != null )
        {
            try
            {
                return (Filter)AbstractLoggingComponentFactory.createNamedLoggingComponent( value, props_.asMap() );
            }
            catch( final Exception e )
            {
                if( Debug.SERVICES_LOGGING )
                {
                    Debug.trace( String.format( "Failed to create logger filter '%1$s'.", value ), e ); //$NON-NLS-1$
                }
            }
        }

        return defaultValue;
    }

    /**
     * Gets the value of the {@code handlers} property.
     * 
     * @return The value of the {@code handlers} property; never {@code null}.
     * 
     * @see java.util.logging.Logger#getHandlers()
     */
    /* @NonNull */
    List<Handler> getHandlers()
    {
        final List<Handler> handlers = new ArrayList<Handler>();
        final String value = props_.getProperty( name_, "handlers" ); //$NON-NLS-1$
        if( value != null )
        {
            for( final String name : value.split( "[,\\s]+" ) ) //$NON-NLS-1$
            {
                try
                {
                    handlers.add( (Handler)AbstractLoggingComponentFactory.createNamedLoggingComponent( name, props_.asMap() ) );
                }
                catch( final Exception e )
                {
                    if( Debug.SERVICES_LOGGING )
                    {
                        Debug.trace( String.format( "Failed to create logger handler '%1$s'.", name ), e ); //$NON-NLS-1$
                    }
                }
            }
        }

        return handlers;
    }

    /**
     * Gets the value of the {@code level} property.
     * 
     * @param defaultValue
     *        The default value to use if the property has not been configured;
     *        may be {@code null}.
     * 
     * @return The value of the {@code level} property; may be {@code null}.
     * 
     * @see java.util.logging.Logger#getLevel()
     */
    /* @Nullable */
    Level getLevel(
        /* @Nullable */
        final Level defaultValue )
    {
        try
        {
            final String value = props_.getProperty( name_, "level" ); //$NON-NLS-1$
            if( value != null )
            {
                return Level.parse( value );
            }
        }
        catch( final IllegalArgumentException e )
        {
            if( Debug.SERVICES_LOGGING )
            {
                Debug.trace( "Failed to parse logger level.", e ); //$NON-NLS-1$
            }
        }

        return defaultValue;
    }

    /**
     * Gets the name of the logger.
     * 
     * @return The name of the logger; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return name_;
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
        final String value = props_.getProperty( name_, "useParentHandlers" ); //$NON-NLS-1$
        if( value != null )
        {
            return Boolean.parseBoolean( value );
        }

        return defaultValue;
    }
}
