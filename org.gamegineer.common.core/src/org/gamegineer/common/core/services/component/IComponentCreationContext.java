/*
 * IComponentCreationContext.java
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
 * Created on Apr 10, 2008 at 9:25:18 PM.
 */

package org.gamegineer.common.core.services.component;

/**
 * A context that defines how a component is to be initialized when created.
 * 
 * <p>
 * A component creation context defines a collection of attributes which are
 * used by component factories to determine how a particular component is to be
 * created. Contexts must <i>not</i> allow {@code null} values to be stored in
 * the attribute collection as this value is used to indicate an undefined
 * attribute.
 * </p>
 * 
 * <p>
 * The only thread-safety guarantee a component creation context implementation
 * must make is that each method will execute atomically.
 * </p>
 */
public interface IComponentCreationContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this context contains the specified attribute.
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
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
     *        The name of the attribute; must not be {@code null}.
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
