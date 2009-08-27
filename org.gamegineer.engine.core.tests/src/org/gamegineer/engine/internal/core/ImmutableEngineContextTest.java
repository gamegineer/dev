/*
 * ImmutableEngineContextTest.java
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
 * Created on Jun 11, 2008 at 11:10:42 PM.
 */

package org.gamegineer.engine.internal.core;

import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.ImmutableEngineContext} class.
 */
public final class ImmutableEngineContextTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The immutable engine context under test in the fixture. */
    private ImmutableEngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ImmutableEngineContextTest}
     * class.
     */
    public ImmutableEngineContextTest()
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
        m_context = new ImmutableEngineContext( new FakeEngineContext() );
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
        m_context = null;
    }

    /**
     * Ensures the constructor throws an exception if passed a {@code null}
     * context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Context_Null()
    {
        new ImmutableEngineContext( null );
    }

    /**
     * Ensures the {@code getState} method returns an immutable view of the
     * engine state.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testGetState_ReturnValue_Immutable()
    {
        m_context.getState().addAttribute( new AttributeName( Scope.APPLICATION, "name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
