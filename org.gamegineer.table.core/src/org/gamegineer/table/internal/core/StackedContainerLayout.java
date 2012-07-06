/*
 * StackedContainerLayout.java
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
 * Created on May 5, 2012 at 9:41:30 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Implementation of {@link IContainerLayout} that lays out the components of a
 * container with one component placed on top of the other with no offset.
 */
@Immutable
public final class StackedContainerLayout
    extends AbstractContainerLayout
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The number of components per stack level. */
    private final int componentsPerStackLevel_;

    /** The offset of each stack level in table coordinates. */
    private final Dimension stackLevelOffset_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StackedContainerLayout} class.
     * 
     * @param componentsPerStackLevel
     *        The number of components per stack level.
     * @param stackLevelOffsetX
     *        The offset of each stack level in the x-direction in table
     *        coordinates.
     * @param stackLevelOffsetY
     *        The offset of each stack level in the y-direction in table
     *        coordinates.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code componentsPerStackLevel}, {@code stackLevelOffsetX}, or
     *         {@code stackLevelOffsetY} is not positive.
     */
    public StackedContainerLayout(
        final int componentsPerStackLevel,
        final int stackLevelOffsetX,
        final int stackLevelOffsetY )
    {
        assertArgumentLegal( componentsPerStackLevel > 0, "componentsPerStackLevel", NonNlsMessages.StackedContainerLayout_ctor_componentsPerStackLevel_notPositive ); //$NON-NLS-1$
        assertArgumentLegal( stackLevelOffsetX > 0, "stackLevelOffsetX", NonNlsMessages.StackedContainerLayout_ctor_stackLevelOffsetX_notPositive ); //$NON-NLS-1$
        assertArgumentLegal( stackLevelOffsetY > 0, "stackLevelOffsetY", NonNlsMessages.StackedContainerLayout_ctor_stackLevelOffsetY_notPositive ); //$NON-NLS-1$

        componentsPerStackLevel_ = componentsPerStackLevel;
        stackLevelOffset_ = new Dimension( stackLevelOffsetX, stackLevelOffsetY );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.core.AbstractContainerLayout#getComponentIndex(org.gamegineer.table.core.IContainer, java.awt.Point)
     */
    @Override
    public int getComponentIndex(
        final IContainer container,
        final Point location )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final int topIndex = container.getComponentCount() - 1;
        if( topIndex >= 0 )
        {
            final IComponent topComponent = container.getComponent( topIndex );
            if( topComponent.getBounds().contains( location ) )
            {
                return topIndex;
            }
        }

        return -1;
    }

    /*
     * @see org.gamegineer.table.internal.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
     */
    @Override
    Dimension getComponentOffsetAt(
        final IContainer container,
        final int index )
    {
        assert container != null;
        assert index >= 0;

        final int stackLevel = index / componentsPerStackLevel_;
        return new Dimension( stackLevelOffset_.width * stackLevel, stackLevelOffset_.height * stackLevel );
    }

    /**
     * Gets the number of components per stack level.
     * 
     * @return The number of components per stack level.
     */
    public int getComponentsPerStackLevel()
    {
        return componentsPerStackLevel_;
    }

    /**
     * Gets the offset of each stack level in table coordinates.
     * 
     * @return The offset of each stack level in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Dimension getStackLevelOffset()
    {
        return new Dimension( stackLevelOffset_ );
    }
}
