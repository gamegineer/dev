/*
 * LoggingProperties.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on May 15, 2008 at 9:33:02 PM.
 */

package org.gamegineer.common.internal.core.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import net.jcip.annotations.Immutable;

/**
 * A collection of useful methods for manipulating properties used to configure
 * the logging service.
 */
@Immutable
final class LoggingProperties
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingProperties} class.
     */
    private LoggingProperties()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a list of names for each ancestor of the specified logger which has
     * an associated configuration in the specified logging properties.
     * 
     * @param properties
     *        The logging properties; must not be {@code null}.
     * @param name
     *        The logger name; must not be {@code null}.
     * 
     * @return A list of names for each ancestor of the specified logger which
     *         has an associated configuration in the specified logging
     *         properties; never {@code null}. The names are ordered from
     *         nearest to furthest ancestor.
     */
    /* @NonNull */
    static List<String> getAncestorLoggerNames(
        /* @NonNull */
        final Map<String, String> properties,
        /* @NonNull */
        final String name )
    {
        assert properties != null;
        assert name != null;

        // Build a list of all possible ancestor names from nearest to furthest ancestor
        final List<String> ancestorNames = new ArrayList<>();
        String parentName = name;
        int index = parentName.lastIndexOf( '.' );
        while( index != -1 )
        {
            parentName = parentName.substring( 0, index );
            ancestorNames.add( parentName );
            index = parentName.lastIndexOf( '.' );
        }

        // Remove any ancestor name which doesn't have an associated configuration property
        final Set<String> propertyNames = properties.keySet();
        for( final Iterator<String> iter = ancestorNames.iterator(); iter.hasNext(); )
        {
            final String ancestorName = iter.next();
            final Pattern pattern = Pattern.compile( String.format( "^%1$s\\.[^.]+$", ancestorName ) ); //$NON-NLS-1$
            boolean exists = false;
            for( final String propertyName : propertyNames )
            {
                if( pattern.matcher( propertyName ).matches() )
                {
                    exists = true;
                    break;
                }
            }

            if( !exists )
            {
                iter.remove();
            }
        }

        return ancestorNames;
    }

    /**
     * Gets the value of the specified component property from the specified
     * logging properties.
     * 
     * @param properties
     *        The logging properties; must not be {@code null}.
     * @param componentName
     *        The component name; must not be {@code null} and must contain at
     *        least one dot.
     * @param propertyName
     *        The property name; must not be {@code null} and must not contain
     *        any dots.
     * 
     * @return The value of the specified component property from the specified
     *         logging properties or {@code null} if the property does not
     *         exist.
     */
    /* @Nullable */
    static String getProperty(
        /* @NonNull */
        final Map<String, String> properties,
        /* @NonNull */
        final String componentName,
        /* @NonNull */
        final String propertyName )
    {
        assert properties != null;
        assert componentName != null;
        assert componentName.indexOf( '.' ) != -1;
        assert propertyName != null;
        assert propertyName.indexOf( '.' ) == -1;

        return properties.get( String.format( "%1$s.%2$s", componentName, propertyName ) ); //$NON-NLS-1$
    }

    /**
     * Gets the value of the specified component property from the specified
     * logging properties.
     * 
     * @param properties
     *        The logging properties; must not be {@code null}.
     * @param type
     *        The component type; must not be {@code null}.
     * @param instanceName
     *        The component instance name; must not be {@code null} and must not
     *        contain any dots.
     * @param propertyName
     *        The property name; must not be {@code null} and must not contain
     *        any dots.
     * 
     * @return The value of the specified component property from the specified
     *         logging properties or {@code null} if the property does not
     *         exist.
     */
    /* @Nullable */
    static String getProperty(
        /* @NonNull */
        final Map<String, String> properties,
        /* @NonNull */
        final Class<?> type,
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final String propertyName )
    {
        assert properties != null;
        assert type != null;
        assert instanceName != null;
        assert instanceName.indexOf( '.' ) == -1;
        assert propertyName != null;
        assert propertyName.indexOf( '.' ) == -1;

        return properties.get( String.format( "%1$s.%2$s.%3$s", type.getName(), instanceName, propertyName ) ); //$NON-NLS-1$
    }

    /**
     * Converts the specified properties collection into an equivalent map.
     * 
     * @param properties
     *        The properties collection; must not be {@code null}.
     * 
     * @return A map that is equivalent to the specified properties collection;
     *         never {@code null}.
     */
    /* @NonNull */
    static Map<String, String> toMap(
        /* @NonNUll */
        final Properties properties )
    {
        assert properties != null;

        final Map<String, String> map = new HashMap<>( properties.size() );
        for( final String name : properties.stringPropertyNames() )
        {
            map.put( name, properties.getProperty( name ) );
        }
        return map;
    }
}
