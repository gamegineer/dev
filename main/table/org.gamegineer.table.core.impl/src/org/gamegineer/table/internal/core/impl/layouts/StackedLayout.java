/*
 * StackedLayout.java
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
 * Created on May 5, 2012 at 9:41:30 PM.
 */

package org.gamegineer.table.internal.core.impl.layouts;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Implementation of {@link IContainerLayout} that lays out the components of a
 * container with one component placed on top of the other with no offset.
 */
@Immutable
final class StackedLayout
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
     * Initializes a new instance of the {@code StackedLayout} class.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * @param componentsPerStackLevel
     *        The number of components per stack level; must be positive.
     * @param stackLevelOffsetX
     *        The offset of each stack level in the x-direction in table
     *        coordinates; must be positive.
     * @param stackLevelOffsetY
     *        The offset of each stack level in the y-direction in table
     *        coordinates; must be positive.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    StackedLayout(
        /* @NonNull */
        final ContainerLayoutId id,
        final int componentsPerStackLevel,
        final int stackLevelOffsetX,
        final int stackLevelOffsetY )
    {
        super( id );

        assert componentsPerStackLevel > 0;
        assert stackLevelOffsetX > 0;
        assert stackLevelOffsetY > 0;

        componentsPerStackLevel_ = componentsPerStackLevel;
        stackLevelOffset_ = new Dimension( stackLevelOffsetX, stackLevelOffsetY );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
     */
    @Override
    protected Dimension getComponentOffsetAt(
        final IContainer container,
        final int index )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentLegal( index >= 0, "index", NonNlsMessages.AbstractContainerLayout_getComponentOffsetAt_index_negative ); //$NON-NLS-1$

        final int stackLevel = index / componentsPerStackLevel_;
        return new Dimension( stackLevelOffset_.width * stackLevel, stackLevelOffset_.height * stackLevel );
    }
}
