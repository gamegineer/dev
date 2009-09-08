/*
 * AbstractCommandEventMediatorCommandTestCase.java
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
 * Created on Jun 9, 2008 at 11:22:30 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.extensions.commandeventmediator.MockCommandListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Superclass for fixtures that test commands which interact with the command
 * event mediator.
 * 
 * @param <C>
 *        The concrete type of the command implementation under test.
 * @param <T>
 *        The result type of the command.
 */
public abstract class AbstractCommandEventMediatorCommandTestCase<C extends ICommand<T>, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command under test in the fixture. */
    private C command_;

    /** The engine for the fixture. */
    private IEngine engine_;

    /** The command listener for the fixture. */
    private MockCommandListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCommandEventMediatorCommandTestCase} class.
     */
    protected AbstractCommandEventMediatorCommandTestCase()
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
     *        The command listener to associate with the command; must not be
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
        MockCommandListener listener );

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
     * Gets the command listener for the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The command listener for the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final MockCommandListener getCommandListener()
    {
        assertNotNull( listener_ );
        return listener_;
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
        listener_ = new MockCommandListener();
        command_ = createCommand( listener_ );
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
        listener_ = null;
        engine_ = null;
    }

    /**
     * Ensures the {@code execute} method throws an exception if the command
     * event mediator extension is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_CommandEventMediator_Unavailable()
        throws Exception
    {
        command_.execute( new FakeEngineContext() );
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
        assertNull( engine_.executeCommand( command_ ) );
    }
}
