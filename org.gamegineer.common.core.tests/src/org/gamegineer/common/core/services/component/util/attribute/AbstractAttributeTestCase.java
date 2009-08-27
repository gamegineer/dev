/*
 * AbstractAttributeTestCase.java
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
 * Created on May 18, 2008 at 10:08:31 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collections;
import java.util.List;
import org.gamegineer.common.core.services.component.MockComponentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 * 
 * @param <T>
 *        The type of the attribute.
 */
public abstract class AbstractAttributeTestCase<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private IAttribute<T> m_attribute;


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
     */
    /* @NonNull */
    protected abstract IAttribute<T> createAttribute();

    /**
     * Gets a collection of illegal attribute values.
     * 
     * <p>
     * The default implementation returns an empty collection.
     * </p>
     * 
     * @return A collection of illegal attribute values; never {@code null}.
     */
    /* @NonNull */
    protected List<T> getIllegalValues()
    {
        return Collections.emptyList();
    }

    /**
     * Gets a collection of legal attribute values.
     * 
     * <p>
     * The collection must contain at least one element.
     * </p>
     * 
     * @return A collection of legal attribute values; never {@code null}.
     */
    /* @NonNull */
    protected abstract List<T> getLegalValues();

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
     * Ensures the {@code getValue} method throws an exception when passed a
     * {@code null} accessor.
     */
    @Test( expected = NullPointerException.class )
    public void testGetValue_Accessor_Null()
    {
        m_attribute.getValue( null );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when the value is
     * absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetValue_Value_Absent()
    {
        m_attribute.getValue( new MockComponentFactory() );
    }

    /**
     * Ensures the {@code getValue} method throws an exception when reading an
     * illegal value.
     */
    @Test
    public void testGetValue_Value_Illegal()
    {
        for( final T value : getIllegalValues() )
        {
            final MockComponentFactory factory = new MockComponentFactory();
            factory.setAttribute( m_attribute.getName(), value );
            try
            {
                m_attribute.getValue( factory );
                fail();
            }
            catch( final IllegalArgumentException e )
            {
                // expected
            }
        }
    }

    /**
     * Ensures the {@code getValue} method does not throw an exception when
     * reading a legal value.
     */
    @Test
    public void testGetValue_Value_Legal()
    {
        for( final T value : getLegalValues() )
        {
            final MockComponentFactory factory = new MockComponentFactory();
            factory.setAttribute( m_attribute.getName(), value );
            m_attribute.getValue( factory );
        }
    }

    /**
     * Ensures the {@code isPresent} method throws an exception when passed a
     * {@code null} accessor.
     */
    @Test( expected = NullPointerException.class )
    public void testIsPresent_Accessor_Null()
    {
        m_attribute.isPresent( null );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates a value is
     * absent from the accessor.
     */
    @Test
    public void testIsPresent_Value_Absent()
    {
        assertFalse( m_attribute.isPresent( new MockComponentFactory() ) );
    }

    /**
     * Ensures the {@code isPresent} method correctly indicates a value is
     * present in the accessor.
     */
    @Test
    public void testIsPresent_Value_Present()
    {
        final MockComponentFactory factory = new MockComponentFactory();
        factory.setAttribute( m_attribute.getName(), getLegalValues().get( 0 ) );

        final boolean isPresent = m_attribute.isPresent( factory );

        assertTrue( isPresent );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed a
     * {@code null} mutator.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValue_Mutator_Null()
    {
        m_attribute.setValue( null, getLegalValues().get( 0 ) );
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed an
     * illegal value.
     */
    @Test
    public void testSetValue_Value_Illegal()
    {
        for( final T value : getIllegalValues() )
        {
            try
            {
                m_attribute.setValue( new MockComponentFactory(), value );
                fail();
            }
            catch( final IllegalArgumentException e )
            {
                // expected
            }
        }
    }

    /**
     * Ensures the {@code setValue} method does not throw an exception when
     * passed a legal value.
     */
    @Test
    public void testSetValue_Value_Legal()
    {
        for( final T value : getLegalValues() )
        {
            m_attribute.setValue( new MockComponentFactory(), value );
        }
    }

    /**
     * Ensures the {@code setValue} method throws an exception when passed a
     * {@code null} value.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValue_Value_Null()
    {
        m_attribute.setValue( new MockComponentFactory(), null );
    }

    /**
     * Ensures the {@code tryGetValue} method throws an exception when passed a
     * {@code null} accessor.
     */
    @Test( expected = NullPointerException.class )
    public void testTryGetValue_Accessor_Null()
    {
        m_attribute.tryGetValue( null );
    }

    /**
     * Ensures the {@code tryGetValue} method returns {@code null} when the
     * value is absent.
     */
    @Test
    public void testTryGetValue_Value_Absent()
    {
        assertNull( m_attribute.tryGetValue( new MockComponentFactory() ) );
    }

    /**
     * Ensures the {@code tryGetValue} method throws an exception when reading
     * an illegal value.
     */
    @Test
    public void testTryGetValue_Value_Illegal()
    {
        for( final T value : getIllegalValues() )
        {
            final MockComponentFactory factory = new MockComponentFactory();
            factory.setAttribute( m_attribute.getName(), value );
            try
            {
                m_attribute.tryGetValue( factory );
                fail();
            }
            catch( final IllegalArgumentException e )
            {
                // expected
            }
        }
    }

    /**
     * Ensures the {@code tryGetValue} method does not throw an exception when
     * reading a legal value.
     */
    @Test
    public void testTryGetValue_Value_Legal()
    {
        for( final T value : getLegalValues() )
        {
            final MockComponentFactory factory = new MockComponentFactory();
            factory.setAttribute( m_attribute.getName(), value );
            m_attribute.tryGetValue( factory );
        }
    }

    /**
     * Ensures the {@code trySetValue} method throws an exception when passed a
     * {@code null} mutator.
     */
    @Test( expected = NullPointerException.class )
    public void testTrySetValue_Mutator_Null()
    {
        m_attribute.trySetValue( null, getLegalValues().get( 0 ) );
    }

    /**
     * Ensures the {@code trySetValue} method throws an exception when passed an
     * illegal value.
     */
    @Test
    public void testTrySetValue_Value_Illegal()
    {
        for( final T value : getIllegalValues() )
        {
            try
            {
                m_attribute.trySetValue( new MockComponentFactory(), value );
                fail();
            }
            catch( final IllegalArgumentException e )
            {
                // expected
            }
        }
    }

    /**
     * Ensures the {@code trySetValue} method does not throw an exception when
     * passed a legal value.
     */
    @Test
    public void testTrySetValue_Value_Legal()
    {
        for( final T value : getLegalValues() )
        {
            m_attribute.trySetValue( new MockComponentFactory(), value );
        }
    }

    /**
     * Ensures the {@code trySetValue} method does nothing when passed a
     * {@code null} value.
     */
    @Test
    public void testTrySetValue_Value_Null()
    {
        final MockComponentFactory factory = new MockComponentFactory();

        m_attribute.trySetValue( factory, null );

        assertFalse( m_attribute.isPresent( factory ) );
    }
}
