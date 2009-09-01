/*
 * RemoveCommandListenerCommandTest.java
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
 * Created on Jun 9, 2008 at 10:53:05 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandeventmediator.MockCommandListener;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.RemoveCommandListenerCommand}
 * class.
 */
public final class RemoveCommandListenerCommandTest
    extends AbstractCommandEventMediatorCommandTestCase<RemoveCommandListenerCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * RemoveCommandListenerCommandTest} class.
     */
    public RemoveCommandListenerCommandTest()
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
    protected RemoveCommandListenerCommand createCommand(
        final MockCommandListener listener )
    {
        return new RemoveCommandListenerCommand( listener );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.commandeventmediator.AbstractCommandEventMediatorCommandTestCase#setUp()
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
        new RemoveCommandListenerCommand( null );
    }

    /**
     * Ensures the {@code execute} method unregisters a command listener that
     * has been previously registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_Listener_Registered()
        throws Exception
    {
        getEngine().executeCommand( getCommand() );
        getCommandListener().clearEvents();
        getEngine().executeCommand( new MockCommand<Void>() );

        assertEquals( 0, getCommandListener().getCommandExecutingEventCount() );
        assertEquals( 0, getCommandListener().getCommandExecutedEventCount() );
    }

    /**
     * Ensures the {@code execute} method invokes all event handlers on a
     * newly-unregistered command listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_Listener_Registered_AllHandlersInvoked()
        throws Exception
    {
        getCommandListener().clearEvents();
        getEngine().executeCommand( getCommand() );

        assertEquals( 1, getCommandListener().getCommandExecutingEventCount() );
        assertEquals( 1, getCommandListener().getCommandExecutedEventCount() );
    }

    /**
     * Ensures the {@code execute} method throws an exception if a command
     * listener has not been previously registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testExecute_Listener_Unregistered()
        throws Exception
    {
        getEngine().executeCommand( new RemoveCommandListenerCommand( new MockCommandListener() ) );
    }
}
