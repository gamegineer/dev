/*
 * GameSystemSourceFactory.java
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
 * Created on Nov 30, 2008 at 9:59:25 PM.
 */

package org.gamegineer.server.core.system;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.server.internal.core.system.RegistryGameSystemSource;

/**
 * A factory for creating game system sources.
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
public final class GameSystemSourceFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemSourceFactory} class.
     */
    private GameSystemSourceFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system source that uses game system registry as its
     * source.
     * 
     * @return A game system source; never {@code null}.
     */
    /* @NonNull */
    public static IGameSystemSource createRegistryGameSystemSource()
    {
        return new RegistryGameSystemSource();
    }
}
