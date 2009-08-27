/*
 * StopLocalServerCommandlet.java
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
 * Created on May 22, 2009 at 10:20:00 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.farm;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.server.core.IGameServer;

/**
 * A commandlet that stops a game server in the local farm.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class StopLocalServerCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StopLocalServerCommandlet}
     * class.
     */
    public StopLocalServerCommandlet()
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
            throw new CommandletException( Messages.StopLocalServerCommandlet_execute_noGameServerIdArg, Messages.StopLocalServerCommandlet_output_noGameServerIdArg );
        }
        else if( argCount > 1 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final String gameServerId = context.getArguments().get( 0 );

        final Map<String, IGameServer> gameServers = FarmAttributes.GAME_SERVERS.ensureGetValue( context.getStatelet() );
        final IGameServer gameServer = gameServers.remove( gameServerId );
        if( gameServer == null )
        {
            throw new CommandletException( Messages.StopLocalServerCommandlet_execute_gameServerIdUnregistered( gameServerId ), Messages.StopLocalServerCommandlet_output_gameServerIdUnregistered( gameServerId ) );
        }

        context.getConsole().getDisplay().getWriter().println( Messages.StopLocalServerCommandlet_output_stopped( gameServerId, gameServer ) );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.StopLocalServerCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.StopLocalServerCommandlet_help_synopsis;
    }
}
