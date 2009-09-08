/*
 * AbstractStateManagerCommandTestCase.java
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
 * Created on Jul 3, 2008 at 9:57:38 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Superclass for fixtures that test commands which interact with the state
 * manager.
 * 
 * @param <C>
 *        The concrete type of the command implementation under test.
 * @param <T>
 *        The result type of the command.
 */
public abstract class AbstractStateManagerCommandTestCase<C extends ICommand<T>, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command under test in the fixture. */
    private C command_;

    /** The engine for the fixture. */
    private IEngine engine_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractStateManagerCommandTestCase} class.
     */
    protected AbstractStateManagerCommandTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command to be tested.
     * 
     * @return The command to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract C createCommand();

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
        assertNotNull( command_ );
        return command_;
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
        assertNotNull( engine_ );
        return engine_;
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
        engine_ = EngineFactory.createEngine();
        command_ = createCommand();
        assertNotNull( command_ );
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
        command_ = null;
        engine_ = null;
    }

    /**
     * Ensures the {@code execute} method throws an exception if the state
     * manager extension is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_StateManager_Unavailable()
        throws Exception
    {
        command_.execute( new FakeEngineContext() );
    }
}
