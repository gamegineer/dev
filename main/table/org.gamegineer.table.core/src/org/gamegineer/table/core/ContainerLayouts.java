/*
 * ContainerLayouts.java
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
 * Created on Aug 11, 2012 at 9:52:18 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
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

    /** The null container layout. */
    public static final IContainerLayout NULL = new NullContainerLayout();


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
     * A null container layout.
     */
    @Immutable
    static final class NullContainerLayout
        extends AbstractContainerLayout
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /** The container layout identifier. */
        private static final ContainerLayoutId ID = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.null" ); //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NullContainerLayout} class.
         */
        NullContainerLayout()
        {
            super( ID );
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
            assertArgumentLegal( index >= 0, "index", NonNlsMessages.ContainerLayouts_NullContainerLayout_getComponentOffsetAt_index_negative ); //$NON-NLS-1$

            final Point containerOrigin = container.getOrigin();
            final Point componentLocation = container.getComponent( index ).getLocation();
            return new Dimension( componentLocation.x - containerOrigin.x, componentLocation.y - containerOrigin.y );
        }
    }
}
