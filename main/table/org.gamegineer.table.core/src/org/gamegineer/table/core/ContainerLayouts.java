/*
 * ContainerLayouts.java
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
 * Created on Aug 11, 2012 at 9:52:18 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of common container layouts.
 */
@ThreadSafe
public final class ContainerLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A layout in which the container is laid out with all components at their
     * absolute position in table coordinates.
     */
    public static final IContainerLayout ABSOLUTE = new AbsoluteLayout( ContainerLayoutIds.ABSOLUTE );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayouts}.
     */
    private ContainerLayouts()
    {
    }


    // ======================================================================
    // Nested Types
    // ======================================================================
    /**
     * Implementation of {@link IContainerLayout} that lays out the components
     * of a container at their absolute table coordinates.
     */
    @Immutable
    private static final class AbsoluteLayout
        extends AbstractContainerLayout
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbsoluteLayout} class.
         * 
         * @param id
         *        The container layout identifier; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code id} is {@code null}.
         */
        AbsoluteLayout(
            /* @NonNull */
            final ContainerLayoutId id )
        {
            super( id );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.AbstractContainerLayout#getComponentOffsetAt(org.gamegineer.table.core.IContainer, int)
         */
        @Override
        protected Dimension getComponentOffsetAt(
            final IContainer container,
            final int index )
        {
            assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
            assertArgumentLegal( index >= 0, "index", NonNlsMessages.AbsoluteLayout_getComponentOffsetAt_index_negative ); //$NON-NLS-1$

            final Point containerOrigin = container.getOrigin();
            final Point componentLocation = container.getComponent( index ).getLocation();
            return new Dimension( componentLocation.x - containerOrigin.x, componentLocation.y - containerOrigin.y );
        }
    }
}
