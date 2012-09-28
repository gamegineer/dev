/*
 * IComponentStrategyUIRegistry.java
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
 * Created on Sep 25, 2012 at 8:05:19 PM.
 */

package org.gamegineer.table.ui;

import java.util.Collection;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * A service for the management and discovery of component strategy user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IComponentStrategyUIRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component strategy user interface with the specified identifier.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The component strategy user interface with the specified
     *         identifier or {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public IComponentStrategyUI getComponentStrategyUI(
        /* @NonNull */
        ComponentStrategyId id );

    /**
     * Gets a collection of all component strategy user interfaces registered
     * with this service.
     * 
     * @return A collection of all component strategy user interfaces registered
     *         with this service; never {@code null}. This collection is a
     *         snapshot of the component strategy user interfaces registered at
     *         the time of the call.
     */
    /* @NonNull */
    public Collection<IComponentStrategyUI> getComponentStrategyUIs();

    /**
     * Registers the specified component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a component strategy user interface with the same identifier
     *         is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyUI} is {@code null}.
     */
    public void registerComponentStrategyUI(
        /* @NonNull */
        IComponentStrategyUI componentStrategyUI );

    /**
     * Unregisters the specified component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified component strategy user interface was not
     *         previously registered.
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyUI} is {@code null}.
     */
    public void unregisterComponentStrategyUI(
        /* @NonNull */
        IComponentStrategyUI componentStrategyUI );
}
