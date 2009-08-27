/*
 * CommandEventMediatorExtensionTest.java
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
 * Created on May 30, 2008 at 9:16:35 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.extension.FakeExtensionContext;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandEventMediatorExtension}
 * class.
 */
public final class CommandEventMediatorExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command event mediator extension under test in the fixture. */
    private CommandEventMediatorExtension m_extension;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandEventMediatorExtensionTest} class.
     */
    public CommandEventMediatorExtensionTest()
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
        m_extension = new CommandEventMediatorExtension();
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
        m_extension = null;
    }

    /**
     * Ensures the {@code addCommandListener} method throws an exception when
     * called before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddCommandListener_ExtensionNotStarted()
    {
        m_extension.addCommandListener( createDummy( IEngineContext.class ), createDummy( ICommandListener.class ) );
    }

    /**
     * Ensures the {@code fireCommandExecuting} method throws an exception when
     * passed a {@code null} command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFireCommandExecuting_Command_Null()
        throws Exception
    {
        CommandEventMediatorExtension.fireCommandExecuting( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the {@code fireCommandExecuting} method throws an exception when
     * passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFireCommandExecuting_Context_Null()
        throws Exception
    {
        CommandEventMediatorExtension.fireCommandExecuting( null, createDummy( ICommand.class ) );
    }

    /**
     * Ensures the {@code fireFailedCommandExecuted} method throws an exception
     * when passed a {@code null} command.
     */
    @Test( expected = NullPointerException.class )
    public void testFireFailedCommandExecuted_Command_Null()
    {
        CommandEventMediatorExtension.fireFailedCommandExecuted( createDummy( IEngineContext.class ), null, new Exception() );
    }

    /**
     * Ensures the {@code fireFailedCommandExecuted} method throws an exception
     * when passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testFireFailedCommandExecuted_Context_Null()
    {
        CommandEventMediatorExtension.fireFailedCommandExecuted( null, createDummy( ICommand.class ), new Exception() );
    }

    /**
     * Ensures the {@code fireFailedCommandExecuted} method throws an exception
     * when passed a {@code null} exception.
     */
    @Test( expected = NullPointerException.class )
    public void testFireFailedCommandExecuted_Exception_Null()
    {
        CommandEventMediatorExtension.fireFailedCommandExecuted( createDummy( IEngineContext.class ), createDummy( ICommand.class ), null );
    }

    /**
     * Ensures the {@code fireSuccessfulCommandExecuted} method throws an
     * exception when passed a {@code null} command.
     */
    @Test( expected = NullPointerException.class )
    public void testFireSuccessfulCommandExecuted_Command_Null()
    {
        CommandEventMediatorExtension.fireSuccessfulCommandExecuted( createDummy( IEngineContext.class ), null, new Object() );
    }

    /**
     * Ensures the {@code fireSuccessfulCommandExecuted} method throws an
     * exception when passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testFireSuccessfulCommandExecuted_Context_Null()
    {
        CommandEventMediatorExtension.fireSuccessfulCommandExecuted( null, createDummy( ICommand.class ), new Object() );
    }

    /**
     * Ensures the {@code preExecuteCommand} method adds the active command
     * listener collection attribute to the extension context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testPreExecuteCommand_AddsActiveCommandListeners()
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
        final CommandEventMediatorExtension extension = new CommandEventMediatorExtension();
        extension.start( engineContext );

        CommandEventMediatorExtension.preExecuteCommand( engineContext );

        assertTrue( extensionContext.containsAttribute( CommandEventMediatorExtensionFacade.ATTR_ACTIVE_COMMAND_LISTENERS() ) );
    }

    /**
     * Ensures the {@code preExecuteCommand} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testPreExecuteCommand_Context_Null()
    {
        CommandEventMediatorExtension.preExecuteCommand( null );
    }

    /**
     * Ensures the {@code removeCommandListener} method throws an exception when
     * called before the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveCommandListener_ExtensionNotStarted()
        throws Exception
    {
        m_extension.removeCommandListener( createDummy( IEngineContext.class ), createDummy( ICommandListener.class ) );
    }
}
