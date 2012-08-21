/*
 * ContainerLayouts.java
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
 * Created on Aug 11, 2012 at 9:52:18 PM.
 */

package org.gamegineer.table.internal.core.layouts;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;

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
    public static final IContainerLayout ABSOLUTE = new AbsoluteLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.absolute" ) ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately below it.
     */
    public static final IContainerLayout ACCORDIAN_DOWN = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianDown" ), 0, 18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the left of it.
     */
    public static final IContainerLayout ACCORDIAN_LEFT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianLeft" ), -16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the right of it.
     */
    public static final IContainerLayout ACCORDIAN_RIGHT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianRight" ), 16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately above it.
     */
    public static final IContainerLayout ACCORDIAN_UP = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianUp" ), 0, -18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out with one component placed on
     * top of the other with no offset.
     */
    public static final IContainerLayout STACKED = new StackedLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.stacked" ), 10, 2, 1 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayouts}.
     */
    private ContainerLayouts()
    {
    }
}
