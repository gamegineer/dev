/*
 * AbstractInvertibleCommandTestCase.java
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
 * Created on Jun 22, 2008 at 10:22:16 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.gamegineer.engine.internal.core.PhantomCommand;
import org.gamegineer.engine.internal.core.commands.GetStateCommand;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.IInvertibleCommand} interface.
 * 
 * @param <C>
 *        The type of the invertible command.
 * @param <T>
 *        The result type of the command.
 */
public abstract class AbstractInvertibleCommandTestCase<C extends IInvertibleCommand<T>, T>
    extends AbstractCommandTestCase<C, T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractInvertibleCommandTestCase} class.
     */
    protected AbstractInvertibleCommandTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked by the test case that ensures the inverse command reverts all
     * state changes made by the command under test.
     * 
     * <p>
     * Implementors should prepare the engine state in such a way that the
     * sequence of calls:
     * </p>
     * 
     * <ol>
     * <li>{@code command.execute()}</li>
     * <li>{@code command.getInverseCommand().execute()}</li>
     * </ol>
     * 
     * <p>
     * does not generate any errors and restores the state of the engine to what
     * it was before the command was executed.
     * </p>
     * 
     * <p>
     * The default implementation does nothing.
     * </p>
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param command
     *        The invertible command under test; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code command} is {@code null}.
     */
    protected void prepareEngineForInverseCommandTest(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IInvertibleCommand<T> command )
        throws Exception
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getInverseCommand} method does not return {@code null}.
     */
    @Test
    public void testGetInverseCommand_ReturnValue_NonNull()
    {
        assertNotNull( getCommand().getInverseCommand() );
    }

    /**
     * Ensures the {@code getInverseCommand} method returns a phantom command if
     * the command is a phantom command.
     */
    @Test
    public void testGetInverseCommand_ReturnValue_PhantomCommand()
    {
        if( !getCommand().getClass().isAnnotationPresent( PhantomCommand.class ) )
        {
            return;
        }

        assertTrue( getCommand().getInverseCommand().getClass().isAnnotationPresent( PhantomCommand.class ) );
    }

    /**
     * Ensures the {@code getInverseCommand} method returns a command that
     * reverts all state changes made by the command under test.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetInverseCommand_ReturnValue_RevertStateChanges()
        throws Exception
    {
        // Prepare the engine for the test and save its current state.
        final IEngine engine = EngineFactory.createEngine();
        prepareEngineForInverseCommandTest( engine, getCommand() );
        final Map<AttributeName, Object> oldState = engine.executeCommand( new GetStateCommand() );

        // Execute the command under test and its inverse
        engine.executeCommand( getCommand() );
        engine.executeCommand( getCommand().getInverseCommand() );

        // Ensure the new engine state equals the old engine state before command execution
        final Map<AttributeName, Object> newState = engine.executeCommand( new GetStateCommand() );
        assertEquals( oldState, newState );
    }
}
