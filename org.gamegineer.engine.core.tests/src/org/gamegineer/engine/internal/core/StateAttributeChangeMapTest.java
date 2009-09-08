/*
 * StateAttributeChangeMapTest.java
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
 * Created on Jun 1, 2008 at 8:48:36 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the attribute change map returned from the
 * {@link org.gamegineer.engine.internal.core.State#getAttributeChanges()}
 * method.
 */
public final class StateAttributeChangeMapTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the first test attribute. */
    private static final AttributeName ATTR_NAME_1 = new AttributeName( Scope.APPLICATION, "test1" ); //$NON-NLS-1$

    /** The name of the second test attribute. */
    private static final AttributeName ATTR_NAME_2 = new AttributeName( Scope.APPLICATION, "test2" ); //$NON-NLS-1$

    /** The name of the third test attribute. */
    private static final AttributeName ATTR_NAME_3 = new AttributeName( Scope.APPLICATION, "test3" ); //$NON-NLS-1$

    /** The new value of the first test attribute. */
    private static final String ATTR_VALUE_NEW_1 = "newValue1"; //$NON-NLS-1$

    /** The new value of the second test attribute. */
    private static final String ATTR_VALUE_NEW_2 = "newValue2"; //$NON-NLS-1$

    /** The new value of the third test attribute. */
    private static final String ATTR_VALUE_NEW_3 = "newValue3"; //$NON-NLS-1$

    /** The original value of the first test attribute. */
    private static final String ATTR_VALUE_OLD_1 = null;

    /** The original value of the second test attribute. */
    private static final String ATTR_VALUE_OLD_2 = "value2"; //$NON-NLS-1$

    /** The state under test in the fixture. */
    private State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateAttributeChangeMapTest}
     * class.
     */
    public StateAttributeChangeMapTest()
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
        state_ = new State();
        state_.beginTransaction();
        state_.addAttribute( ATTR_NAME_1, ATTR_VALUE_OLD_1 );
        state_.addAttribute( ATTR_NAME_2, ATTR_VALUE_OLD_2 );
        state_.commitTransaction();

        state_.beginTransaction();
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
        if( state_.isTransactionActive() )
        {
            state_.commitTransaction();
        }
        state_ = null;
    }

    /**
     * Ensures adding a new attribute adds it to the attribute change map.
     */
    @Test
    public void testAdd()
    {
        state_.addAttribute( ATTR_NAME_3, ATTR_VALUE_NEW_3 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_3 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_3 );
        assertFalse( change.hasOldValue() );
        assertEquals( ATTR_VALUE_NEW_3, change.getNewValue() );
    }

    /**
     * Ensures adding a new attribute and immediately removing it does not add
     * it to the attribute change map.
     */
    @Test
    public void testAdd_Remove()
    {
        state_.addAttribute( ATTR_NAME_3, ATTR_VALUE_NEW_3 );
        state_.removeAttribute( ATTR_NAME_3 );
        assertFalse( state_.getAttributeChanges().containsKey( ATTR_NAME_3 ) );
    }

    /**
     * Ensures changing the value of multiple existing attributes adds them to
     * the attribute change map.
     */
    @Test
    public void testChange_Existing_Multiple_NewValue()
    {
        state_.setAttribute( ATTR_NAME_1, ATTR_VALUE_NEW_1 );
        state_.setAttribute( ATTR_NAME_2, ATTR_VALUE_NEW_2 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_1 ) );
        final IAttributeChange change1 = attributeChanges.get( ATTR_NAME_1 );
        assertEquals( ATTR_VALUE_OLD_1, change1.getOldValue() );
        assertEquals( ATTR_VALUE_NEW_1, change1.getNewValue() );
        assertTrue( attributeChanges.containsKey( ATTR_NAME_2 ) );
        final IAttributeChange change2 = attributeChanges.get( ATTR_NAME_2 );
        assertEquals( ATTR_VALUE_OLD_2, change2.getOldValue() );
        assertEquals( ATTR_VALUE_NEW_2, change2.getNewValue() );
    }

    /**
     * Ensures changing the value of a single existing attribute adds it to the
     * attribute change map.
     */
    @Test
    public void testChange_Existing_Single_NewValue()
    {
        state_.setAttribute( ATTR_NAME_2, ATTR_VALUE_NEW_2 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_2 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_2 );
        assertEquals( ATTR_VALUE_OLD_2, change.getOldValue() );
        assertEquals( ATTR_VALUE_NEW_2, change.getNewValue() );
    }

    /**
     * Ensures changing the non-{@code null} value of a single existing
     * attribute to a {@code null} value adds it to the attribute change map.
     */
    @Test
    public void testChange_Existing_Single_NewValue_NonNullToNull()
    {
        state_.setAttribute( ATTR_NAME_2, null );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_2 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_2 );
        assertEquals( ATTR_VALUE_OLD_2, change.getOldValue() );
        assertNull( change.getNewValue() );
    }

    /**
     * Ensures changing the {@code null} value of a single existing attribute to
     * a non-{@code null} value adds it to the attribute change map.
     */
    @Test
    public void testChange_Existing_Single_NewValue_NullToNonNull()
    {
        state_.setAttribute( ATTR_NAME_1, ATTR_VALUE_NEW_1 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_1 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_1 );
        assertNull( change.getOldValue() );
        assertEquals( ATTR_VALUE_NEW_1, change.getNewValue() );
    }

    /**
     * Ensures changing the {@code null} value of a single existing attribute to
     * a {@code null} value does not add it to the attribute change map.
     */
    @Test
    public void testChange_Existing_Single_NewValue_NullToNull()
    {
        assertNull( ATTR_VALUE_OLD_1 );
        state_.setAttribute( ATTR_NAME_1, ATTR_VALUE_OLD_1 );
        assertFalse( state_.getAttributeChanges().containsKey( ATTR_NAME_1 ) );
    }

    /**
     * Ensures changing the value of a single existing attribute to its current
     * value does not add it to the attribute change map.
     */
    @Test
    public void testChange_Existing_Single_OldValue()
    {
        state_.setAttribute( ATTR_NAME_2, ATTR_VALUE_OLD_2 );
        assertFalse( state_.getAttributeChanges().containsKey( ATTR_NAME_2 ) );
    }

    /**
     * Ensures removing a single existing attribute and immediately adding it
     * back with a new value adds it to the attribute change map.
     */
    @Test
    public void testRemove_Existing_Add_NewValue()
    {
        state_.removeAttribute( ATTR_NAME_1 );
        state_.addAttribute( ATTR_NAME_1, ATTR_VALUE_NEW_1 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_1 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_1 );
        assertEquals( ATTR_VALUE_OLD_1, change.getOldValue() );
        assertEquals( ATTR_VALUE_NEW_1, change.getNewValue() );
    }

    /**
     * Ensures removing a single existing attribute and immediately adding it
     * back with its old value does not add it to the attribute change map.
     */
    @Test
    public void testRemove_Existing_Add_OldValue()
    {
        state_.removeAttribute( ATTR_NAME_1 );
        state_.addAttribute( ATTR_NAME_1, ATTR_VALUE_OLD_1 );
        assertFalse( state_.getAttributeChanges().containsKey( ATTR_NAME_1 ) );
    }

    /**
     * Ensures removing multiple existing attributes adds them to the attribute
     * change map.
     */
    @Test
    public void testRemove_Existing_Multiple()
    {
        state_.removeAttribute( ATTR_NAME_1 );
        state_.removeAttribute( ATTR_NAME_2 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_1 ) );
        final IAttributeChange change1 = attributeChanges.get( ATTR_NAME_1 );
        assertEquals( ATTR_VALUE_OLD_1, change1.getOldValue() );
        assertFalse( change1.hasNewValue() );
        assertTrue( attributeChanges.containsKey( ATTR_NAME_2 ) );
        final IAttributeChange change2 = attributeChanges.get( ATTR_NAME_2 );
        assertEquals( ATTR_VALUE_OLD_2, change2.getOldValue() );
        assertFalse( change2.hasNewValue() );
    }

    /**
     * Ensures removing a single existing attribute adds it to the changed
     * attribute name set.
     */
    @Test
    public void testRemove_Existing_Single()
    {
        state_.removeAttribute( ATTR_NAME_2 );
        final Map<AttributeName, IAttributeChange> attributeChanges = state_.getAttributeChanges();
        assertTrue( attributeChanges.containsKey( ATTR_NAME_2 ) );
        final IAttributeChange change = attributeChanges.get( ATTR_NAME_2 );
        assertEquals( ATTR_VALUE_OLD_2, change.getOldValue() );
        assertFalse( change.hasNewValue() );
    }
}
