/*
 * AbstractMementoTestCase.java
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
 * Created on Jun 30, 2008 at 11:24:30 PM.
 */

package org.gamegineer.common.persistence.memento;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.persistence.memento.IMemento} interface.
 */
public abstract class AbstractMementoTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The memento under test in the fixture. */
    private IMemento memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMementoTestCase} class.
     */
    protected AbstractMementoTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the memento to be tested.
     * 
     * @param attributes
     *        The attribute collection; must not be {@code null}.
     * 
     * @return The memento to be tested; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    /* @NonNull */
    protected abstract IMemento createMemento(
        /* @NonNull */
        Map<String, Object> attributes );

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
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name4", "value4" ); //$NON-NLS-1$ //$NON-NLS-2$
        memento_ = createMemento( attributes );
        assertNotNull( memento_ );
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
        memento_ = null;
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( memento_.containsAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        assertTrue( memento_.containsAttribute( "name1" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        memento_.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * specified attribute does not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetAttribute_Attribute_Absent()
    {
        memento_.getAttribute( "unknown_name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        assertEquals( "value1", memento_.getAttribute( "name1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        memento_.getAttribute( null );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns all the attribute
     * names in the memento when the memento contains at least one attribute.
     */
    @Test
    public void testGetAttributeNames()
    {
        final Set<String> names = memento_.getAttributeNames();
        assertEquals( 4, names.size() );
        assertTrue( names.contains( "name1" ) ); //$NON-NLS-1$
        assertTrue( names.contains( "name2" ) ); //$NON-NLS-1$
        assertTrue( names.contains( "name3" ) ); //$NON-NLS-1$
        assertTrue( names.contains( "name4" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an empty collection
     * when the memento contains no attributes.
     */
    @Test
    public void testGetAttributeNames_Empty()
    {
        final IMemento memento = createMemento( Collections.<String, Object>emptyMap() );
        final Set<String> names = memento.getAttributeNames();
        assertNotNull( names );
        assertTrue( names.size() == 0 );
    }

    /**
     * Ensures the {@code getAttributeNames} method returns an immutable
     * collection.
     */
    @Test
    public void testGetAttributeNames_ReturnValue_Immutable()
    {
        assertImmutableCollection( memento_.getAttributeNames() );
    }
}
