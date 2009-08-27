/*
 * EngineAsCommandHistoryTest.java
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
 * Created on Apr 25, 2008 at 10:40:29 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;
import org.gamegineer.engine.core.extensions.commandhistory.AbstractCommandHistoryTestCase;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.Engine}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory}
 * interface.
 */
public final class EngineAsCommandHistoryTest
    extends AbstractCommandHistoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineAsCommandHistoryTest}
     * class.
     */
    public EngineAsCommandHistoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.AbstractCommandHistoryTestCase#createCommandHistory(org.gamegineer.engine.core.IEngineContext, java.util.List)
     */
    @Override
    protected ICommandHistory createCommandHistory(
        final IEngineContext context,
        final List<IInvertibleCommand<?>> commands )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final Engine engine = ((EngineContext)context).getEngine();
        for( final IInvertibleCommand<?> command : commands )
        {
            engine.executeCommand( command );
        }
        return engine;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandhistory.AbstractCommandHistoryTestCase#createEngineContext()
     */
    @Override
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new EngineContext( Engine.createEngine(), new FakeCommandContext() );
    }
}
