/*
 * CreateGameCommandletAsCommandletTest.java
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
 * Created on Jan 10, 2009 at 10:30:03 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gamegineer.client.core.connection.GameServerConnectionFactory;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletArguments;
import org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.engine.core.extensions.securitymanager.Principals;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.server.core.GameServerConfigurationException;
import org.gamegineer.server.core.GameServerFactory;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.config.GameServerConfigurationBuilder;
import org.gamegineer.server.core.system.FakeGameSystemSource;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlets.server.CreateGameCommandlet}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public final class CreateGameCommandletAsCommandletTest
    extends AbstractCommandletTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CreateGameCommandletAsCommandletTest} class.
     */
    public CreateGameCommandletAsCommandletTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.AbstractCommandletTestCase#createCommandlet()
     */
    @Override
    protected ICommandlet createCommandlet()
    {
        return new CreateGameCommandlet();
    }

    /**
     * Creates a commandlet argument list appropriate for the specified game
     * system.
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @return A commandlet argument list; never {@code null}.
     */
    /* @NonNull */
    private static List<String> createCommandletArgumentList(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assert gameSystem != null;

        final List<String> args = new ArrayList<String>();
        args.add( gameSystem.getId() );
        args.add( "game-id" ); //$NON-NLS-1$
        args.add( "game-name" ); //$NON-NLS-1$

        final Map<String, String> playerRoleBindings = new HashMap<String, String>();
        for( final IRole role : gameSystem.getRoles() )
        {
            final IPlayerConfiguration playerConfig = org.gamegineer.game.core.config.Configurations.createPlayerConfiguration( role.getId() );
            playerRoleBindings.put( playerConfig.getRoleId(), playerConfig.getUserId() );
        }
        args.add( CommandletArguments.formatNamedValueList( playerRoleBindings ) );

        return args;
    }

    /**
     * Creates a new commandlet context for the specified argument list,
     * associates it with a default console, and connects its associated game
     * client to the specified game server.
     * 
     * @param gameServer
     *        The game server; must not be {@code null}.
     * @param args
     *        The commandlet argument list; must not be {@code null}.
     * 
     * @return A new commandlet context; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs creating the commandlet context.
     */
    /* @NonNull */
    private ICommandletContext createCommandletContext(
        /* @NonNull */
        final IGameServer gameServer,
        /* @NonNull */
        final List<String> args )
        throws Exception
    {
        assert gameServer != null;
        assert args != null;

        final ICommandletContext context = createCommandletContext( args );
        context.getGameClient().connect( GameServerConnectionFactory.createLocalGameServerConnection( gameServer, Principals.getAnonymousUserPrincipal() ) );
        return context;
    }

    /**
     * Creates a game server appropriate for testing within this fixture.
     * 
     * @return A game server; never {@code null}.
     * 
     * @throws org.gamegineer.server.core.GameServerConfigurationException
     *         If the game server cannot be created.
     */
    /* @NonNull */
    private static IGameServer createGameServer()
        throws GameServerConfigurationException
    {
        final GameServerConfigurationBuilder builder = org.gamegineer.server.core.config.Configurations.createGameServerConfigurationBuilder();
        builder.setGameSystemSource( new FakeGameSystemSource( Collections.singletonList( GameSystems.createUniqueGameSystem() ) ) );
        return GameServerFactory.createGameServer( builder.toGameServerConfiguration() );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list that contains too few arguments.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_TooFew()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1", "2", "3" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * argument list that contains too many arguments.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_ArgCount_TooMany()
        throws Exception
    {
        getCommandlet().execute( createCommandletContext( Arrays.asList( "1", "2", "3", "4", "5" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * illegal game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_GameConfig_Illegal()
        throws Exception
    {
        final IGameServer gameServer = createGameServer();
        final IGameSystem gameSystem = gameServer.getGameSystems().iterator().next();
        final List<String> args = createCommandletArgumentList( gameSystem );
        args.set( CreateGameCommandlet.ARG_PLAYER_ROLE_BINDING_LIST, "" ); // clear player role bindings to make game configuration illegal //$NON-NLS-1$
        final ICommandletContext context = createCommandletContext( gameServer, args );

        getCommandlet().execute( context );
    }

    /**
     * Ensures the {@code execute} method creates a new game when passed a legal
     * game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_GameConfig_Legal()
        throws Exception
    {
        final IGameServer gameServer = createGameServer();
        final IGameSystem gameSystem = gameServer.getGameSystems().iterator().next();
        final List<String> args = createCommandletArgumentList( gameSystem );
        final ICommandletContext context = createCommandletContext( gameServer, args );

        getCommandlet().execute( context );

        assertEquals( 1, gameServer.getGames().size() );
        assertNotNull( gameServer.getGame( args.get( CreateGameCommandlet.ARG_GAME_ID ) ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * unknown game system identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_GameSystemId_Unknown()
        throws Exception
    {
        final IGameServer gameServer = createGameServer();
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<String> args = createCommandletArgumentList( gameSystem );
        final ICommandletContext context = createCommandletContext( gameServer, args );

        getCommandlet().execute( context );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed an
     * illegal player role binding list.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = CommandletException.class )
    public void testExecute_PlayerRoleBindings_Illegal()
        throws Exception
    {
        final IGameServer gameServer = createGameServer();
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<String> args = createCommandletArgumentList( gameSystem );
        args.set( CreateGameCommandlet.ARG_PLAYER_ROLE_BINDING_LIST, "," ); //$NON-NLS-1$
        final ICommandletContext context = createCommandletContext( gameServer, args );

        getCommandlet().execute( context );
    }
}
