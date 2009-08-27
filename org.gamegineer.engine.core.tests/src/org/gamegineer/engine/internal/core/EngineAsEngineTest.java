/*
 * EngineAsEngineTest.java
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
 * Created on Apr 6, 2008 at 10:01:49 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import org.gamegineer.engine.core.AbstractEngineTestCase;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.extensions.commandhistory.CommandHistoryFacade;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.Engine}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.IEngine} interface.
 */
public final class EngineAsEngineTest
    extends AbstractEngineTestCase<Engine>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineAsEngineTest} class.
     */
    public EngineAsEngineTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#createEngine()
     */
    @Override
    protected Engine createEngine()
        throws Exception
    {
        return Engine.createEngine();
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#createEngine(org.gamegineer.engine.core.ICommand)
     */
    @Override
    protected Engine createEngine(
        final ICommand<?> command )
        throws Exception
    {
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        return Engine.createEngine( command );
    }

    /*
     * @see org.gamegineer.engine.core.AbstractEngineTestCase#getCommandHistory(org.gamegineer.engine.core.IEngine)
     */
    @Override
    protected List<IInvertibleCommand<?>> getCommandHistory(
        final Engine engine )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        return CommandHistoryFacade.getCommandHistory( engine );
    }
}
