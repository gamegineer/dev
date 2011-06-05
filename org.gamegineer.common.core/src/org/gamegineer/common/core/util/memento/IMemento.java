/*
 * IMemento.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 29, 2008 at 10:23:43 PM.
 */

package org.gamegineer.common.core.util.memento;

import java.util.Set;

/**
 * A memento for saving and restoring the state of an object.
 * 
 * <p>
 * An implementation of this interface should be immutable to ensure it is
 * thread-safe.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMemento
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this memento contains the specified attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this memento contains the specified attribute;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        String name );

    /**
     * Gets an attribute from this memento.
     * 
     * @param <T>
     *        The type of the attribute value.
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the specified name does not exist in this
     *         memento.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public <T> T getAttribute(
        /* @NonNull */
        String name );

    /**
     * Gets the collection of attribute names contained in this memento.
     * 
     * @return The collection of attribute names contained in this memento;
     *         never {@code null}.
     */
    /* @NonNull */
    public Set<String> getAttributeNames();
}
