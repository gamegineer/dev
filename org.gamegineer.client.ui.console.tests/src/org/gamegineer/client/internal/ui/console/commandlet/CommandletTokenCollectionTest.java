/*
 * CommandletTokenCollectionTest.java
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
 * Created on Oct 23, 2008 at 10:52:53 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.commandlet.CommandletTokenCollection}
 * class.
 */
public final class CommandletTokenCollectionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet token collection under test in the fixture. */
    private CommandletTokenCollection collection_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletTokenCollectionTest}
     * class.
     */
    public CommandletTokenCollectionTest()
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
        final List<String> args = new ArrayList<String>();
        args.add( "arg1" ); //$NON-NLS-1$
        args.add( "arg2" ); //$NON-NLS-1$
        collection_ = new CommandletTokenCollection( "name", args ); //$NON-NLS-1$
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
        collection_ = null;
    }

    /**
     * Ensures the constructor makes a deep copy of the argument list.
     */
    @Test
    public void testConstructor_Args_DeepCopy()
    {
        final List<String> args = new ArrayList<String>();
        final CommandletTokenCollection tokens = new CommandletTokenCollection( "name", args ); //$NON-NLS-1$

        args.add( "arg" ); //$NON-NLS-1$

        assertEquals( 0, tokens.getArguments().size() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * argument list.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Args_Null()
    {
        new CommandletTokenCollection( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed an empty name.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Name_Empty()
    {
        new CommandletTokenCollection( "", Collections.<String>emptyList() ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * name.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Name_Null()
    {
        new CommandletTokenCollection( null, Collections.<String>emptyList() );
    }

    /**
     * Ensures the {@code getArguments} method returns an immutable collection.
     */
    @Test
    public void testGetArguments_ReturnValue_Immutable()
    {
        assertImmutableCollection( collection_.getArguments() );
    }

    /**
     * Ensures the {@code getArguments} method does not return {@code null}.
     */
    @Test
    public void testGetArguments_ReturnValue_NonNull()
    {
        assertNotNull( collection_.getArguments() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( collection_.getName() );
    }
}
