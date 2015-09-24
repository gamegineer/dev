/*
 * ObjectUtilsTest.java
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
 * Created on Jun 10, 2012 at 5:51:18 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * A fixture for testing the {@link ObjectUtilsTest} class.
 */
public final class ObjectUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectUtilsTest} class.
     */
    public ObjectUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ObjectUtils#equals(Object, Object)} method returns
     * {@code true} when the first object is not {@code null} and the second
     * object is not {@code null} and the objects are equal.
     */
    @Test
    public void testEquals_Obj1_NonNull_Obj2_NonNull_Equal()
    {
        final Object obj = new Object();

        assertTrue( ObjectUtils.equals( obj, obj ) );
    }

    /**
     * Ensures the {@link ObjectUtils#equals(Object, Object)} method returns
     * {@code false} when the first object is not {@code null} and the second
     * object is not {@code null} and the objects are not equal.
     */
    @Test
    public void testEquals_Obj1_NonNull_Obj2_NonNull_Unequal()
    {
        assertFalse( ObjectUtils.equals( new Object(), new Object() ) );
    }

    /**
     * Ensures the {@link ObjectUtils#equals(Object, Object)} method returns
     * {@code false} when the first object is not {@code null} and the second
     * object is {@code null}.
     */
    @Test
    public void testEquals_Obj1_NonNull_Obj2_Null()
    {
        assertFalse( ObjectUtils.equals( new Object(), null ) );
    }

    /**
     * Ensures the {@link ObjectUtils#equals(Object, Object)} method returns
     * {@code false} when the first object is {@code null} and the second object
     * is not {@code null}.
     */
    @Test
    public void testEquals_Obj1_Null_Obj2_NonNull()
    {
        assertFalse( ObjectUtils.equals( null, new Object() ) );
    }

    /**
     * Ensures the {@link ObjectUtils#equals(Object, Object)} method returns
     * {@code true} when the first object is {@code null} and the second object
     * is {@code null}.
     */
    @Test
    public void testEquals_Obj1_Null_Obj2_Null()
    {
        assertTrue( ObjectUtils.equals( null, null ) );
    }

    /**
     * Ensures the {@link ObjectUtils#hashCode(Object)} method returns the
     * correct value when the object is not {@code null}.
     */
    @Test
    public void testHashCode_Obj_NonNull()
    {
        final Object obj = new Object();
        final int expectedValue = obj.hashCode();

        final int actualValue = ObjectUtils.hashCode( obj );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link ObjectUtils#hashCode(Object)} method returns the
     * correct value when the object is {@code null}.
     */
    @Test
    public void testHashCode_Obj_Null()
    {
        assertEquals( 0, ObjectUtils.hashCode( null ) );
    }

    /**
     * Ensures the {@link ObjectUtils#toString(Object)} method returns the
     * correct value when the object is not {@code null}.
     */
    @Test
    public void testToString_Obj_NonNull()
    {
        final Object obj = new Object();
        final String expectedValue = obj.toString();

        final String actualValue = ObjectUtils.toString( obj );

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@link ObjectUtils#toString(Object)} method returns the
     * correct value when the object is {@code null}.
     */
    @Test
    public void testToString_Obj_Null()
    {
        assertFalse( ObjectUtils.toString( null ).isEmpty() );
    }
}
