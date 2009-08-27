/*
 * IState.java
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
 * Created on Feb 18, 2008 at 10:08:49 PM.
 */

package org.gamegineer.engine.core;

import java.util.Set;

/**
 * The state of an engine.
 * 
 * <p>
 * The engine state is simply a collection of named attributes. Each attribute
 * exists within a particular scope. Engine components may access and modify the
 * state they receive in their engine execution context as necessary.
 * </p>
 * 
 * <p>
 * In order to preserve the integrity of the engine state and to ensure state
 * changes are properly tracked, all attribute values should be immutable
 * objects. For example, assume a particular attribute is logically represented
 * as a {@code List<String>}. If you need to add a new element to the list, you
 * should not call {@code add} directly on the instance retrieved from the
 * state. Instead, you should create a new list from the existing list, add the
 * new element, and return the new list to the state by calling
 * {@code setAttribute}.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IState
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new attribute to this state.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an attribute with the same name already exists in this state.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void addAttribute(
        /* @NonNull */
        AttributeName name,
        /* @Nullable */
        Object value );

    /**
     * Indicates this state contains the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return {@code true} if this state contains the specified attribute;
     *         otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public boolean containsAttribute(
        /* @NonNull */
        AttributeName name );

    /**
     * Gets the value of the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this state.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public Object getAttribute(
        /* @NonNull */
        AttributeName name );

    /**
     * Gets an immutable set view of the attribute names contained in this
     * state.
     * 
     * <p>
     * The set is backed by the state, so changes to the state are reflected in
     * the set. If the state is modified while an iteration over the set is in
     * progress, the results of the iteration are undefined.
     * </p>
     * 
     * @return An immutable set view of the attribute names contained in this
     *         state; never {@code null}.
     */
    /* @NonNull */
    public Set<AttributeName> getAttributeNames();

    /**
     * Removes the attribute with the specified name from this state.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this state.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void removeAttribute(
        /* @NonNull */
        AttributeName name );

    /**
     * Sets the value of the attribute with the specified name.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param value
     *        The attribute value; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified attribute does not exist in this state.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public void setAttribute(
        /* @NonNull */
        AttributeName name,
        /* @Nullable */
        Object value );


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The scope of a state attribute.
     * 
     * <p>
     * The engine state is partitioned into scopes in order to collect
     * attributes into functionally-related partitions. An attribute is
     * associated with exactly one scope. The engine has a different policy for
     * how attributes in each scope are handled as described in the
     * documentation for each individual scope below.
     * </p>
     */
    public enum Scope
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /**
         * The application scope.
         * 
         * <p>
         * This is the scope in which all application state is stored. The
         * engine requires all application state changes to be invertible in
         * order to preserve the integrity of the application. Application state
         * must always be persistable in order to save and restore the
         * application at a later time.
         * </p>
         */
        APPLICATION,

        /**
         * The engine control scope.
         * 
         * <p>
         * This is the scope in which all engine control state is stored.
         * Control data is used to manage the operation of the engine
         * independently of the executing application. The engine requires all
         * control state changes to be invertible although control state is
         * transient and should not be persisted.
         * </p>
         * 
         * <p>
         * This scope is typically reserved for the engine kernel and its
         * extensions. All engine components other than extensions should, in
         * general, avoid manipulating attributes in this scope.
         * </p>
         */
        ENGINE_CONTROL,
    }
}
