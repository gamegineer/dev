/*
 * IAttribute.java
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
 * Created on May 18, 2009 at 10:06:49 PM.
 */

package org.gamegineer.client.ui.console.commandlet.util.attribute;

import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * A console statelet attribute.
 * 
 * <p>
 * Instances of this interface allow clients to manipulate a console statelet
 * attribute in a type-safe manner.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 * 
 * @param <T>
 *        The type of the attribute value.
 */
public interface IAttribute<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds this attribute to the specified statelet with the specified value.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is present in the specified statelet or the
     *         attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    public void add(
        /* @NonNull */
        IStatelet statelet,
        /* @Nullable */
        T value );

    /**
     * Gets the value of this attribute from the specified statelet. The
     * attribute will be added to the statelet with a default value if it is not
     * already present.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * 
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    /* @Nullable */
    public T ensureGetValue(
        /* @NonNull */
        IStatelet statelet );

    /**
     * Sets the value of this attribute in the specified statelet. The attribute
     * will be added to the statelet with the specified value if it is not
     * already present.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    public void ensureSetValue(
        /* @NonNull */
        IStatelet statelet,
        /* @Nullable */
        T value );

    /**
     * Gets the name of this attribute.
     * 
     * @return The name of this attribute; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets the value of this attribute from the specified statelet.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * 
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified statelet.
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    /* @Nullable */
    public T getValue(
        /* @NonNull */
        IStatelet statelet );

    /**
     * Indicates this attribute is present in the specified statelet.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * 
     * @return {@code true} if this attribute is present in the specified
     *         statelet attribute; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    public boolean isPresent(
        /* @NonNull */
        IStatelet statelet );

    /**
     * Removes this attribute from the specified statelet.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified statelet.
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    public void remove(
        /* @NonNull */
        IStatelet statelet );

    /**
     * Sets the value of this attribute in the specified statelet.
     * 
     * @param statelet
     *        The console statelet; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified statelet or the
     *         attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code statelet} is {@code null}.
     */
    public void setValue(
        /* @NonNull */
        IStatelet statelet,
        /* @Nullable */
        T value );
}
