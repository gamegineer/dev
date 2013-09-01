/*
 * AbstractEquatableTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on May 27, 2010 at 10:43:43 PM.
 */

package org.gamegineer.test.core;

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
 * A fixture for testing the basic aspects of classes that are equatable.
 * 
 * <p>
 * An equatable class is any class that overrides both the default
 * implementations of {@link java.lang.Object#equals(Object)} and
 * {@link java.lang.Object#hashCode()}.
 * </p>
 * 
 * @param <T>
 *        The type of the equatable class.
 */
public abstract class AbstractEquatableTestCase<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The reference instance for use in the fixture. */
    private T reference_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEquatableTestCase}
     * class.
     */
    protected AbstractEquatableTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
     * Ensures the {@link Object#equals} method correctly indicates two equal
     * but different instances are equal.
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
        assertEquals( reference_, other );
        assertEquals( other, reference_ ); // symmetric
    }

    /**
     * Ensures the {@link Object#equals} method correctly indicates an instance
     * is equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        assertEquals( reference_, reference_ ); // reflexive
    }

    /**
     * Ensures the {@link Object#equals} method correctly indicates two unequal
     * instances are unequal.
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
            assertFalse( String.format( "expected <%1$s> and <%2$s> to be unequal", reference_, other ), reference_.equals( other ) ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Object#equals} method correctly indicates an instance
     * is unequal to an instance of a different class.
     */
    @Test
    public void testEquals_Unequal_DifferentClass()
    {
        assertFalse( reference_.equals( new Object() ) );
    }

    /**
     * Ensures the {@link Object#equals} method correctly handles a {@code null}
     * instance.
     */
    @Test
    public void testEquals_Unequal_Null()
    {
        assertFalse( reference_.equals( null ) );
    }

    /**
     * Ensures the {@link Object#hashCode} method returns the same hash code for
     * equal but different instances.
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
        assertEquals( reference_.hashCode(), other.hashCode() );
    }

    /**
     * Ensures the {@link Object#hashCode} method returns a different hash code
     * for unequal instances.
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
            assertTrue( String.format( "expected hash codes for <%1$s> and <%2$s> to be unequal", reference_, other ), reference_.hashCode() != other.hashCode() ); //$NON-NLS-1$
        }
    }
}
