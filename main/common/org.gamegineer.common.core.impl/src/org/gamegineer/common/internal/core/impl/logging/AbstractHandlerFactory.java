/*
 * AbstractHandlerFactory.java
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
 * Created on May 23, 2008 at 12:03:31 AM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.gamegineer.common.internal.core.impl.Debug;
import org.osgi.service.component.ComponentException;

/**
 * Superclass for all factories that create logging handlers.
 * 
 * @param <T>
 *        The type of the handler.
 */
@ThreadSafe
public abstract class AbstractHandlerFactory<@NonNull T extends Handler>
    extends AbstractLoggingComponentFactory<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractHandlerFactory} class.
     * 
     * @param type
     *        The type of the logging component; must not be {@code null}.
     */
    protected AbstractHandlerFactory(
        final Class<T> type )
    {
        super( type );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractLoggingComponentFactory#configureLoggingComponent(java.lang.Object, java.lang.String, java.util.Map)
     */
    @Override
    protected void configureLoggingComponent(
        final T component,
        final String instanceName,
        final @Nullable Map<String, String> properties )
    {
        super.configureLoggingComponent( component, instanceName, properties );

        if( properties == null )
        {
            return;
        }

        setEncoding( component, instanceName, properties );
        setFilter( component, instanceName, properties );
        setFormatter( component, instanceName, properties );
        setLevel( component, instanceName, properties );
    }

    /**
     * Sets the encoding property on the specified handler if it was specified
     * in the logging properties.
     * 
     * @param handler
     *        The handler; must not be {@code null}.
     * @param instanceName
     *        The instance name of the handler; must not be {@code null}.
     * @param properties
     *        The logging properties; must not be {@code null}.
     */
    private static void setEncoding(
        final Handler handler,
        final String instanceName,
        final Map<String, String> properties )
    {
        final String value = LoggingProperties.getProperty( properties, nonNull( handler.getClass() ), instanceName, LoggingServiceConstants.PROPERTY_HANDLER_ENCODING );
        if( value != null )
        {
            try
            {
                handler.setEncoding( value );
            }
            catch( final UnsupportedEncodingException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to parse encoding for handler '%1$s.%2$s'", handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Sets the filter property on the specified handler if it was specified in
     * the logging properties.
     * 
     * @param handler
     *        The handler; must not be {@code null}.
     * @param instanceName
     *        The instance name of the handler; must not be {@code null}.
     * @param properties
     *        The logging properties; must not be {@code null}.
     */
    private static void setFilter(
        final Handler handler,
        final String instanceName,
        final Map<String, String> properties )
    {
        final String value = LoggingProperties.getProperty( properties, nonNull( handler.getClass() ), instanceName, LoggingServiceConstants.PROPERTY_HANDLER_FILTER );
        if( value != null )
        {
            try
            {
                handler.setFilter( createNamedLoggingComponent( Filter.class, value, properties ) );
            }
            catch( final IllegalArgumentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Illegal filter name specified for handler '%1$s.%2$s'", handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
            catch( final ComponentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to create filter '%1$s' for handler '%2$s.%3$s'", value, handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Sets the formatter property on the specified handler if it was specified
     * in the logging properties.
     * 
     * @param handler
     *        The handler; must not be {@code null}.
     * @param instanceName
     *        The instance name of the handler; must not be {@code null}.
     * @param properties
     *        The logging properties; must not be {@code null}.
     */
    private static void setFormatter(
        final Handler handler,
        final String instanceName,
        final Map<String, String> properties )
    {
        final String value = LoggingProperties.getProperty( properties, nonNull( handler.getClass() ), instanceName, LoggingServiceConstants.PROPERTY_HANDLER_FORMATTER );
        if( value != null )
        {
            try
            {
                handler.setFormatter( createNamedLoggingComponent( Formatter.class, value, properties ) );
            }
            catch( final IllegalArgumentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Illegal formatter name specified for handler '%1$s.%2$s'", handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
            catch( final ComponentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to create formatter '%1$s' for handler '%2$s.%3$s'", value, handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
        }
    }

    /**
     * Sets the level property on the specified handler if it was specified in
     * the logging properties.
     * 
     * @param handler
     *        The handler; must not be {@code null}.
     * @param instanceName
     *        The instance name of the handler; must not be {@code null}.
     * @param properties
     *        The logging properties; must not be {@code null}.
     */
    private static void setLevel(
        final Handler handler,
        final String instanceName,
        final Map<String, String> properties )
    {
        final String value = LoggingProperties.getProperty( properties, nonNull( handler.getClass() ), instanceName, LoggingServiceConstants.PROPERTY_HANDLER_LEVEL );
        if( value != null )
        {
            try
            {
                handler.setLevel( Level.parse( value ) );
            }
            catch( final IllegalArgumentException e )
            {
                Debug.getDefault().trace( Debug.OPTION_LOGGING, String.format( "Failed to parse level for handler '%1$s.%2$s'", handler.getClass().getName(), instanceName ), e ); //$NON-NLS-1$
            }
        }
    }
}
