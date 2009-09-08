/*
 * AbstractCommandEventMediatorTestCase.java
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
 * Created on May 30, 2008 at 8:33:36 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator}
 * interface.
 */
public abstract class AbstractCommandEventMediatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for use in the fixture. */
    private IEngineContext context_;

    /** The command event mediator under test in the fixture. */
    private ICommandEventMediator mediator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCommandEventMediatorTestCase} class.
     */
    protected AbstractCommandEventMediatorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command event mediator to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The command event mediator to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICommandEventMediator createCommandEventMediator(
        /* @NonNull */
        IEngineContext context )
        throws Exception;

    /**
     * Creates an engine context suitable for testing the command event
     * mediator.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the command event
     *         mediator; never {@code null}.
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
        mediator_ = createCommandEventMediator( context_ );
        assertNotNull( mediator_ );
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
        mediator_ = null;
        context_ = null;
    }

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCommandListener_Context_Null()
    {
        mediator_.addCommandListener( null, createDummy( ICommandListener.class ) );
    }

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCommandListener_Listener_Null()
    {
        mediator_.addCommandListener( context_, null );
    }

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * passed a listener that has already been registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddCommandListener_Listener_Registered()
    {
        final ICommandListener listener = new MockCommandListener();
        mediator_.addCommandListener( context_, listener );

        mediator_.addCommandListener( context_, listener );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCommandListener_Context_Null()
    {
        mediator_.removeCommandListener( null, createDummy( ICommandListener.class ) );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCommandListener_Listener_Null()
    {
        mediator_.removeCommandListener( context_, null );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * passed a listener that has not been registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveCommandListener_Listener_Unregistered()
    {
        mediator_.removeCommandListener( context_, new MockCommandListener() );
    }
}
