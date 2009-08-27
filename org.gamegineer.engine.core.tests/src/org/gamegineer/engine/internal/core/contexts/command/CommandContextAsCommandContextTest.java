/*
 * CommandContextAsCommandContextTest.java
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
 * Created on Apr 11, 2009 at 9:50:40 PM.
 */

package org.gamegineer.engine.internal.core.contexts.command;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;
import org.gamegineer.engine.core.contexts.command.AbstractCommandContextTestCase;
import org.gamegineer.engine.core.contexts.command.ICommandContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.contexts.command.CommandContext}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.contexts.command.ICommandContext}
 * interface.
 */
public final class CommandContextAsCommandContextTest
    extends AbstractCommandContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandContextAsCommandContextTest} class.
     */
    public CommandContextAsCommandContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.contexts.command.AbstractCommandContextTestCase#createCommandContext(java.util.Map)
     */
    @Override
    protected ICommandContext createCommandContext(
        final Map<String, Object> attributes )
    {
        assertArgumentNotNull( attributes, "attributes" ); //$NON-NLS-1$

        return new CommandContext( attributes );
    }
}
