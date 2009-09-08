/*
 * CommandletExecutorTest.java
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
 * Created on Oct 23, 2008 at 11:30:55 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.IStatelet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletExecutor}
 * class.
 */
public final class CommandletExecutorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet executor under test in the fixture. */
    private CommandletExecutor executor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletExecutorTest} class.
     */
    public CommandletExecutorTest()
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
        executor_ = new CommandletExecutor( createDummy( ICommandlet.class ), new ArrayList<String>() );
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
        executor_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * commandlet.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Commandlet_Null()
    {
        new CommandletExecutor( null, Collections.<String>emptyList() );
    }

    /**
     * Ensures the constructor makes a deep copy of the commandlet argument
     * list.
     */
    @Test
    public void testConstructor_CommandletArgs_DeepCopy()
    {
        final List<String> args = new ArrayList<String>();
        final CommandletExecutor executor = new CommandletExecutor( createDummy( ICommandlet.class ), args );

        args.add( "arg" ); //$NON-NLS-1$

        assertEquals( 0, executor.getCommandletArguments().size() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * commandlet argument list.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_CommandletArgs_Null()
    {
        new CommandletExecutor( createDummy( ICommandlet.class ), null );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * {@code null} console.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecute_Console_Null()
        throws Exception
    {
        executor_.execute( null, createDummy( IStatelet.class ), createDummy( IGameClient.class ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * {@code null} game client.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecute_GameClient_Null()
        throws Exception
    {
        executor_.execute( createDummy( IConsole.class ), createDummy( IStatelet.class ), null );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * {@code null} statelet.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecute_Statelet_Null()
        throws Exception
    {
        executor_.execute( createDummy( IConsole.class ), null, createDummy( IGameClient.class ) );
    }

    /**
     * Ensures the {@code getCommandlet} method does not return {@code null}.
     */
    @Test
    public void testGetCommandlet_ReturnValue_NonNull()
    {
        assertNotNull( executor_.getCommandlet() );
    }

    /**
     * Ensures the {@code getCommandletArgument} method returns an immutable
     * collection.
     */
    @Test
    public void testGetCommandletArguments_ReturnValue_Immutable()
    {
        assertImmutableCollection( executor_.getCommandletArguments() );
    }

    /**
     * Ensures the {@code getCommandletArguments} method does not return {@code
     * null}.
     */
    @Test
    public void testGetCommandletArguments_ReturnValue_NonNull()
    {
        assertNotNull( executor_.getCommandletArguments() );
    }
}
