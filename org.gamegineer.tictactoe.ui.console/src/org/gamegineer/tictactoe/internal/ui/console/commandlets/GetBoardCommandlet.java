/*
 * GetBoardCommandlet.java
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
 * Created on Jun 15, 2009 at 10:14:29 PM.
 */

package org.gamegineer.tictactoe.internal.ui.console.commandlets;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;
import org.gamegineer.tictactoe.core.IBoard;
import org.gamegineer.tictactoe.core.ITicTacToeGame;
import org.gamegineer.tictactoe.core.SquareId;
import org.gamegineer.tictactoe.core.TicTacToeGameFactory;

/**
 * A commandlet that displays the game board.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetBoardCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetBoardCommandlet} class.
     */
    public GetBoardCommandlet()
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
            throw new CommandletException( Messages.GetBoardCommandlet_execute_noGameIdArg, Messages.GetBoardCommandlet_output_noGameIdArg );
        }
        else if( argCount > 1 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final PrintWriter writer = context.getConsole().getDisplay().getWriter();
        final String gameId = context.getArguments().get( 0 );
        final IGame game = context.getGameClient().getGameServerConnection().getGameServer().getGame( gameId );
        if( game == null )
        {
            writer.println( Messages.GetBoardCommandlet_output_noGame );
            return;
        }

        final IGameSystemUi gameSystemUi = context.getGameClient().getGameSystemUi( game.getSystemId() );
        final Map<String, String> roleNames = new HashMap<String, String>();
        roleNames.put( null, " " ); //$NON-NLS-1$
        for( final IRoleUi roleUi : gameSystemUi.getRoles() )
        {
            roleNames.put( roleUi.getId(), roleUi.getName() );
        }

        final ITicTacToeGame ticTacToeGame = TicTacToeGameFactory.toTicTacToeGame( game );
        final IBoard board = ticTacToeGame.getBoard();
        writer.println( String.format( " %1$s | %2$s | %3$s ", roleNames.get( board.getSquare( SquareId.TOP_LEFT ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.TOP_CENTER ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.TOP_RIGHT ).getOwnerRoleId() ) ) ); //$NON-NLS-1$
        writer.println( "---+---+---" ); //$NON-NLS-1$
        writer.println( String.format( " %1$s | %2$s | %3$s ", roleNames.get( board.getSquare( SquareId.MIDDLE_LEFT ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.MIDDLE_CENTER ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.MIDDLE_RIGHT ).getOwnerRoleId() ) ) ); //$NON-NLS-1$
        writer.println( "---+---+---" ); //$NON-NLS-1$
        writer.println( String.format( " %1$s | %2$s | %3$s ", roleNames.get( board.getSquare( SquareId.BOTTOM_LEFT ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.BOTTOM_CENTER ).getOwnerRoleId() ), roleNames.get( board.getSquare( SquareId.BOTTOM_RIGHT ).getOwnerRoleId() ) ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.GetBoardCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.GetBoardCommandlet_help_synopsis;
    }
}
