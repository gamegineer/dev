/*
 * ICommandContext.java
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
 * Created on Apr 11, 2009 at 9:42:16 PM.
 */

package org.gamegineer.engine.core.contexts.command;

/**
 * A context that encapsulates the call state at the time a command is submitted
 * to an engine for execution.
 * 
 * <p>
 * A command context enables arbitrary state to be transmitted between the
 * command submitter's thread and the engine thread on which the command is
 * executed.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ICommandContext
{
    // ======================================================================
    // Methods
    // ======================================================================

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
