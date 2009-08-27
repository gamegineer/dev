/*
 * IExtensionRegistry.java
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
 * Created on Apr 17, 2008 at 9:33:11 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;

/**
 * An engine extension that manages a registry of all other engine extensions.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IExtensionRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the engine extension that implements the specified type of behavior.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param type
     *        The type that defines the behavior of the extension; must not be
     *        {@code null}.
     * 
     * @return The engine extension that implements the specified type of
     *         behavior or {@code null} if no such extension was registered. If
     *         a non-{@code null} object is returned, the object is guaranteed
     *         to be castable to the requested type.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code type} is {@code null}.
     */
    /* @Nullable */
    public IExtension getExtension(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        Class<?> type );

    /**
     * Registers the specified extension.
     * 
     * <p>
     * An engine only supports the registration of a single extension per
     * extension type.
     * </p>
     * 
     * <p>
     * If an error occurs while starting the extension, the extension will not
     * be registered and an appropriate exception will be thrown.
     * </p>
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code extension} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while registering the extension.
     * @throws
     *         org.gamegineer.engine.core.extensions.extensionregistry.TooManyExtensionsException
     *         If an extension supporting the same extension type has already
     *         been registered.
     */
    public void registerExtension(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        IExtension extension )
        throws EngineException, TooManyExtensionsException;

    /**
     * Unregisters the specified extension.
     * 
     * <p>
     * If an error occurs while stopping the extension, the extension will still
     * be unregistered and no exception will be thrown.
     * </p>
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * @param extension
     *        The extension; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code extension} is not currently registered.
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code extension} is {@code null}.
     */
    public void unregisterExtension(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        IExtension extension );
}
