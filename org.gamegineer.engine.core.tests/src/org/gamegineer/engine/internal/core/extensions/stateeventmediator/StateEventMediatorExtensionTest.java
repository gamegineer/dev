/*
 * StateEventMediatorExtensionTest.java
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
 * Created on May 31, 2008 at 9:42:52 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.extension.FakeExtensionContext;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.StateEventMediatorExtension}
 * class.
 */
public final class StateEventMediatorExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state event mediator extension under test in the fixture. */
    private StateEventMediatorExtension extension_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorExtensionTest}
     * class.
     */
    public StateEventMediatorExtensionTest()
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
        extension_ = new StateEventMediatorExtension();
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
        extension_ = null;
    }

    /**
     * Ensures the {@code addStateListener} method throws an exception when
     * called before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddStateListener_ExtensionNotStarted()
    {
        extension_.addStateListener( createDummy( IEngineContext.class ), createDummy( IStateListener.class ) );
    }

    /**
     * Ensures the {@code fireStateChanged} method throws an exception when
     * passed a {@code null} attribute change collection.
     */
    @Test( expected = NullPointerException.class )
    public void testFireStateChanged_AttributeChanges_Null()
    {
        StateEventMediatorExtension.fireStateChanged( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the {@code fireStateChanged} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testFireStateChanged_Context_Null()
    {
        StateEventMediatorExtension.fireStateChanged( null, Collections.<AttributeName, IAttributeChange>emptyMap() );
    }

    /**
     * Ensures the {@code fireStateChanging} method throws an exception when
     * passed a {@code null} attribute change collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFireStateChanging_AttributeChanges_Null()
        throws Exception
    {
        StateEventMediatorExtension.fireStateChanging( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the {@code fireStateChanging} method throws an exception when
     * passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFireStateChanging_Context_Null()
        throws Exception
    {
        StateEventMediatorExtension.fireStateChanging( null, Collections.<AttributeName, IAttributeChange>emptyMap() );
    }

    /**
     * Ensures the {@code preExecuteCommand} method adds the active state
     * listener collection attribute to the extension context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testPreExecuteCommand_AddsActiveStateListeners()
        throws Exception
    {
        final IExtensionContext extensionContext = new FakeExtensionContext();
        final IEngineContext engineContext = new FakeEngineContext()
        {
            @Override
            public <T> T getContext(
                final Class<T> type )
            {
                if( type == IExtensionContext.class )
                {
                    return type.cast( extensionContext );
                }

                return super.getContext( type );
            }
        };
        final StateEventMediatorExtension extension = new StateEventMediatorExtension();
        extension.start( engineContext );

        StateEventMediatorExtension.preExecuteCommand( engineContext );

        assertTrue( extensionContext.containsAttribute( StateEventMediatorExtensionFacade.ATTR_ACTIVE_STATE_LISTENERS() ) );
    }

    /**
     * Ensures the {@code preExecuteCommand} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testPreExecuteCommand_Context_Null()
    {
        StateEventMediatorExtension.preExecuteCommand( null );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * called before the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveStateListener_ExtensionNotStarted()
        throws Exception
    {
        extension_.removeStateListener( createDummy( IEngineContext.class ), createDummy( IStateListener.class ) );
    }
}
