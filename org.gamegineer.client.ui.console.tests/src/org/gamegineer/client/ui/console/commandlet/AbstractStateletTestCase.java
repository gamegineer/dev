/*
 * AbstractStateletTestCase.java
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
 * Created on May 15, 2009 at 9:03:37 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.commandlet.IStatelet} interface.
 */
public abstract class AbstractStateletTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The statelet under test in the fixture. */
    private IStatelet m_statelet;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStateletTestCase} class.
     */
    protected AbstractStateletTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the statelet to be tested.
     * 
     * @return The statelet to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IStatelet createStatelet()
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
        m_statelet = createStatelet();
        assertNotNull( m_statelet );
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
        m_statelet = null;
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

        m_statelet.addAttribute( name, value );

        assertSame( value, m_statelet.getAttribute( name ) );
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
        m_statelet.addAttribute( name, value );

        m_statelet.addAttribute( name, value );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Name_Null()
    {
        m_statelet.addAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code addAttribute} method allows a {@code null} value.
     */
    @Test
    public void testAddAttribute_Value_Null()
    {
        m_statelet.addAttribute( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( m_statelet.containsAttribute( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$

        m_statelet.addAttribute( name, new Object() );

        assertTrue( m_statelet.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        m_statelet.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetAttribute_Attribute_Absent()
    {
        m_statelet.getAttribute( "name" ); //$NON-NLS-1$
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

        m_statelet.addAttribute( name, value );

        assertSame( value, m_statelet.getAttribute( name ) );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        m_statelet.getAttribute( null );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns all the attribute
     * names in the statelet when the statelet contains at least one attribute.
     */
    @Test
    public void testGetAttributeNames()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        m_statelet.addAttribute( name1, new Object() );
        m_statelet.addAttribute( name2, new Object() );

        final Set<String> names = m_statelet.getAttributeNames();

        assertTrue( names.size() == 2 );
        assertTrue( names.contains( name1 ) );
        assertTrue( names.contains( name2 ) );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an empty set when
     * the statelet contains no attributes.
     */
    @Test
    public void testGetAttributeNames_Empty()
    {
        final Set<String> names = m_statelet.getAttributeNames();

        assertNotNull( names );
        assertTrue( names.size() == 0 );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an immutable set.
     */
    @Test
    public void testGetAttributeNames_ReturnValue_Immutable()
    {
        m_statelet.addAttribute( "name", new Object() ); //$NON-NLS-1$

        assertImmutableCollection( m_statelet.getAttributeNames() );
    }

    /**
     * Ensures the {@code removeAttribute} throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveAttribute_Attribute_Absent()
    {
        m_statelet.removeAttribute( "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code removeAttribute} method behaves correctly when the
     * specified attribute does exist.
     */
    @Test
    public void testRemoveAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        m_statelet.addAttribute( name, new Object() );
        assertTrue( m_statelet.containsAttribute( name ) );

        m_statelet.removeAttribute( name );

        assertFalse( m_statelet.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code removeAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveAttribute_Name_Null()
    {
        m_statelet.removeAttribute( null );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetAttribute_Attribute_Absent()
    {
        m_statelet.setAttribute( "name", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setAttribute} method replaces the value of an existing
     * attribute.
     */
    @Test
    public void testSetAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final Object value1 = new Object();
        final Object value2 = new Object();
        m_statelet.addAttribute( name, value1 );

        m_statelet.setAttribute( name, value2 );

        assertSame( value2, m_statelet.getAttribute( name ) );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetAttribute_Name_Null()
    {
        m_statelet.setAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code setAttribute} method allows a {@code null} value.
     */
    @Test
    public void testSetAttribute_Value_Null()
    {
        final String name = "name"; //$NON-NLS-1$
        m_statelet.addAttribute( name, new Object() );

        m_statelet.setAttribute( name, null );
    }
}
