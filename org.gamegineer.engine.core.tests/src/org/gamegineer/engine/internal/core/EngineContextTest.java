/*
 * EngineContextTest.java
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
 * Created on Jun 11, 2008 at 7:50:49 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.EngineContext} class.
 */
public final class EngineContextTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context under test in the fixture. */
    private EngineContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineContextTest} class.
     */
    public EngineContextTest()
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
        context_ = new EngineContext( Engine.createEngine(), createDummy( ICommandContext.class ) );
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
     * Ensures the constructor throws an exception when passed a {@code null}
     * command context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_CommandContext_Null()
        throws Exception
    {
        new EngineContext( Engine.createEngine(), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * engine.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Engine_Null()
    {
        new EngineContext( null, createDummy( ICommandContext.class ) );
    }

    /**
     * Ensures the {@code getContext} method does not return {@code null} when
     * queried for the command context.
     */
    @Test
    public void testGetContext_CommandContext_ReturnValue_NonNull()
    {
        assertNotNull( context_.getContext( ICommandContext.class ) );
    }

    /**
     * Ensures the {@code getContext} method does not return {@code null} when
     * queried for the extension context.
     */
    @Test
    public void testGetContext_ExtensionContext_ReturnValue_NonNull()
    {
        assertNotNull( context_.getContext( IExtensionContext.class ) );
    }
}
