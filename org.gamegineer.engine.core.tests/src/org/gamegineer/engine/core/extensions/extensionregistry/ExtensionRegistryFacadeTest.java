/*
 * ExtensionRegistryFacadeTest.java
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
 * Created on Jul 20, 2008 at 11:21:20 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.gamegineer.engine.core.EngineFactory;
import org.gamegineer.engine.core.MockExtension;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.ExtensionRegistryFacade}
 * class.
 */
public final class ExtensionRegistryFacadeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryFacadeTest}
     * class.
     */
    public ExtensionRegistryFacadeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code registerExtension} method throws an exception when
     * passed a {@code null} engine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterExtension_Engine_Null()
        throws Exception
    {
        ExtensionRegistryFacade.registerExtension( null, new MockExtension( Object.class ) );
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
        ExtensionRegistryFacade.registerExtension( EngineFactory.createEngine(), null );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed a {@code null} engine.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterExtension_Engine_Null()
    {
        ExtensionRegistryFacade.unregisterExtension( null, new MockExtension( Object.class ) );
    }

    /**
     * Ensures the {@code unregisterExtension} method throws an exception when
     * passed a {@code null} extension.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterExtension_Extension_Null()
        throws Exception
    {
        ExtensionRegistryFacade.unregisterExtension( EngineFactory.createEngine(), null );
    }
}
