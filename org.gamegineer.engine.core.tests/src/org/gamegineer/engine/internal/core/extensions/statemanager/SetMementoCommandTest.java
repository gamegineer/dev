/*
 * SetMementoCommandTest.java
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
 * Created on Jul 3, 2008 at 9:52:51 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.junit.Assert.assertEquals;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MementoBuilder;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.MockCommands;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.statemanager.SetMementoCommand}
 * class.
 */
public final class SetMementoCommandTest
    extends AbstractStateManagerCommandTestCase<SetMementoCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SetMementoCommandTest} class.
     */
    public SetMementoCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.statemanager.AbstractStateManagerCommandTestCase#createCommand()
     */
    @Override
    protected SetMementoCommand createCommand()
    {
        final MementoBuilder builder = new MementoBuilder();
        return new SetMementoCommand( builder.toMemento() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * memento.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Memento_Null()
    {
        new SetMementoCommand( null );
    }

    /**
     * Ensures the {@code execute} method throws an exception when the memento
     * contains a transient attribute.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_Memento_Illegal_Attribute_Transient()
        throws Exception
    {
        final IMemento baseMemento = getEngine().executeCommand( new GetMementoCommand() );

        final MementoBuilder builder = new MementoBuilder();
        for( final String name : baseMemento.getAttributeNames() )
        {
            builder.addAttribute( name, baseMemento.getAttribute( name ) );
        }
        builder.addAttribute( new AttributeName( Scope.ENGINE_CONTROL, "name" ).toString(), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
        final IMemento memento = builder.toMemento();

        final IEngine newEngine = EngineFactory.createEngine();
        newEngine.executeCommand( new SetMementoCommand( memento ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when the memento
     * contains a malformed attribute name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_Memento_Illegal_AttributeName_Malformed()
        throws Exception
    {
        final IMemento baseMemento = getEngine().executeCommand( new GetMementoCommand() );

        final MementoBuilder builder = new MementoBuilder();
        for( final String name : baseMemento.getAttributeNames() )
        {
            builder.addAttribute( name, baseMemento.getAttribute( name ) );
        }
        builder.addAttribute( "APPLICATION.name", "value" ); //$NON-NLS-1$ //$NON-NLS-2$
        final IMemento memento = builder.toMemento();

        final IEngine newEngine = EngineFactory.createEngine();
        newEngine.executeCommand( new SetMementoCommand( memento ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception when the memento
     * is missing the command history.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_Memento_Illegal_CommandHistory_Absent()
        throws Exception
    {
        final MementoBuilder builder = new MementoBuilder();
        builder.addAttribute( new AttributeName( Scope.APPLICATION, "name1" ).toString(), "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        builder.addAttribute( new AttributeName( Scope.APPLICATION, "name2" ).toString(), "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        final IMemento memento = builder.toMemento();

        final IEngine newEngine = EngineFactory.createEngine();
        newEngine.executeCommand( new SetMementoCommand( memento ) );
    }

    /**
     * Ensures the {@code execute} method sets the engine state using a legal
     * memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_Memento_Legal()
        throws Exception
    {
        getEngine().executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "intValue" ), new Integer( 42 ) ) ); //$NON-NLS-1$
        getEngine().executeCommand( MockCommands.createAddAttributeCommand( new AttributeName( Scope.APPLICATION, "stringValue" ), "a string" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        final IMemento memento = getEngine().executeCommand( new GetMementoCommand() );

        final IEngine newEngine = EngineFactory.createEngine();
        newEngine.executeCommand( new SetMementoCommand( memento ) );
        final IMemento newMemento = newEngine.executeCommand( new GetMementoCommand() );

        assertEquals( memento, newMemento );
    }
}
