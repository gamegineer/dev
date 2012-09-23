/*
 * AbstractContainerLayout.java
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
 * Created on May 5, 2012 at 10:08:36 PM.
 */

package org.gamegineer.table.internal.core.layouts;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Superclass for implementations of {@link IContainerLayout}.
 */
@Immutable
abstract class AbstractContainerLayout
    implements IContainerLayout
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout identifier. */
    private final ContainerLayoutId id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayout} class.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    AbstractContainerLayout(
        /* @NonNull */
        final ContainerLayoutId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation returns the top-most component within the specified
     * container at the specified location.
     * 
     * @see org.gamegineer.table.core.IContainerLayout#getComponentIndex(org.gamegineer.table.core.IContainer,
     *      java.awt.Point)
     */
    @Override
    public int getComponentIndex(
        final IContainer container,
        final Point location )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final List<IComponent> components = container.getComponents();
        for( int index = components.size() - 1; index >= 0; --index )
        {
            if( components.get( index ).getBounds().contains( location ) )
            {
                return index;
            }
        }

        return -1;
    }

    /**
     * Gets the offset from the container origin in table coordinates of the
     * component at the specified index.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param index
     *        The component index; must be non-negative.
     * 
     * @return The offset from the container origin in table coordinates of the
     *         component at the specified index; never {@code null}.
     */
    /* @NonNull */
    abstract Dimension getComponentOffsetAt(
        /* @NonNull */
        IContainer container,
        int index );

    /*
     * @see org.gamegineer.table.core.IContainerLayout#getId()
     */
    @Override
    public final ContainerLayoutId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.core.IContainerLayout#layout(org.gamegineer.table.core.IContainer)
     */
    @Override
    public final void layout(
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$

        final Point containerOrigin = container.getOrigin();
        final Point componentLocation = new Point();
        for( int index = 0, size = container.getComponentCount(); index < size; ++index )
        {
            componentLocation.setLocation( containerOrigin );
            final Dimension componentOffset = getComponentOffsetAt( container, index );
            componentLocation.translate( componentOffset.width, componentOffset.height );
            container.getComponent( index ).setLocation( componentLocation );
        }
    }
}