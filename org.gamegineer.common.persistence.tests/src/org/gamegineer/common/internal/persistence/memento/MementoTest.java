/*
 * MementoTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jul 1, 2008 at 10:11:25 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import static org.gamegineer.test.core.Assert.assertImmutableMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.Memento} class.
 */
public final class MementoTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The memento under test in the fixture. */
    private Memento memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoTest} class.
     */
    public MementoTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an attribute collection for a memento.
     * 
     * @param size
     *        The size of the attribute collection; must be greater than or
     *        equal to zero.
     * @param firstIndex
     *        The starting index of the counter that is appended to the name and
     *        value of each attribute; must be greater than or equal to zero.
     * 
     * @return An attribute collection for a memento; never {@code null}.
     */
    /* @NonNull */
    private static Map<String, Object> createAttributes(
        final int size,
        final int firstIndex )
    {
        assert size >= 0;
        assert firstIndex >= 0;

        final Map<String, Object> attributes = new HashMap<String, Object>();
        for( int index = 0; index < size; ++index )
        {
            final String name = "name" + (index + firstIndex); //$NON-NLS-1$
            final String value = "value" + (index + firstIndex); //$NON-NLS-1$
            attributes.put( name, value );
        }
        return attributes;
    }

    /**
     * Creates an attribute collection for the fixture memento.
     * 
     * @return An attribute collection for the fixture memento; never {@code
     *         null}.
     */
    /* @NonNull */
    private static Map<String, Object> createFixtureAttributes()
    {
        return createAttributes( 4, 1 );
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
        memento_ = new Memento( createFixtureAttributes() );
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
     * Ensures the constructor makes a copy of the attribute collection.
     */
    @Test
    public void testConstructor_Attributes_Copy()
    {
        final Map<String, Object> expectedAttributes = createFixtureAttributes();
        final Map<String, Object> attributes = new HashMap<String, Object>( expectedAttributes );

        final Memento memento = new Memento( attributes );
        attributes.put( "new-name", new Object() ); //$NON-NLS-1$

        assertEquals( expectedAttributes, memento.getAttributes() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * attribute collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Attributes_Null()
    {
        new Memento( null );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different mementos are equal.
     */
    @Test
    public void testEquals_Equal_NotSame()
    {
        final Memento memento1 = memento_;
        final Memento memento2 = new Memento( createFixtureAttributes() );
        assertNotSame( memento1, memento2 );
        assertEquals( memento1, memento2 );
        assertEquals( memento2, memento1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null}
     * memento.
     */
    @Test
    public void testEquals_Equal_Null()
    {
        assertFalse( memento_.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates the same memento is
     * equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        assertEquals( memento_, memento_ ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two mementos whose
     * attribute collection sizes differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Size()
    {
        final Memento memento1 = memento_;
        final Memento memento2 = new Memento( createAttributes( 3, 1 ) );
        assertFalse( memento1.equals( memento2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two mementos whose
     * sizes are equal but whose values differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Values()
    {
        final Memento memento1 = memento_;
        final Memento memento2 = new Memento( createAttributes( 4, 2 ) );
        assertFalse( memento1.equals( memento2 ) );
    }

    /**
     * Ensures the {@code getAttributes} method returns all the attributes in
     * the memento when the memento contains at least one attribute.
     */
    @Test
    public void testGetAttributes()
    {
        final Map<String, Object> attributes = memento_.getAttributes();
        assertEquals( createFixtureAttributes(), attributes );
    }

    /**
     * Ensures the {@code getAttributes} method returns an empty collection when
     * the memento contains no attributes.
     */
    @Test
    public void testGetAttributes_Empty()
    {
        final Memento memento = new Memento( Collections.<String, Object>emptyMap() );
        final Map<String, Object> attributes = memento.getAttributes();
        assertNotNull( attributes );
        assertTrue( attributes.size() == 0 );
    }

    /**
     * Ensures the {@code getAttributes} method returns an immutable collection.
     */
    @Test
    public void testGetAttributes_ReturnValue_Immutable()
    {
        assertImmutableMap( memento_.getAttributes() );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * mementos.
     */
    @Test
    public void testHashCode_Equal()
    {
        final Memento memento1 = memento_;
        final Memento memento2 = new Memento( createFixtureAttributes() );
        assertNotSame( memento1, memento2 );
        assertEquals( memento1.hashCode(), memento2.hashCode() );
    }
}
