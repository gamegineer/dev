/*
 * FakeCommandletContext.java
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
 * Created on Oct 20, 2008 at 10:37:11 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;

/**
 * Fake implementation of
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletContext}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@Immutable
public class FakeCommandletContext
    implements ICommandletContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet argument collection. */
    private final List<String> args_;

    /** The console from which the commandlet was executed. */
    private final IConsole console_;

    /** The game client against which the commandlet is executed. */
    private final IGameClient gameClient_;

    /** The console statelet. */
    private final IStatelet statelet_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeCommandletContext} class.
     * 
     * @param console
     *        The console from which the commandlet was executed; must not be
     *        {@code null}.
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param gameClient
     *        The game client against which the commandlet is executed; must not
     *        be {@code null}.
     * @param args
     *        The commandlet argument collection; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code console}, {@code gameClient}, or {@code args} is
     *         {@code null}.
     */
    public FakeCommandletContext(
        /* @NonNull */
        final IConsole console,
        /* @NonNull */
        final IStatelet statelet,
        /* @NonNull */
        final IGameClient gameClient,
        /* @NonNull */
        final List<String> args )
    {
        assertArgumentNotNull( console, "console" ); //$NON-NLS-1$
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$
        assertArgumentNotNull( gameClient, "gameClient" ); //$NON-NLS-1$
        assertArgumentNotNull( args, "args" ); //$NON-NLS-1$

        console_ = console;
        statelet_ = statelet;
        gameClient_ = gameClient;
        args_ = Collections.unmodifiableList( args );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletContext#getArguments()
     */
    public List<String> getArguments()
    {
        return args_;
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
