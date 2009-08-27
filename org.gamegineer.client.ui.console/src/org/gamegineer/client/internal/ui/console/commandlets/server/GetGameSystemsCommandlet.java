/*
 * GetGameSystemsCommandlet.java
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
 * Created on Jan 2, 2009 at 11:22:20 PM.
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
import org.gamegineer.game.core.system.IGameSystem;

/**
 * A commandlet that displays the game systems available on the server.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetGameSystemsCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetGameSystemsCommandlet} class.
     */
    public GetGameSystemsCommandlet()
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

        final Collection<IGameSystem> gameSystems = context.getGameClient().getGameServerConnection().getGameServer().getGameSystems();

        final PrintWriter writer = context.getConsole().getDisplay().getWriter();
        if( gameSystems.isEmpty() )
        {
            writer.println( Messages.GetGameSystemsCommandlet_output_noGameSystems );
        }
        else
        {
            for( final IGameSystem gameSystem : gameSystems )
            {
                writer.println( Messages.GetGameSystemsCommandlet_output_gameSystemInfo( CommandletUtils.getGameSystemUi( context, gameSystem.getId() ) ) );
            }
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.GetGameSystemsCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.GetGameSystemsCommandlet_help_synopsis;
    }
}
