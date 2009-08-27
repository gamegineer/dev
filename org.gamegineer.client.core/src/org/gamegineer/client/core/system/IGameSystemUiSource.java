/*
 * IGameSystemUiSource.java
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
 * Created on Mar 6, 2009 at 10:39:04 PM.
 */

package org.gamegineer.client.core.system;

import java.util.Collection;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * A source of game system user interfaces.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IGameSystemUiSource
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system user interfaces available from this source.
     * 
     * @return The game system user interfaces available from this source; never
     *         {@code null}.
     */
    /* @NonNull */
    public Collection<IGameSystemUi> getGameSystemUis();
}
