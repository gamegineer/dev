/*
 * GameSystemUiSourceFactory.java
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
 * Created on Mar 7, 2009 at 9:02:11 PM.
 */

package org.gamegineer.client.core.system;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.internal.core.system.RegistryGameSystemUiSource;

/**
 * A factory for creating game system user interface sources.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class GameSystemUiSourceFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiSourceFactory}
     * class.
     */
    private GameSystemUiSourceFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system user interface source that uses game system
     * user interface registry as its source.
     * 
     * @return A game system user interface source; never {@code null}.
     */
    /* @NonNull */
    public static IGameSystemUiSource createRegistryGameSystemUiSource()
    {
        return new RegistryGameSystemUiSource();
    }
}
