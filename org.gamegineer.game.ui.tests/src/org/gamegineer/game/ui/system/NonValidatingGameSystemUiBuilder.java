/*
 * NonValidatingGameSystemUiBuilder.java
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
 * Created on Feb 28, 2009 at 9:56:02 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.ui.system.NonValidatingGameSystemUi}.
 * 
 * <p>
 * Each game system user interface built by an instance of this class is
 * immutable and thus guaranteed to be thread-safe. This class does not validate
 * its attributes and thus allows the construction of malformed game system user
 * interfaces that may violate the contract of {@code IGameSystemUi}. It is only
 * intended to be used during testing.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class NonValidatingGameSystemUiBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private String id_;

    /** The game system name. */
    private String name_;

    /** The role user interface list. */
    private List<IRoleUi> roleUis_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * NonValidatingGameSystemUiBuilder} class with an uninitialized game system
     * user interface.
     */
    public NonValidatingGameSystemUiBuilder()
    {
        id_ = null;
        name_ = null;
        roleUis_ = null;
    }

    /**
     * Initializes a new instance of the {@code
     * NonValidatingGameSystemUiBuilder} class using the specified game system
     * user interface.
     * 
     * @param gameSystemUi
     *        The game system user interface used to initialize this builder;
     *        must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} is {@code null}.
     */
    public NonValidatingGameSystemUiBuilder(
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$

        id_ = gameSystemUi.getId();
        name_ = gameSystemUi.getName();
        roleUis_ = gameSystemUi.getRoles();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a game role.
     * 
     * @param roleUi
     *        The role user interface; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemUiBuilder addRole(
        /* @Nullable */
        final IRoleUi roleUi )
    {
        if( roleUis_ == null )
        {
            roleUis_ = new ArrayList<IRoleUi>();
        }

        roleUis_.add( roleUi );

        return this;
    }

    /**
     * Clears the game roles.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemUiBuilder clearRoles()
    {
        roleUis_ = null;

        return this;
    }

    /**
     * Sets the game system identifier.
     * 
     * @param id
     *        The game system identifier; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemUiBuilder setId(
        /* @Nullable */
        final String id )
    {
        id_ = id;

        return this;
    }

    /**
     * Sets the game system name.
     * 
     * @param name
     *        The game system name; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemUiBuilder setName(
        /* @Nullable */
        final String name )
    {
        name_ = name;

        return this;
    }

    /**
     * Creates a new game system user interface based on the current state of
     * this builder.
     * 
     * @return A new game system user interface; never {@code null}.
     */
    /* @NonNull */
    public IGameSystemUi toGameSystemUi()
    {
        return new NonValidatingGameSystemUi( id_, name_, roleUis_ );
    }
}
