/*
 * AbstractExtensionRegistryTestCase.java
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
 * Created on Apr 19, 2008 at 10:21:55 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.MockExtension;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry}
 * interface.
 */
public abstract class AbstractExtensionRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for use in the fixture. */
    private IEngineContext m_context;

    /** The extension registry under test in the fixture. */
    private IExtensionRegistry m_extensionRegistry;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractExtensionRegistryTestCase} class.
     */
    protected AbstractExtensionRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for testing the extension registry.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the extension registry;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new FakeEngineContext();
    }

    /**
     * Creates the extension registry to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The extension registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract IExtensionRegistry createExtensionRegistry(
        /* @NonNull */
        IEngineContext context )
        throws Exception;

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
        m_context = createEngineContext();
        m_extensionRegistry = createExtensionRegistry( m_context );
        assertNotNull( m_extensionRegistry );
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
        m_extensionRegistry = null;
        m_context = null;
    }

    /**
     * Ensures the {@code getExtension} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtension_Context_Null()
    {
        m_extensionRegistry.getExtension( null, Object.class );
    }

    /**
     * Ensures the {@code getExtension} method throws an exception when passed a
     * {@code null} extension type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtension_Type_Null()
    {
        m_extensionRegistry.getExtension( m_context, null );
    }

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterExtension_Context_Null()
        throws Exception
    {
        m_extensionRegistry.registerExtension( null, createDummy( IExtension.class ) );
    }

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * passed a {@code null} extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterExtension_Extension_Null()
        throws Exception
    {
        m_extensionRegistry.registerExtension( m_context, null );
    }

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * passed an extension that has already been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TooManyExtensionsException.class )
    public void testRegisterExtension_Extension_Registered_Extension()
        throws Exception
    {
        final IExtension extension = new MockExtension( Object.class );
        m_extensionRegistry.registerExtension( m_context, extension );

        m_extensionRegistry.registerExtension( m_context, extension );
    }

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * passed an extension whose extension type has already been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TooManyExtensionsException.class )
    public void testRegisterExtension_Extension_Registered_ExtensionType()
        throws Exception
    {
        m_extensionRegistry.registerExtension( m_context, new MockExtension( Object.class ) );

        m_extensionRegistry.registerExtension( m_context, new MockExtension( Object.class ) );
    }

    /**
     * Ensures the {@code registerExtension} method invokes the {@code start}
     * method of the extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRegisterExtension_Extension_Started()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class );

        m_extensionRegistry.registerExtension( m_context, extension );

        assertTrue( extension.isStarted() );
    }

    /**
     * Ensures the {@code registerExtension} method propagates any checked
     * exception thrown by the {@code start} method of an extension as a checked
     * exception of an appropriate type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testRegisterExtension_Extension_StartFails_CheckedException()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class )
        {
            @Override
            public void start(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
                throws EngineException
            {
                throw new EngineException();
            }
        };

        m_extensionRegistry.registerExtension( m_context, extension );
    }

    /**
     * Ensures the {@code registerExtension} method propagates any unchecked
     * exception thrown by the {@code start} method of an extension as a checked
     * exception of an appropriate type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testRegisterExtension_Extension_StartFails_UncheckedException()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class )
        {
            @Override
            public void start(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                throw new RuntimeException();
            }
        };

        m_extensionRegistry.registerExtension( m_context, extension );
    }

    /**
     * Ensures the {@code registerExtension} method properly registers an
     * extension whose extension type has not yet been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRegisterExtension_Extension_Unregistered()
        throws Exception
    {
        final IExtension extension = new MockExtension( Object.class );

        m_extensionRegistry.registerExtension( m_context, extension );

        assertSame( extension, m_extensionRegistry.getExtension( m_context, Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterExtension_Context_Null()
    {
        m_extensionRegistry.unregisterExtension( null, createDummy( IExtension.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed a {@code null} extension.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterExtension_Extension_Null()
    {
        m_extensionRegistry.unregisterExtension( m_context, null );
    }

    /**
     * Ensures the {@code unregisterExtension} method properly unregisters a
     * registered extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnregisterExtension_Extension_Registered()
        throws Exception
    {
        final IExtension extension = new MockExtension( Object.class );
        m_extensionRegistry.registerExtension( m_context, extension );
        assertSame( extension, m_extensionRegistry.getExtension( m_context, Object.class ) );

        m_extensionRegistry.unregisterExtension( m_context, extension );

        assertNull( m_extensionRegistry.getExtension( m_context, Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method does not propagate any
     * checked exception thrown by the {@code stop} method of an extension and
     * unregisters the exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnregisterExtension_Extension_StopFails_CheckedException()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class )
        {
            @Override
            public void stop(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
                throws EngineException
            {
                throw new EngineException();
            }
        };
        try
        {
            m_extensionRegistry.registerExtension( m_context, extension );
        }
        catch( final Exception e )
        {
            fail( "Failed to register extension." ); //$NON-NLS-1$
            throw e;
        }

        m_extensionRegistry.unregisterExtension( m_context, extension );

        assertNull( m_extensionRegistry.getExtension( m_context, Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method does not propagate any
     * unchecked exception thrown by the {@code stop} method of an extension and
     * unregisters the exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnregisterExtension_Extension_StopFails_UncheckedException()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class )
        {
            @Override
            public void stop(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                throw new RuntimeException();
            }
        };
        try
        {
            m_extensionRegistry.registerExtension( m_context, extension );
        }
        catch( final Exception e )
        {
            fail( "Failed to register extension." ); //$NON-NLS-1$
            throw e;
        }

        m_extensionRegistry.unregisterExtension( m_context, extension );

        assertNull( m_extensionRegistry.getExtension( m_context, Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method invokes the {@code stop}
     * method of the extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnegisterExtension_Extension_Stopped()
        throws Exception
    {
        final MockExtension extension = new MockExtension( Object.class );
        m_extensionRegistry.registerExtension( m_context, extension );

        m_extensionRegistry.unregisterExtension( m_context, extension );

        assertFalse( extension.isStarted() );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed an extension whose extension type has been registered but the
     * registered extension is not the same as the argument.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterExtension_Extension_Unregistered_Extension()
        throws Exception
    {
        m_extensionRegistry.registerExtension( m_context, new MockExtension( Object.class ) );

        m_extensionRegistry.unregisterExtension( m_context, new MockExtension( Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed an extension whose extension type has not yet been registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterExtension_Extension_Unregistered_ExtensionType()
    {
        m_extensionRegistry.unregisterExtension( m_context, new MockExtension( Object.class ) );
    }
}
