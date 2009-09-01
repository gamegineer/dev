/*
 * CommandHistoryEntryTest.java
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
 * Created on Mar 28, 2009 at 10:01:01 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.MockInvertibleCommand;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.CommandHistory.Entry} class.
 */
public final class CommandHistoryEntryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command history entry under test in the fixture. */
    private CommandHistory.Entry m_entry;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryEntryTest} class.
     */
    public CommandHistoryEntryTest()
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
        m_entry = new CommandHistory.Entry( createDummy( IInvertibleCommand.class ), createDummy( ICommandContext.class ) );
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
        m_entry = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * command.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Command_Null()
    {
        new CommandHistory.Entry( null, createDummy( ICommandContext.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * command context.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_CommandContext_Null()
    {
        new CommandHistory.Entry( createDummy( IInvertibleCommand.class ), null );
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
        final CommandHistory.Entry entry1 = new CommandHistory.Entry( command, commandContext );
        final CommandHistory.Entry entry2 = new CommandHistory.Entry( command, commandContext );

        assertTrue( entry1.equals( entry2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null} value
     * for the other object.
     */
    @Test
    public void testEquals_Object_Null()
    {
        assertFalse( m_entry.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly reports when the other object
     * is unequal.
     */
    @Test
    public void testEquals_Object_Unequal()
    {
        final CommandHistory.Entry entry1 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );
        final CommandHistory.Entry entry2 = new CommandHistory.Entry( new MockInvertibleCommand<Void>(), new FakeCommandContext() );

        assertFalse( entry1.equals( entry2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly handles a value for the other
     * object when it is of the wrong type.
     */
    @Test
    public void testEquals_Object_WrongType()
    {
        assertFalse( m_entry.equals( new Object() ) );
    }

    /**
     * Ensures the {@code getCommand} method does not return {@code null}.
     */
    @Test
    public void testGetCommand_ReturnValue_NonNull()
    {
        assertNotNull( m_entry.getCommand() );
    }

    /**
     * Ensures the {@code getCommandContext} method does not return {@code null}.
     */
    @Test
    public void testGetCommandContext_ReturnValue_NonNull()
    {
        assertNotNull( m_entry.getCommandContext() );
    }

    /**
     * Ensures the {@code hashCode} method returns the same value for two
     * different instances which are equal.
     */
    @Test
    public void testHashCode_ReturnValue_EqualInstances()
    {
        final IInvertibleCommand<Void> command = new MockInvertibleCommand<Void>();
        final ICommandContext commandContext = new FakeCommandContext();
        final CommandHistory.Entry entry1 = new CommandHistory.Entry( command, commandContext );
        final CommandHistory.Entry entry2 = new CommandHistory.Entry( command, commandContext );

        assertNotSame( entry1, entry2 );
        assertEquals( entry1, entry2 );
        assertEquals( entry1.hashCode(), entry2.hashCode() );
    }
}
