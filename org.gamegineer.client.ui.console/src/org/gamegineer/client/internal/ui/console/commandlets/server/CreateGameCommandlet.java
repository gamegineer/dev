/*
 * CreateGameCommandlet.java
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
 * Created on Jan 10, 2009 at 10:29:47 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletArguments;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.GameConfigurationBuilder;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.PlayerConfigurationBuilder;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * A commandlet that creates a new game.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class CreateGameCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The argument index of the game identifier. */
    static final int ARG_GAME_ID = 1;

    /** The argument index of the game name. */
    static final int ARG_GAME_NAME = 2;

    /** The argument index of the game system identifier. */
    static final int ARG_GAME_SYSTEM_ID = 0;

    /** The argument index of the player role binding list. */
    static final int ARG_PLAYER_ROLE_BINDING_LIST = 3;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CreateGameCommandlet} class.
     */
    public CreateGameCommandlet()
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

        final IGameConfiguration gameConfig = parseGameConfiguration( context );

        final IGame game;
        try
        {
            game = context.getGameClient().getGameServerConnection().getGameServer().createGame( gameConfig );
        }
        catch( final GameConfigurationException e )
        {
            throw new CommandletException( Messages.CreateGameCommandlet_execute_illegalGameConfiguration, Messages.CreateGameCommandlet_output_illegalGameConfiguration, e );
        }

        context.getConsole().getDisplay().getWriter().println( Messages.CreateGameCommandlet_output_gameCreated( game.getId() ) );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.CreateGameCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.CreateGameCommandlet_help_synopsis;
    }

    /**
     * Parses the game configuration from the commandlet argument list.
     * 
     * <p>
     * The position of each game configuration component in the commandlet
     * argument list is given below:
     * </p>
     * 
     * <ol>
     * <li>Game system identifier.</li>
     * <li>Game identifier.</li>
     * <li>Game name.</li>
     * <li>Player role binding list.</li>
     * </ol>
     * 
     * @param context
     *        The commandlet context; must not be {@code null}.
     * 
     * @return A game configuration; never {@code null}.
     * 
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If an error occurs while parsing the game configuration.
     */
    /* @NonNull */
    private static IGameConfiguration parseGameConfiguration(
        /* @NonNull */
        final ICommandletContext context )
        throws CommandletException
    {
        assert context != null;

        final int argCount = context.getArguments().size();
        if( argCount < 4 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooFewArgs, CommandletMessages.Commandlet_output_tooFewArgs );
        }
        else if( argCount > 4 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final String gameSystemId = context.getArguments().get( ARG_GAME_SYSTEM_ID );
        final String gameId = context.getArguments().get( ARG_GAME_ID );
        final String gameName = context.getArguments().get( ARG_GAME_NAME );
        final Map<String, String> playerRoleBindings;
        try
        {
            playerRoleBindings = CommandletArguments.parseNamedValueList( context.getArguments().get( ARG_PLAYER_ROLE_BINDING_LIST ) );
        }
        catch( final IllegalArgumentException e )
        {
            throw new CommandletException( Messages.CreateGameCommandlet_parseGameConfiguration_parsePlayerRoleBindingsError, Messages.CreateGameCommandlet_output_parsePlayerRoleBindingsError, e );
        }

        final IGameSystem gameSystem = context.getGameClient().getGameServerConnection().getGameServer().getGameSystem( gameSystemId );
        if( gameSystem == null )
        {
            throw new CommandletException( Messages.CreateGameCommandlet_parseGameConfiguration_unknownGameSystemId( gameSystemId ), Messages.CreateGameCommandlet_ouput_unknownGameSystemId( gameSystemId ) );
        }

        final GameConfigurationBuilder gameConfigBuilder = new GameConfigurationBuilder();
        gameConfigBuilder.setId( gameId );
        gameConfigBuilder.setName( gameName );
        gameConfigBuilder.setSystem( gameSystem );
        for( final Map.Entry<String, String> playerRoleBindingEntry : playerRoleBindings.entrySet() )
        {
            final PlayerConfigurationBuilder playerConfigBuilder = new PlayerConfigurationBuilder();
            playerConfigBuilder.setRoleId( playerRoleBindingEntry.getKey() );
            playerConfigBuilder.setUserId( playerRoleBindingEntry.getValue() );
            gameConfigBuilder.addPlayer( playerConfigBuilder.toPlayerConfiguration() );
        }

        try
        {
            return gameConfigBuilder.toGameConfiguration();
        }
        catch( final IllegalStateException e )
        {
            throw new CommandletException( Messages.CreateGameCommandlet_execute_illegalGameConfiguration, Messages.CreateGameCommandlet_output_illegalGameConfiguration, e );
        }
    }
}
