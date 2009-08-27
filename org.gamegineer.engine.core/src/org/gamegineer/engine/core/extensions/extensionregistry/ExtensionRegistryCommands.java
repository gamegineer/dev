/*
 * ExtensionRegistryCommands.java
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
 * Created on Jul 20, 2008 at 11:08:03 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.internal.core.extensions.extensionregistry.RegisterExtensionCommand;
import org.gamegineer.engine.internal.core.extensions.extensionregistry.UnregisterExtensionCommand;

/**
 * A collection of commands for exercising the functionality of the extension
 * registry extension.
 */
public final class ExtensionRegistryCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryCommands}
     * class.
     */
    private ExtensionRegistryCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that registers the specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return A command that registers the specified extension; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code extension} is {@code null}.
     * 
     * @see IExtensionRegistry#registerExtension(org.gamegineer.engine.core.IEngineContext,
     *      IExtension)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createRegisterExtensionCommand(
        /* @NonNull */
        final IExtension extension )
    {
        return new RegisterExtensionCommand( extension );
    }

    /**
     * Creates a command that unregisters the specified extension.
     * 
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @return A command that unregisters the specified extension; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code extension} is {@code null}.
     * 
     * @see IExtensionRegistry#unregisterExtension(org.gamegineer.engine.core.IEngineContext,
     *      IExtension)
     */
    /* @NonNull */
    public static IInvertibleCommand<Void> createUnregisterExtensionCommand(
        /* @NonNull */
        final IExtension extension )
    {
        return new UnregisterExtensionCommand( extension );
    }
}
