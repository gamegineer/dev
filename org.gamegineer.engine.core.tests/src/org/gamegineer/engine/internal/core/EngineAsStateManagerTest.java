/*
 * EngineAsStateManagerTest.java
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
 * Created on Jul 2, 2008 at 11:43:17 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;
import org.gamegineer.engine.core.extensions.statemanager.AbstractStateManagerTestCase;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.Engine}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.statemanager.IStateManager}
 * interface.
 */
public final class EngineAsStateManagerTest
    extends AbstractStateManagerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineAsStateManagerTest} class.
     */
    public EngineAsStateManagerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.statemanager.AbstractStateManagerTestCase#createEngineContext()
     */
    @Override
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new EngineContext( Engine.createEngine(), new FakeCommandContext() );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.statemanager.AbstractStateManagerTestCase#createStateManager(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected IStateManager createStateManager(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return ((EngineContext)context).getEngine();
    }
}
