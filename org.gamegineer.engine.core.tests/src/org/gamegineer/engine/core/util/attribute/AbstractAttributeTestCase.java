/*
 * AbstractAttributeTestCase.java
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
 * Created on Sep 6, 2008 at 9:23:47 PM.
 */

package org.gamegineer.engine.core.util.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import org.gamegineer.engine.core.FakeState;
import org.gamegineer.engine.core.IState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} interface.
 * 
 * @param <T>
 *        The type of the attribute value.
 */
public abstract class AbstractAttributeTestCase<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private IAttribute<T> attribute_;

    /** The engine state for use in the test. */
    private IState state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractAttributeTestCase}
     * class.
     */
    protected AbstractAttributeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the attribute to be tested.
     * 
     * @return The attribute to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IAttribute<T> createAttribute()
        throws Exception;

    /**
     * Gets a collection of illegal values for the attribute to be tested.
     * 
     * @return A collection of illegal values for the attribute to be tested;
     *         never {@code null}. An empty collection if the attribute does not
     *         define any illegal values.
     */
    /* @NonNull */
    protected abstract Collection<T> getIllegalAttributeValues();

    /**
     * Gets a legal value for the attribute to be tested.
     * 
     * @return A legal value for the attribute to be tested; may be {@code null}
     *         .
     */
    /* @Nullable */
    protected abstract T getLegalAttributeValue();

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
        state_ = new FakeState();
        attribute_ = createAttribute();
        assertNotNull( attribute_ );
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
        attribute_ = null;
        state_ = null;
    }

    /**
     * Ensures the {@code add} method adds an attribute that is absent.
     */
    @Test
    public void testAdd_Attribute_Absent()
    {
        final T value = getLegalAttributeValue();

        attribute_.add( state_, value );

        assertEquals( value, state_.getAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code add} method throws an exception when passed an illegal
     * attribute value.
     */
    @Test
    public void testAdd_Attribute_Absent_IllegalValue()
    {
        for( final T value : getIllegalAttributeValues() )
        {
            try
            {
                attribute_.add( state_, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertFalse( state_.containsAttribute( attribute_.getName() ) );
            }
        }
    }

    /**
     * Ensures the {@code add} method throws an exception when the attribute is
     * present.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAdd_Attribute_Present()
    {
        final T value = getLegalAttributeValue();
        state_.addAttribute( attribute_.getName(), value );

        attribute_.add( state_, value );
    }

    /**
     * Ensures the {@code add} method throws an exception when passed a {@code
     * null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testAdd_State_Null()
    {
        attribute_.add( null, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( attribute_.getName() );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when the
     * attribute is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetValue_Attribute_Absent()
    {
        attribute_.getValue( state_ );
    }

    /**
     * Ensures the {@code getValue} method retrieves the value when the
     * attribute is present.
     */
    @Test
    public void testGetValue_Attribute_Present()
    {
        final T expectedValue = getLegalAttributeValue();
        state_.addAttribute( attribute_.getName(), expectedValue );

        final T actualValue = attribute_.getValue( state_ );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testGetValue_State_Null()
    {
        attribute_.getValue( null );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates an absent
     * attribute.
     */
    @Test
    public void testIsPresent_Attribute_Absent()
    {
        assertFalse( attribute_.isPresent( state_ ) );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates a present
     * attribute.
     */
    @Test
    public void testIsPresent_Attribute_Present()
    {
        state_.addAttribute( attribute_.getName(), getLegalAttributeValue() );

        assertTrue( attribute_.isPresent( state_ ) );
    }

    /**
     * Ensures the {@code isPresent} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testIsPresent_State_Null()
    {
        attribute_.isPresent( null );
    }

    /**
     * Ensures the {@code remove} throws an exception when the attribute is
     * absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemove_Attribute_Absent()
    {
        attribute_.remove( state_ );
    }

    /**
     * Ensures the {@code remove} method behaves correctly when the attribute is
     * present.
     */
    @Test
    public void testRemove_Attribute_Present()
    {
        state_.addAttribute( attribute_.getName(), getLegalAttributeValue() );

        attribute_.remove( state_ );

        assertFalse( state_.containsAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code remove} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testRemove_State_Null()
    {
        attribute_.remove( null );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when the
     * attribute is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetValue_Attribute_Absent()
    {
        attribute_.setValue( state_, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed an
     * illegal attribute value.
     */
    @Test
    public void testSetValue_Attribute_Absent_IllegalValue()
    {
        final T originalValue = getLegalAttributeValue();
        state_.addAttribute( attribute_.getName(), originalValue );

        for( final T value : getIllegalAttributeValues() )
        {
            try
            {
                attribute_.setValue( state_, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertEquals( originalValue, state_.getAttribute( attribute_.getName() ) );
            }
        }
    }

    /**
     * Ensures the {@code setValue} method replaces the value of a present
     * attribute.
     */
    @Test
    public void testSetValue_Attribute_Present()
    {
        final T value1 = getLegalAttributeValue();
        final T value2 = getLegalAttributeValue();
        assertNotSame( value1, value2 );
        state_.addAttribute( attribute_.getName(), value1 );

        attribute_.setValue( state_, value2 );

        assertEquals( value2, state_.getAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed a
     * {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValue_State_Null()
    {
        attribute_.setValue( null, getLegalAttributeValue() );
    }
}
