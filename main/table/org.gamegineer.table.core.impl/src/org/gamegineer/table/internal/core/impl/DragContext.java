/*
 * DragContext.java
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
 * Created on Feb 22, 2013 at 9:46:18 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.IterableUtils;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITableEnvironmentLock;
import org.gamegineer.table.core.dnd.DefaultDragStrategyFactory;
import org.gamegineer.table.core.dnd.IDragContext;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;
import org.gamegineer.table.core.dnd.NullDragStrategy;

/**
 * Implementation of {@link IDragContext}.
 */
@ThreadSafe
final class DragContext
    implements IDragContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag strategy. */
    private final IDragStrategy dragStrategy_;

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
     *        The table associated with the drag context.
     * @param initialLocation
     *        The initial drag location in table coordinates. No copy is made of
     *        this value and it must not be modified after calling this method.
     * @param preDragComponentStates
     *        The collection of component states prior to the beginning of the
     *        drag-and-drop operation. No copy is made of this value and it must
     *        not be modified after calling this method.
     * @param mobileContainer
     *        The container used to hold the components being dragged during the
     *        drag-and-drop operation.
     * @param dragStrategy
     *        The drag strategy.
     */
    private DragContext(
        final Table table,
        final Point initialLocation,
        final List<PreDragComponentState> preDragComponentStates,
        final Container mobileContainer,
        final IDragStrategy dragStrategy )
    {
        dragStrategy_ = dragStrategy;
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
     *        The table associated with the drag-and-drop operation.
     * @param location
     *        The beginning drag location in table coordinates.
     * @param component
     *        The component to be dragged.
     * @param dragStrategyFactory
     *        A factory for creating the drag strategy for the drag-and-drop
     *        operation.
     * 
     * @return A new instance of the {@code DragContext} class or {@code null}
     *         if a drag-and-drop operation is not possible for the specified
     *         arguments.
     */
    @GuardedBy( "table.getTableEnvironment().getLock()" )
    static @Nullable DragContext beginDrag(
        final Table table,
        final Point location,
        final Component component,
        final IDragStrategyFactory dragStrategyFactory )
    {
        assert table.getTableEnvironment().getLock().isHeldByCurrentThread();

        final IDragStrategy dragStrategy = dragStrategyFactory.createDragStrategy( component, getDefaultDragStrategy( component ) );
        final List<IComponent> dragComponents = dragStrategy.getDragComponents();
        if( dragComponents.isEmpty() )
        {
            return null;
        }

        final List<PreDragComponentState> preDragComponentStates = new ArrayList<>( dragComponents.size() );
        for( final IComponent dragComponent : dragComponents )
        {
            preDragComponentStates.add( new PreDragComponentState( dragComponent ) );
        }

        int index = 0;
        final Container mobileContainer = new Container( table.getTableEnvironment(), ComponentStrategies.NULL_CONTAINER );
        for( final IComponent dragComponent : dragComponents )
        {
            final IContainer dragComponentContainer = dragComponent.getContainer();
            assert dragComponentContainer != null;
            dragComponentContainer.removeComponent( dragComponent );
            mobileContainer.addComponent( dragComponent );

            final PreDragComponentState preDragComponentState = preDragComponentStates.get( index++ );
            preDragComponentState.initialize();
        }
        table.getTabletop().addComponent( mobileContainer );

        return new DragContext( table, new Point( location ), preDragComponentStates, mobileContainer, dragStrategy );
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragContext#cancel()
     */
    @Override
    public void cancel()
    {
        getLock().lock();
        try
        {
            assertStateLegal( table_.isDragActive(), NonNlsMessages.DragContext_dragNotActive );

            endDrag( null );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragContext#drag(java.awt.Point)
     */
    @Override
    public void drag(
        final Point location )
    {
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

    /**
     * Ends the drag-and-drop operation.
     * 
     * @param location
     *        The ending drag location in table coordinates or {@code null} if
     *        the drag-and-drop operation should be reverted.
     */
    @GuardedBy( "getLock()" )
    private void endDrag(
        final @Nullable Point location )
    {
        assert getLock().isHeldByCurrentThread();

        final List<IComponent> dragComponents = mobileContainer_.removeAllComponents();
        table_.getTabletop().removeComponent( mobileContainer_ );

        boolean revert = false;
        if( location != null )
        {
            final IContainer dropContainer = getDropContainer( location );
            if( dragStrategy_.canDrop( dropContainer ) )
            {
                dropContainer.addComponents( dragComponents );
            }
            else
            {
                revert = true;
            }
        }
        else
        {
            revert = true;
        }

        if( revert )
        {
            for( final PreDragComponentState preDragComponentState : preDragComponentStates_ )
            {
                preDragComponentState.revert();
            }
        }

        table_.endDrag();
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragContext#drop(java.awt.Point)
     */
    @Override
    public void drop(
        final Point location )
    {
        getLock().lock();
        try
        {
            assertStateLegal( table_.isDragActive(), NonNlsMessages.DragContext_dragNotActive );

            moveMobileContainer( location );
            endDrag( location );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the default drag strategy for the specified component.
     * 
     * @param component
     *        The component.
     * 
     * @return The default drag strategy for the specified component.
     */
    private static IDragStrategy getDefaultDragStrategy(
        final IComponent component )
    {
        IDragStrategyFactory dragStrategyFactory = component.getStrategy().getExtension( IDragStrategyFactory.class );
        if( dragStrategyFactory == null )
        {
            dragStrategyFactory = DefaultDragStrategyFactory.INSTANCE;
        }

        return dragStrategyFactory.createDragStrategy( component, NullDragStrategy.INSTANCE );
    }

    /**
     * Gets the container for the drop at the specified location.
     * 
     * @param location
     *        The drop location in table coordinates.
     * 
     * @return The container for the drop at the specified location.
     */
    @GuardedBy( "getLock()" )
    private IContainer getDropContainer(
        final Point location )
    {
        assert getLock().isHeldByCurrentThread();

        for( final IComponent component : IterableUtils.reverse( table_.getComponents( location ) ) )
        {
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
     * @return The table environment lock.
     */
    private ITableEnvironmentLock getLock()
    {
        return table_.getTableEnvironment().getLock();
    }

    /**
     * Moves the mobile container based on the specified drag location.
     * 
     * @param location
     *        The drag location in table coordinates.
     */
    private void moveMobileContainer(
        final Point location )
    {
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
         *        The component being dragged.
         */
        PreDragComponentState(
            final IComponent component )
        {
            component_ = component;
            final IContainer container = component.getContainer();
            assert container != null;
            container_ = container;
            final ComponentPath componentPath = component.getPath();
            assert componentPath != null;
            index_ = componentPath.getIndex();
            origin_ = component.getOrigin();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Initializes the component state at the start of the drag-and-drop
         * operation.
         */
        void initialize()
        {
            component_.setOrigin( origin_ );
        }

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
