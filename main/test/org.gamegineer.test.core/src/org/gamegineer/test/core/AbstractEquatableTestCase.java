/*
 * AbstractEquatableTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that are equatable.
 * 
 * <p>
 * An equatable class is any class that overrides both the default
 * implementations of {@link Object#equals} and {@link Object#hashCode}.
 * </p>
 * 
 * @param <T>
 *        The type of the equatable class.
 */
public abstract class AbstractEquatableTestCase<T>
{
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
     * @return A reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract T createReferenceInstance()
        throws Exception;

    /**
     * Creates a collection of instances that are unequal to the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine equality. The returned collection must not be empty.
     * </p>
     * 
     * @return A collection of instances that are unequal to the reference
     *         instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract Collection<T> createUnequalInstances()
        throws Exception;

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
        final T reference = createReferenceInstance();
        final T other = createReferenceInstance();

        assertNotSame( reference, other );
        assertEquals( reference, other );
        assertEquals( other, reference ); // symmetric
    }

    /**
     * Ensures the {@link Object#equals} method correctly indicates an instance
     * is equal to itself.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Equal_Same()
        throws Exception
    {
        final T reference = createReferenceInstance();

        assertEquals( reference, reference ); // reflexive
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
        final T reference = createReferenceInstance();
        for( final T other : createUnequalInstances() )
        {
            assertFalse( String.format( "expected <%1$s> and <%2$s> to be unequal", reference, other ), reference.equals( other ) ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Object#equals} method correctly indicates an instance
     * is unequal to an instance of a different class.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Unequal_DifferentClass()
        throws Exception
    {
        final T reference = createReferenceInstance();

        assertFalse( reference.equals( new Object() ) );
    }

    /**
     * Ensures the {@link Object#equals} method correctly handles a {@code null}
     * instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Unequal_Null()
        throws Exception
    {
        final T reference = createReferenceInstance();

        assertFalse( reference.equals( null ) );
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
        final T reference = createReferenceInstance();
        final T other = createReferenceInstance();

        assertNotSame( reference, other );
        assertEquals( reference.hashCode(), other.hashCode() );
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
        final T reference = createReferenceInstance();
        for( final T other : createUnequalInstances() )
        {
            assertTrue( String.format( "expected hash codes for <%1$s> and <%2$s> to be unequal", reference, other ), reference.hashCode() != other.hashCode() ); //$NON-NLS-1$
        }
    }
}
