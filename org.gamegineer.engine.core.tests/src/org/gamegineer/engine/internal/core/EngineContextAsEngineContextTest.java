/*
 * EngineContextAsEngineContextTest.java
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
 * Created on Jun 11, 2008 at 7:48:33 PM.
 */

package org.gamegineer.engine.internal.core;

import org.gamegineer.engine.core.AbstractEngineContextTestCase;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.EngineContext} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.engine.core.IEngineContext} interface.
 */
public final class EngineContextAsEngineContextTest
    extends AbstractEngineContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code EngineContextAsEngineContextTest} class.
     */
    public EngineContextAsEngineContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractEngineContextTestCase#createEngineContext()
     */
    @Override
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new EngineContext( Engine.createEngine(), new FakeCommandContext() );
    }
}
