/*
 * ExtensionRegistryCommandsTest.java
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
 * Created on Jul 20, 2008 at 11:08:24 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.ExtensionRegistryCommands}
 * class.
 */
public final class ExtensionRegistryCommandsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryCommandsTest}
     * class.
     */
    public ExtensionRegistryCommandsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createRegisterExtensionCommand} method throws an
     * exception when passed a {@code null} extension.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateRegisterExtensionCommand_Extension_Null()
    {
        ExtensionRegistryCommands.createRegisterExtensionCommand( null );
    }

    /**
     * Ensures the {@code createUnregisterExtensionCommand} method throws an
     * exception when passed a {@code null} extension.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateUnregisterExtensionCommand_Extension_Null()
    {
        ExtensionRegistryCommands.createUnregisterExtensionCommand( null );
    }
}
