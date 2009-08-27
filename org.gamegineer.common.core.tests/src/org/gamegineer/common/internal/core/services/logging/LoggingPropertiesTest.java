/*
 * LoggingPropertiesTest.java
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
 * Created on May 15, 2008 at 9:42:50 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import static org.gamegineer.test.core.Assert.assertImmutableMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.LoggingProperties}
 * class.
 */
public final class LoggingPropertiesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The backing store for the logging properties. */
    private Properties m_propStore;

    /** The logging properties under test in the fixture. */
    private LoggingProperties m_props;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingPropertiesTest} class.
     */
    public LoggingPropertiesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_propStore = new Properties();
        m_propStore.put( "a.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.c.d.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.c.d.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.c.d.e.f.p1", "v1" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_propStore.put( "a.b.c.d.e.f.p2", "v2" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_props = new LoggingProperties( m_propStore );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_props = null;
        m_propStore = null;
    }

    /**
     * Ensures the {@code asMap} method returns a map view of all properties.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAsMap()
    {
        final Map<String, String> propMap = m_props.asMap();
        assertEquals( m_propStore.size(), propMap.size() );
        for( final String name : m_propStore.stringPropertyNames() )
        {
            assertEquals( m_propStore.getProperty( name ), propMap.get( name ) );
        }
    }

    /**
     * Ensures the {@code asMap} method returns an immutable map.
     */
    @Test
    public void testAsMap_ReturnValue_Immutable()
    {
        assertImmutableMap( m_props.asMap() );
    }

    /**
     * Ensures the {@code asMap} method does not return {@code null} when the
     * underlying property collection is empty.
     */
    @Test
    public void testAsMap_ReturnValue_NonNull()
    {
        final LoggingProperties props = new LoggingProperties( new Properties() );
        final Map<String, String> propMap = props.asMap();
        assertNotNull( propMap );
        assertTrue( propMap.isEmpty() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * properties object.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Properties_Null()
    {
        new LoggingProperties( null );
    }

    /**
     * Ensures the {@code getAncestorLoggerNames} method throws an exception
     * when passed a {@code null} name.
     */
    @Test( expected = AssertionError.class )
    public void testGetAncestorLoggerNames_Name_Null()
    {
        m_props.getAncestorLoggerNames( null );
    }

    /**
     * Ensures the {@code getAncestorLoggerNames} method does not return
     * {@code null} when there are no ancestors for the specified logger.
     */
    @Test
    public void testGetAncestorLoggerNames_ReturnValue_NonNull()
    {
        final List<String> nameList = m_props.getAncestorLoggerNames( "z" ); //$NON-NLS-1$
        assertNotNull( nameList );
        assertTrue( nameList.isEmpty() );
    }

    /**
     * Ensures the {@code getAncestorLoggerNames} method returns a non-empty
     * name list in order from nearest ancestor to furthest ancestor.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetAncestorLoggerNames_ReturnValue_Ordered()
    {
        final List<String> nameList = m_props.getAncestorLoggerNames( "a.b.c.d.e.f" ); //$NON-NLS-1$
        assertEquals( 3, nameList.size() );
        assertEquals( "a.b.c.d", nameList.get( 0 ) ); //$NON-NLS-1$
        assertEquals( "a.b", nameList.get( 1 ) ); //$NON-NLS-1$
        assertEquals( "a", nameList.get( 2 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getLoggerConfiguration} method throws an exception
     * when passed a {@code null} name.
     */
    @Test( expected = AssertionError.class )
    public void testGetLoggerConfiguration_Name_Null()
    {
        m_props.getLoggerConfiguration( null );
    }

    /**
     * Ensures the {@code getLoggerConfiguration} method does not return
     * {@code null} when there is not configuration for the named logger.
     */
    @Test
    public void testGetLoggerConfiguration_ReturnValue_NonNull()
    {
        assertNotNull( m_props.getLoggerConfiguration( "z" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getProperty} method throws an exception when passed a
     * {@code null} entity name.
     */
    @Test( expected = AssertionError.class )
    public void testGetProperty_EntityName_Null()
    {
        m_props.getProperty( null, "p1" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getProperty} method returns the expected value for a
     * property that exists.
     */
    @Test
    public void testGetProperty_Property_Exists()
    {
        assertEquals( "v1", m_props.getProperty( "a.b.c.d", "p1" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Ensures the {@code getProperty} method returns the expected value for a
     * property that does not exist.
     */
    @Test
    public void testGetProperty_Property_Nonexistent()
    {
        assertNull( m_props.getProperty( "a.b.c.d", "p1000" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code getProperty} method throws an exception when passed a
     * property name that contains dots.
     */
    @Test( expected = AssertionError.class )
    public void testGetProperty_PropertyName_Dotted()
    {
        m_props.getProperty( "a", "p.p" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code getProperty} method throws an exception when passed a
     * {@code null} property name.
     */
    @Test( expected = AssertionError.class )
    public void testGetProperty_PropertyName_Null()
    {
        m_props.getProperty( "a", null ); //$NON-NLS-1$
    }
}
