/*
 * NullCommand.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Apr 23, 2008 at 11:23:34 PM.
 */

package org.gamegineer.engine.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.IEngineContext;

/**
 * A command that does nothing.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NullCommand
    extends AbstractCommand<Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullCommand} class.
     */
    public NullCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public Void execute(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return null;
    }
}
