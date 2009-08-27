/*
 * IGameSystemUiRegistry.java
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
 * Created on Mar 2, 2009 at 11:28:31 PM.
 */

package org.gamegineer.game.ui.services.systemuiregistry;

import java.util.Collection;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * A service for the management and discovery of game system user interfaces in
 * the environment.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameSystemUiRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system user interface with the specified identifier.
     * 
     * @param id
     *        The game system user interface identifier; must not be
     *        {@code null}.
     * 
     * @return The game system user interface with the specified identifier or
     *         {@code null} if no such identifier exists.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public IGameSystemUi getGameSystemUi(
        /* @NonNull */
        String id );

    /**
     * Gets a collection of all game system user interfaces registered with this
     * service.
     * 
     * @return A collection of all game system user interfaces registered with
     *         this service; never {@code null}. This collection is a snapshot
     *         of the game system user interfaces available at the time of the
     *         call.
     */
    /* @NonNull */
    public Collection<IGameSystemUi> getGameSystemUis();

    /**
     * Registers the specified game system user interface.
     * 
     * <p>
     * This method does nothing if the specified game system user interface or a
     * game system with user interface the same identifier was previously
     * registered.
     * </p>
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} is {@code null}.
     */
    public void registerGameSystemUi(
        /* @NonNull */
        IGameSystemUi gameSystemUi );

    /**
     * Unregisters the specified game system user interface.
     * 
     * <p>
     * This method does nothing if the specified game system user interface was
     * not previously registered.
     * </p>
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} is {@code null}.
     */
    public void unregisterGameSystemUi(
        /* @NonNull */
        IGameSystemUi gameSystemUi );
}
