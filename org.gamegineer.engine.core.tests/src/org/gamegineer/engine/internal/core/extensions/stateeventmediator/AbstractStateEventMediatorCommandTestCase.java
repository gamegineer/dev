/*
 * AbstractStateEventMediatorCommandTestCase.java
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
 * Created on Jun 10, 2008 at 11:48:24 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.extensions.stateeventmediator.MockStateListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Superclass for fixtures that test commands which interact with the state
 * event mediator.
 * 
 * @param <C>
 *        The concrete type of the command implementation under test.
 * @param <T>
 *        The result type of the command.
 */
public abstract class AbstractStateEventMediatorCommandTestCase<C extends ICommand<T>, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command under test in the fixture. */
    private C m_command;

    /** The engine for the fixture. */
    private IEngine m_engine;

    /** The state listener for the fixture. */
    private MockStateListener m_listener;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractStateEventMediatorCommandTestCase} class.
     */
    protected AbstractStateEventMediatorCommandTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command to be tested.
     * 
     * @param listener
     *        The state listener to associate with the command; must not be
     *        {@code null}.
     * 
     * @return The command to be tested; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    /* @NonNull */
    protected abstract C createCommand(
        /* @NonNull */
        MockStateListener listener );

    /**
     * Gets the command under test in the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The command under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final C getCommand()
    {
        assertNotNull( m_command );
        return m_command;
    }

    /**
     * Gets the engine for the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The engine for the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final IEngine getEngine()
    {
        assertNotNull( m_engine );
        return m_engine;
    }

    /**
     * Gets the state listener for the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The state listener for the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final MockStateListener getStateListener()
    {
        assertNotNull( m_listener );
        return m_listener;
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
        m_engine = EngineFactory.createEngine();
        m_listener = new MockStateListener();
        m_command = createCommand( m_listener );
        assertNotNull( m_command );
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
        m_command = null;
        m_listener = null;
        m_engine = null;
    }

    /**
     * Ensures the {@code execute} method throws an exception if the state event
     * mediator extension is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_StateEventMediator_Unavailable()
        throws Exception
    {
        m_command.execute( new FakeEngineContext() );
    }

    /**
     * Ensures the {@code execute} method always returns {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue_Null()
        throws Exception
    {
        assertNull( m_engine.executeCommand( m_command ) );
    }
}
