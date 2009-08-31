/*
 * AbstractCommandHistoryTestCase.java
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
 * Created on Apr 25, 2008 at 10:35:20 PM.
 */

package org.gamegineer.engine.core.extensions.commandhistory;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.engine.core.AbstractInvertibleCommand;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory}
 * interface.
 */
public abstract class AbstractCommandHistoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command history under test in the fixture. */
    private ICommandHistory m_commandHistory;

    /** The commands used to initialize the command history. */
    private final List<IInvertibleCommand<?>> m_commands;

    /** The engine context for use in the fixture. */
    private IEngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandHistoryTestCase}
     * class.
     */
    protected AbstractCommandHistoryTestCase()
    {
        final int COMMAND_COUNT = 3;
        m_commands = new ArrayList<IInvertibleCommand<?>>( COMMAND_COUNT );
        for( int index = 0; index < COMMAND_COUNT; ++index )
        {
            m_commands.add( new Command( getCommandId( index ) ) );
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command history to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param commands
     *        The initial command history; must not be {@code null}.
     * 
     * @return The command history to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code commands} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICommandHistory createCommandHistory(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        List<IInvertibleCommand<?>> commands )
        throws Exception;

    /**
     * Creates an engine context suitable for testing the command history.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the command history; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new FakeEngineContext();
    }

    /**
     * Gets the expected identifier for the command at the specified index in
     * the command history.
     * 
     * @param index
     *        The command history index; must be greater than or equal to zero.
     * 
     * @return The expected identifier for the command at the specified index in
     *         the command history; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getCommandId(
        final int index )
    {
        assert index >= 0;

        return String.format( "command.%1$d", index ); //$NON-NLS-1$
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_context = createEngineContext();
        m_commandHistory = createCommandHistory( m_context, m_commands );
        assertNotNull( m_commandHistory );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_commandHistory = null;
        m_context = null;
    }

    /**
     * Ensures the {@code canRedo} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testCanRedo_Context_Null()
    {
        m_commandHistory.canRedo( null );
    }

    /**
     * Ensures the {@code canRedo} method correctly reports when redo is
     * allowed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCanRedo_Redo_Allowed()
        throws Exception
    {
        m_commandHistory.undo( m_context );

        assertTrue( m_commandHistory.canRedo( m_context ) );
    }

    /**
     * Ensures the {@code canRedo} method correctly reports when redo is not
     * allowed.
     */
    @Test
    public void testCanRedo_Redo_Disallowed()
    {
        assertFalse( m_commandHistory.canRedo( m_context ) );
    }

    /**
     * Ensures the {@code canUndo} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testCanUndo_Context_Null()
    {
        m_commandHistory.canUndo( null );
    }

    /**
     * Ensures the {@code canUndo} method correctly reports when undo is
     * allowed.
     */
    @Test
    public void testCanUndo_Undo_Allowed()
    {
        assertTrue( m_commandHistory.canUndo( m_context ) );
    }

    /**
     * Ensures the {@code canUndo} method correctly reports when undo is not
     * allowed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCanUndo_Undo_Disallowed()
        throws Exception
    {
        final List<IInvertibleCommand<?>> actualCommands = m_commandHistory.getCommands( m_context );
        for( int index = 0, size = actualCommands.size(); index < size; ++index )
        {
            m_commandHistory.undo( m_context );
        }

        assertFalse( m_commandHistory.canUndo( m_context ) );
    }

    /**
     * Ensures the {@code getCommands} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCommands_Context_Null()
    {
        m_commandHistory.getCommands( null );
    }

    /**
     * Ensures the {@code getCommands} method returns a copy of the command
     * history.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetCommands_ReturnValue_Copy()
    {
        final List<IInvertibleCommand<?>> commands = m_commandHistory.getCommands( m_context );
        final int expectedCommandsSize = commands.size();

        commands.add( createDummy( IInvertibleCommand.class ) );

        assertEquals( expectedCommandsSize, m_commandHistory.getCommands( m_context ).size() );
    }

    /**
     * Ensures the {@code getCommands} method returns the expected command
     * history.
     */
    @Test
    public void testGetCommands_ReturnValue_Expected()
    {
        final List<IInvertibleCommand<?>> actualCommands = m_commandHistory.getCommands( m_context );
        assertTrue( m_commands.size() <= actualCommands.size() );
        final int sizeDifference = actualCommands.size() - m_commands.size();

        for( int index = 0, size = m_commands.size(); index < size; ++index )
        {
            final Command expectedCommand = (Command)m_commands.get( index );
            final Command actualCommand = (Command)actualCommands.get( index + sizeDifference );
            assertEquals( expectedCommand.getId(), actualCommand.getId() );
        }
    }

    /**
     * Ensures the {@code getCommands} method does not return {@code null} when
     * the command history is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetCommands_ReturnValue_NonNull()
        throws Exception
    {
        final IEngineContext context = createEngineContext();
        final ICommandHistory commandHistory = createCommandHistory( context, Collections.<IInvertibleCommand<?>>emptyList() );

        assertNotNull( commandHistory.getCommands( context ) );
    }

    /**
     * Ensures the {@code redo} method executes the most recent command in the
     * undo history and moves it to the command history.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testRedo()
        throws Exception
    {
        final int originalCommandsSize = m_commandHistory.getCommands( m_context ).size();
        final String expectedId = getCommandId( m_commands.size() - 1 );
        m_commandHistory.undo( m_context );

        m_commandHistory.redo( m_context );

        final List<IInvertibleCommand<?>> commands = m_commandHistory.getCommands( m_context );
        assertEquals( originalCommandsSize, commands.size() );
        final Command lastCommand = (Command)commands.get( commands.size() - 1 );
        assertEquals( expectedId, lastCommand.getId() );
    }

    /**
     * Ensures the {@code redo} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRedo_Context_Null()
        throws Exception
    {
        m_commandHistory.redo( null );
    }

    /**
     * Ensures the {@code redo} method throws an exception when the undo history
     * is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testRedo_UndoHistory_Empty()
        throws Exception
    {
        m_commandHistory.redo( m_context );
    }

    /**
     * Ensures the {@code undo} method executes the inverse of the most recent
     * command in the command history and moves it to the undo history.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testUndo()
        throws Exception
    {
        final int originalCommandsSize = m_commandHistory.getCommands( m_context ).size();
        final String expectedId = getCommandId( m_commands.size() - 2 );

        m_commandHistory.undo( m_context );

        final List<IInvertibleCommand<?>> commands = m_commandHistory.getCommands( m_context );
        assertEquals( originalCommandsSize - 1, commands.size() );
        final Command lastCommand = (Command)commands.get( commands.size() - 1 );
        assertEquals( expectedId, lastCommand.getId() );
    }

    /**
     * Ensures the {@code undo} method throws an exception when the command
     * history is empty.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testUndo_CommandHistory_Empty()
        throws Exception
    {
        final List<IInvertibleCommand<?>> actualCommands = m_commandHistory.getCommands( m_context );
        for( int index = 0, size = actualCommands.size(); index < size; ++index )
        {
            m_commandHistory.undo( m_context );
        }

        m_commandHistory.undo( m_context );
    }

    /**
     * Ensures the {@code undo} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testUndo_Context_Null()
        throws Exception
    {
        m_commandHistory.undo( null );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A command used to test command history functionality.
     * 
     * <p>
     * This command increments the integer value {@code ATTR_TEST} in the engine
     * state. Subsequently, its inverse decrements that value.
     * </p>
     */
    @CommandBehavior( writeLockRequired = true )
    private static final class Command
        extends AbstractInvertibleCommand<Integer>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The name of the test attribute. */
        static final AttributeName ATTR_TEST = new AttributeName( Scope.APPLICATION, "AbstractCommandHistoryTestCase.Command.test" ); //$NON-NLS-1$

        /** The command identifier. */
        private final String m_id;

        /** Indicates this command instance should perform the inverse operation. */
        private final boolean m_isInverse;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Command} class.
         * 
         * @param id
         *        The command identifier; must not be {@code null}.
         */
        Command(
            /* @NonNull */
            final String id )
        {
            this( id, false );
        }

        /**
         * Initializes a new instance of the {@code Command} class to perform
         * either the normal or inverse operation.
         * 
         * @param id
         *        The command identifier; must not be {@code null}.
         * @param isInverse
         *        {@code true} if this command instance should perform the
         *        inverse operation; {@code false} to perform the normal
         *        operation.
         */
        private Command(
            /* @NonNull */
            final String id,
            final boolean isInverse )
        {
            assert id != null;

            m_id = id;
            m_isInverse = isInverse;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * @return The current value of the {@code ATTR_TEST} attribute after
         *         the command has performed its designated operation.
         * 
         * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
         */
        @SuppressWarnings( "boxing" )
        public Integer execute(
            final IEngineContext context )
        {
            final IState state = context.getState();
            if( !state.containsAttribute( ATTR_TEST ) )
            {
                state.addAttribute( ATTR_TEST, 0 );
            }
            final Integer value = (Integer)state.getAttribute( ATTR_TEST ) + (m_isInverse ? -1 : +1);
            state.setAttribute( ATTR_TEST, value );
            return value;
        }

        /**
         * Gets the command identifier.
         * 
         * @return The command identifier; never {@code null}.
         */
        /* @NonNull */
        String getId()
        {
            return m_id;
        }

        /*
         * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
         */
        public IInvertibleCommand<Integer> getInverseCommand()
        {
            return new Command( m_id + ".inverse", !m_isInverse ); //$NON-NLS-1$
        }
    }
}
