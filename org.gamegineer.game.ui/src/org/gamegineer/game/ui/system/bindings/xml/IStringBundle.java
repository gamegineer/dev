/*
 * IStringBundle.java
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
 * Created on Feb 26, 2009 at 11:12:48 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import java.util.Set;

/**
 * A collection of locale-specific strings that are referenced using
 * locale-neutral keys.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStringBundle
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates this bundle contains a locale-specific string for the specified
     * locale-neutral key.
     * 
     * @param key
     *        The locale-neutral key; must not be {@code null}.
     * 
     * @return {@code true} if this bundle contains a locale-specific string for
     *         the specified locale-neutral key; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code key} is {@code null}.
     */
    public boolean containsKey(
        /* @NonNull */
        String key );

    /**
     * Gets an immutable collection of locale-neutral keys contained within this
     * bundle.
     * 
     * @return An immutable collection of locale-neutral keys contained within
     *         this bundle; never {@code null}.
     */
    public Set<String> getKeys();

    /**
     * Gets the locale-specific string associated with the specified
     * locale-neutral key.
     * 
     * @param key
     *        The locale-neutral key; must not be {@code null}.
     * 
     * @return The locale-specific string associated with the specified
     *         locale-neutral key or {@code null} if the key does not exist in
     *         this bundle.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code key} is {@code null}.
     */
    /* @Nullable */
    public String getString(
        /* @NonNull */
        String key );
}
