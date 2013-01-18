/*
 * ComparableUtilsTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jan 17, 2013 at 11:34:02 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.util.ComparableUtils} class.
 */
public final class ComparableUtilsTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The first object for use in the fixture. */
    private static final Integer OBJECT_1 = new Integer( -100 );

    /** The second object for use in the fixture. */
    private static final Integer OBJECT_2 = new Integer( 100 );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComparableUtilsTest} class.
     */
    public ComparableUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is not {@code null} and the second object is
     * not {@code null} and both objects are equal.
     */
    @Test
    public void testCompareTo_Obj1_NonNull_Obj2_NonNull_Equal()
    {
        assertEquals( 0, ComparableUtils.compareTo( OBJECT_1, OBJECT_1 ) );
    }

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is not {@code null} and the second object is
     * not {@code null} and the first object is greater than the second object.
     */
    @Test
    public void testCompareTo_Obj1_NonNull_Obj2_NonNull_GreaterThan()
    {
        assertTrue( ComparableUtils.compareTo( OBJECT_2, OBJECT_1 ) > 0 );
    }

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is not {@code null} and the second object is
     * not {@code null} and the first object is less than the second object.
     */
    @Test
    public void testCompareTo_Obj1_NonNull_Obj2_NonNull_LessThan()
    {
        assertTrue( ComparableUtils.compareTo( OBJECT_1, OBJECT_2 ) < 0 );
    }

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is not {@code null} and the second object is
     * {@code null}.
     */
    @Test
    public void testCompareTo_Obj1_NonNull_Obj2_Null()
    {
        assertTrue( ComparableUtils.compareTo( OBJECT_1, null ) > 0 );
    }

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is {@code null} and the second object is not
     * {@code null}.
     */
    @Test
    public void testCompareTo_Obj1_Null_Obj2_NonNull()
    {
        assertTrue( ComparableUtils.compareTo( null, OBJECT_1 ) < 0 );
    }

    /**
     * Ensures the {@link ComparableUtils#compareTo} method returns the correct
     * result when the first object is {@code null} and the second object is
     * {@code null}.
     */
    @Test
    public void testCompareTo_Obj1_Null_Obj2_Null()
    {
        assertEquals( 0, ComparableUtils.compareTo( null, null ) );
    }
}
