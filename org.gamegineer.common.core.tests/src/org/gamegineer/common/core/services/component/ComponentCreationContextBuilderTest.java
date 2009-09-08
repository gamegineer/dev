/*
 * ComponentCreationContextBuilderTest.java
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
 * Created on Apr 11, 2008 at 10:12:40 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of the
 * {@link org.gamegineer.common.core.services.component.ComponentCreationContextBuilder}
 * class.
 */
public final class ComponentCreationContextBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The builder under test in the fixture. */
    private ComponentCreationContextBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ComponentCreationContextBuilderTest} class.
     */
    public ComponentCreationContextBuilderTest()
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
        builder_ = new ComponentCreationContextBuilder();
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
        builder_ = null;
    }

    /**
     * Ensures the primary constructor adds the initial attributes to the
     * resulting context.
     */
    @Test
    public void testConstructor_Primary_AddsInitialAttributes()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder( attributes );
        final IComponentCreationContext context = builder.toComponentCreationContext();
        assertEquals( "value1", context.getAttribute( "name1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "value2", context.getAttribute( "name2" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the primary constructor throws an exception when passed a {@code
     * null} attribute collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Primary_Attributes_Null()
    {
        new ComponentCreationContextBuilder( null );
    }

    /**
     * Ensures the constructor throws an exception when passed an attribute
     * collection which contains one or more {@code null} values.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Primary_Attributes_NullValues()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name2", null ); //$NON-NLS-1$
        attributes.put( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        new ComponentCreationContextBuilder( attributes );
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( builder_.containsAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        final String name = "name1"; //$NON-NLS-1$
        builder_.setAttribute( name, "value1" ); //$NON-NLS-1$
        assertTrue( builder_.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        builder_.containsAttribute( null );
    }

    /**
     * Ensures the {@code createComponentCreationContext} method adds the
     * specified attributes to the resulting context.
     */
    @Test
    public void testCreateComponentCreationContext_AddsAttributes()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        final IComponentCreationContext context = ComponentCreationContextBuilder.createComponentCreationContext( attributes );
        assertEquals( "value1", context.getAttribute( "name1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "value2", context.getAttribute( "name2" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code createComponentCreationContext} method throws an
     * exception when passed a {@code null} attribute collection.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentCreationContext_Attributes_Null()
    {
        ComponentCreationContextBuilder.createComponentCreationContext( null );
    }

    /**
     * Ensures the {@code getAttribute} method returns {@code null} when the
     * specified attribute does not exist.
     */
    @Test
    public void testGetAttribute_Attribute_Absent()
    {
        assertNull( builder_.getAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        final String name = "name1"; //$NON-NLS-1$
        final String value = "value1"; //$NON-NLS-1$
        builder_.setAttribute( name, value );
        assertEquals( value, builder_.getAttribute( name ) );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        builder_.getAttribute( null );
    }

    /**
     * Ensures the {@code setAttribute} method adds the specified attributes to
     * the resulting context.
     */
    @Test
    public void testSetAttribute_AddsAttributes()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        builder_.setAttribute( name1, value1 );
        builder_.setAttribute( name2, value2 );
        final IComponentCreationContext context = builder_.toComponentCreationContext();
        assertEquals( value1, context.getAttribute( name1 ) );
        assertEquals( value2, context.getAttribute( name2 ) );
    }
}
