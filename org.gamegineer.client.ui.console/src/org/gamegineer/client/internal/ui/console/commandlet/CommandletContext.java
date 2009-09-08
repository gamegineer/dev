/*
 * CommandletContext.java
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
 * Created on Oct 3, 2008 at 9:34:36 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * Implementation of
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletContext}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class CommandletContext
    implements ICommandletContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The console from which the commandlet was executed. */
    private final IConsole console_;

    /** The executor that initiated the commandlet execution. */
    private final CommandletExecutor executor_;

    /** The game client against which the commandlet is executed. */
    private final IGameClient gameClient_;

    /** The console statelet. */
    private final IStatelet statelet_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletContext} class.
     * 
     * @param executor
     *        The executor that initiated the commandlet execution; must not be
     *        {@code null}.
     * @param console
     *        The console from which the commandlet was executed; must not be
     *        {@code null}.
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param gameClient
     *        The game client against which the commandlet is executed; must not
     *        be {@code null}.
     */
    CommandletContext(
        /* @NonNull */
        final CommandletExecutor executor,
        /* @NonNull */
        final IConsole console,
        /* @NonNull */
        final IStatelet statelet,
        /* @NonNull */
        final IGameClient gameClient )
    {
        assert executor != null;
        assert console != null;
        assert statelet != null;
        assert gameClient != null;

        executor_ = executor;
        console_ = console;
        statelet_ = statelet;
        gameClient_ = gameClient;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletContext#getArguments()
     */
    public List<String> getArguments()
    {
        return executor_.getCommandletArguments();
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletContext#getConsole()
     */
    public IConsole getConsole()
    {
        return console_;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletContext#getGameClient()
     */
    public IGameClient getGameClient()
    {
        return gameClient_;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletContext#getStatelet()
     */
    public IStatelet getStatelet()
    {
        return statelet_;
    }
}
