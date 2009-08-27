/*
 * RemoveStateListenerCommandTest.java
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
 * Created on Jun 10, 2008 at 11:57:20 PM.
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
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.RemoveStateListenerCommand}
 * class.
 */
public final class RemoveStateListenerCommandTest
    extends AbstractStateEventMediatorCommandTestCase<RemoveStateListenerCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoveStateListenerCommandTest}
     * class.
     */
    public RemoveStateListenerCommandTest()
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
    protected RemoveStateListenerCommand createCommand(
        final MockStateListener listener )
    {
        return new RemoveStateListenerCommand( listener );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.stateeventmediator.AbstractStateEventMediatorCommandTestCase#setUp()
     */
    @Override
    public void setUp()
        throws Exception
    {
        super.setUp();

        getEngine().executeCommand( getCommand().getInverseCommand() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * listener.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Listener_Null()
    {
        new RemoveStateListenerCommand( null );
    }

    /**
     * Ensures the {@code execute} method unregisters a state listener that has
     * been previously registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Listener_Registered()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
        getStateListener().clearEvents();
        getEngine().executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "name" ), "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        assertEquals( 0, getStateListener().getStateChangingEventCount() );
        assertEquals( 0, getStateListener().getStateChangedEventCount() );
    }

    /**
     * Ensures the {@code execute} method invokes all event handlers on a
     * newly-unregistered state listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testExecute_Listener_Registered_AllHandlersInvoked()
        throws Exception
    {
        getStateListener().clearEvents();
        getEngine().executeCommand( getCommand() );

        assertEquals( 1, getStateListener().getStateChangingEventCount() );
        assertEquals( 1, getStateListener().getStateChangedEventCount() );
    }

    /**
     * Ensures the {@code execute} method throws an exception if a state
     * listener has not been previously registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testExecute_Listener_Unregistered()
        throws Exception
    {
        getEngine().executeCommand( new RemoveStateListenerCommand( new MockStateListener() ) );
    }
}
