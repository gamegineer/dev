/*
 * CommandHistoryExtensionTest.java
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
 * Created on Apr 26, 2008 at 12:00:58 AM.
 */

package org.gamegineer.engine.internal.core.extensions.commandhistory;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandhistory.ICommandHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandhistory.CommandHistoryExtension}
 * class.
 */
public final class CommandHistoryExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command history extension under test in the fixture. */
    private CommandHistoryExtension m_extension;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistoryExtensionTest}
     * class.
     */
    public CommandHistoryExtensionTest()
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
        m_extension = new CommandHistoryExtension( createDummy( ICommandHistory.class ) );
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
     * Ensures the {@code canRedo} method throws an exception when called before
     * the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testCanRedo_ExtensionNotStarted()
    {
        m_extension.canRedo( createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code canUndo} method throws an exception when called before
     * the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testCanUndo_ExtensionNotStarted()
    {
        m_extension.canUndo( createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Delegate_Null()
    {
        new CommandHistoryExtension( null );
    }

    /**
     * Ensures the {@code getCommands} method throws an exception when called
     * before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetCommands_ExtensionNotStarted()
    {
        m_extension.getCommands( createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code redo} method throws an exception when called before
     * the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testRedo_ExtensionNotStarted()
        throws Exception
    {
        m_extension.redo( createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code undo} method throws an exception when called before
     * the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testUndo_ExtensionNotStarted()
        throws Exception
    {
        m_extension.undo( createDummy( IEngineContext.class ) );
    }
}
