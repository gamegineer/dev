/*
 * AddStateListenerCommandTest.java
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
 * Created on Jun 10, 2008 at 11:53:45 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.stateeventmediator.MockStateListener;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.AddStateListenerCommand}
 * class.
 */
public final class AddStateListenerCommandTest
    extends AbstractStateEventMediatorCommandTestCase<AddStateListenerCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AddStateListenerCommandTest}
     * class.
     */
    public AddStateListenerCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.stateeventmediator.AbstractStateEventMediatorCommandTestCase#createCommand(org.gamegineer.engine.core.extensions.stateeventmediator.MockStateListener)
     */
    @Override
    protected AddStateListenerCommand createCommand(
        final MockStateListener listener )
    {
        return new AddStateListenerCommand( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * listener.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Listener_Null()
    {
        new AddStateListenerCommand( null );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * state listener that has already been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testExecute_Listener_Registered()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
        getEngine().executeCommand( getCommand() );
    }

    /**
     * Ensures the {@code execute} method registers a state listener that has
     * not been previously registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Listener_Unregistered()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
        getStateListener().clearEvents();
        getEngine().executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertEquals( 1, getStateListener().getStateChangingEventCount() );
        assertEquals( 1, getStateListener().getStateChangedEventCount() );
    }

    /**
     * Ensures the {@code execute} method does not invoke any event handlers on
     * a newly-registered state listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Listener_Unregistered_NoHandlersInvoked()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );

        assertEquals( 0, getStateListener().getStateChangingEventCount() );
        assertEquals( 0, getStateListener().getStateChangedEventCount() );
    }
}
