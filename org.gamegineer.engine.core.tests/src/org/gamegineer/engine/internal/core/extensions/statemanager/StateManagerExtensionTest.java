/*
 * StateManagerExtensionTest.java
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
 * Created on Jul 2, 2008 at 11:32:22 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.statemanager.StateManagerExtension}
 * class.
 */
public final class StateManagerExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state manager extension under test in the fixture. */
    private StateManagerExtension m_extension;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateManagerExtensionTest}
     * class.
     */
    public StateManagerExtensionTest()
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
        m_extension = new StateManagerExtension( createDummy( IStateManager.class ) );
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
     * Ensures the constructor throws an exception when passed a {@code null}
     * delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Delegate_Null()
    {
        new StateManagerExtension( null );
    }

    /**
     * Ensures the {@code getMemento} method throws an exception when called
     * before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetMemento_ExtensionNotStarted()
    {
        m_extension.getMemento( createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code setMemento} method throws an exception when called
     * before the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testSetMemento_ExtensionNotStarted()
        throws Exception
    {
        m_extension.setMemento( createDummy( IEngineContext.class ), createDummy( IMemento.class ) );
    }
}
