/*
 * ExtensionRegistryFacade.java
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
 * Created on Jul 20, 2008 at 11:20:16 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngine;
import org.gamegineer.engine.core.IExtension;

/**
 * A facade for exercising the functionality of the extension registry
 * extension.
 */
public final class ExtensionRegistryFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionRegistryFacade} class.
     */
    private ExtensionRegistryFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Registers the specified extension.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code extension} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while registering the extension.
     * @throws
     *         org.gamegineer.engine.core.extensions.extensionregistry.TooManyExtensionsException
     *         If an extension supporting the same extension type has already
     *         been registered.
     * 
     * @see IExtensionRegistry#registerExtension(org.gamegineer.engine.core.IEngineContext,
     *      IExtension)
     */
    public static void registerExtension(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IExtension extension )
        throws EngineException, TooManyExtensionsException
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        engine.executeCommand( ExtensionRegistryCommands.createRegisterExtensionCommand( extension ) );
    }

    /**
     * Unregisters the specified extension.
     * 
     * @param engine
     *        The engine; must not be {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code extension} is not currently registered.
     * @throws java.lang.NullPointerException
     *         If {@code engine} or {@code extension} is {@code null}.
     * 
     * @see IExtensionRegistry#unregisterExtension(org.gamegineer.engine.core.IEngineContext,
     *      IExtension)
     */
    public static void unregisterExtension(
        /* @NonNull */
        final IEngine engine,
        /* @NonNull */
        final IExtension extension )
    {
        assertArgumentNotNull( engine, "engine" ); //$NON-NLS-1$

        try
        {
            engine.executeCommand( ExtensionRegistryCommands.createUnregisterExtensionCommand( extension ) );
        }
        catch( final EngineException e )
        {
            throw TaskUtils.launderThrowable( e );
        }
    }
}
