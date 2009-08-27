/*
 * IStatelet.java
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
 * Created on May 15, 2009 at 8:54:02 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import java.util.Set;

/**
 * The state of a console that is accessible by commandlets.
 * 
 * <p>
 * The statelet is simply a collection of named attributes. Commandlets may
 * access and modify the statelet they receive in their commandlet context as
 * necessary.
 * </p>
 * 
 * <p>
 * Statelet attributes are not persistent outside the console lifetime and are
 * discarded when the console is closed.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStatelet
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new attribute to this statelet.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the same name already exists in this
     *         statelet.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void addAttribute(
        /* @NonNull */
        String name,
        /* @Nullable */
        Object value );

    /**
     * Indicates this statelet contains the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this statelet contains the specified attribute;
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
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this statelet.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        String name );

    /**
     * Gets an immutable set view of the attribute names contained in this
     * statelet.
     * 
     * <p>
     * The set is backed by the statelet, so changes to the statelet are
     * reflected in the set. If the statelet is modified while an iteration over
     * the set is in progress, the results of the iteration are undefined.
     * </p>
     * 
     * @return An immutable set view of the attribute names contained in this
     *         statelet; never {@code null}.
     */
    /* @NonNull */
    public Set<String> getAttributeNames();

    /**
     * Removes the attribute with the specified name from this statelet.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this statelet.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void removeAttribute(
        /* @NonNull */
        String name );

    /**
     * Sets the value of the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this statelet.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void setAttribute(
        /* @NonNull */
        String name,
        /* @Nullable */
        Object value );
}
