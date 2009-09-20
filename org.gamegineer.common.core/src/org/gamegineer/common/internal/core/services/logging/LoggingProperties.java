/*
 * LoggingProperties.java
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
 * Created on May 15, 2008 at 9:33:02 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import net.jcip.annotations.Immutable;

/**
 * A collection of properties used to configure the logging service.
 */
@Immutable
final class LoggingProperties
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The logging properties. */
    private final Properties props_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingProperties} class.
     * 
     * @param props
     *        The logging properties; must not be {@code null}.
     */
    LoggingProperties(
        /* @NonNull */
        final Properties props )
    {
        assert props != null;

        props_ = props;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an immutable map view of the logging properties.
     * 
     * @return An immutable map view of the logging properties; never {@code
     *         null}.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    Map<String, String> asMap()
    {
        // HACK: Cast to raw type required because of the legacy nature of java.util.Properties
        return Collections.<String, String>unmodifiableMap( (Map)props_ );
    }

    /**
     * Gets a list of names for each ancestor of the specified logger which has
     * an associated configuration.
     * 
     * @param name
     *        The logger name; must not be {@code null}.
     * 
     * @return A list of names for each ancestor of the specified logger which
     *         has an associated configuration; never {@code null}. The names
     *         are ordered from nearest to furthest ancestor.
     */
    /* @NonNull */
    List<String> getAncestorLoggerNames(
        /* @NonNull */
        final String name )
    {
        assert name != null;

        // Build a list of all possible ancestor names from nearest to furthest ancestor
        final List<String> ancestorNames = new ArrayList<String>();
        String parentName = name;
        int index = parentName.lastIndexOf( '.' );
        while( index != -1 )
        {
            parentName = parentName.substring( 0, index );
            ancestorNames.add( parentName );
            index = parentName.lastIndexOf( '.' );
        }

        // Remove any ancestor name which doesn't have an associated configuration property
        final Set<Object> propertyNames = props_.keySet();
        for( final Iterator<String> iter = ancestorNames.iterator(); iter.hasNext(); )
        {
            final String ancestorName = iter.next();
            final Pattern pattern = Pattern.compile( String.format( "^%1$s\\.[^.]+$", ancestorName ) ); //$NON-NLS-1$
            boolean exists = false;
            for( final Object propertyName : propertyNames )
            {
                if( pattern.matcher( (String)propertyName ).matches() )
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
     * Gets the configuration for the specified logger.
     * 
     * @param name
     *        The logger name; must not be {@code null}.
     * 
     * @return The configuration for the specified logger; never {@code null}. A
     *         default configuration is returned if no configuration exists for
     *         the specified logger.
     */
    /* @NonNull */
    LoggerConfiguration getLoggerConfiguration(
        /* @NonNull */
        final String name )
    {
        assert name != null;

        return new LoggerConfiguration( this, name );
    }

    /**
     * Gets the value of the specified entity property.
     * 
     * @param entityName
     *        The entity name; must not be {@code null}.
     * @param propertyName
     *        The property name; must not be {@code null} and must not contain
     *        any dots.
     * 
     * @return The value of the specified entity property or {@code null} if the
     *         property does not exist.
     */
    /* @Nullable */
    String getProperty(
        /* @NonNull */
        final String entityName,
        /* @NonNull */
        final String propertyName )
    {
        assert entityName != null;
        assert propertyName != null;
        assert propertyName.indexOf( '.' ) == -1;

        return props_.getProperty( entityName + "." + propertyName ); //$NON-NLS-1$
    }
}
