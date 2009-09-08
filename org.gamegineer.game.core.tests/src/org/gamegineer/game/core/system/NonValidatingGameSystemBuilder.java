/*
 * NonValidatingGameSystemBuilder.java
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
 * Created on Nov 20, 2008 at 11:09:49 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.NonValidatingGameSystem}.
 * 
 * <p>
 * Each game system built by an instance of this class is immutable and thus
 * guaranteed to be thread-safe. This class does not validate its attributes and
 * thus allows the construction of malformed game systems that may violate the
 * contract of {@code IGameSystem}. It is only intended to be used during
 * testing.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class NonValidatingGameSystemBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private String id_;

    /** The role list. */
    private List<IRole> roles_;

    /** The stage list. */
    private List<IStage> stages_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingGameSystemBuilder}
     * class with an uninitialized game system.
     */
    public NonValidatingGameSystemBuilder()
    {
        id_ = null;
        roles_ = null;
        stages_ = null;
    }

    /**
     * Initializes a new instance of the {@code NonValidatingGameSystemBuilder}
     * class using the specified game system.
     * 
     * @param gameSystem
     *        The game system used to initialize this builder; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    public NonValidatingGameSystemBuilder(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        id_ = gameSystem.getId();
        roles_ = gameSystem.getRoles();
        stages_ = gameSystem.getStages();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a game role.
     * 
     * @param role
     *        The role; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemBuilder addRole(
        /* @Nullable */
        final IRole role )
    {
        if( roles_ == null )
        {
            roles_ = new ArrayList<IRole>();
        }

        roles_.add( role );

        return this;
    }

    /**
     * Adds a game stage.
     * 
     * @param stage
     *        The stage; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemBuilder addStage(
        /* @Nullable */
        final IStage stage )
    {
        if( stages_ == null )
        {
            stages_ = new ArrayList<IStage>();
        }

        stages_.add( stage );

        return this;
    }

    /**
     * Clears the game roles.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemBuilder clearRoles()
    {
        roles_ = null;

        return this;
    }

    /**
     * Clears the game stages.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingGameSystemBuilder clearStages()
    {
        stages_ = null;

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
    public NonValidatingGameSystemBuilder setId(
        /* @Nullable */
        final String id )
    {
        id_ = id;

        return this;
    }

    /**
     * Creates a new game system based on the current state of this builder.
     * 
     * @return A new game system; never {@code null}.
     */
    /* @NonNull */
    public IGameSystem toGameSystem()
    {
        return new NonValidatingGameSystem( id_, roles_, stages_ );
    }
}
