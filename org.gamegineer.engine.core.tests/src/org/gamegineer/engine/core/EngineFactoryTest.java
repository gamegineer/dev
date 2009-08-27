/*
 * EngineFactoryTest.java
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
 * Created on Jun 28, 2008 at 10:31:20 PM.
 */

package org.gamegineer.engine.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.internal.core.commands.GetStateCommand;
import org.gamegineer.engine.internal.core.extensions.statemanager.GetMementoCommand;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.engine.core.EngineFactory}
 * class.
 */
public final class EngineFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineFactoryTest} class.
     */
    public EngineFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createEngine()} method does not return {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateEngine_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( EngineFactory.createEngine() );
    }

    /**
     * Ensures the {@code createEngine(ICommand)} method returns an engine whose
     * state reflects the actions of the specified command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateEngineFromCommand()
        throws Exception
    {
        final Map<AttributeName, Object> expectedState = new HashMap<AttributeName, Object>();
        expectedState.put( new AttributeName( Scope.APPLICATION, "name1" ), new Object() ); //$NON-NLS-1$
        expectedState.put( new AttributeName( Scope.APPLICATION, "name2" ), new Object() ); //$NON-NLS-1$
        expectedState.put( new AttributeName( Scope.APPLICATION, "name3" ), new Object() ); //$NON-NLS-1$
        final ICommand<Void> command = new AbstractCommand<Void>()
        {
            public Void execute(
                final IEngineContext context )
            {
                for( final Map.Entry<AttributeName, Object> entry : expectedState.entrySet() )
                {
                    context.getState().addAttribute( entry.getKey(), entry.getValue() );
                }

                return null;
            }
        };
        final IEngine engine = EngineFactory.createEngine( command );

        final Map<AttributeName, Object> actualState = engine.executeCommand( new GetStateCommand() );
        for( final Map.Entry<AttributeName, Object> entry : expectedState.entrySet() )
        {
            assertTrue( actualState.containsKey( entry.getKey() ) );
            assertEquals( entry.getValue(), actualState.get( entry.getKey() ) );
        }
    }

    /**
     * Ensures the {@code createEngine(ICommand)} method throws an exception
     * when passed a {@code null} command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateEngineFromCommand_Command_Null()
        throws Exception
    {
        final ICommand<?> command = null;
        EngineFactory.createEngine( command );
    }

    /**
     * Ensures the {@code createEngine(IMemento)} method returns an engine whose
     * state reflects the specified memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateEngineFromMemento()
        throws Exception
    {
        final IEngine engine = EngineFactory.createEngine();
        engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "intValue" ), new Integer( 42 ) ) ); //$NON-NLS-1$
        engine.executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "stringValue" ), "a string" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        final IMemento memento = engine.executeCommand( new GetMementoCommand() );

        final IEngine newEngine = EngineFactory.createEngine( memento );
        final IMemento newMemento = newEngine.executeCommand( new GetMementoCommand() );

        assertEquals( memento, newMemento );
    }

    /**
     * Ensures the {@code createEngine(IMemento)} method throws an exception
     * when passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateEngineFromMemento_Memento_Null()
        throws Exception
    {
        final IMemento memento = null;
        EngineFactory.createEngine( memento );
    }
}
