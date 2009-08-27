/*
 * AbstractStateTestCase.java
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
 * Created on Feb 24, 2008 at 10:51:04 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.IState} interface.
 */
public abstract class AbstractStateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state under test in the fixture. */
    private IState m_state;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStateTestCase} class.
     */
    protected AbstractStateTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the state to be tested.
     * 
     * @return The state to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract IState createState();

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
        m_state = createState();
        assertNotNull( m_state );
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
        m_state = null;
    }

    /**
     * Ensures the {@code addAttribute} method adds an attribute that does not
     * exist.
     */
    @Test
    public void testAddAttribute_Attribute_Absent()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        m_state.addAttribute( name, value );
        assertSame( value, m_state.getAttribute( name ) );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when the
     * specified attribute already exists.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddAttribute_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        m_state.addAttribute( name, value );
        m_state.addAttribute( name, value );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Name_Null()
    {
        m_state.addAttribute( null, "value" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code addAttribute} method allows a {@code null} value.
     */
    @Test
    public void testAddAttribute_Value_Null()
    {
        m_state.addAttribute( new AttributeName( Scope.APPLICATION, "name" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( m_state.containsAttribute( new AttributeName( Scope.APPLICATION, "name" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        assertTrue( m_state.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        m_state.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetAttribute_Attribute_Absent()
    {
        m_state.getAttribute( new AttributeName( Scope.APPLICATION, "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        m_state.addAttribute( name, value );
        assertSame( value, m_state.getAttribute( name ) );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        m_state.getAttribute( null );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns all the attribute
     * names in the state when the state contains at least one attribute.
     */
    @Test
    public void testGetAttributeNames()
    {
        final AttributeName name1 = new AttributeName( Scope.APPLICATION, "name1" ); //$NON-NLS-1$
        final AttributeName name2 = new AttributeName( Scope.ENGINE_CONTROL, "name2" ); //$NON-NLS-1$
        m_state.addAttribute( name1, "value1" ); //$NON-NLS-1$
        m_state.addAttribute( name2, "value2" ); //$NON-NLS-1$
        final Set<AttributeName> nameSet = m_state.getAttributeNames();
        assertTrue( nameSet.size() == 2 );
        assertTrue( nameSet.contains( name1 ) );
        assertTrue( nameSet.contains( name2 ) );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an empty set when
     * the state contains no attributes.
     */
    @Test
    public void testGetAttributeNames_Empty()
    {
        final Set<AttributeName> nameSet = m_state.getAttributeNames();
        assertNotNull( nameSet );
        assertTrue( nameSet.size() == 0 );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an immutable set.
     */
    @Test
    public void testGetAttributeNames_ReturnValue_Immutable()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        assertImmutableCollection( m_state.getAttributeNames() );
    }

    /**
     * Ensures the {@code removeAttribute} throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveAttribute_Attribute_Absent()
    {
        m_state.removeAttribute( new AttributeName( Scope.APPLICATION, "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code removeAttribute} method behaves correctly when the
     * specified attribute does exist.
     */
    @Test
    public void testRemoveAttribute_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        assertTrue( m_state.containsAttribute( name ) );
        m_state.removeAttribute( name );
        assertFalse( m_state.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code removeAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveAttribute_Name_Null()
    {
        m_state.removeAttribute( null );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetAttribute_Attribute_Absent()
    {
        m_state.setAttribute( new AttributeName( Scope.APPLICATION, "name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code setAttribute} method replaces the value of an existing
     * attribute.
     */
    @Test
    public void testSetAttribute_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        m_state.addAttribute( name, value1 );
        m_state.setAttribute( name, value2 );
        assertSame( value2, m_state.getAttribute( name ) );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetAttribute_Name_Null()
    {
        m_state.setAttribute( null, "value" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setAttribute} method allows a {@code null} value.
     */
    @Test
    public void testSetAttribute_Value_Null()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.setAttribute( name, null );
    }
}
