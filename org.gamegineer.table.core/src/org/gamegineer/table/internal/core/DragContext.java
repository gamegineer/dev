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
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IDragContext;

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

    /** The original origin of the component being dragged in table coordinates. */
    private final Point originalDragComponentOrigin_;

    /** The original origin of the mobile container in table coordinates. */
    private final Point originalMobileContainerOrigin_;

    /** The container that originally held the components being dragged. */
    private final Container sourceContainer_;

    /** The table associated with the drag context. */
    private final Table table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    // TODO: consider replacing ctor with a factory method and move Table.beginDrag() logic to it

    /**
     * Initializes a new instance of the {@code DragContext} class.
     * 
     * @param table
     *        The table associated with the drag context; must not be
     *        {@code null}.
     * @param initialLocation
     *        The initial drag location in table coordinates; must not be
     *        {@code null}.
     * @param originalDragComponentOrigin
     *        The original origin of the component being dragged in table
     *        coordinates; must not be {@code null}.
     * @param sourceContainer
     *        The container that originally held the components being dragged;
     *        must not be {@code null}.
     * @param mobileContainer
     *        The container used to hold the components being dragged during the
     *        drag-and-drop operation; must not be {@code null}.
     */
    DragContext(
        /* @NonNull */
        final Table table,
        /* @NonNull */
        final Point initialLocation,
        /* @NonNull */
        final Point originalDragComponentOrigin,
        /* @NonNull */
        final Container sourceContainer,
        /* @NonNull */
        final Container mobileContainer )
    {
        assert table != null;
        assert initialLocation != null;
        assert originalDragComponentOrigin != null;
        assert sourceContainer != null;
        assert mobileContainer != null;

        initialLocation_ = initialLocation;
        mobileContainer_ = mobileContainer;
        originalDragComponentOrigin_ = originalDragComponentOrigin;
        originalMobileContainerOrigin_ = mobileContainer.getOrigin();
        sourceContainer_ = sourceContainer;
        table_ = table;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

            // FIXME: assuming only a single component is being dragged
            final IComponent dragComponent = mobileContainer_.removeTopComponent();
            table_.getTabletop().removeComponent( mobileContainer_ );
            dragComponent.setOrigin( originalDragComponentOrigin_ );
            sourceContainer_.addComponent( dragComponent );

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

            // FIXME: assuming only a single component is being dragged
            final IComponent dragComponent = mobileContainer_.removeTopComponent();
            table_.getTabletop().removeComponent( mobileContainer_ );
            getTargetContainer( location ).addComponent( dragComponent );

            table_.endDrag();
        }
        finally
        {
            getLock().unlock();
        }
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
     * Gets the target container for the drop at the specified location.
     * 
     * @param location
     *        The ending drag location in table coordinates; must not be
     *        {@code null}.
     * 
     * @return The target container for the drop at the specified location;
     *         never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    private IContainer getTargetContainer(
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
}
