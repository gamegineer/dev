/*
 * GameClientFactory.java
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
 * Created on Dec 27, 2008 at 9:28:48 PM.
 */

package org.gamegineer.client.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.internal.core.GameClient;

/**
 * A factory for creating game client.
 * 
 * <p>
 * Game clients created by this factory are thread-safe.
 * </p>
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
public final class GameClientFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientFactory} class.
     */
    private GameClientFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game client.
     * 
     * @param gameClientConfig
     *        The game client configuration; must not be {@code null}.
     * 
     * @return A new game client; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameClientConfig} is {@code null}.
     * @throws org.gamegineer.client.core.GameClientConfigurationException
     *         If a game client that satisfies the requested configuration could
     *         not be created or {@code gameClientConfig} represents an illegal
     *         configuration.
     */
    /* @NonNull */
    public static IGameClient createGameClient(
        /* @NonNull */
        final IGameClientConfiguration gameClientConfig )
        throws GameClientConfigurationException
    {
        assertArgumentNotNull( gameClientConfig, "gameClientConfig" ); //$NON-NLS-1$

        return GameClient.createGameClient( gameClientConfig );
    }
}
