/*
 * DragContext.java
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
 * Created on Feb 22, 2013 at 9:46:18 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IDragContext;
import org.gamegineer.table.core.IDragStrategy;

/**
 * Implementation of {@link org.gamegineer.table.core.IDragContext}.
 */
@ThreadSafe
final class DragContext
    implements IDragContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The initial drag location in table coordinates. */
    private final Point initialLocation_;

    /**
     * The container used to hold the components being dragged during the
     * drag-and-drop operation.
     */
    private final Container mobileContainer_;

    /** The original origin of the mobile container in table coordinates. */
    private final Point originalMobileContainerOrigin_;

    /**
     * The collection of component states prior to the beginning of the
     * drag-and-drop operation.
     */
    private final List<PreDragComponentState> preDragComponentStates_;

    /** The table associated with the drag context. */
    private final Table table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DragContext} class.
     * 
     * @param table
     *        The table associated with the drag context; must not be
     *        {@code null}.
     * @param initialLocation
     *        The initial drag location in table coordinates; must not be
     *        {@code null}. No copy is made of this value and it must not be
     *        modified after calling this method.
     * @param preDragComponentStates
     *        The collection of component states prior to the beginning of the
     *        drag-and-drop operation; must not be {@code null}. No copy is made
     *        of this value and it must not be modified after calling this
     *        method.
     * @param mobileContainer
     *        The container used to hold the components being dragged during the
     *        drag-and-drop operation; must not be {@code null}.
     */
    private DragContext(
        /* @NonNull */
        final Table table,
        /* @NonNull */
        final Point initialLocation,
        /* @NonNull */
        final List<PreDragComponentState> preDragComponentStates,
        /* @NonNull */
        final Container mobileContainer )
    {
        assert table != null;
        assert initialLocation != null;
        assert preDragComponentStates != null;
        assert mobileContainer != null;

        initialLocation_ = initialLocation;
        mobileContainer_ = mobileContainer;
        originalMobileContainerOrigin_ = mobileContainer.getOrigin();
        preDragComponentStates_ = preDragComponentStates;
        table_ = table;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Begins a drag-and-drop operation.
     * 
     * @param table
     *        The table associated with the drag-and-drop operation; must not be
     *        {@code null}.
     * @param location
     *        The beginning drag location in table coordinates; must not be
     *        {@code null}.
     * @param component
     *        The component to be dragged; must not be {@code null}.
     * 
     * @return A new instance of the {@code DragContext} class; never
     *         {@code null}.
     */
    @GuardedBy( "table.getTableEnvironment().getLock()" )
    /* @NonNull */
    static DragContext beginDrag(
        /* @NonNull */
        final Table table,
        /* @NonNull */
        final Point location,
        /* @NonNull */
        final Component component )
    {
        assert table != null;
        assert location != null;
        assert component != null;
        assert table.getTableEnvironment().getLock().isHeldByCurrentThread();

        final IDragStrategy dragStrategy = component.getDragStrategy();
        final List<IComponent> dragComponents = dragStrategy.getDragComponents();
        // TODO: need to abort drag if dragComponents.isEmpty() -- change signature of ITable.beginDrag() to allow null return value?

        final List<PreDragComponentState> preDragComponentStates = new ArrayList<PreDragComponentState>( dragComponents.size() );
        for( final IComponent dragComponent : dragComponents )
        {
            preDragComponentStates.add( new PreDragComponentState( dragComponent ) );
        }

        final Container mobileContainer = new Container( table.getTableEnvironment(), ComponentStrategies.NULL_CONTAINER );
        for( final IComponent dragComponent : dragComponents )
        {
            dragComponent.getContainer().removeComponent( dragComponent );
            mobileContainer.addComponent( dragComponent );
        }
        table.getTabletop().addComponent( mobileContainer );

        return new DragContext( table, new Point( location ), preDragComponentStates, mobileContainer );
    }

    /*
     * @see org.gamegineer.table.core.IDragContext#cancel()
     */
    @Override
    public void cancel()
    {
        getLock().lock();
        try
        {
            assertStateLegal( table_.isDragActive(), NonNlsMessages.DragContext_dragNotActive );

            mobileContainer_.removeAllComponents();
            table_.getTabletop().removeComponent( mobileContainer_ );

            for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
            {
                preDragComponentState.revert();
            }

            table_.endDrag();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IDragContext#drag(java.awt.Point)
     */
    @Override
    public void drag(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            assertStateLegal( table_.isDragActive(), NonNlsMessages.DragContext_dragNotActive );

            moveMobileContainer( location );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IDragContext#drop(java.awt.Point)
     */
    @Override
    public void drop(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            assertStateLegal( table_.isDragActive(), NonNlsMessages.DragContext_dragNotActive );

            moveMobileContainer( location );

            // TODO: need to consult the drag strategy

            final List<IComponent> dragComponents = mobileContainer_.removeAllComponents();
            table_.getTabletop().removeComponent( mobileContainer_ );
            getDropContainer( location ).addComponents( dragComponents );

            table_.endDrag();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the container for the drop at the specified location.
     * 
     * @param location
     *        The drop location in table coordinates; must not be {@code null}.
     * 
     * @return The container for the drop at the specified location; never
     *         {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private IContainer getDropContainer(
        /* @NonNull */
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

        final List<IComponent> components = table_.getComponents( location );
        for( final ListIterator<IComponent> iterator = components.listIterator( components.size() ); iterator.hasPrevious(); )
        {
            final IComponent component = iterator.previous();
            if( component instanceof IContainer )
            {
                return (IContainer)component;
            }
        }

        return table_.getTabletop();
    }

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    private ReentrantLock getLock()
    {
        return table_.getTableEnvironment().getLock();
    }

    /**
     * Moves the mobile container based on the specified drag location.
     * 
     * @param location
     *        The drag location in table coordinates; must not be {@code null}.
     */
    private void moveMobileContainer(
        /* @NonNull */
        final Point location )
    {
        assert location != null;

        final Dimension offset = new Dimension( location.x - initialLocation_.x, location.y - initialLocation_.y );
        mobileContainer_.setOrigin( new Point( originalMobileContainerOrigin_.x + offset.width, originalMobileContainerOrigin_.y + offset.height ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The state of a component being dragged prior to beginning the
     * drag-and-drop operation.
     */
    @Immutable
    private static final class PreDragComponentState
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component being dragged. */
        private final IComponent component_;

        /** The component container prior to being dragged. */
        private final IContainer container_;

        /**
         * The component index within the source container prior to being
         * dragged.
         */
        private final int index_;

        /** The component origin prior to being dragged. */
        private final Point origin_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PreDragComponentState}
         * class.
         * 
         * @param component
         *        The component being dragged; must not be {@code null}.
         */
        PreDragComponentState(
            /* @NonNull */
            final IComponent component )
        {
            assert component != null;

            component_ = component;
            container_ = component.getContainer();
            index_ = component.getPath().getIndex();
            origin_ = component.getOrigin();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Reverts the state of the component to its state before beginning the
         * drag-and-drop operation.
         */
        void revert()
        {
            component_.setOrigin( origin_ );
            container_.addComponent( component_, index_ );
        }
    }
}
