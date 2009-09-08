/*
 * NonValidatingRoleUiBuilder.java
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
 * Created on Feb 28, 2009 at 9:49:58 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.ui.system.NonValidatingRoleUi}.
 * 
 * <p>
 * Each role user interface built by an instance of this class is immutable and
 * thus guaranteed to be thread-safe. This class does not validate its
 * attributes and thus allows the construction of malformed role user interfaces
 * that may violate the contract of {@code IRoleUi}. It is only intended to be
 * used during testing.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class NonValidatingRoleUiBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private String id_;

    /** The role name. */
    private String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingRoleUiBuilder}
     * class with an uninitialized role user interface.
     */
    public NonValidatingRoleUiBuilder()
    {
        id_ = null;
        name_ = null;
    }

    /**
     * Initializes a new instance of the {@code NonValidatingRoleUiBuilder}
     * class using the specified role user interface.
     * 
     * @param roleUi
     *        The role user interface used to initialize this builder; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleUi} is {@code null}.
     */
    public NonValidatingRoleUiBuilder(
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assertArgumentNotNull( roleUi, "roleUi" ); //$NON-NLS-1$

        id_ = roleUi.getId();
        name_ = roleUi.getName();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the role identifier.
     * 
     * @param id
     *        The role identifier; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingRoleUiBuilder setId(
        /* @Nullable */
        final String id )
    {
        id_ = id;

        return this;
    }

    /**
     * Sets the role name.
     * 
     * @param name
     *        The role name; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingRoleUiBuilder setName(
        /* @Nullable */
        final String name )
    {
        name_ = name;

        return this;
    }

    /**
     * Creates a new role user interface based on the current state of this
     * builder.
     * 
     * @return A new role user interface; never {@code null}.
     */
    /* @NonNull */
    public IRoleUi toRoleUi()
    {
        return new NonValidatingRoleUi( id_, name_ );
    }
}
