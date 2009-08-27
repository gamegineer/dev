/*
 * CommandEventMediatorFacadeTest.java
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
 * Created on Jul 19, 2008 at 10:05:03 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.EngineFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandEventMediatorFacade}
 * class.
 */
public final class CommandEventMediatorFacadeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandEventMediatorFacadeTest}
     * class.
     */
    public CommandEventMediatorFacadeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCommandListener_Engine_Null()
    {
        CommandEventMediatorFacade.addCommandListener( null, new MockCommandListener() );
    }

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * passed a {@code null} listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAddCommandListener_Listener_Null()
        throws Exception
    {
        CommandEventMediatorFacade.addCommandListener( EngineFactory.createEngine(), null );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCommandListener_Engine_Null()
    {
        CommandEventMediatorFacade.removeCommandListener( null, new MockCommandListener() );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * passed a {@code null} listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveCommandListener_Listener_Null()
        throws Exception
    {
        CommandEventMediatorFacade.removeCommandListener( EngineFactory.createEngine(), null );
    }
}
