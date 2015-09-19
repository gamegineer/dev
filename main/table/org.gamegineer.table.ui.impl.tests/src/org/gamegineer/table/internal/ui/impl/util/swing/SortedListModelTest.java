/*
 * SortedListModelTest.java
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
 * Created on Oct 16, 2011 at 5:33:00 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import static org.junit.Assert.assertEquals;
import java.util.Comparator;
import javax.swing.DefaultListModel;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Test;

/**
 * A fixture for testing the {@link SortedListModel} class.
 */
public final class SortedListModelTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SortedListModelTest} class.
     */
    public SortedListModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link SortedListModel#getElementAt} method returns the
     * correct element for a sorted list model that uses a custom comparator.
     */
    @Test
    public void testGetElementAt_CustomComparator()
    {
        final @NonNull FakeElement[] elements = new @NonNull FakeElement[] { //
            new FakeElement( 3 ), //
            new FakeElement( 2 ), //
            new FakeElement( 1 ), //
            new FakeElement( 0 )
        };
        final DefaultListModel<FakeElement> unsortedListModel = new DefaultListModel<>();
        for( final FakeElement element : elements )
        {
            unsortedListModel.addElement( element );
        }
        final SortedListModel<FakeElement> sortedListModel = new SortedListModel<>( unsortedListModel, new Comparator<FakeElement>()
        {
            @Override
            public int compare(
                final @Nullable FakeElement o1,
                final @Nullable FakeElement o2 )
            {
                assert o1 != null;
                assert o2 != null;

                return o1.value - o2.value;
            }
        } );

        assertEquals( elements[ 3 ], sortedListModel.getElementAt( 0 ) );
        assertEquals( elements[ 2 ], sortedListModel.getElementAt( 1 ) );
        assertEquals( elements[ 1 ], sortedListModel.getElementAt( 2 ) );
        assertEquals( elements[ 0 ], sortedListModel.getElementAt( 3 ) );
    }

    /**
     * Ensures the {@link SortedListModel#getElementAt} method returns the
     * correct element for a sorted list model that uses the default comparator.
     */
    @Test
    public void testGetElementAt_DefaultComparator()
    {
        final @NonNull String[] elements = new @NonNull String[] { //
            "element3", //$NON-NLS-1$
            "element2", //$NON-NLS-1$
            "element1", //$NON-NLS-1$
            "element0" //$NON-NLS-1$
        };
        final DefaultListModel<String> unsortedListModel = new DefaultListModel<>();
        for( final String element : elements )
        {
            unsortedListModel.addElement( element );
        }
        final SortedListModel<String> sortedListModel = new SortedListModel<>( unsortedListModel );

        assertEquals( elements[ 3 ], sortedListModel.getElementAt( 0 ) );
        assertEquals( elements[ 2 ], sortedListModel.getElementAt( 1 ) );
        assertEquals( elements[ 1 ], sortedListModel.getElementAt( 2 ) );
        assertEquals( elements[ 0 ], sortedListModel.getElementAt( 3 ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake list model element.
     */
    @Immutable
    private static final class FakeElement
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The element value. */
        final int value;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeElement} class.
         * 
         * @param value
         *        The element value.
         */
        FakeElement(
            final int value )
        {
            this.value = value;
        }
    }
}
