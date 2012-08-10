/*
 * IContainerLayoutRegistry.java
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
 * Created on Aug 9, 2012 at 8:09:51 PM.
 */

package org.gamegineer.table.core;

import java.util.Collection;

/**
 * A service for the management and discovery of container layouts.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IContainerLayoutRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container layout with the specified identifier.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The container layout with the specified identifier or
     *         {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public IContainerLayout getContainerLayout(
        /* @NonNull */
        ContainerLayoutId id );

    /**
     * Gets a collection of all container layouts registered with this service.
     * 
     * @return A collection of all container layouts registered with this
     *         service; never {@code null}. This collection is a snapshot of the
     *         container layouts registered at the time of the call.
     */
    /* @NonNull */
    public Collection<IContainerLayout> getContainerLayouts();

    /**
     * Registers the specified container layout.
     * 
     * @param containerLayout
     *        The container layout; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a container layout with the same identifier is already
     *         registered.
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public void registerContainerLayout(
        /* @NonNull */
        IContainerLayout containerLayout );

    /**
     * Unregisters the specified container layout.
     * 
     * @param containerLayout
     *        The container layout; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified container layout was not previously registered.
     * @throws java.lang.NullPointerException
     *         If {@code containerLayout} is {@code null}.
     */
    public void unregisterContainerLayout(
        /* @NonNull */
        IContainerLayout containerLayout );
}
