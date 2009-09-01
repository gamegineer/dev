/*
 * NullDebugOptionsTest.java
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
 * Created on Sep 16, 2008 at 10:42:14 PM.
 */

package org.gamegineer.common.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.NullDebugOptions} class.
 */
public final class NullDebugOptionsTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default option name to be used by the fixture. */
    private static final String OPTION = "bundle/option"; //$NON-NLS-1$

    /** The debug options under test in the fixture. */
    private DebugOptions m_debugOptions;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullDebugOptionsTest} class.
     */
    public NullDebugOptionsTest()
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
        m_debugOptions = new NullDebugOptions();
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
        m_debugOptions = null;
    }

    /**
     * Ensures the {@code getBooleanOption} method throws an exception when
     * passed a {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testGetBooleanOption_Option_Null()
    {
        m_debugOptions.getBooleanOption( null, false );
    }

    /**
     * Ensures the {@code getBooleanOption} method always returns the default
     * value.
     */
    @Test
    public void testGetBooleanOption_ReturnsDefaultValue()
    {
        assertTrue( m_debugOptions.getBooleanOption( OPTION, true ) );
        assertFalse( m_debugOptions.getBooleanOption( OPTION, false ) );
    }

    /**
     * Ensures the {@code getIntegerOption} method throws an exception when
     * passed a {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testGetIntegerOption_Option_Null()
    {
        m_debugOptions.getIntegerOption( null, 0 );
    }

    /**
     * Ensures the {@code getIntegerOption} method always returns the default
     * value.
     */
    @Test
    public void testGetIntegerOption_ReturnsDefaultValue()
    {
        assertEquals( 1, m_debugOptions.getIntegerOption( OPTION, 1 ) );
        assertEquals( 0, m_debugOptions.getIntegerOption( OPTION, 0 ) );
        assertEquals( -1, m_debugOptions.getIntegerOption( OPTION, -1 ) );
    }

    /**
     * Ensures the {@code getOption(String)} method throws an exception when
     * passed a {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testGetOption_Option_Null()
    {
        m_debugOptions.getOption( null );
    }

    /**
     * Ensures the {@code getOption(String)} method always returns {@code null}.
     */
    @Test
    public void testGetOption_ReturnsNull()
    {
        assertNull( m_debugOptions.getOption( OPTION ) );
    }

    /**
     * Ensures the {@code getOption(String, String)} method throws an exception
     * when passed a {@code null} default value.
     */
    @Test( expected = NullPointerException.class )
    public void testGetOptionWithDefault_DefaultValue_Null()
    {
        m_debugOptions.getOption( OPTION, null );
    }

    /**
     * Ensures the {@code getOption(String, String)} method throws an exception
     * when passed a {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testGetOptionWithDefault_Option_Null()
    {
        m_debugOptions.getOption( null, "defaultValue" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getOption(String, String)} method always returns the
     * default value.
     */
    @Test
    public void testGetOptionWithDefault_ReturnsDefaultValue()
    {
        final String value = "defaultValue"; //$NON-NLS-1$
        assertEquals( value, m_debugOptions.getOption( OPTION, value ) );
    }

    /**
     * Ensures the {@code newDebugTrace(String)} method throws an exception when
     * passed a {@code null} bundle symbolic name.
     */
    @Test( expected = NullPointerException.class )
    public void testNewDebugTrace_BundleSymbolicName_Null()
    {
        m_debugOptions.newDebugTrace( null );
    }

    /**
     * Ensures the {@code newDebugTrace(String,Class)} method throws an
     * exception when passed a {@code null} bundle symbolic name.
     */
    @Test( expected = NullPointerException.class )
    public void testNewDebugTraceWithTraceEntryClass_BundleSymbolicName_Null()
    {
        m_debugOptions.newDebugTrace( null, Object.class );
    }

    /**
     * Ensures the {@code newDebugTrace(String,Class)} method throws an
     * exception when passed a {@code null} trace entry class.
     */
    @Test( expected = NullPointerException.class )
    public void testNewDebugTraceWithTraceEntryClass_TraceEntryClass_Null()
    {
        m_debugOptions.newDebugTrace( "", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code removeOption} method throws an exception when passed a
     * {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveOption_Option_Null()
    {
        m_debugOptions.removeOption( null );
    }

    /**
     * Ensures the {@code setOption} method throws an exception when passed a
     * {@code null} option.
     */
    @Test( expected = NullPointerException.class )
    public void testSetOption_Option_Null()
    {
        m_debugOptions.setOption( null, "" ); //$NON-NLS-1$
    }
}
