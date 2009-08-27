/*
 * AddCommandListenerCommandTest.java
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
 * Created on Jun 9, 2008 at 10:46:19 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandeventmediator.MockCommandListener;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.AddCommandListenerCommand}
 * class.
 */
public final class AddCommandListenerCommandTest
    extends AbstractCommandEventMediatorCommandTestCase<AddCommandListenerCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AddCommandListenerCommandTest}
     * class.
     */
    public AddCommandListenerCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.commandeventmediator.AbstractCommandEventMediatorCommandTestCase#createCommand(org.gamegineer.engine.core.extensions.commandeventmediator.MockCommandListener)
     */
    @Override
    protected AddCommandListenerCommand createCommand(
        final MockCommandListener listener )
    {
        return new AddCommandListenerCommand( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * listener.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Listener_Null()
    {
        new AddCommandListenerCommand( null );
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * listener that has already been registered.
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
     * Ensures the {@code execute} method registers a command listener that has
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
        getCommandListener().clearEvents();
        getEngine().executeCommand( new MockCommand<Void>() );

        assertEquals( 1, getCommandListener().getCommandExecutingEventCount() );
        assertEquals( 1, getCommandListener().getCommandExecutedEventCount() );
    }

    /**
     * Ensures the {@code execute} method does not invoke any event handlers on
     * a newly-registered command listener.
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

        assertEquals( 0, getCommandListener().getCommandExecutingEventCount() );
        assertEquals( 0, getCommandListener().getCommandExecutedEventCount() );
    }
}
