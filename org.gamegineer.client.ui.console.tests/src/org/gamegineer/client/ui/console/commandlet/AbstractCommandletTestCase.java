/*
 * AbstractCommandletTestCase.java
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
 * Created on Oct 3, 2008 at 9:19:03 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.gamegineer.client.core.GameClientFactory;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.core.config.Configurations;
import org.gamegineer.client.ui.console.FakeConsole;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.NullDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.commandlet.ICommandlet} interface.
 */
public abstract class AbstractCommandletTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The commandlet under test in the fixture. */
    private ICommandlet commandlet_;

    /** A default game client for use in the fixture. */
    private IGameClient defaultGameClient_;

    /** A default statelet for use in the fixture. */
    private IStatelet defaultStatelet_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandletTestCase}
     * class.
     */
    protected AbstractCommandletTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the commandlet to be tested.
     * 
     * @return The commandlet to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICommandlet createCommandlet()
        throws Exception;

    /**
     * Creates a new commandlet context for the specified argument list and
     * associates it with a default console.
     * 
     * @param args
     *        The commandlet argument list; must not be {@code null}.
     * 
     * @return A new commandlet context; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code args} is {@code null}.
     */
    /* @NonNull */
    protected final ICommandletContext createCommandletContext(
        /* @NonNull */
        final List<String> args )
    {
        assertArgumentNotNull( args, "args" ); //$NON-NLS-1$

        return createCommandletContext( new FakeConsole( new NullDisplay() ), args );
    }

    /**
     * Creates a new commandlet context for the specified argument list and
     * associates it with the specified console.
     * 
     * @param console
     *        The console associated with the commandlet context; must not be
     *        {@code null}.
     * @param args
     *        The commandlet argument list; must not be {@code null}.
     * 
     * @return A new commandlet context; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code console} or {@code args} is {@code null}.
     */
    /* @NonNull */
    protected final ICommandletContext createCommandletContext(
        /* @NonNull */
        final IConsole console,
        /* @NonNull */
        final List<String> args )
    {
        assertArgumentNotNull( console, "console" ); //$NON-NLS-1$
        assertArgumentNotNull( args, "args" ); //$NON-NLS-1$

        return new FakeCommandletContext( console, defaultStatelet_, defaultGameClient_, args );
    }

    /**
     * Gets the commandlet under test in the fixture.
     * 
     * @return The commandlet under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final ICommandlet getCommandlet()
    {
        assertNotNull( commandlet_ );
        return commandlet_;
    }

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
        defaultStatelet_ = new FakeStatelet();
        defaultGameClient_ = GameClientFactory.createGameClient( Configurations.createGameClientConfiguration() );
        commandlet_ = createCommandlet();
        assertNotNull( commandlet_ );
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
        commandlet_ = null;
        defaultGameClient_ = null;
        defaultStatelet_ = null;
    }

    /**
     * Ensures the {@code execute} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecute_Context_Null()
        throws Exception
    {
        commandlet_.execute( null );
    }
}
