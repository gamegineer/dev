/*
 * SecurityManagerExtensionTest.java
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
 * Created on Apr 1, 2009 at 10:04:18 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.command.CommandContextBuilder;
import org.gamegineer.engine.core.contexts.command.FakeCommandContext;
import org.gamegineer.engine.core.contexts.command.ICommandContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.securitymanager.SecurityManagerExtension}
 * class.
 */
public final class SecurityManagerExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The security manager extension under test in the fixture. */
    private SecurityManagerExtension m_extension;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecurityManagerExtensionTest}
     * class.
     */
    public SecurityManagerExtensionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for testing the methods of
     * {@code SecurityManagerExtension}.
     * 
     * @return An engine context; never {@code null}.
     */
    /* @NonNull */
    static IEngineContext createEngineContext()
    {
        final CommandContextBuilder builder = new CommandContextBuilder();
        SecurityManagerExtension.preSubmitCommand( builder );
        return createEngineContext( builder.toCommandContext() );
    }

    /**
     * Creates an engine context that encapsulates the specified command
     * context.
     * 
     * @param commandContext
     *        The command context; must not be {@code null}.
     * 
     * @return An engine context; never {@code null}.
     */
    /* @NonNull */
    private static IEngineContext createEngineContext(
        /* @NonNull */
        final ICommandContext commandContext )
    {
        assert commandContext != null;

        return new FakeEngineContext()
        {
            @Override
            public <T> T getContext(
                final Class<T> type )
            {
                if( type == ICommandContext.class )
                {
                    return type.cast( commandContext );
                }

                return super.getContext( type );
            }
        };
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
        m_extension = new SecurityManagerExtension();
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
     * Ensures the {@code getUserPrincipal} method throws an exception when the
     * engine context does not contain a command context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetUserPrincipal_Context_NoCommandContext()
        throws Exception
    {
        m_extension.start( new FakeEngineContext() );

        m_extension.getUserPrincipal( createEngineContext( new FakeCommandContext() ) );
    }

    /**
     * Ensures the {@code getUserPrincipal} method throws an exception when the
     * engine context does not contain a command context that specifies a user
     * principal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetUserPrincipal_Context_NoUserPrincipal()
        throws Exception
    {
        m_extension.start( new FakeEngineContext() );

        m_extension.getUserPrincipal( new FakeEngineContext() );
    }

    /**
     * Ensures the {@code getUserPrincipal} method throws an exception when
     * called before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetUserPrincipal_ExtensionNotStarted()
    {
        m_extension.getUserPrincipal( createEngineContext() );
    }

    /**
     * Ensures the {@code preSubmitCommand} method adds the user principal to
     * the command context.
     */
    @Test
    public void testPreSubmitCommand()
    {
        final CommandContextBuilder builder = new CommandContextBuilder();

        SecurityManagerExtension.preSubmitCommand( builder );

        assertNotNull( builder.toCommandContext().getAttribute( SecurityManagerExtensionFacade.ATTR_USER_PRINCIPAL() ) );
    }

    /**
     * Ensures the {@code preSubmitCommand} method throws an exception when
     * passed a {@code null} builder.
     */
    @Test( expected = NullPointerException.class )
    public void testPreSubmitCommand_Builder_Null()
    {
        SecurityManagerExtension.preSubmitCommand( null );
    }
}
