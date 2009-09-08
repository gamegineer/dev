/*
 * AbstractAttributeChangeTestCase.java
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
 * Created on Jun 14, 2008 at 11:12:13 AM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange}
 * interface.
 */
public abstract class AbstractAttributeChangeTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute name for the fixture. */
    private static final AttributeName ATTR_NAME = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$

    /** The new attribute value for the fixture. */
    private static final Object ATTR_VALUE_NEW = "value_new"; //$NON-NLS-1$

    /** The old attribute value for the fixture. */
    private static final Object ATTR_VALUE_OLD = "value_old"; //$NON-NLS-1$

    /** The attribute change under test in the fixture. */
    private IAttributeChange change_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractAttributeChangeTestCase}
     * class.
     */
    protected AbstractAttributeChangeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the attribute change to be tested.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param hasOldValue
     *        {@code true} if the attribute has an old value; {@code false} if
     *        the attribute was added.
     * @param oldValue
     *        The old attribute value; may be {@code null}.
     * @param hasNewValue
     *        {@code true} if the attribute has a new value; {@code false} if
     *        the attribute was removed.
     * @param newValue
     *        The new attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If both {@code hasOldValue} and {@code hasNewValue} are {@code
     *         false}.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     * 
     * @return The attribute change to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract IAttributeChange createAttributeChange(
        /* @NonNull */
        AttributeName name,
        boolean hasOldValue,
        /* @Nullable */
        Object oldValue,
        boolean hasNewValue,
        /* @Nullable */
        Object newValue );

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
        change_ = createAttributeChange( ATTR_NAME, true, ATTR_VALUE_OLD, true, ATTR_VALUE_NEW );
        assertNotNull( change_ );
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
        change_ = null;
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertEquals( ATTR_NAME, change_.getName() );
    }

    /**
     * Ensures the {@code getNewValue} method throws an exception when the new
     * attribute value is absent and the old attribute value is present.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetNewValue_NewValue_Absent_OldValue_Present()
    {
        final IAttributeChange change = createAttributeChange( ATTR_NAME, true, ATTR_VALUE_OLD, false, null );
        change.getNewValue();
    }

    /**
     * Ensures the {@code getNewValue} method returns the expected value when
     * the new attribute value is present and the old attribute value is absent.
     */
    @Test
    public void testGetNewValue_NewValue_Present_OldValue_Absent()
    {
        final IAttributeChange change = createAttributeChange( ATTR_NAME, false, null, true, ATTR_VALUE_NEW );
        assertEquals( ATTR_VALUE_NEW, change.getNewValue() );
    }

    /**
     * Ensures the {@code getNewValue} method returns the expected value when
     * both new and old attribute values are present.
     */
    @Test
    public void testGetNewValue_NewValue_Present_OldValue_Present()
    {
        assertEquals( ATTR_VALUE_NEW, change_.getNewValue() );
    }

    /**
     * Ensures the {@code getOldValue} method returns the expected value when
     * the new attribute value is absent and the old attribute value is present.
     */
    @Test
    public void testGetOldValue_NewValue_Absent_OldValue_Present()
    {
        final IAttributeChange change = createAttributeChange( ATTR_NAME, true, ATTR_VALUE_OLD, false, null );
        assertEquals( ATTR_VALUE_OLD, change.getOldValue() );
    }

    /**
     * Ensures the {@code getOldValue} method throws an exception when the new
     * attribute value is present and the old attribute value is absent.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetOldValue_NewValue_Present_OldValue_Absent()
    {
        final IAttributeChange change = createAttributeChange( ATTR_NAME, false, null, true, ATTR_VALUE_NEW );
        change.getOldValue();
    }

    /**
     * Ensures the {@code getOldValue} method returns the expected value when
     * both new and old attribute values are present.
     */
    @Test
    public void testGetOldValue_NewValue_Present_OldValue_Present()
    {
        assertEquals( ATTR_VALUE_OLD, change_.getOldValue() );
    }
}
