/*
 * InitializeEngineCommand.java
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
 * Created on Jul 23, 2008 at 11:58:06 PM.
 */

package org.gamegineer.game.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;
import org.gamegineer.game.core.GameException;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.StageBuilder;
import org.gamegineer.game.internal.core.GameAttributes;
import org.gamegineer.game.internal.core.Player;
import org.gamegineer.game.internal.core.Stage;
import org.gamegineer.game.internal.core.system.stages.RootStageStrategy;

/**
 * A command to initialize the application state of the game engine.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class InitializeEngineCommand
    extends AbstractCommand<Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game configuration. */
    private final IGameConfiguration m_gameConfig;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InitializeEngineCommand} class.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}. It is assumed
     *        the game configuration has already been validated.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameConfig} is {@code null}.
     */
    public InitializeEngineCommand(
        /* @NonNull */
        final IGameConfiguration gameConfig )
    {
        assertArgumentNotNull( gameConfig, "gameConfig" ); //$NON-NLS-1$

        m_gameConfig = gameConfig;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "boxing" )
    public Void execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IState state = context.getState();

        // Add simple game attributes
        GameAttributes.GAME_ID.add( state, m_gameConfig.getId() );
        GameAttributes.GAME_NAME.add( state, m_gameConfig.getName() );
        GameAttributes.GAME_SYSTEM_ID.add( state, m_gameConfig.getSystem().getId() );

        // Add game player list
        final List<Player> players = new ArrayList<Player>();
        for( final IPlayerConfiguration playerConfig : m_gameConfig.getPlayers() )
        {
            players.add( new Player( getRole( playerConfig.getRoleId() ), playerConfig.getUserId() ) );
        }
        GameAttributes.PLAYER_LIST.add( state, players );

        // Add and activate the root game stage
        final StageBuilder builder = new StageBuilder();
        builder.setId( "org.gamegineer.game.stage.root" ); //$NON-NLS-1$
        builder.setCardinality( 0 );
        builder.setStrategy( new RootStageStrategy() );
        for( final IStage stage : m_gameConfig.getSystem().getStages() )
        {
            builder.addStage( stage );
        }
        final Stage rootStage = new Stage( builder.toStage() );
        GameAttributes.ROOT_STAGE.add( state, rootStage );
        rootStage.activate( context );

        // Because no engine listeners have yet been registered, the root stage
        // will not automatically submit an ActivateStageCommand in response to
        // the above activate() call.  Thus, we manually submit that command
        // here.  This is safe because the root stage is guaranteed to have at
        // least one child stage.
        final ICommandQueue commandQueue = context.getExtension( ICommandQueue.class );
        if( commandQueue == null )
        {
            throw new GameException( Messages.InitializeEngineCommand_commandQueueExtension_unavailable );
        }
        commandQueue.submitCommand( context, new ActivateStageCommand( rootStage.getId(), rootStage.getVersion( state ) ) );

        return null;
    }

    /**
     * Gets the role in the game configuration associated with the specified
     * role identifier.
     * 
     * @param roleId
     *        The role identifier; must not be {@code null}.
     * 
     * @return The role in the game configuration associated with the specified
     *         role identifier; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code roleId} does not exist in the game configuration.
     */
    /* @NonNull */
    private IRole getRole(
        /* @NonNull */
        final String roleId )
    {
        assert roleId != null;

        for( final IRole role : m_gameConfig.getSystem().getRoles() )
        {
            if( roleId.equals( role.getId() ) )
            {
                return role;
            }
        }

        throw new IllegalArgumentException( "roleId" ); //$NON-NLS-1$
    }
}
