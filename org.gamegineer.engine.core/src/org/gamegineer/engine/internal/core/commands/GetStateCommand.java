/*
 * GetStateCommand.java
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
 * Created on Apr 29, 2008 at 10:26:07 PM.
 */

package org.gamegineer.engine.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;

/**
 * A command that retrieves the engine state.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetStateCommand
    extends AbstractCommand<Map<AttributeName, Object>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetStateCommand} class.
     */
    public GetStateCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @return A map of engine state attributes; never {@code null}. A map key
     *         is an {@code AttributeName} representing the attribute name. A
     *         map value is an {@code Object} representing the attribute value.
     * 
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public Map<AttributeName, Object> execute(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IState state = context.getState();
        final Map<AttributeName, Object> attributes = new HashMap<AttributeName, Object>();
        for( final AttributeName attributeName : state.getAttributeNames() )
        {
            attributes.put( attributeName, state.getAttribute( attributeName ) );
        }

        return attributes;
    }
}
