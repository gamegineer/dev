/*
 * GetGameCommandlet.java
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
 * Created on Jan 15, 2009 at 10:59:02 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.PrintWriter;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.game.core.IGame;

/**
 * A commandlet that gets a game from the server.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetGameCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGameCommandlet} class.
     */
    public GetGameCommandlet()
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

        final int argCount = context.getArguments().size();
        if( argCount == 0 )
        {
            throw new CommandletException( Messages.GetGameCommandlet_execute_noGameIdArg, Messages.GetGameCommandlet_output_noGameIdArg );
        }
        else if( argCount > 1 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final String gameId = context.getArguments().get( 0 );
        final IGame game = context.getGameClient().getGameServerConnection().getGameServer().getGame( gameId );

        final PrintWriter writer = context.getConsole().getDisplay().getWriter();
        if( game == null )
        {
            writer.println( Messages.GetGameCommandlet_output_noGame );
        }
        else
        {
            writer.println( Messages.GetGameCommandlet_output_gameInfo( game, CommandletUtils.getGameSystemUi( context, game.getSystemId() ) ) );
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.GetGameCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.GetGameCommandlet_help_synopsis;
    }
}
