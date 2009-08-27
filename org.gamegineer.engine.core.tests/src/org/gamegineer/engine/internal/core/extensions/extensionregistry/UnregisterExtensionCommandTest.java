/*
 * UnregisterExtensionCommandTest.java
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
 * Created on Apr 17, 2008 at 10:22:31 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.junit.Assert.assertNull;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.UnregisterExtensionCommand}
 * class.
 */
public final class UnregisterExtensionCommandTest
    extends AbstractExtensionRegistryCommandTestCase<UnregisterExtensionCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code UnregisterExtensionCommandTest}
     * test.
     */
    public UnregisterExtensionCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.extensionregistry.AbstractExtensionRegistryCommandTestCase#createCommand()
     */
    @Override
    protected UnregisterExtensionCommand createCommand()
    {
        return new UnregisterExtensionCommand( MOCK_EXTENSION );
    }

    /*
     * @see org.gamegineer.engine.internal.core.extensions.extensionregistry.AbstractExtensionRegistryCommandTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        super.setUp();

        final EngineContext context = getEngineContext();
        context.getExtension( IExtensionRegistry.class ).registerExtension( context, MOCK_EXTENSION );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * extension.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Extension_Null()
    {
        new UnregisterExtensionCommand( null );
    }

    /**
     * Ensures the {@code execute} method unregisters an extension whose
     * extension type has an extension associated with it.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_Extension_Registered()
        throws Exception
    {
        getCommand().execute( getEngineContext() );

        assertNull( getEngineContext().getExtension( Object.class ) );
    }

    /**
     * Ensures the {@code execute} method throws an exception if an extension
     * with the same extension type does not yet have an extension associated
     * with it.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testExecute_Extension_Unregistered()
        throws Exception
    {
        final EngineContext context = getEngineContext();
        context.getExtension( IExtensionRegistry.class ).unregisterExtension( context, MOCK_EXTENSION );

        getCommand().execute( context );
    }
}
