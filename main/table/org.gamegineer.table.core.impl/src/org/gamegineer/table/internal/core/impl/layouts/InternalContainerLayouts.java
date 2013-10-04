/*
 * InternalContainerLayouts.java
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

package org.gamegineer.table.internal.core.impl.layouts;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ContainerLayoutIds;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A collection of container layouts provided by this bundle.
 */
@ThreadSafe
public final class InternalContainerLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately below it.
     */
    public static final IContainerLayout ACCORDIAN_DOWN = new AccordianLayout( ContainerLayoutIds.ACCORDIAN_DOWN, 0, 18 );

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the left of it.
     */
    public static final IContainerLayout ACCORDIAN_LEFT = new AccordianLayout( ContainerLayoutIds.ACCORDIAN_LEFT, -16, 0 );

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the right of it.
     */
    public static final IContainerLayout ACCORDIAN_RIGHT = new AccordianLayout( ContainerLayoutIds.ACCORDIAN_RIGHT, 16, 0 );

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately above it.
     */
    public static final IContainerLayout ACCORDIAN_UP = new AccordianLayout( ContainerLayoutIds.ACCORDIAN_UP, 0, -18 );

    /**
     * A layout in which the container is laid out with one component placed on
     * top of the other with no offset.
     */
    public static final IContainerLayout STACKED = new StackedLayout( ContainerLayoutIds.STACKED, 10, 2, 1 );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalContainerLayouts}.
     */
    private InternalContainerLayouts()
    {
    }
}
