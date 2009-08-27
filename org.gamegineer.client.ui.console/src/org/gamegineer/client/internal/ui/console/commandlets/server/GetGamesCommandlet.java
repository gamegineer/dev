/*
 * GetGamesCommandlet.java
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
 * Created on Jan 11, 2009 at 11:14:36 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.PrintWriter;
import java.util.Collection;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.game.core.IGame;

/**
 * A commandlet that displays the games available on the server.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetGamesCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGamesCommandlet} class.
     */
    public GetGamesCommandlet()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandlet#execute(org.gamegineer.client.ui.console.commandlet.ICommandletContext)
     */
    public void execute(
        final ICommandletContext context )
        throws CommandletException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        if( !context.getArguments().isEmpty() )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final Collection<IGame> games = context.getGameClient().getGameServerConnection().getGameServer().getGames();

        final PrintWriter writer = context.getConsole().getDisplay().getWriter();
        if( games.isEmpty() )
        {
            writer.println( Messages.GetGamesCommandlet_output_noGames );
        }
        else
        {
            for( final IGame game : games )
            {
                writer.println( Messages.GetGamesCommandlet_output_gameInfo( game, CommandletUtils.getGameSystemUi( context, game.getSystemId() ) ) );
            }
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.GetGamesCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.GetGamesCommandlet_help_synopsis;
    }
}
