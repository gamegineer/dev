/*
 * CommandHistoryTest.java
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
 * Created on Apr 27, 2008 at 10:54:35 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.MockInvertibleCommand;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.CommandHistory} class.
 */
public final class CommandHistoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command history under test in the fixture. */
    private CommandHistory m_history;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryTest} class.
     */
    public CommandHistoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_history = new CommandHistory();
        m_history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        m_history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        m_history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
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
        m_history = null;
    }

    /**
     * Ensures the {@code add(IInvertibleCommand, CommandContext)} method adds
     * commands to the command history as expected.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddWithCommand()
    {
        final CommandHistory history = new CommandHistory();

        history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );

        assertEquals( 2, history.getEntries().size() );
    }

    /**
     * Ensures the {@code add(IInvertibleCommand, CommandContext)} method throws
     * an exception when passed a {@code null} command.
     */
    @Test( expected = AssertionError.class )
    public void testAddWithCommand_Command_Null()
    {
        m_history.add( null, createDummy( ICommandContext.class ) );
    }

    /**
     * Ensures the {@code add(IInvertibleCommand, CommandContext)} method throws
     * an exception when passed a {@code null} command context.
     */
    @Test( expected = AssertionError.class )
    public void testAddWithCommand_CommandContext_Null()
    {
        m_history.add( createDummy( IInvertibleCommand.class ), null );
    }

    /**
     * Ensures the {@code add(IInvertibleCommand, CommandContext)} method clears
     * the undo history.
     */
    @Test
    public void testAddWithCommand_UndoHistory_Cleared()
    {
        m_history.undo();
        assertTrue( m_history.canRedo() );

        m_history.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );

        assertFalse( m_history.canRedo() );
    }

    /**
     * Ensures the {@code add(CommandHistory.Entry)} method adds commands to the
     * command history as expected.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddWithEntry()
    {
        final CommandHistory history = new CommandHistory();

        history.add( new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() ) );
        history.add( new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() ) );

        assertEquals( 2, history.getEntries().size() );
    }

    /**
     * Ensures the {@code add(CommandHistory.Entry)} method throws an exception
     * when passed a {@code null} entry.
     */
    @Test( expected = AssertionError.class )
    public void testAddWithEntry_Entry_Null()
    {
        m_history.add( null );
    }

    /**
     * Ensures the {@code add(CommandHistory.Entry)} method clears the undo
     * history.
     */
    @Test
    public void testAddWithEntry_UndoHistory_Cleared()
    {
        m_history.undo();
        assertTrue( m_history.canRedo() );

        m_history.add( new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() ) );

        assertFalse( m_history.canRedo() );
    }

    /**
     * Ensures the {@code canRedo} method correctly reports when redo is
     * allowed.
     */
    @Test
    public void testCanRedo_Redo_Allowed()
    {
        m_history.undo();

        assertTrue( m_history.canRedo() );
    }

    /**
     * Ensures the {@code canRedo} method correctly reports when redo is not
     * allowed.
     */
    @Test
    public void testCanRedo_Redo_Disallowed()
    {
        assertFalse( m_history.canRedo() );
    }

    /**
     * Ensures the {@code canUndo} method correctly reports when undo is
     * allowed.
     */
    @Test
    public void testCanUndo_Undo_Allowed()
    {
        assertTrue( m_history.canUndo() );
    }

    /**
     * Ensures the {@code canUndo} method correctly reports when undo is not
     * allowed.
     */
    @Test
    public void testCanUndo_Undo_Disallowed()
    {
        final CommandHistory history = new CommandHistory();

        assertFalse( history.canUndo() );
    }

    /**
     * Ensures the {@code equals} method correctly reports when the other object
     * is equal.
     */
    @Test
    public void testEquals_Object_Equal()
    {
        final IInvertibleCommand<Void> command = new MockInvertibleCommand<Void>();
        final ICommandContext commandContext = new FakeCommandContext();
        final CommandHistory history1 = new CommandHistory();
        history1.add( command, commandContext );
        final CommandHistory history2 = new CommandHistory();
        history2.add( command, commandContext );

        assertTrue( history1.equals( history2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null} value
     * for the other object.
     */
    @Test
    public void testEquals_Object_Null()
    {
        assertFalse( m_history.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly reports when the other object
     * is unequal.
     */
    @Test
    public void testEquals_Object_Unequal()
    {
        final CommandHistory history1 = new CommandHistory();
        history1.add( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory history2 = new CommandHistory();

        assertFalse( history1.equals( history2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a value for the other
     * object when it is of the wrong type.
     */
    @Test
    public void testEquals_Object_WrongType()
    {
        assertFalse( m_history.equals( new Object() ) );
    }

    /**
     * Ensures the {@code getEntries} method does not return {@code null} when
     * the command history is empty.
     */
    @Test
    public void testGetEntries_ReturnValue_NonNull()
    {
        final CommandHistory history = new CommandHistory();

        assertNotNull( history.getEntries() );
    }

    /**
     * Ensures the {@code getRedoEntry} method throws an exception when redo is
     * not allowed.
     */
    @Test( expected = AssertionError.class )
    public void testGetRedoEntry_Redo_Disallowed()
    {
        m_history.getRedoEntry();
    }

    /**
     * Ensures the {@code getUndoEntry} method throws an exception when undo is
     * not allowed.
     */
    @Test( expected = AssertionError.class )
    public void testGetUndoEntry_Undo_Disallowed()
    {
        final CommandHistory history = new CommandHistory();

        history.getUndoEntry();
    }

    /**
     * Ensures the {@code hashCode} method returns the same value for two
     * different instances which are equal.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHashCode_ReturnValue_EqualInstances()
    {
        final IInvertibleCommand<Void> command = new MockInvertibleCommand<Void>();
        final ICommandContext commandContext = new FakeCommandContext();
        final CommandHistory history1 = new CommandHistory();
        history1.add( command, commandContext );
        final CommandHistory history2 = new CommandHistory();
        history2.add( command, commandContext );

        assertNotSame( history1, history2 );
        assertEquals( history1, history2 );
        assertEquals( history1.hashCode(), history2.hashCode() );
    }

    /**
     * Ensures the {@code redo} method updates the next command index
     * appropriately.
     */
    @Test
    public void testRedo_NextCommandIndex_Updated()
    {
        final CommandHistory.Entry entry1 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory.Entry entry2 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory history = new CommandHistory();
        history.add( entry1 );
        history.add( entry2 );
        history.undo();
        history.undo();

        history.redo();

        assertEquals( entry1, history.getUndoEntry() );
        assertEquals( entry2, history.getRedoEntry() );
    }

    /**
     * Ensures the {@code redo} method throws an exception when redo is not
     * allowed.
     */
    @Test( expected = AssertionError.class )
    public void testRedo_Redo_Disallowed()
    {
        m_history.redo();
    }

    /**
     * Ensures the {@code reset} method resets the command history as expected.
     */
    @Test
    public void testReset()
    {
        final int newEntriesSize = m_history.getEntries().size() / 2;
        final List<CommandHistory.Entry> newEntries = new ArrayList<CommandHistory.Entry>();
        for( int index = 0; index < newEntriesSize; ++index )
        {
            newEntries.add( new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() ) );
        }

        m_history.reset( newEntries );

        assertEquals( newEntries, m_history.getEntries() );
        assertFalse( m_history.canRedo() );
        assertTrue( m_history.canUndo() );
    }

    /**
     * Ensures the {@code reset} method throws an exception when passed a
     * {@code null} entry list.
     */
    @Test( expected = AssertionError.class )
    public void testReset_Entries_Null()
    {
        m_history.reset( null );
    }

    /**
     * Ensures the {@code undo} method updates the next command index
     * appropriately.
     */
    @Test
    public void testUndo_NextCommandIndex_Updated()
    {
        final CommandHistory.Entry entry1 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory.Entry entry2 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory history = new CommandHistory();
        history.add( entry1 );
        history.add( entry2 );

        history.undo();

        assertEquals( entry1, history.getUndoEntry() );
        assertEquals( entry2, history.getRedoEntry() );
    }

    /**
     * Ensures the {@code undo} method throws an exception when undo is not
     * allowed.
     */
    @Test( expected = AssertionError.class )
    public void testUndo_Undo_Disallowed()
    {
        final CommandHistory history = new CommandHistory();

        history.undo();
    }
}
