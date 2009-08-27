/*
 * IExtensionContext.java
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
 * Created on May 1, 2009 at 11:08:15 PM.
 */

package org.gamegineer.engine.core.contexts.extension;

// TODO: Bad smell... the name of this type is too generic.  In fact, while
// extensions may be the primary users of this context, it may be used by any
// engine entity that has access to the engine context.  Try to think of a
// better name.

/**
 * A context that provides a repository for the transient state of extensions
 * during the execution of a command.
 * 
 * <p>
 * A new extension context is provided by the engine during the execution of
 * every command. The context is discarded at the conclusion of command
 * execution, thus extensions must not store any persistent state within it.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IExtensionContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new attribute to this context.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the same name already exists in this
     *         context.
     * @throws java.lang.NullPointerException
     *         If {@code name} or {@code value} is {@code null}.
     */
    public void addAttribute(
        /* @NonNull */
        String name,
        /* @NonNull */
        Object value );

    /**
     * Indicates this context contains the specified attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this context contains the specified attribute;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        String name );

    /**
     * Gets the value of the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return The value of the specified attribute or {@code null} if the
     *         attribute does not exist in this context.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        String name );
}
