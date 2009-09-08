/*
 * AbstractStateEventMediatorTestCase.java
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
 * Created on May 31, 2008 at 9:19:45 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateEventMediator}
 * interface.
 */
public abstract class AbstractStateEventMediatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for use in the fixture. */
    private IEngineContext context_;

    /** The state event mediator under test in the fixture. */
    private IStateEventMediator mediator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractStateEventMediatorTestCase} class.
     */
    protected AbstractStateEventMediatorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for testing the state event mediator.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the state event mediator;
     *         never {@code null}.
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
     * Creates the state event mediator to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The state event mediator to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract IStateEventMediator createStateEventMediator(
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
        mediator_ = createStateEventMediator( context_ );
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
     * Ensures the {@code addStateListener} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStateListener_Context_Null()
    {
        mediator_.addStateListener( null, createDummy( IStateListener.class ) );
    }

    /**
     * Ensures the {@code addStateListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStateListener_Listener_Null()
    {
        mediator_.addStateListener( context_, null );
    }

    /**
     * Ensures the {@code addStateListener} method throws an exception when
     * passed a listener that has already been registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddStateListener_Listener_Registered()
    {
        final IStateListener listener = new MockStateListener();
        mediator_.addStateListener( context_, listener );

        mediator_.addStateListener( context_, listener );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveStateListener_Context_Null()
    {
        mediator_.removeStateListener( null, createDummy( IStateListener.class ) );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveStateListener_Listener_Null()
    {
        mediator_.removeStateListener( context_, null );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * passed a listener that has not been registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveStateListener_Listener_Unregistered()
    {
        mediator_.removeStateListener( context_, new MockStateListener() );
    }
}
