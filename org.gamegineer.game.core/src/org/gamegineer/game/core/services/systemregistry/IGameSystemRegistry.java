/*
 * IGameSystemRegistry.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Feb 16, 2009 at 10:10:15 PM.
 */

package org.gamegineer.game.core.services.systemregistry;

import java.util.Collection;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * A service for the management and discovery of game systems in the
 * environment.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameSystemRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system with the specified identifier.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system with the specified identifier or {@code null} if
     *         no such identifier exists.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public IGameSystem getGameSystem(
        /* @NonNull */
        String id );

    /**
     * Gets a collection of all game systems registered with this service.
     * 
     * @return A collection of all game systems registered with this service;
     *         never {@code null}. This collection is a snapshot of the game
     *         systems available at the time of the call.
     */
    /* @NonNull */
    public Collection<IGameSystem> getGameSystems();

    /**
     * Registers the specified game system.
     * 
     * <p>
     * This method does nothing if the specified game system or a game system
     * with the same identifier was previously registered.
     * </p>
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    public void registerGameSystem(
        /* @NonNull */
        IGameSystem gameSystem );

    /**
     * Unregisters the specified game system.
     * 
     * <p>
     * This method does nothing if the specified game system was not previously
     * registered.
     * </p>
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    public void unregisterGameSystem(
        /* @NonNull */
        IGameSystem gameSystem );
}
