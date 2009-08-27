/*
 * IAttribute.java
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
 * Created on Sep 6, 2008 at 9:12:50 PM.
 */

package org.gamegineer.engine.core.util.attribute;

import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState;

/**
 * An engine attribute.
 * 
 * <p>
 * Instances of this interface allow clients to manipulate an engine attribute
 * in a type-safe manner.
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
     * Adds this attribute to the specified state with the specified value.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is present in the specified state or the
     *         attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public void add(
        /* @NonNull */
        IState state,
        /* @Nullable */
        T value );

    /**
     * Gets the name of this attribute.
     * 
     * @return The name of this attribute; never {@code null}.
     */
    /* @NonNull */
    public AttributeName getName();

    /**
     * Gets the value of this attribute from the specified state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified state.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    /* @Nullable */
    public T getValue(
        /* @NonNull */
        IState state );

    /**
     * Indicates this attribute is present in the specified state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @return {@code true} if this attribute is present in the specified state
     *         attribute; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public boolean isPresent(
        /* @NonNull */
        IState state );

    /**
     * Removes this attribute from the specified state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified state.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public void remove(
        /* @NonNull */
        IState state );

    /**
     * Sets the value of this attribute in the specified state.
     * 
     * @param state
     *        The engine state; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified state or the
     *         attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code state} is {@code null}.
     */
    public void setValue(
        /* @NonNull */
        IState state,
        /* @Nullable */
        T value );
}
