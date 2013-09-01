/*
 * IterableUtilsTest.java
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
 * Created on Apr 25, 2013 at 10:30:43 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

/**
 * A fixture for testing the {@link IterableUtils} class.
 */
public final class IterableUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code IterableUtilsTest} class.
     */
    public IterableUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link IterableUtils#reverse} method returns a view whose
     * iterator advances through the list in the reverse order.
     */
    @Test
    public void testReverse_IteratorAdvancesInReverseOrder()
    {
        final Object element1 = new Object();
        final Object element2 = new Object();
        final Object element3 = new Object();
        final List<Object> list = new ArrayList<>( Arrays.asList( element1, element2, element3 ) );

        final Iterator<Object> iterator = IterableUtils.reverse( list ).iterator();

        assertEquals( element3, iterator.next() );
        assertEquals( element2, iterator.next() );
        assertEquals( element1, iterator.next() );
        assertFalse( iterator.hasNext() );
    }

    /**
     * Ensures the {@link IterableUtils#reverse} method returns a view whose
     * iterator removes the correct element when {@link Iterator#remove} is
     * called.
     */
    @Test
    public void testReverse_IteratorRemovesElement()
    {
        final Object element1 = new Object();
        final Object element2 = new Object();
        final Object element3 = new Object();
        final List<Object> list = new ArrayList<>( Arrays.asList( element1, element2, element3 ) );

        final Iterator<Object> iterator = IterableUtils.reverse( list ).iterator();
        iterator.next();
        iterator.remove();

        assertEquals( 2, list.size() );
        assertFalse( list.contains( element3 ) );
    }

    /**
     * Ensures the {@link IterableUtils#reverse} method throws an exception when
     * passed a {@code null} list.
     */
    @Test( expected = NullPointerException.class )
    public void testReverse_List_Null()
    {
        IterableUtils.reverse( null );
    }
}
