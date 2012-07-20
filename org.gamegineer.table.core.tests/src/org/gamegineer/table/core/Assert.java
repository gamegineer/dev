/*
 * Assert.java
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
 * Created on Jun 9, 2011 at 10:31:32 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of assertion methods useful for writing table model tests.
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
     * Asserts that two components are equal.
     * 
     * @param expected
     *        The expected value; may be {@code null}.
     * @param actual
     *        The actual value; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    public static void assertComponentEquals(
        /* @Nullable */
        final IComponent expected,
        /* @Nullable */
        final IComponent actual )
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
            assertEquals( expected.getBounds(), actual.getBounds() );
            assertEquals( expected.getOrientation(), actual.getOrientation() );
            assertEquals( expected.getOrigin(), actual.getOrigin() );

            assertEquals( expected.getSupportedOrientations(), actual.getSupportedOrientations() );
            for( final ComponentOrientation orientation : expected.getSupportedOrientations() )
            {
                assertEquals( expected.getSurfaceDesign( orientation ), actual.getSurfaceDesign( orientation ) );
            }

            if( expected instanceof IContainer )
            {
                assertTrue( actual instanceof IContainer );
                assertContainerEquals( (IContainer)expected, (IContainer)actual );
            }
            else if( actual instanceof IContainer )
            {
                assertTrue( expected instanceof IContainer );
                assertContainerEquals( (IContainer)expected, (IContainer)actual );
            }
        }
    }

    /**
     * Asserts that two containers are equal.
     * 
     * @param expected
     *        The expected value; must not be {@code null}.
     * @param actual
     *        The actual value; must not be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    private static void assertContainerEquals(
        /* @NonNull */
        final IContainer expected,
        /* @NonNull */
        final IContainer actual )
    {
        assert expected != null;
        assert actual != null;

        assertEquals( expected.getLayout(), actual.getLayout() );

        final List<IComponent> expectedComponents = expected.getComponents();
        final List<IComponent> actualComponents = actual.getComponents();
        assertEquals( expectedComponents.size(), actualComponents.size() );
        for( int index = 0, size = expectedComponents.size(); index < size; ++index )
        {
            assertComponentEquals( expectedComponents.get( index ), actualComponents.get( index ) );
        }
    }

    /**
     * Asserts that two tables are equal.
     * 
     * @param expected
     *        The expected value; may be {@code null}.
     * @param actual
     *        The actual value; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two values are not equal.
     */
    public static void assertTableEquals(
        /* @Nullable */
        final ITable expected,
        /* @Nullable */
        final ITable actual )
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
            assertComponentEquals( expected.getTabletop(), actual.getTabletop() );
        }
    }
}
