/*
 * CommandletExecutor.java
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
 * Created on Oct 23, 2008 at 11:20:44 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * A commandlet executor.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class CommandletExecutor
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet to execute. */
    private final ICommandlet m_commandlet;

    /** The commandlet argument list. */
    private final List<String> m_commandletArgs;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletExecutor} class.
     * 
     * @param commandlet
     *        The commandlet to execute; must not be {@code null}.
     * @param commandletArgs
     *        The commandlet argument list; must not be {@code null}.
     */
    CommandletExecutor(
        /* @NonNull */
        final ICommandlet commandlet,
        /* @NonNull */
        final List<String> commandletArgs )
    {
        assert commandlet != null;
        assert commandletArgs != null;

        m_commandlet = commandlet;
        m_commandletArgs = Collections.unmodifiableList( new ArrayList<String>( commandletArgs ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Executes the commandlet associated with this executor.
     * 
     * @param console
     *        The console; must not be {@code null}.
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param gameClient
     *        The game client; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code console}, {@code statelet}, or {@code gameClient} is
     *         {@code null}.
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If an error occurs during the execution of the commandlet.
     */
    public void execute(
        /* @NonNull */
        final IConsole console,
        /* @NonNull */
        final IStatelet statelet,
        /* @NonNull */
        final IGameClient gameClient )
        throws CommandletException
    {
        assertArgumentNotNull( console, "console" ); //$NON-NLS-1$
        assertArgumentNotNull( statelet, "statelet" ); //$NON-NLS-1$
        assertArgumentNotNull( gameClient, "gameClient" ); //$NON-NLS-1$

        m_commandlet.execute( new CommandletContext( this, console, statelet, gameClient ) );
    }

    /**
     * Gets the commandlet associated with this executor.
     * 
     * @return The commandlet associated with this executor; never {@code null}.
     */
    /* @NonNull */
    ICommandlet getCommandlet()
    {
        return m_commandlet;
    }

    /**
     * Gets an immutable view of the arguments for the commandlet associated
     * with this executor.
     * 
     * @return An immutable view of the arguments for the commandlet associated
     *         with this executor; never {@code null}.
     */
    /* @NonNull */
    List<String> getCommandletArguments()
    {
        return m_commandletArgs;
    }
}
