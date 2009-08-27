/*
 * UndoLastCommandCommandAsCommandTest.java
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
 * Created on Apr 26, 2008 at 11:40:17 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.AbstractInvertibleCommandTestCase;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.IState.Scope;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.UndoCommand}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IInvertibleCommand} interface.
 */
public final class UndoCommandAsInvertibleCommandTest
    extends AbstractInvertibleCommandTestCase<UndoCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code UndoCommandAsInvertibleCommandTest} class.
     */
    public UndoCommandAsInvertibleCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractCommandTestCase#createCommand()
     */
    @Override
    protected UndoCommand createCommand()
    {
        return new UndoCommand();
    }

    /*
     * @see org.gamegineer.engine.core.AbstractInvertibleCommandTestCase#prepareEngineForInverseCommandTest(org.gamegineer.engine.core.IEngine, org.gamegineer.engine.core.IInvertibleCommand)
     */
    @Override
    protected void prepareEngineForInverseCommandTest(
        final IEngine engine,
        final IInvertibleCommand<Void> command )
        throws Exception
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "UndoCommandAsInvertibleCommandTest.attribute" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
