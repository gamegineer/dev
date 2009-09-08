/*
 * AbstractEngineContextTestCase.java
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
 * Created on Jun 11, 2008 at 7:42:14 PM.
 */

package org.gamegineer.engine.core;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.IEngineContext} interface.
 */
public abstract class AbstractEngineContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context under test in the fixture. */
    private IEngineContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEngineContextTestCase}
     * class.
     */
    protected AbstractEngineContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the engine context to be tested.
     * 
     * @return The engine context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IEngineContext createEngineContext()
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
        context_ = createEngineContext();
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
     * Ensures the {@code getContext} method throws an exception when passed a
     * {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetContext_Type_Null()
    {
        context_.getContext( null );
    }

    /**
     * Ensures the {@code getExtension} method throws an exception when passed a
     * {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtension_Type_Null()
    {
        context_.getExtension( null );
    }

    /**
     * Ensures the {@code getState} method does not return {@code null}.
     */
    @Test
    public void testGetState_ReturnValue_NonNull()
    {
        assertNotNull( context_.getState() );
    }
}
