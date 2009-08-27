/*
 * CommandletUtils.java
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
 * Created on Mar 11, 2009 at 11:27:04 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.game.ui.system.GameSystemUiUtils;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * A collection of useful methods for commandlet implementors.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
final class CommandletUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletUtils} class.
     */
    private CommandletUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system user interface with the specified identifier.
     * 
     * @param context
     *        The commandlet context; must not be {@code null}.
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return A game system user interface; never {@code null}. If no game
     *         system user interface with the specified identifier is available
     *         from the commandlet context, an appropriate default
     *         implementation will be returned.
     */
    /* @NonNull */
    static IGameSystemUi getGameSystemUi(
        /* @NonNull */
        final ICommandletContext context,
        /* @NonNull */
        final String gameSystemId )
    {
        assert context != null;
        assert gameSystemId != null;

        final GameSystemUiUtils.IGameSystemUiFactory gameSystemUiFactory = new GameSystemUiUtils.IGameSystemUiFactory()
        {
            public IGameSystemUi getGameSystemUi(
                final String id )
            {
                return context.getGameClient().getGameSystemUi( id );
            }
        };
        return GameSystemUiUtils.getGameSystemUi( gameSystemUiFactory, gameSystemId );
    }
}
