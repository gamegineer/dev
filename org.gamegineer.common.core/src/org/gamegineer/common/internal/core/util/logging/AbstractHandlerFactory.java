/*
 * AbstractHandlerFactory.java
 * Copyright 2008 Gamegineer.org
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

package org.gamegineer.common.internal.core.util.logging;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.gamegineer.common.internal.core.Debug;
import org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory;

/**
 * Superclass for all factories that create handlers.
 * 
 * @param <T>
 *        The type of the handler.
 */
abstract class AbstractHandlerFactory<T extends Handler>
    extends AbstractLoggingComponentFactory<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the filter property. */
    static final String PROPERTY_FILTER = "filter"; //$NON-NLS-1$

    /** The name of the formatter property. */
    static final String PROPERTY_FORMATTER = "formatter"; //$NON-NLS-1$

    /** The name of the encoding property. */
    static final String PROPERTY_ENCODING = "encoding"; //$NON-NLS-1$

    /** The name of the level property. */
    static final String PROPERTY_LEVEL = "level"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractHandlerFactory} class.
     * 
     * @param type
     *        The type of the handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    protected AbstractHandlerFactory(
        /* @NonNull */
        final Class<T> type )
    {
        super( type );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.internal.core.util.logging.AbstractLoggingComponentFactory#configureLoggingComponent(java.lang.Object, java.lang.String, java.util.Map)
     */
    @Override
    protected void configureLoggingComponent(
        final T component,
        final String instanceName,
        final Map<String, String> loggingProperties )
    {
        super.configureLoggingComponent( component, instanceName, loggingProperties );

        if( loggingProperties == null )
        {
            return;
        }

        setEncoding( component, instanceName, loggingProperties );
        setFilter( component, instanceName, loggingProperties );
        setFormatter( component, instanceName, loggingProperties );
        setLevel( component, instanceName, loggingProperties );
    }

    /**
     * Sets the encoding property on the specified handler if it was specified
     * in the logging properties.
     * 
     * @param handler
     *        The handler; must not be {@code null}.
     * @param instanceName
     *        The instance name of the handler; must not be {@code null}.
     * @param loggingProperties
     *        The logging properties; must not be {@code null}.
     */
    private void setEncoding(
        /* @NonNull */
        final Handler handler,
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final Map<String, String> loggingProperties )
    {
        assert handler != null;
        assert instanceName != null;
        assert loggingProperties != null;

        final String value = getLoggingProperty( instanceName, PROPERTY_ENCODING, loggingProperties );
        if( value != null )
        {
            try
            {
                handler.setEncoding( value );
            }
            catch( final UnsupportedEncodingException e )
            {
                if( Debug.UTILITY_LOGGING )
                {
                    Debug.trace( "Failed to parse handler encoding.", e ); //$NON-NLS-1$
                }
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
     * @param loggingProperties
     *        The logging properties; must not be {@code null}.
     */
    private void setFilter(
        /* @NonNull */
        final Handler handler,
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final Map<String, String> loggingProperties )
    {
        assert handler != null;
        assert instanceName != null;
        assert loggingProperties != null;

        final String value = getLoggingProperty( instanceName, PROPERTY_FILTER, loggingProperties );
        if( value != null )
        {
            try
            {
                handler.setFilter( (Filter)createNamedLoggingComponent( value, loggingProperties ) );
            }
            catch( final Exception e )
            {
                if( Debug.UTILITY_LOGGING )
                {
                    Debug.trace( String.format( "Failed to create handler filter '%1$s'.", value ), e ); //$NON-NLS-1$
                }
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
     * @param loggingProperties
     *        The logging properties; must not be {@code null}.
     */
    private void setFormatter(
        /* @NonNull */
        final Handler handler,
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final Map<String, String> loggingProperties )
    {
        assert handler != null;
        assert instanceName != null;
        assert loggingProperties != null;

        final String value = getLoggingProperty( instanceName, PROPERTY_FORMATTER, loggingProperties );
        if( value != null )
        {
            try
            {
                handler.setFormatter( (Formatter)createNamedLoggingComponent( value, loggingProperties ) );
            }
            catch( final Exception e )
            {
                if( Debug.UTILITY_LOGGING )
                {
                    Debug.trace( String.format( "Failed to create handler formatter '%1$s'.", value ), e ); //$NON-NLS-1$
                }
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
     * @param loggingProperties
     *        The logging properties; must not be {@code null}.
     */
    private void setLevel(
        /* @NonNull */
        final Handler handler,
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final Map<String, String> loggingProperties )
    {
        assert handler != null;
        assert instanceName != null;
        assert loggingProperties != null;

        final String value = getLoggingProperty( instanceName, PROPERTY_LEVEL, loggingProperties );
        if( value != null )
        {
            try
            {
                handler.setLevel( Level.parse( value ) );
            }
            catch( final IllegalArgumentException e )
            {
                if( Debug.UTILITY_LOGGING )
                {
                    Debug.trace( "Failed to parse handler level.", e ); //$NON-NLS-1$
                }
            }
        }
    }
}
