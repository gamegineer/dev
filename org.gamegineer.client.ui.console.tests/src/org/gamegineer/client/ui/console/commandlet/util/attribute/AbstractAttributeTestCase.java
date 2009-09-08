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
 * Created on May 18, 2009 at 10:10:52 PM.
 */

package org.gamegineer.client.ui.console.commandlet.util.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import org.gamegineer.client.ui.console.commandlet.FakeStatelet;
import org.gamegineer.client.ui.console.commandlet.IStatelet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute}
 * interface.
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

    /** The console statelet for use in the test. */
    private IStatelet statelet_;


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
        statelet_ = new FakeStatelet();
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
        statelet_ = null;
    }

    /**
     * Ensures the {@code add} method adds an attribute that is absent.
     */
    @Test
    public void testAdd_Attribute_Absent()
    {
        final T value = getLegalAttributeValue();

        attribute_.add( statelet_, value );

        assertEquals( value, statelet_.getAttribute( attribute_.getName() ) );
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
                attribute_.add( statelet_, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertFalse( statelet_.containsAttribute( attribute_.getName() ) );
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
        statelet_.addAttribute( attribute_.getName(), value );

        attribute_.add( statelet_, value );
    }

    /**
     * Ensures the {@code add} method throws an exception when passed a {@code
     * null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testAdd_Statelet_Null()
    {
        attribute_.add( null, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code ensureGetValue} method does not throw an exception
     * when the attribute is absent.
     */
    @Test
    public void testEnsureGetValue_Attribute_Absent()
    {
        attribute_.ensureGetValue( statelet_ );
    }

    /**
     * Ensures the {@code ensureGetValue} method retrieves the value when the
     * attribute is present.
     */
    @Test
    public void testEnsureGetValue_Attribute_Present()
    {
        final T expectedValue = getLegalAttributeValue();
        statelet_.addAttribute( attribute_.getName(), expectedValue );

        final T actualValue = attribute_.getValue( statelet_ );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code ensureGetValue} method throws an exception when passed
     * a {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testEnsureGetValue_Statelet_Null()
    {
        attribute_.ensureGetValue( null );
    }

    /**
     * Ensures the {@code ensureSetValue} method adds the value of an absent
     * attribute.
     */
    @Test
    public void testEnsureSetValue_Attribute_Absent()
    {
        final T expectedValue = getLegalAttributeValue();

        attribute_.ensureSetValue( statelet_, expectedValue );

        assertEquals( expectedValue, attribute_.getValue( statelet_ ) );
    }

    /**
     * Ensures the {@code ensureSetValue} method throws an exception when passed
     * an illegal attribute value.
     */
    @Test
    public void testEnsureSetValue_Attribute_Absent_IllegalValue()
    {
        for( final T value : getIllegalAttributeValues() )
        {
            try
            {
                attribute_.ensureSetValue( statelet_, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertFalse( statelet_.containsAttribute( attribute_.getName() ) );
            }
        }
    }

    /**
     * Ensures the {@code ensureSetValue} method replaces the value of a present
     * attribute.
     */
    @Test
    public void testEnsureSetValue_Attribute_Present()
    {
        final T value1 = getLegalAttributeValue();
        final T value2 = getLegalAttributeValue();
        assertNotSame( value1, value2 );
        statelet_.addAttribute( attribute_.getName(), value1 );

        attribute_.ensureSetValue( statelet_, value2 );

        assertEquals( value2, statelet_.getAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code ensureSetValue} method throws an exception when passed
     * a {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testEnsureSetValue_Statelet_Null()
    {
        attribute_.ensureSetValue( null, getLegalAttributeValue() );
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
        attribute_.getValue( statelet_ );
    }

    /**
     * Ensures the {@code getValue} method retrieves the value when the
     * attribute is present.
     */
    @Test
    public void testGetValue_Attribute_Present()
    {
        final T expectedValue = getLegalAttributeValue();
        statelet_.addAttribute( attribute_.getName(), expectedValue );

        final T actualValue = attribute_.getValue( statelet_ );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testGetValue_Statelet_Null()
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
        assertFalse( attribute_.isPresent( statelet_ ) );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates a present
     * attribute.
     */
    @Test
    public void testIsPresent_Attribute_Present()
    {
        statelet_.addAttribute( attribute_.getName(), getLegalAttributeValue() );

        assertTrue( attribute_.isPresent( statelet_ ) );
    }

    /**
     * Ensures the {@code isPresent} method throws an exception when passed a
     * {@code null} statelet.
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
        attribute_.remove( statelet_ );
    }

    /**
     * Ensures the {@code remove} method behaves correctly when the attribute is
     * present.
     */
    @Test
    public void testRemove_Attribute_Present()
    {
        statelet_.addAttribute( attribute_.getName(), getLegalAttributeValue() );

        attribute_.remove( statelet_ );

        assertFalse( statelet_.containsAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code remove} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testRemove_Statelet_Null()
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
        attribute_.setValue( statelet_, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed an
     * illegal attribute value.
     */
    @Test
    public void testSetValue_Attribute_Absent_IllegalValue()
    {
        final T originalValue = getLegalAttributeValue();
        statelet_.addAttribute( attribute_.getName(), originalValue );

        for( final T value : getIllegalAttributeValues() )
        {
            try
            {
                attribute_.setValue( statelet_, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertEquals( originalValue, statelet_.getAttribute( attribute_.getName() ) );
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
        statelet_.addAttribute( attribute_.getName(), value1 );

        attribute_.setValue( statelet_, value2 );

        assertEquals( value2, statelet_.getAttribute( attribute_.getName() ) );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValue_Statelet_Null()
    {
        attribute_.setValue( null, getLegalAttributeValue() );
    }
}
