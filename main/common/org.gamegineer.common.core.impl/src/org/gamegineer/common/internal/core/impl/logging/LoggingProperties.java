/*
 * LoggingProperties.java
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
 * Created on May 15, 2008 at 9:33:02 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

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
     *        The logging properties.
     * @param name
     *        The logger name.
     * 
     * @return A list of names for each ancestor of the specified logger which
     *         has an associated configuration in the specified logging
     *         properties. The names are ordered from nearest to furthest
     *         ancestor.
     */
    static List<String> getAncestorLoggerNames(
        final Map<String, String> properties,
        final String name )
    {
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
     *        The logging properties.
     * @param componentName
     *        The component name; must contain at least one dot.
     * @param propertyName
     *        The property name; must not contain any dots.
     * 
     * @return The value of the specified component property from the specified
     *         logging properties or {@code null} if the property does not
     *         exist.
     */
    static @Nullable String getProperty(
        final Map<String, String> properties,
        final String componentName,
        final String propertyName )
    {
        assert componentName.indexOf( '.' ) != -1;
        assert propertyName.indexOf( '.' ) == -1;

        return properties.get( String.format( "%1$s.%2$s", componentName, propertyName ) ); //$NON-NLS-1$
    }

    /**
     * Gets the value of the specified component property from the specified
     * logging properties.
     * 
     * @param properties
     *        The logging properties.
     * @param type
     *        The component type.
     * @param instanceName
     *        The component instance name; must not contain any dots.
     * @param propertyName
     *        The property name; must not contain any dots.
     * 
     * @return The value of the specified component property from the specified
     *         logging properties or {@code null} if the property does not
     *         exist.
     */
    static @Nullable String getProperty(
        final Map<String, String> properties,
        final Class<?> type,
        final String instanceName,
        final String propertyName )
    {
        assert instanceName.indexOf( '.' ) == -1;
        assert propertyName.indexOf( '.' ) == -1;

        return properties.get( String.format( "%1$s.%2$s.%3$s", type.getName(), instanceName, propertyName ) ); //$NON-NLS-1$
    }

    /**
     * Converts the specified properties collection into an equivalent map.
     * 
     * @param properties
     *        The properties collection.
     * 
     * @return A map that is equivalent to the specified properties collection.
     */
    static Map<String, String> toMap(
        final Properties properties )
    {
        final Map<String, String> map = new HashMap<>( properties.size() );
        for( final String name : properties.stringPropertyNames() )
        {
            final String value = properties.getProperty( name );
            assert value != null;
            map.put( name, value );
        }
        return map;
    }
}
