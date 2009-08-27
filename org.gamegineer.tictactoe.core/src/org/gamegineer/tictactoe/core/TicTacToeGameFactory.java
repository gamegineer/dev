/*
 * TicTacToeGameFactory.java
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
 * Created on Aug 25, 2009 at 9:58:02 PM.
 */

package org.gamegineer.tictactoe.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Future;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.game.core.IGame;

/**
 * A factory for creating a Tic-Tac-Toe game.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class TicTacToeGameFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TicTacToeGameFactory} class.
     */
    private TicTacToeGameFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a Tic-Tac-Toe game adapter for an existing game.
     * 
     * @param game
     *        A game; must not be {@code null}.
     * 
     * @return A Tic-Tac-Toe game adapter; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code game} is {@code null}.
     */
    /* @NonNull */
    public static ITicTacToeGame toTicTacToeGame(
        /* @NonNull */
        final IGame game )
    {
        assertArgumentNotNull( game, "game" ); //$NON-NLS-1$

        return new TicTacToeGameAdapter( game );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Adapts an instance of {@code IGame} to {@code ITicTacToeGame}.
     */
    private static final class TicTacToeGameAdapter
        implements ITicTacToeGame
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual game. */
        private final IGame m_game;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TicTacToeGameAdapter} class.
         * 
         * @param game
         *        The actual game; must not be {@code null}.
         */
        TicTacToeGameAdapter(
            /* @NonNull */
            final IGame game )
        {
            assert game != null;

            m_game = game;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.tictactoe.core.ITicTacToeGame#getBoard()
         */
        public IBoard getBoard()
        {
            try
            {
                return executeCommand( TicTacToeGameCommands.createGetBoardCommand() );
            }
            catch( final EngineException e )
            {
                throw TaskUtils.launderThrowable( e );
            }
        }

        /*
         * @see org.gamegineer.game.core.IGame#getId()
         */
        public String getId()
        {
            return m_game.getId();
        }

        /*
         * @see org.gamegineer.game.core.IGame#getName()
         */
        public String getName()
        {
            return m_game.getName();
        }

        /*
         * @see org.gamegineer.game.core.IGame#getSystemId()
         */
        public String getSystemId()
        {
            return m_game.getSystemId();
        }

        /*
         * @see org.gamegineer.game.core.IGame#isComplete()
         */
        public boolean isComplete()
        {
            return m_game.isComplete();
        }

        /*
         * @see org.gamegineer.engine.core.IEngine#executeCommand(org.gamegineer.engine.core.ICommand)
         */
        public <T> T executeCommand(
            final ICommand<T> command )
            throws EngineException
        {
            return m_game.executeCommand( command );
        }

        /*
         * @see org.gamegineer.engine.core.IEngine#isShutdown()
         */
        public boolean isShutdown()
        {
            return m_game.isShutdown();
        }

        /*
         * @see org.gamegineer.engine.core.IEngine#shutdown()
         */
        public void shutdown()
            throws InterruptedException
        {
            m_game.shutdown();
        }

        /*
         * @see org.gamegineer.engine.core.IEngine#submitCommand(org.gamegineer.engine.core.ICommand)
         */
        public <T> Future<T> submitCommand(
            final ICommand<T> command )
        {
            return m_game.submitCommand( command );
        }
    }
}
