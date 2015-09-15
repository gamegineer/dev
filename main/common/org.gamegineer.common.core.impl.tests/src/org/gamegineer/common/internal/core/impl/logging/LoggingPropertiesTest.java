/*
 * LoggingPropertiesTest.java
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
 * Created on May 15, 2008 at 9:42:50 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link LoggingProperties} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class LoggingPropertiesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The logging properties under test in the fixture. */
    private Map<String, String> properties_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingPropertiesTest} class.
     */
    public LoggingPropertiesTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the logging properties under test in the fixture.
     * 
     * @return The logging properties under test in the fixture; never
     *         {@code null}.
     */
    private Map<String, String> getProperties()
    {
        assertNotNull( properties_ );
        return properties_;
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        properties_ = new HashMap<>();
        properties_.put( Object.class.getName() + ".a.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.c.d.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.c.d.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.c.d.e.f.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties_.put( "a.b.c.d.e.f.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@link LoggingProperties#getAncestorLoggerNames} method
     * returns a non-empty name collection in order from nearest ancestor to
     * furthest ancestor.
     */
    @Test
    public void testGetAncestorLoggerNames_ReturnValue_Ordered()
    {
        final List<String> names = LoggingProperties.getAncestorLoggerNames( getProperties(), "a.b.c.d.e.f" ); //$NON-NLS-1$

        assertEquals( 3, names.size() );
        assertEquals( "a.b.c.d", names.get( 0 ) ); //$NON-NLS-1$
        assertEquals( "a.b", names.get( 1 ) ); //$NON-NLS-1$
        assertEquals( "a", names.get( 2 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link LoggingProperties#getProperty(Map, String, String)}
     * method returns the expected value for a property that is absent.
     */
    @Test
    public void testGetPropertyForComponentName_Property_Absent()
    {
        assertNull( LoggingProperties.getProperty( getProperties(), "a.b.c.d", "p1000" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@link LoggingProperties#getProperty(Map, String, String)}
     * method returns the expected value for a property is present.
     */
    @Test
    public void testGetPropertyForComponentName_Property_Present()
    {
        assertEquals( "v1", LoggingProperties.getProperty( getProperties(), "a.b.c.d", "p1" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Ensures the
     * {@link LoggingProperties#getProperty(Map, Class, String, String)} method
     * returns the expected value for a property that is absent.
     */
    @Test
    public void testGetLoggingProperty_Property_Absent()
    {
        final Class<?> type = Object.class;
        final String instanceName = "b"; //$NON-NLS-1$
        final String propertyName = "p1"; //$NON-NLS-1$
        final Map<String, String> properties = nonNull( Collections.<@NonNull String, @NonNull String>emptyMap() );

        assertNull( LoggingProperties.getProperty( properties, type, instanceName, propertyName ) );
    }

    /**
     * Ensures the
     * {@link LoggingProperties#getProperty(Map, Class, String, String)} method
     * returns the expected value for a property that is present.
     */
    @Test
    public void testGetLoggingProperty_Property_Present()
    {
        final Class<?> type = Object.class;
        final String instanceName = "b"; //$NON-NLS-1$
        final String propertyName = "p1"; //$NON-NLS-1$
        final String propertyValue = "value"; //$NON-NLS-1$
        final Map<String, String> properties = nonNull( Collections.singletonMap( String.format( "%1$s.%2$s.%3$s", type.getName(), instanceName, propertyName ), propertyValue ) ); //$NON-NLS-1$

        assertEquals( propertyValue, LoggingProperties.getProperty( properties, type, instanceName, propertyName ) );
    }

    /**
     * Ensures the {@link LoggingProperties#toMap} method returns the correct
     * map when passed a properties collection that contains an entry whose name
     * is not a string.
     */
    @Test
    public void testToMap_Properties_ContainsNonStringName()
    {
        final Properties properties = new Properties();
        properties.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties.put( new Object(), "value2" ); //$NON-NLS-1$

        final Map<String, String> map = LoggingProperties.toMap( properties );

        assertEquals( 1, map.size() );
    }

    /**
     * Ensures the {@link LoggingProperties#toMap} method returns the correct
     * map when passed a properties collection that contains an entry whose
     * value is not a string.
     */
    @Test
    public void testToMap_Properties_ContainsNonStringValue()
    {
        final Properties properties = new Properties();
        properties.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        properties.put( "name2", new Object() ); //$NON-NLS-1$

        final Map<String, String> map = LoggingProperties.toMap( properties );

        assertEquals( 1, map.size() );
    }

    /**
     * Ensures the {@link LoggingProperties#toMap} method returns an empty map
     * when passed an empty properties collection.
     */
    @Test
    public void testToMap_Properties_Empty()
    {
        final Map<String, String> map = LoggingProperties.toMap( new Properties() );

        assertNotNull( map );
        assertTrue( map.isEmpty() );
    }

    /**
     * Ensures the {@link LoggingProperties#toMap} method returns the correct
     * map when passed a non-empty properties collection.
     */
    @Test
    public void testToMap_Properties_NonEmpty()
    {
        final String name1 = "name1", name2 = "name2"; //$NON-NLS-1$ //$NON-NLS-2$
        final String value1 = "value1", value2 = "value2"; //$NON-NLS-1$ //$NON-NLS-2$
        final Properties properties = new Properties();
        properties.put( name1, value1 );
        properties.put( name2, value2 );

        final Map<String, String> map = LoggingProperties.toMap( properties );

        assertEquals( properties.size(), map.size() );
        assertEquals( value1, map.get( name1 ) );
        assertEquals( value2, map.get( name2 ) );
    }
}
