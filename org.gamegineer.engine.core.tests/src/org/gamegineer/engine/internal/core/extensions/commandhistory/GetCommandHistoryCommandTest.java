/*
 * GetCommandHistoryCommandTest.java
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
 * Created on May 2, 2008 at 10:49:16 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Arrays;
import java.util.List;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.MockInvertibleCommand;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.GetCommandHistoryCommand}
 * class.
 */
public final class GetCommandHistoryCommandTest
    extends AbstractCommandHistoryCommandTestCase<GetCommandHistoryCommand, List<IInvertibleCommand<?>>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetCommandHistoryCommandTest}
     * class.
     */
    public GetCommandHistoryCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.commandhistory.AbstractCommandHistoryCommandTestCase#createCommand()
     */
    @Override
    protected GetCommandHistoryCommand createCommand()
    {
        return new GetCommandHistoryCommand();
    }

    /**
     * Ensures the {@code execute} method returns the expected command history
     * when invertible commands which actually modify the state are executed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_ReturnValue_Expected_InvertibleCommands_StateModified()
        throws Exception
    {
        final IEngine engine = getEngine();
        final ICommand<Void> command1 = MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name1" ), "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        final ICommand<Void> command2 = MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name2" ), "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        final ICommand<Void> command3 = MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name3" ), "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        final List<ICommand<?>> expectedCommandList = Arrays.asList( new ICommand<?>[] {
            command1, command2, command3
        } );
        for( final ICommand<?> command : expectedCommandList )
        {
            engine.executeCommand( command );
        }

        final List<IInvertibleCommand<?>> actualCommandList = engine.executeCommand( getCommand() );
        assertEquals( expectedCommandList.size(), actualCommandList.size() );
        for( int index = 0, size = expectedCommandList.size(); index < size; ++index )
        {
            assertEquals( expectedCommandList.get( index ), actualCommandList.get( index ) );
        }
    }

    /**
     * Ensures the {@code execute} method returns the expected command history
     * when invertible commands that do NOT modify the state are executed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_ReturnValue_Expected_InvertibleCommands_StateNotModified()
        throws Exception
    {
        final IEngine engine = getEngine();
        engine.executeCommand( new MockInvertibleCommand<Void>() );
        engine.executeCommand( new MockInvertibleCommand<Void>() );
        engine.executeCommand( new MockInvertibleCommand<Void>() );

        final List<IInvertibleCommand<?>> actualCommandList = engine.executeCommand( getCommand() );
        assertEquals( 0, actualCommandList.size() );
    }

    /**
     * Ensures the {@code execute} method returns the expected command history
     * when non-invertible commands are executed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_ReturnValue_Expected_NonInvertibleCommands()
        throws Exception
    {
        final IEngine engine = getEngine();
        engine.executeCommand( new MockCommand<Void>() );
        engine.executeCommand( new MockCommand<Void>() );
        engine.executeCommand( new MockCommand<Void>() );

        final List<IInvertibleCommand<?>> actualCommandList = engine.executeCommand( getCommand() );
        assertEquals( 0, actualCommandList.size() );
    }

    /**
     * Ensures the {@code execute} method does not return {@code null} when the
     * command history is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( getEngine().executeCommand( getCommand() ) );
    }
}
