/*
 * IContainerLayout.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on May 5, 2012 at 9:00:16 PM.
 */

package org.gamegineer.table.core;

/**
 * A container layout.
 * 
 * <p>
 * Instances of this type control the position of components within a container.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IContainerLayout
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container layout identifier.
     * 
     * @return The container layout identifier.
     */
    public ContainerLayoutId getId();

    /**
     * Lays out the child components of the specified container according to the
     * rules of this layout.
     * 
     * @param container
     *        The container.
     */
    public void layout(
        IContainer container );
}
