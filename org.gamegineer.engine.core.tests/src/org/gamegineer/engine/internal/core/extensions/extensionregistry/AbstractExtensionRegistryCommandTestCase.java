/*
 * AbstractExtensionRegistryCommandTestCase.java
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
 * Created on Apr 18, 2008 at 10:51:40 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.MockExtension;
import org.gamegineer.engine.core.extensions.extensionregistry.FakeExtensionRegistry;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Superclass for fixtures that test commands which interact with the extension
 * registry.
 * 
 * @param <C>
 *        The concrete type of the command implementation under test.
 * @param <T>
 *        The result type of the command.
 */
public abstract class AbstractExtensionRegistryCommandTestCase<C extends ICommand<T>, T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mock extension to be manipulated by the command. */
    protected final static IExtension MOCK_EXTENSION = new MockExtension( Object.class );

    /** The command under test in the fixture. */
    private C m_command;

    /** The engine context for the fixture. */
    private EngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractExtensionRegistryCommandTestCase} class.
     */
    protected AbstractExtensionRegistryCommandTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command to be tested.
     * 
     * @return The command to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract C createCommand();

    /**
     * Gets the command under test in the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The command under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final C getCommand()
    {
        assertNotNull( m_command );
        return m_command;
    }

    /**
     * Gets the engine context for the fixture.
     * 
     * <p>
     * This method must only be called during the execution of a test.
     * </p>
     * 
     * @return The engine context for the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final EngineContext getEngineContext()
    {
        assertNotNull( m_context );
        return m_context;
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
        m_command = createCommand();
        assertNotNull( m_command );
        m_context = new EngineContext();
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
        m_context = null;
        m_command = null;
    }

    /**
     * Ensures the {@code execute} method throws an exception if the extension
     * registry extension is not available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_ExtensionRegistry_Unavailable()
        throws Exception
    {
        m_command.execute( new FakeEngineContext() );
    }

    /**
     * Ensures the {@code execute} method always returns {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue_Null()
        throws Exception
    {
        assertNull( getCommand().execute( getEngineContext() ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link org.gamegineer.engine.core.IEngineContext}
     * suitable for use by commands that interact with the extension registry.
     */
    protected static final class EngineContext
        extends FakeEngineContext
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The extension registry. */
        private final FakeExtensionRegistry m_extensionRegistry;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code EngineContext} class.
         */
        EngineContext()
        {
            m_extensionRegistry = new FakeExtensionRegistry();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.FakeEngineContext#getExtension(java.lang.Class)
         */
        @Override
        public <T> T getExtension(
            final Class<T> type )
        {
            assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

            if( type == IExtensionRegistry.class )
            {
                return type.cast( m_extensionRegistry );
            }

            final IExtension extension = m_extensionRegistry.getExtension( this, type );
            if( extension != null )
            {
                return type.cast( extension );
            }

            return super.getExtension( type );
        }
    }
}
