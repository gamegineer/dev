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
    private IAttribute<T> m_attribute;

    /** The console statelet for use in the test. */
    private IStatelet m_statelet;


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
     *         never {@code null}. An empty collection if the attribute does
     *         not define any illegal values.
     */
    /* @NonNull */
    protected abstract Collection<T> getIllegalAttributeValues();

    /**
     * Gets a legal value for the attribute to be tested.
     * 
     * @return A legal value for the attribute to be tested; may be {@code null}.
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
        m_statelet = new FakeStatelet();
        m_attribute = createAttribute();
        assertNotNull( m_attribute );
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
        m_attribute = null;
        m_statelet = null;
    }

    /**
     * Ensures the {@code add} method adds an attribute that is absent.
     */
    @Test
    public void testAdd_Attribute_Absent()
    {
        final T value = getLegalAttributeValue();

        m_attribute.add( m_statelet, value );

        assertEquals( value, m_statelet.getAttribute( m_attribute.getName() ) );
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
                m_attribute.add( m_statelet, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertFalse( m_statelet.containsAttribute( m_attribute.getName() ) );
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
        m_statelet.addAttribute( m_attribute.getName(), value );

        m_attribute.add( m_statelet, value );
    }

    /**
     * Ensures the {@code add} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testAdd_Statelet_Null()
    {
        m_attribute.add( null, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code ensureGetValue} method does not throw an exception
     * when the attribute is absent.
     */
    @Test
    public void testEnsureGetValue_Attribute_Absent()
    {
        m_attribute.ensureGetValue( m_statelet );
    }

    /**
     * Ensures the {@code ensureGetValue} method retrieves the value when the
     * attribute is present.
     */
    @Test
    public void testEnsureGetValue_Attribute_Present()
    {
        final T expectedValue = getLegalAttributeValue();
        m_statelet.addAttribute( m_attribute.getName(), expectedValue );

        final T actualValue = m_attribute.getValue( m_statelet );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code ensureGetValue} method throws an exception when passed
     * a {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testEnsureGetValue_Statelet_Null()
    {
        m_attribute.ensureGetValue( null );
    }

    /**
     * Ensures the {@code ensureSetValue} method adds the value of an absent
     * attribute.
     */
    @Test
    public void testEnsureSetValue_Attribute_Absent()
    {
        final T expectedValue = getLegalAttributeValue();

        m_attribute.ensureSetValue( m_statelet, expectedValue );

        assertEquals( expectedValue, m_attribute.getValue( m_statelet ) );
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
                m_attribute.ensureSetValue( m_statelet, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertFalse( m_statelet.containsAttribute( m_attribute.getName() ) );
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
        m_statelet.addAttribute( m_attribute.getName(), value1 );

        m_attribute.ensureSetValue( m_statelet, value2 );

        assertEquals( value2, m_statelet.getAttribute( m_attribute.getName() ) );
    }

    /**
     * Ensures the {@code ensureSetValue} method throws an exception when passed
     * a {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testEnsureSetValue_Statelet_Null()
    {
        m_attribute.ensureSetValue( null, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_attribute.getName() );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when the
     * attribute is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetValue_Attribute_Absent()
    {
        m_attribute.getValue( m_statelet );
    }

    /**
     * Ensures the {@code getValue} method retrieves the value when the
     * attribute is present.
     */
    @Test
    public void testGetValue_Attribute_Present()
    {
        final T expectedValue = getLegalAttributeValue();
        m_statelet.addAttribute( m_attribute.getName(), expectedValue );

        final T actualValue = m_attribute.getValue( m_statelet );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testGetValue_Statelet_Null()
    {
        m_attribute.getValue( null );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates an absent
     * attribute.
     */
    @Test
    public void testIsPresent_Attribute_Absent()
    {
        assertFalse( m_attribute.isPresent( m_statelet ) );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates a present
     * attribute.
     */
    @Test
    public void testIsPresent_Attribute_Present()
    {
        m_statelet.addAttribute( m_attribute.getName(), getLegalAttributeValue() );

        assertTrue( m_attribute.isPresent( m_statelet ) );
    }

    /**
     * Ensures the {@code isPresent} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testIsPresent_State_Null()
    {
        m_attribute.isPresent( null );
    }

    /**
     * Ensures the {@code remove} throws an exception when the attribute is
     * absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemove_Attribute_Absent()
    {
        m_attribute.remove( m_statelet );
    }

    /**
     * Ensures the {@code remove} method behaves correctly when the attribute is
     * present.
     */
    @Test
    public void testRemove_Attribute_Present()
    {
        m_statelet.addAttribute( m_attribute.getName(), getLegalAttributeValue() );

        m_attribute.remove( m_statelet );

        assertFalse( m_statelet.containsAttribute( m_attribute.getName() ) );
    }

    /**
     * Ensures the {@code remove} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testRemove_Statelet_Null()
    {
        m_attribute.remove( null );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when the
     * attribute is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetValue_Attribute_Absent()
    {
        m_attribute.setValue( m_statelet, getLegalAttributeValue() );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed an
     * illegal attribute value.
     */
    @Test
    public void testSetValue_Attribute_Absent_IllegalValue()
    {
        final T originalValue = getLegalAttributeValue();
        m_statelet.addAttribute( m_attribute.getName(), originalValue );

        for( final T value : getIllegalAttributeValues() )
        {
            try
            {
                m_attribute.setValue( m_statelet, value );
                fail( "expected exception of type java.lang.IllegalArgumentException" ); //$NON-NLS-1$
            }
            catch( final IllegalArgumentException e )
            {
                assertEquals( originalValue, m_statelet.getAttribute( m_attribute.getName() ) );
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
        m_statelet.addAttribute( m_attribute.getName(), value1 );

        m_attribute.setValue( m_statelet, value2 );

        assertEquals( value2, m_statelet.getAttribute( m_attribute.getName() ) );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed a
     * {@code null} statelet.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValue_Statelet_Null()
    {
        m_attribute.setValue( null, getLegalAttributeValue() );
    }
}
