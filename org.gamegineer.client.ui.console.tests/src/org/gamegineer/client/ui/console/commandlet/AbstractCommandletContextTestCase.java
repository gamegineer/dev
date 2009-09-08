/*
 * AbstractCommandletContextTestCase.java
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
 * Created on Oct 3, 2008 at 9:37:57 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandletContext}
 * interface.
 */
public abstract class AbstractCommandletContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet context under test in the fixture. */
    private ICommandletContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCommandletContextTestCase} class.
     */
    protected AbstractCommandletContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the commandlet context to be tested.
     * 
     * @return The commandlet context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICommandletContext createCommandletContext()
        throws Exception;

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
        context_ = createCommandletContext();
        assertNotNull( context_ );
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
        context_ = null;
    }

    /**
     * Ensures the {@code getArguments} method returns an immutable list.
     */
    @Test
    public void testGetArguments_ReturnValue_Immutable()
    {
        assertImmutableCollection( context_.getArguments() );
    }

    /**
     * Ensures the {@code getArguments} method does not return {@code null}.
     */
    @Test
    public void testGetArguments_ReturnValue_NonNull()
    {
        assertNotNull( context_.getArguments() );
    }

    /**
     * Ensures the {@code getConsole} method does not return {@code null}.
     */
    @Test
    public void testGetConsole_ReturnValue_NonNull()
    {
        assertNotNull( context_.getConsole() );
    }

    /**
     * Ensures the {@code getGameClient} method does not return {@code null}.
     */
    @Test
    public void testGetGameClient_ReturnValue_NonNull()
    {
        assertNotNull( context_.getGameClient() );
    }

    /**
     * Ensures the {@code getStatelet} method does not return {@code null}.
     */
    @Test
    public void testGetStatelet_ReturnValue_NonNull()
    {
        assertNotNull( context_.getStatelet() );
    }
}
