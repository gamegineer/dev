/*
 * AbstractComparableTestCase.java
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
 * Created on Jan 17, 2013 at 10:59:15 PM.
 */

package org.gamegineer.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link Comparable} interface.
 * 
 * @param <T>
 *        The type of the comparable class.
 */
public abstract class AbstractComparableTestCase<T extends Comparable<T>>
    extends AbstractEquatableTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComparableTestCase}
     * class.
     */
    protected AbstractComparableTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a collection of instances that are greater than the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine comparability. The returned collection must not be empty.
     * </p>
     * 
     * @return A collection of instances that are greater than the reference
     *         instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract Collection<T> createGreaterThanInstances()
        throws Exception;

    /**
     * Creates a collection of instances that are less than the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine comparability. The returned collection must not be empty.
     * </p>
     * 
     * @return A collection of instances that are less than the reference
     *         instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract Collection<T> createLessThanInstances()
        throws Exception;

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are equal to the reference instance are equal to the
     * reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCompareTo_Equal()
        throws Exception
    {
        final T reference1 = createReferenceInstance();
        final T reference2 = createReferenceInstance();

        assertEquals( 0, reference1.compareTo( reference2 ) );
        assertEquals( 0, reference2.compareTo( reference1 ) );
    }

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are greater than the reference instance are greater than
     * the reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCompareTo_GreaterThan()
        throws Exception
    {
        final T reference = createReferenceInstance();

        for( final T other : createGreaterThanInstances() )
        {
            assertTrue( String.format( "expected <%s> to be less than <%s>", reference, other ), reference.compareTo( other ) < 0 ); //$NON-NLS-1$
            assertTrue( String.format( "expected <%s> to be greater than <%s>", other, reference ), other.compareTo( reference ) > 0 ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are less than the reference instance are less than the
     * reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCompareTo_LessThan()
        throws Exception
    {
        final T reference = createReferenceInstance();

        for( final T other : createLessThanInstances() )
        {
            assertTrue( String.format( "expected <%s> to be greater than <%s>", reference, other ), reference.compareTo( other ) > 0 ); //$NON-NLS-1$
            assertTrue( String.format( "expected <%s> to be less than <%s>", other, reference ), other.compareTo( reference ) < 0 ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Comparable#compareTo} method throws an exception when
     * passed a {@code null} instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCompareTo_Other_Null()
        throws Exception
    {
        @SuppressWarnings( "null" )
        final Comparable<@Nullable T> reference = (Comparable<@Nullable T>)createReferenceInstance();

        reference.compareTo( null );
    }
}
