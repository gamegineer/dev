/*
 * ComponentAxis.java
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
 * Created on Jan 16, 2013 at 7:52:49 PM.
 */

package org.gamegineer.table.internal.ui.model;

/**
 * A component-relative axis.
 */
public enum ComponentAxis
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /**
     * The axis representing the components that precede a component in the
     * z-order.
     */
    PRECEDING,

    /**
     * The axis representing the components that follow a component in the
     * z-order.
     */
    FOLLOWING,

    /**
     * The axis representing the ancestors of a component (which necessarily
     * precede it in the z-order).
     */
    ANCESTOR,

    /**
     * The axis representing the descendants of a component (which necessarily
     * follow it in the z-order).
     */
    DESCENDANT,

    /**
     * The axis representing the siblings of a component that precede it in the
     * z-order.
     */
    PRECEDING_SIBLING,

    /**
     * The axis representing the siblings of a component that follow it in the
     * z-order.
     */
    FOLLOWING_SIBLING;
}
