/*
 * ExtensionRegistryExtensionTest.java
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
 * Created on Apr 19, 2008 at 11:18:24 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertSame;
import java.util.Collections;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.FakeState;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.ExtensionRegistryExtension}
 * class.
 */
public final class ExtensionRegistryExtensionTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension registry extension under test in the fixture. */
    private ExtensionRegistryExtension extension_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryExtensionTest}
     * class.
     */
    public ExtensionRegistryExtensionTest()
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
        extension_ = new ExtensionRegistryExtension();
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
        extension_ = null;
    }

    /**
     * Ensures the {@code getExtension} method throws an exception when called
     * before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetExtension_ExtensionNotStarted()
    {
        extension_.getExtension( createDummy( IEngineContext.class ), Object.class );
    }

    /**
     * Ensures the {@code getExtensionRegistry} method throws an exception when
     * passed a state that does not contain an extension map.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetExtensionRegistry_State_Illegal_NoExtensionMap()
    {
        ExtensionRegistryExtension.getExtensionRegistry( new FakeState() );
    }

    /**
     * Ensures the {@code getExtensionRegistry} method throws an exception when
     * passed a state that does not contain an extension registry in the
     * extension map.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetExtensionRegistry_State_Illegal_NoExtensionRegistry()
    {
        final IState state = new FakeState();
        ExtensionRegistryExtensionFacade.EXTENSIONS_ATTRIBUTE().add( state, Collections.<Class<?>, IExtension>emptyMap() );

        ExtensionRegistryExtension.getExtensionRegistry( state );
    }

    /**
     * Ensures the {@code getExtensionRegistry} method throws an exception when
     * passed a {@code null} state.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtensionRegistry_State_Null()
    {
        ExtensionRegistryExtension.getExtensionRegistry( null );
    }

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * called before the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testRegisterExtension_ExtensionNotStarted()
        throws Exception
    {
        extension_.registerExtension( createDummy( IEngineContext.class ), createDummy( IExtension.class ) );
    }

    /**
     * Ensures the {@code start} method registers the extension itself.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testStart_RegisterSelf()
        throws Exception
    {
        final IEngineContext context = new FakeEngineContext();

        extension_.start( context );

        assertSame( extension_, extension_.getExtension( context, IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * called before the extension has been started.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnregisterExtension_ExtensionNotStarted()
    {
        extension_.unregisterExtension( createDummy( IEngineContext.class ), createDummy( IExtension.class ) );
    }
}
