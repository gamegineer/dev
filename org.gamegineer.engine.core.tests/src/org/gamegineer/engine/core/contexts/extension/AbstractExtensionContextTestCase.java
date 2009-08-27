/*
 * AbstractExtensionContextTestCase.java
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
 * Created on May 2, 2009 at 8:45:50 PM.
 */

package org.gamegineer.engine.core.contexts.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.contexts.extension.IExtensionContext}
 * interface.
 */
public abstract class AbstractExtensionContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension context under test in the fixture. */
    private IExtensionContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractExtensionContextTestCase} class.
     */
    protected AbstractExtensionContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the extension context to be tested.
     * 
     * @return The extension context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IExtensionContext createExtensionContext()
        throws Exception;

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
        m_context = createExtensionContext();
        assertNotNull( m_context );
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
        m_context = null;
    }

    /**
     * Ensures the {@code addAttribute} method adds an attribute that does not
     * exist.
     */
    @Test
    public void testAddAttribute_Attribute_Absent()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value = new Object();

        m_context.addAttribute( name, value );

        assertEquals( value, m_context.getAttribute( name ) );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when the
     * specified attribute already exists.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value = new Object();
        m_context.addAttribute( name, value );

        m_context.addAttribute( name, value );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Name_Null()
    {
        m_context.addAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} value.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Value_Null()
    {
        m_context.addAttribute( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( m_context.containsAttribute( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value = new Object();
        m_context.addAttribute( name, value );

        assertTrue( m_context.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        m_context.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method returns {@code null} when the
     * specified attribute does not exist.
     */
    @Test
    public void testGetAttribute_Attribute_Absent()
    {
        assertNull( m_context.getAttribute( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value = new Object();
        m_context.addAttribute( name, value );

        assertEquals( value, m_context.getAttribute( name ) );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        m_context.getAttribute( null );
    }
}
