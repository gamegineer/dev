/*
 * PrintLineCommandlet.java
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
 * Created on Oct 19, 2008 at 9:41:37 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;

/**
 * A commandlet that prints a line of text to the console.
 * 
 * <p>
 * This commandlet expects zero or one arguments. The single argument is the
 * line of text to be printed to the console. If no arguments are specified, it
 * is equivalent to specifying an empty line of text.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class PrintLineCommandlet
    implements ICommandlet
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PrintLineCommandlet} class.
     */
    public PrintLineCommandlet()
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
        if( argCount > 1 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        final String line = (argCount == 1) ? context.getArguments().get( 0 ) : ""; //$NON-NLS-1$
        context.getConsole().getDisplay().getWriter().println( line );
    }
}
