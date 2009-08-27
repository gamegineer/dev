/*
 * IEngineContext.java
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
 * Created on Jun 11, 2008 at 7:38:58 PM.
 */

package org.gamegineer.engine.core;

/**
 * The context within which an engine entity, such as a command or extension, is
 * executed.
 * 
 * <p>
 * The context provides information about the current state of the engine as
 * well as access to services provided by the engine in the form of extensions.
 * </p>
 * 
 * <p>
 * The context also provides access to secondary contexts that capture transient
 * information about the current execution. Such transient information is
 * provided either by the engine itself or any of its extensions for the benefit
 * of the entity being executed.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IEngineContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the secondary context that implements the specified type of
     * behavior.
     * 
     * @param <T>
     *        The type of the context.
     * @param type
     *        The type that defines the behavior of the context; must not be
     *        {@code null}.
     * 
     * @return The secondary context that implements the specified type of
     *         behavior or {@code null} if no such context was registered. If a
     *         non-{@code null} object is returned, the object is guaranteed to
     *         be castable to the requested type.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public <T> T getContext(
        /* @NonNull */
        Class<T> type );

    /**
     * Gets the engine extension that implements the specified type of behavior.
     * 
     * @param <T>
     *        The type of the extension.
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
     *         If {@code type} is {@code null}.
     */
    /* @Nullable */
    public <T> T getExtension(
        /* @NonNull */
        Class<T> type );

    /**
     * Gets the engine state.
     * 
     * @return The engine state; never {@code null}.
     */
    /* @NonNull */
    public IState getState();
}
