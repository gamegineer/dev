/*
 * AbstractEqualityComparatorTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on May 17, 2012 at 8:36:09 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.util.IEqualityComparator} interface.
 * 
 * @param <T>
 *        The type of the comparable object.
 */
public abstract class AbstractEqualityComparatorTestCase<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The equality comparator under test in the fixture. */
    private IEqualityComparator<T> equalityComparator_;

    /** The reference instance for use in the fixture. */
    private T reference_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractEqualityComparatorTestCase} class.
     */
    protected AbstractEqualityComparatorTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the equality comparator to be tested.
     * 
     * @return The equality comparator to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IEqualityComparator<T> createEqualityComparator()
        throws Exception;

    /**
     * Creates a reference instance for use in the fixture.
     * 
     * <p>
     * Implementors must always return a distinct instance that is equal to any
     * other instance returned by this method.
     * </p>
     * 
     * @return A reference instance; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createReferenceInstance()
        throws Exception;

    /**
     * Creates a collection of instances that are unequal to the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine equality. The returned collection must not be empty and must
     * not contain a {@code null} entry.
     * </p>
     * 
     * @return A collection of instances that are unequal to the reference
     *         instance; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract Collection<T> createUnequalInstances()
        throws Exception;

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
        equalityComparator_ = createEqualityComparator();
        assertNotNull( equalityComparator_ );
        reference_ = createReferenceInstance();
        assertNotNull( reference_ );
    }

    /**
     * Ensures the collection returned by the {@code createUnequalInstances}
     * method satisfy the criteria of this fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateUnequalInstances()
        throws Exception
    {
        final Collection<T> others = createUnequalInstances();

        assertNotNull( others );
        assertFalse( "unequal instances collection must not be empty", others.isEmpty() ); //$NON-NLS-1$
        assertFalse( "unequal instances collection must not contain a null element", others.contains( null ) ); //$NON-NLS-1$
        for( final T other : others )
        {
            assertEquals( "same instance appears multiple times in the unequal instances collection", 1, Collections.frequency( others, other ) ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly indicates two equal but different instances are equal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Equal_NotSame()
        throws Exception
    {
        final T other = createReferenceInstance();

        assertNotSame( reference_, other );
        assertTrue( equalityComparator_.equals( reference_, other ) );
        assertTrue( equalityComparator_.equals( other, reference_ ) ); // symmetric
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly indicates two {@code null} instances are equal.
     */
    @Test
    public void testEquals_Equal_Null()
    {
        assertTrue( equalityComparator_.equals( null, null ) );
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly indicates an instance is equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        assertTrue( equalityComparator_.equals( reference_, reference_ ) ); // reflexive
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly indicates two unequal instances are unequal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Unequal()
        throws Exception
    {
        for( final T other : createUnequalInstances() )
        {
            assertFalse( String.format( "expected <%1$s> and <%2$s> to be unequal", reference_, other ), equalityComparator_.equals( reference_, other ) ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * throws an exception when passed a first instance that has a different
     * class than that expected by the comparator.
     */
    @Test( expected = ClassCastException.class )
    public void testEquals_Unequal_DifferentClass_Obj1()
    {
        @SuppressWarnings( "unchecked" )
        final IEqualityComparator<Object> untypedEqualityComparator = (IEqualityComparator<Object>)equalityComparator_;
        final Object obj1 = new Object()
        {
            // anonymous type in case the comparator generic type really is Object
        };

        untypedEqualityComparator.equals( obj1, reference_ );
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * throws an exception when passed a second instance that has a different
     * class than that expected by the comparator.
     */
    @Test( expected = ClassCastException.class )
    public void testEquals_Unequal_DifferentClass_Obj2()
    {
        @SuppressWarnings( "unchecked" )
        final IEqualityComparator<Object> untypedEqualityComparator = (IEqualityComparator<Object>)equalityComparator_;
        final Object obj2 = new Object()
        {
            // anonymous type in case the comparator generic type really is Object
        };

        untypedEqualityComparator.equals( reference_, obj2 );
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly handles a {@code null} first instance when the second instance
     * is not {@code null}.
     */
    @Test
    public void testEquals_Unequal_Null_Obj1()
    {
        assertFalse( equalityComparator_.equals( null, reference_ ) );
    }

    /**
     * Ensures the {@link IEqualityComparator#equals(Object, Object)} method
     * correctly handles a {@code null} second instance when the first instance
     * is not {@code null}.
     */
    @Test
    public void testEquals_Unequal_Null_Obj2()
    {
        assertFalse( equalityComparator_.equals( reference_, null ) );
    }

    /**
     * Ensures the {@link IEqualityComparator#hashCode(Object)} method returns
     * the same hash code for equal but different instances.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHashCode_Equal()
        throws Exception
    {
        final T other = createReferenceInstance();

        assertNotSame( reference_, other );
        assertEquals( equalityComparator_.hashCode( reference_ ), equalityComparator_.hashCode( other ) );
    }

    /**
     * Ensures the {@link IEqualityComparator#hashCode(Object)} method returns
     * zero for a {@code null} instance.
     */
    @Test
    public void testHashCode_Equal_Null()
    {
        assertEquals( 0, equalityComparator_.hashCode( null ) );
    }

    /**
     * Ensures the {@link IEqualityComparator#hashCode(Object)} method returns a
     * different hash code for unequal instances.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHashCode_Unequal()
        throws Exception
    {
        for( final T other : createUnequalInstances() )
        {
            assertTrue( String.format( "expected hash codes for <%1$s> and <%2$s> to be unequal", reference_, other ), equalityComparator_.hashCode( reference_ ) != equalityComparator_.hashCode( other ) ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link IEqualityComparator#hashCode(Object)} method throws an
     * exception when passed an instance that has a different class than that
     * expected by the comparator.
     */
    @Test( expected = ClassCastException.class )
    public void testHashCode_Unequal_DifferentClass()
    {
        @SuppressWarnings( "unchecked" )
        final IEqualityComparator<Object> untypedEqualityComparator = (IEqualityComparator<Object>)equalityComparator_;
        final Object obj = new Object()
        {
            // anonymous type in case the comparator generic type really is Object
        };

        untypedEqualityComparator.hashCode( obj );
    }
}
