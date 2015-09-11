/*
 * SortedListModel.java
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
 * Created on Oct 16, 2011 at 5:32:38 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A decorator for the {@link ListModel} interface that ensures its elements are
 * sorted.
 * 
 * @param <E>
 *        The type of the model elements.
 * 
 * @author John O'Conner
 */
@NotThreadSafe
public final class SortedListModel<E>
    extends AbstractListModel<E>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4725390083082651280L;

    /** The comparator used to sort the list model. */
    private final Comparator<E> comparator_;

    /** The collection of sorted list model entries. */
    private final List<Entry> sortedEntries_;

    /** The unsorted list model. */
    private final ListModel<E> unsortedListModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SortedListModel} class using the
     * default comparator.
     * 
     * @param listModel
     *        The unsorted list model; must not be {@code null}.
     */
    public SortedListModel(
        final ListModel<E> listModel )
    {
        this( listModel, null );
    }

    /**
     * Initializes a new instance of the {@code SortedListModel} class using the
     * specified comparator.
     * 
     * @param listModel
     *        The unsorted list model; must not be {@code null}.
     * @param comparator
     *        The comparator used to sort the list model or {@code null} to use
     *        the default comparator.
     */
    public SortedListModel(
        final ListModel<E> listModel,
        @Nullable
        final Comparator<E> comparator )
    {
        comparator_ = (comparator != null) ? comparator : createDefaultComparator();
        sortedEntries_ = new ArrayList<>( listModel.getSize() );
        unsortedListModel_ = listModel;

        registerListeners();
        initializeSortedEntries();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a default comparator used to sort elements by their natural
     * order.
     * 
     * @return A default comparator; never {@code null}.
     */
    private Comparator<E> createDefaultComparator()
    {
        return new Comparator<E>()
        {
            @Override
            @SuppressWarnings( "unchecked" )
            public int compare(
                @Nullable
                final E o1,
                @Nullable
                final E o2 )
            {
                assert o1 != null;
                assert o2 != null;

                return ((Comparable<E>)o1).compareTo( o2 );
            }
        };
    }

    /*
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Nullable
    @Override
    public E getElementAt(
        final int index )
    {
        final int unsortedIndex = toUnsortedListModelIndex( index );
        return unsortedListModel_.getElementAt( unsortedIndex );
    }

    /**
     * Gets the index in the collection of sorted list model entries into which
     * the specified entry should be inserted.
     * 
     * @param entry
     *        The entry to be inserted into the collection of sorted list model
     *        entries; must not be {@code null}.
     * 
     * @return The index in the collection of sorted list model entries into
     *         which the specified entry should be inserted.
     */
    private int getInsertionIndex(
        final Entry entry )
    {
        int sortedIndex = Collections.binarySearch( sortedEntries_, entry );
        if( sortedIndex < 0 )
        {
            sortedIndex = -(sortedIndex + 1);
        }

        return sortedIndex;
    }

    /*
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize()
    {
        return sortedEntries_.size();
    }

    /**
     * Initializes the collection of sorted list model entries.
     */
    private void initializeSortedEntries()
    {
        for( int unsortedIndex = 0, size = unsortedListModel_.getSize(); unsortedIndex < size; ++unsortedIndex )
        {
            final Entry entry = new Entry( unsortedIndex );
            final int sortedIndex = getInsertionIndex( entry );
            sortedEntries_.add( sortedIndex, entry );
        }
    }

    /**
     * Registers all listeners associated with this object.
     */
    private void registerListeners()
    {
        @SuppressWarnings( "synthetic-access" )
        final ListDataListener listDataListener = new ListDataListener()
        {
            @Override
            public void contentsChanged(
                @Nullable
                final ListDataEvent event )
            {
                assert event != null;

                unsortedContentsChanged( event );
            }

            @Override
            public void intervalAdded(
                @Nullable
                final ListDataEvent event )
            {
                assert event != null;

                unsortedIntervalAdded( event );
            }

            @Override
            public void intervalRemoved(
                @Nullable
                final ListDataEvent event )
            {
                assert event != null;

                unsortedIntervalRemoved( event );
            }
        };
        unsortedListModel_.addListDataListener( listDataListener );
    }

    /**
     * Convert the specified sorted list model index to its associated unsorted
     * list model index.
     * 
     * @param sortedIndex
     *        An index into the sorted list model.
     * 
     * @return modelIndex an index in the unsorted model
     */
    private int toUnsortedListModelIndex(
        final int sortedIndex )
    {
        return sortedEntries_.get( sortedIndex ).getIndex();
    }

    /**
     * Invoked when the contents of the unsorted list model have changed.
     * 
     * @param event
     *        Describes the event; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void unsortedContentsChanged(
        @SuppressWarnings( "unused" )
        final ListDataEvent event )
    {
        Collections.sort( sortedEntries_ );
        fireContentsChanged( ListDataEvent.CONTENTS_CHANGED, 0, sortedEntries_.size() - 1 );
    }

    /**
     * Invoked after an interval of elements have been added to the unsorted
     * list model.
     * 
     * @param event
     *        Describes the event; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void unsortedIntervalAdded(
        final ListDataEvent event )
    {
        final int beginUnsortedIndex = event.getIndex0();
        final int endUnsortedIndex = event.getIndex1();
        final int elementsAdded = endUnsortedIndex - beginUnsortedIndex + 1;

        // Items in the decorated model have shifted in flight.
        // Increment our model pointers into the decorated model.
        // We must increment indices that intersect with the insertion
        // point in the decorated model.
        for( final Entry entry : sortedEntries_ )
        {
            final int unsortedIndex = entry.getIndex();
            if( unsortedIndex >= beginUnsortedIndex )
            {
                entry.setIndex( unsortedIndex + elementsAdded );
            }
        }

        // Add the new items from the decorated model
        for( int unsortedIndex = beginUnsortedIndex; unsortedIndex <= endUnsortedIndex; ++unsortedIndex )
        {
            final Entry entry = new Entry( unsortedIndex );
            final int sortedIndex = getInsertionIndex( entry );
            sortedEntries_.add( sortedIndex, entry );
            fireIntervalAdded( ListDataEvent.INTERVAL_ADDED, sortedIndex, sortedIndex );
        }
    }

    /**
     * Invoked after an interval of elements have been removed from the unsorted
     * list model.
     * 
     * @param event
     *        Describes the event; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void unsortedIntervalRemoved(
        final ListDataEvent event )
    {
        final int beginUnsortedIndex = event.getIndex0();
        final int endUnsortedIndex = event.getIndex1();
        final int elementsRemoved = endUnsortedIndex - beginUnsortedIndex + 1;

        // Move from end to beginning of our sorted model, updating
        // element indices into the decorated model or removing
        // elements as necessary.
        final int sortedEntriesSize = sortedEntries_.size();
        final boolean[] wasElementRemoved = new boolean[ sortedEntriesSize ];
        for( int sortedIndex = sortedEntriesSize - 1; sortedIndex >= 0; --sortedIndex )
        {
            final Entry entry = sortedEntries_.get( sortedIndex );
            final int unsortedIndex = entry.getIndex();
            if( unsortedIndex > endUnsortedIndex )
            {
                entry.setIndex( unsortedIndex - elementsRemoved );
            }
            else if( unsortedIndex >= beginUnsortedIndex )
            {
                sortedEntries_.remove( sortedIndex );
                wasElementRemoved[ sortedIndex ] = true;
            }
        }

        for( int sortedIndex = sortedEntriesSize - 1; sortedIndex >= 0; --sortedIndex )
        {
            if( wasElementRemoved[ sortedIndex ] )
            {
                fireIntervalRemoved( ListDataEvent.INTERVAL_REMOVED, sortedIndex, sortedIndex );
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An entry in the sorted list model.
     */
    @NotThreadSafe
    private final class Entry
        implements Comparable<Entry>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The index of the associated element in the unsorted list model. */
        private int index_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Entry} class.
         * 
         * @param index
         *        The index of the associated element in the unsorted list
         *        model.
         */
        Entry(
            final int index )
        {
            index_ = index;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        @SuppressWarnings( {
            "synthetic-access", "unchecked"
        } )
        public int compareTo(
            @Nullable
            final Entry other )
        {
            assert other != null;

            final Object element = unsortedListModel_.getElementAt( index_ );
            final Object otherElement = unsortedListModel_.getElementAt( other.getIndex() );
            return ((Comparator<Object>)comparator_).compare( element, otherElement );
        }

        /**
         * Gets the index of the associated element in the unsorted list model.
         * 
         * @return The index of the associated element in the unsorted list
         *         model.
         */
        int getIndex()
        {
            return index_;
        }

        /**
         * Sets the index of the associated element in the unsorted list model.
         * 
         * @param index
         *        The index of the associated element in the unsorted list
         *        model.
         */
        void setIndex(
            final int index )
        {
            index_ = index;
        }
    }
}
