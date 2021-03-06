/*
 * Assert.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on May 17, 2012 at 8:36:39 PM.
 */

package org.gamegineer.common.core.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.IEqualityComparator;
import org.gamegineer.common.internal.core.test.Activator;

/**
 * A collection of assertion methods useful for writing tests using the common
 * utility types.
 */
@ThreadSafe
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the specified objects are equal.
     * 
     * <p>
     * This method will attempt to first locate an implementation of
     * {@link IEqualityComparator} suitable for comparing the specified objects
     * for equality. If such an implementation cannot be located, this method
     * will fall back to using {@link Object#equals}.
     * </p>
     * 
     * @param expected
     *        The expected object.
     * @param actual
     *        The actual object.
     * 
     * @throws java.lang.AssertionError
     *         If the objects are not equal.
     */
    public static void assertObjectEquals(
        final @Nullable Object expected,
        final @Nullable Object actual )
    {
        if( expected == null )
        {
            assertNull( actual );
        }
        else if( actual == null )
        {
            assertNull( expected );
        }
        else
        {
            if( assertEqualsUsingEqualityComparator( expected, actual, getPlatformEqualityComparator( expected ) ) )
            {
                return;
            }
            else if( assertEqualsUsingEqualityComparator( expected, actual, getPlatformEqualityComparator( actual ) ) )
            {
                return;
            }

            assertEquals( expected, actual );
        }
    }

    /**
     * Asserts that the specified objects are equal using the specified equality
     * comparator.
     * 
     * @param expected
     *        The expected object.
     * @param actual
     *        The actual object.
     * @param equalityComparator
     *        The equality comparator.
     * 
     * @return {@code true} if the objects are equal or {@code false} if the
     *         objects could not be compared using the specified equality
     *         comparator.
     * 
     * @throws java.lang.AssertionError
     *         If the objects are not equal.
     */
    private static boolean assertEqualsUsingEqualityComparator(
        final Object expected,
        final Object actual,
        final @Nullable IEqualityComparator<Object> equalityComparator )
    {
        if( equalityComparator == null )
        {
            return false;
        }

        try
        {
            if( equalityComparator.equals( expected, actual ) )
            {
                return true;
            }
        }
        catch( final @SuppressWarnings( "unused" ) ClassCastException e )
        {
            return false;
        }

        throw new AssertionError( String.format( "expected:<%s> but was:<%s>", expected, actual ) ); //$NON-NLS-1$
    }

    /**
     * Gets the platform equality comparator associated with the specified
     * object.
     * 
     * @param obj
     *        The object.
     * 
     * @return The platform equality comparator associated with the specified
     *         object or {@code null} if no equality comparator is available.
     */
    private static @Nullable IEqualityComparator<Object> getPlatformEqualityComparator(
        final Object obj )
    {
        final IAdapterManager adapterManager = Activator.getDefault().getAdapterManager();
        if( adapterManager == null )
        {
            return null;
        }

        @SuppressWarnings( "all" )
        final IEqualityComparator<Object> equalityComparator = adapterManager.getAdapter( obj, IEqualityComparator.class );
        return equalityComparator;
    }
}
