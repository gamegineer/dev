/*
 * IGameSystem.java
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
 * Created on Oct 29, 2008 at 11:17:01 PM.
 */

package org.gamegineer.game.core.system;

import java.util.List;

/**
 * A game system.
 * 
 * <p>
 * A game system specifies the rules by which a game is played. A game system
 * consists of two distinct types of attributes: those that are fixed by the
 * game designer and cannot be changed, and those that are variable and may (or
 * must) be specified by the user before a new game can be started. The
 * specification (or instantiation) of these variable attributes is represented
 * by an instance of {@link org.gamegineer.game.core.config.IGameConfiguration}
 * that is associated with the game system.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameSystem
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system identifier.
     * 
     * @return The game identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    /**
     * Gets a list view of the game roles.
     * 
     * <p>
     * The roles are returned in the order they are played.
     * </p>
     * 
     * @return A list view of the game roles; never {@code null}.
     */
    /* @NonNull */
    public List<IRole> getRoles();

    /**
     * Gets a list view of the game stages.
     * 
     * <p>
     * The stages are returned in the order they are played.
     * </p>
     * 
     * @return A list view of the game stages; never {@code null}.
     */
    /* @NonNull */
    public List<IStage> getStages();
}
