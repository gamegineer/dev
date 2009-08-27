/*
 * StartLocalServerCommandlet.java
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
 * Created on May 15, 2009 at 10:42:27 PM.
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
import org.gamegineer.server.core.GameServerConfigurationException;
import org.gamegineer.server.core.GameServerFactory;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.config.GameServerConfigurationBuilder;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.core.system.GameSystemSourceFactory;

/**
 * A commandlet that starts a new game server in the local farm.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class StartLocalServerCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StartLocalServerCommandlet}
     * class.
     */
    public StartLocalServerCommandlet()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a local game server.
     * 
     * @param gameServerName
     *        The game server name; must not be {@code null}.
     * 
     * @return A local game server; never {@code null}.
     * 
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If the game server cannot be created.
     */
    /* @NonNull */
    private IGameServer createLocalGameServer(
        /* @NonNull */
        final String gameServerName )
        throws CommandletException
    {
        assert gameServerName != null;

        final GameServerConfigurationBuilder builder = new GameServerConfigurationBuilder();
        builder.setName( gameServerName );
        builder.setGameSystemSource( GameSystemSourceFactory.createRegistryGameSystemSource() );
        final IGameServerConfiguration gameServerConfig;
        try
        {
            gameServerConfig = builder.toGameServerConfiguration();
        }
        catch( final IllegalStateException e )
        {
            throw new CommandletException( Messages.StartLocalServerCommandlet_createLocalGameServer_illegalGameServerConfig, Messages.StartLocalServerCommandlet_output_illegalGameServerConfig, e );
        }

        try
        {
            return GameServerFactory.createGameServer( gameServerConfig );
        }
        catch( final GameServerConfigurationException e )
        {
            throw new CommandletException( Messages.StartLocalServerCommandlet_createLocalGameServer_createGameServerError, Messages.StartLocalServerCommandlet_output_createGameServerError, e );
        }
    }

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
            throw new CommandletException( Messages.StartLocalServerCommandlet_execute_noGameServerIdArg, Messages.StartLocalServerCommandlet_output_noGameServerIdArg );
        }
        else if( argCount == 1 )
        {
            throw new CommandletException( Messages.StartLocalServerCommandlet_execute_noGameServerNameArg, Messages.StartLocalServerCommandlet_output_noGameServerNameArg );
        }
        else if( argCount > 2 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final String gameServerId = context.getArguments().get( 0 );
        final String gameServerName = context.getArguments().get( 1 );

        final Map<String, IGameServer> gameServers = FarmAttributes.GAME_SERVERS.ensureGetValue( context.getStatelet() );
        if( gameServers.containsKey( gameServerId ) )
        {
            throw new CommandletException( Messages.StartLocalServerCommandlet_execute_gameServerIdRegistered( gameServerId ), Messages.StartLocalServerCommandlet_output_gameServerIdRegistered( gameServerId ) );
        }
        final IGameServer gameServer = createLocalGameServer( gameServerName );
        gameServers.put( gameServerId, gameServer );

        context.getConsole().getDisplay().getWriter().println( Messages.StartLocalServerCommandlet_output_started( gameServerId, gameServer ) );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.StartLocalServerCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.StartLocalServerCommandlet_help_synopsis;
    }
}
