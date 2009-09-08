/*
 * MementoBuilderTest.java
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
 * Created on Jul 1, 2008 at 9:27:17 PM.
 */

package org.gamegineer.common.persistence.memento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.persistence.memento.MementoBuilder} class.
 */
public final class MementoBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The builder under test in the fixture. */
    private MementoBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoBuilderTest} class.
     */
    public MementoBuilderTest()
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
        builder_ = new MementoBuilder();
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
     * Ensures the {@code addAttribute} method adds an attribute that does not
     * exist.
     */
    @Test
    public void testAddAttribute_Attribute_Absent()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        builder_.addAttribute( name1, value1 );
        builder_.addAttribute( name2, value2 );
        final IMemento memento = builder_.toMemento();
        assertEquals( value1, memento.getAttribute( name1 ) );
        assertEquals( value2, memento.getAttribute( name2 ) );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when the
     * specified attribute already exists.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddAttribute_Attribute_Present()
    {
        final String name = "name"; //$NON-NLS-1$
        final String value = "value"; //$NON-NLS-1$
        builder_.addAttribute( name, value );
        builder_.addAttribute( name, value );
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testAddAttribute_Name_Null()
    {
        builder_.addAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code addAttribute} method does not throw an exception when
     * passed a {@code null} value.
     */
    @Test
    public void testAddAttribute_Value_Null()
    {
        builder_.addAttribute( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the primary constructor adds the initial attributes to the
     * resulting memento.
     */
    @Test
    public void testConstructor_Primary_AddsInitialAttributes()
    {
        final String name1 = "name1"; //$NON-NLS-1$
        final String value1 = "value1"; //$NON-NLS-1$
        final String name2 = "name2"; //$NON-NLS-1$
        final String value2 = "value2"; //$NON-NLS-1$
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( name1, value1 );
        attributes.put( name2, value2 );
        final MementoBuilder builder = new MementoBuilder( attributes );
        final IMemento memento = builder.toMemento();
        assertEquals( value1, memento.getAttribute( name1 ) );
        assertEquals( value2, memento.getAttribute( name2 ) );
    }

    /**
     * Ensures the primary constructor throws an exception when passed a {@code
     * null} attribute collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Primary_Attributes_Null()
    {
        new MementoBuilder( null );
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
        builder_.addAttribute( name, "value1" ); //$NON-NLS-1$
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
}
