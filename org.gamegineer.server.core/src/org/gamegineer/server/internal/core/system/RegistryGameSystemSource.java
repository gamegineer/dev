/*
 * RegistryGameSystemSource.java
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
 * Created on Feb 22, 2009 at 9:48:53 PM.
 */

package org.gamegineer.server.internal.core.system;

import java.util.Collection;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.server.core.system.IGameSystemSource;
import org.gamegineer.server.internal.core.Services;

/**
 * Implementation of {@link org.gamegineer.server.core.system.IGameSystemSource}
 * that uses the game system registry as its source.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class RegistryGameSystemSource
    implements IGameSystemSource
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RegistryGameSystemSource} class.
     */
    public RegistryGameSystemSource()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.config.IGameSystemSource#getGameSystems()
     */
    public Collection<IGameSystem> getGameSystems()
    {
        return Services.getDefault().getGameSystemRegistry().getGameSystems();
    }
}
