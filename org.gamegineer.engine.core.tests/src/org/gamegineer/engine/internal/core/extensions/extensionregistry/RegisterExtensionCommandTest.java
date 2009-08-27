/*
 * RegisterExtensionCommandTest.java
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
 * Created on Apr 17, 2008 at 10:03:31 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.EngineException;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.RegisterExtensionCommand}
 * class.
 */
public final class RegisterExtensionCommandTest
    extends AbstractExtensionRegistryCommandTestCase<RegisterExtensionCommand, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RegisterExtensionCommandTest}
     * test.
     */
    public RegisterExtensionCommandTest()
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
    protected RegisterExtensionCommand createCommand()
    {
        return new RegisterExtensionCommand( MOCK_EXTENSION );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * extension.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Extension_Null()
    {
        new RegisterExtensionCommand( null );
    }

    /**
     * Ensures the {@code execute} method throws an exception if an extension
     * with the same extension type has already been registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testExecute_Extension_Registered()
        throws Exception
    {
        getCommand().execute( getEngineContext() );
        getCommand().execute( getEngineContext() );
    }

    /**
     * Ensures the {@code execute} method registers an extension whose extension
     * type does not yet have an extension associated with it.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_Extension_Unregistered()
        throws Exception
    {
        getCommand().execute( getEngineContext() );
        assertNotNull( getEngineContext().getExtension( Object.class ) );
    }
}
