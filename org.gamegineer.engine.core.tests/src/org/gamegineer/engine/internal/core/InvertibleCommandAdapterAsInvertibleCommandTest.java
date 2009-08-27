/*
 * InvertibleCommandAdapterAsInvertibleCommandTest.java
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
 * Created on Aug 13, 2008 at 10:49:21 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.AbstractInvertibleCommandTestCase;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.MockWriteCommand;
import org.gamegineer.engine.core.IState.Scope;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.InvertibleCommandAdapter} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IInvertibleCommand} interface.
 */
public final class InvertibleCommandAdapterAsInvertibleCommandTest
    extends AbstractInvertibleCommandTestCase<InvertibleCommandAdapter<Void>, Void>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the attribute to be changed. */
    private static final AttributeName ATTR_NAME_1 = new AttributeName( Scope.APPLICATION, "name1" ); //$NON-NLS-1$

    /** The name of the attribute to be removed. */
    private static final AttributeName ATTR_NAME_2 = new AttributeName( Scope.APPLICATION, "name2" ); //$NON-NLS-1$

    /** The name of the attribute to be added. */
    private static final AttributeName ATTR_NAME_3 = new AttributeName( Scope.APPLICATION, "name3" ); //$NON-NLS-1$

    /** The value of the attribute to be changed. */
    private static final Object ATTR_VALUE_1 = new Object();

    /** The value of the attribute to be removed. */
    private static final Object ATTR_VALUE_2 = new Object();

    /** The value of the attribute to be added. */
    private static final Object ATTR_VALUE_3 = new Object();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InvertibleCommandAdapterAsInvertibleCommandTest} class.
     */
    public InvertibleCommandAdapterAsInvertibleCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractCommandTestCase#createCommand()
     */
    @Override
    protected InvertibleCommandAdapter<Void> createCommand()
    {
        final ICommand<Void> command = new AbstractCommand<Void>()
        {
            @SuppressWarnings( "synthetic-access" )
            public Void execute(
                final IEngineContext context )
            {
                context.getState().setAttribute( ATTR_NAME_1, new Object() );
                context.getState().removeAttribute( ATTR_NAME_2 );
                context.getState().addAttribute( ATTR_NAME_3, ATTR_VALUE_3 );
                return null;
            }
        };
        return new InvertibleCommandAdapter<Void>( command );
    }

    /*
     * @see org.gamegineer.engine.core.AbstractInvertibleCommandTestCase#prepareEngineForInverseCommandTest(org.gamegineer.engine.core.IEngine, org.gamegineer.engine.core.IInvertibleCommand)
     */
    @Override
    protected void prepareEngineForInverseCommandTest(
        final IEngine engine,
        final IInvertibleCommand<Void> command )
        throws Exception
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        final ICommand<Void> initializationCommand = new MockWriteCommand<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void execute(
                final IEngineContext context )
            {
                context.getState().addAttribute( ATTR_NAME_1, ATTR_VALUE_1 );
                context.getState().addAttribute( ATTR_NAME_2, ATTR_VALUE_2 );
                return null;
            }
        };
        engine.executeCommand( initializationCommand );
    }
}
