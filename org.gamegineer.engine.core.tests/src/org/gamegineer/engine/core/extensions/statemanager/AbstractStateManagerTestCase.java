/*
 * AbstractStateManagerTestCase.java
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
 * Created on Jul 2, 2008 at 11:13:49 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.statemanager.IStateManager}
 * interface.
 */
public abstract class AbstractStateManagerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for use in the fixture. */
    private IEngineContext context_;

    /** The state manager under test in the fixture. */
    private IStateManager stateManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStateManagerTestCase}
     * class.
     */
    protected AbstractStateManagerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for testing the state manager.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the state manager; never
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
     * Creates the state manager to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The state manager to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract IStateManager createStateManager(
        /* @NonNull */
        IEngineContext context )
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
        stateManager_ = createStateManager( context_ );
        assertNotNull( stateManager_ );
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
        stateManager_ = null;
        context_ = null;
    }

    /**
     * Ensures the {@code getMemento} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testGetMemento_Context_Null()
    {
        stateManager_.getMemento( null );
    }

    /**
     * Ensures the {@code getMemento} method does not return {@code null}.
     */
    @Test
    public void testGetMemento_ReturnValue_NonNull()
    {
        assertNotNull( stateManager_.getMemento( context_ ) );
    }

    /**
     * Ensures the {@code setMemento} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetMemento_Context_Null()
        throws Exception
    {
        stateManager_.setMemento( null, createDummy( IMemento.class ) );
    }

    /**
     * Ensures the {@code setMemento} method throws an exception when passed a
     * {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetMemento_Memento_Null()
        throws Exception
    {
        stateManager_.setMemento( context_, null );
    }
}
