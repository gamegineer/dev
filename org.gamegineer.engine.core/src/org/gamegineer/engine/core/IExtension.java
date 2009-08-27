/*
 * IExtension.java
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
 * Created on Apr 15, 2008 at 9:40:03 PM.
 */

package org.gamegineer.engine.core;

/**
 * An extension to an engine.
 * 
 * <p>
 * An engine extension enhances the behavior of the engine beyond the basic
 * capabilities the engine itself provides. Command implementations may query
 * and use any registered engine extension via their execution context as
 * necessary.
 * </p>
 * 
 * <p>
 * Extensions are queried by a specific type object that defines the behavior
 * they contribute to the engine. Extensions are expected to implement this type
 * directly so they can be cast to this type without exception. The type object
 * may be a class but is more commonly an interface.
 * </p>
 * 
 * <p>
 * An engine only supports one extension instance per extension type. An attempt
 * to register multiple extensions for a particular extension type will result
 * in an exception.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IExtension
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the type that defines the behavior of this extension.
     * 
     * <p>
     * This extension can be cast without exception to the returned type.
     * </p>
     * 
     * @return The type that defines the behavior of this extension; never
     *         {@code null}.
     */
    /* @NonNull */
    public Class<?> getExtensionType();

    /**
     * Invoked when this extension is started by the engine after it has been
     * registered.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the extension has already been started.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while starting the extension. In this case,
     *         the engine will automatically unregister the extension.
     */
    public void start(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;

    /**
     * Invoked when this extension is stopped by the engine before it has been
     * unregistered.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the extension has not been started.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     * @throws org.gamegineer.engine.core.EngineException
     *         If an error occurs while stopping the extension.
     */
    public void stop(
        /* @NonNull */
        IEngineContext context )
        throws EngineException;
}
