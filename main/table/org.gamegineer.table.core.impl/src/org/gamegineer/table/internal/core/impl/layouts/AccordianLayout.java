/*
 * AccordianLayout.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 5, 2012 at 9:41:38 PM.
 */

package org.gamegineer.table.internal.core.impl.layouts;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerLayout;

/**
 * Implementation of {@link IContainerLayout} that lays out the components of a
 * container as an accordian in a specific direction.
 * 
 * <p>
 * Beginning with the component at the bottom of the container, each successive
 * component is offset a fixed amount in the direction of the accordian.
 * </p>
 */
@Immutable
final class AccordianLayout
    extends AbstractContainerLayout
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The offset of each component in table coordinates. */
    private final Dimension offset_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AccordianLayout} class.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * @param offsetX
     *        The offset of each component in the x-direction in table
     *        coordinates.
     * @param offsetY
     *        The offset of each component in the y-direction in table
     *        coordinates; must not be zero if {@code offsetX} is zero.
     */
    AccordianLayout(
        final ContainerLayoutId id,
        final int offsetX,
        final int offsetY )
    {
        super( id );

        assert (offsetX != 0) || (offsetY != 0);

        offset_ = new Dimension( offsetX, offsetY );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
     */
    @Override
    protected Dimension getComponentOffsetAt(
        @SuppressWarnings( "unused" )
        final IContainer container,
        final int index )
    {
        assertArgumentLegal( index >= 0, "index", NonNlsMessages.AbstractContainerLayout_getComponentOffsetAt_index_negative ); //$NON-NLS-1$

        return new Dimension( index * offset_.width, index * offset_.height );
    }
}
