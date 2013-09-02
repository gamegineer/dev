/*
 * ContainerLayoutIds.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Nov 30, 2012 at 10:24:59 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of container layout identifiers published by this bundle.
 */
@ThreadSafe
public final class ContainerLayoutIds
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The absolute container layout identifier. */
    public static final ContainerLayoutId ABSOLUTE = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.absolute" ); //$NON-NLS-1$

    /** The accordian down container layout identifier. */
    public static final ContainerLayoutId ACCORDIAN_DOWN = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.accordianDown" ); //$NON-NLS-1$

    /** The accordian left container layout identifier. */
    public static final ContainerLayoutId ACCORDIAN_LEFT = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.accordianLeft" ); //$NON-NLS-1$

    /** The accordian right container layout identifier. */
    public static final ContainerLayoutId ACCORDIAN_RIGHT = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.accordianRight" ); //$NON-NLS-1$

    /** The accordian up container layout identifier. */
    public static final ContainerLayoutId ACCORDIAN_UP = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.accordianUp" ); //$NON-NLS-1$

    /** The stacked container layout identifier. */
    public static final ContainerLayoutId STACKED = ContainerLayoutId.fromString( "org.gamegineer.table.containerLayouts.stacked" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutIds} class.
     */
    private ContainerLayoutIds()
    {
    }
}
