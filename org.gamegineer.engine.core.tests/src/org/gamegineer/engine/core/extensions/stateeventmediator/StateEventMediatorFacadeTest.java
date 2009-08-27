/*
 * StateEventMediatorFacadeTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 20, 2008 at 11:44:37 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import org.gamegineer.engine.core.EngineFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateEventMediatorFacade}
 * class.
 */
public final class StateEventMediatorFacadeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateEventMediatorFacadeTest}
     * class.
     */
    public StateEventMediatorFacadeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code addStateListener} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStateListener_Engine_Null()
    {
        StateEventMediatorFacade.addStateListener( null, new MockStateListener() );
    }

    /**
     * Ensures the {@code addStateListener} method throws an exception when
     * passed a {@code null} listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStateListener_Listener_Null()
        throws Exception
    {
        StateEventMediatorFacade.addStateListener( EngineFactory.createEngine(), null );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveStateListener_Engine_Null()
    {
        StateEventMediatorFacade.removeStateListener( null, new MockStateListener() );
    }

    /**
     * Ensures the {@code removeStateListener} method throws an exception when
     * passed a {@code null} listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveStateListener_Listener_Null()
        throws Exception
    {
        StateEventMediatorFacade.removeStateListener( EngineFactory.createEngine(), null );
    }
}
