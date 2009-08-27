/*
 * StateManagerFacadeTest.java
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
 * Created on Jul 20, 2008 at 11:59:59 PM.
 */

package org.gamegineer.engine.core.extensions.statemanager;

import org.gamegineer.common.persistence.memento.MementoBuilder;
import org.gamegineer.engine.core.EngineFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.statemanager.StateManagerFacade}
 * class.
 */
public final class StateManagerFacadeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerFacadeTest} class.
     */
    public StateManagerFacadeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getMemento} method throws an exception when passed a
     * {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testGetMemento_Engine_Null()
    {
        StateManagerFacade.getMemento( null );
    }

    /**
     * Ensures the {@code setMemento} method throws an exception when passed a
     * {@code null} engine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetMemento_Engine_Null()
        throws Exception
    {
        final MementoBuilder builder = new MementoBuilder();
        StateManagerFacade.setMemento( null, builder.toMemento() );
    }

    /**
     * Ensures the {@code setMemento} method throws an exception when passed a
     * {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetMemento_Memento_Null()
        throws Exception
    {
        StateManagerFacade.setMemento( EngineFactory.createEngine(), null );
    }
}
