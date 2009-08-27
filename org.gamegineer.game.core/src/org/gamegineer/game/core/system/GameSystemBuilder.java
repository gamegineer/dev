/*
 * GameSystemBuilder.java
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
 * Created on Nov 15, 2008 at 11:10:41 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.game.internal.core.system.GameSystem;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.IGameSystem}.
 * 
 * <p>
 * Each game system built by an instance of this class is immutable and thus
 * guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class GameSystemBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private String m_id;

    /** The role list. */
    private final List<IRole> m_roles;

    /** The stage list. */
    private final List<IStage> m_stages;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemBuilder} class with an
     * empty game system.
     */
    public GameSystemBuilder()
    {
        m_id = null;
        m_roles = new ArrayList<IRole>();
        m_stages = new ArrayList<IStage>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a role.
     * 
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} is {@code null}.
     */
    /* @NonNull */
    public GameSystemBuilder addRole(
        /* @NonNull */
        final IRole role )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$

        m_roles.add( role );

        return this;
    }

    /**
     * Adds a collection of roles.
     * 
     * @param roles
     *        The role list; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roles} is {@code null}.
     */
    /* @NonNull */
    public GameSystemBuilder addRoles(
        /* @NonNull */
        final List<IRole> roles )
    {
        assertArgumentNotNull( roles, "roles" ); //$NON-NLS-1$

        m_roles.addAll( roles );

        return this;
    }

    /**
     * Adds a stage.
     * 
     * @param stage
     *        The stage; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} is {@code null}.
     */
    /* @NonNull */
    public GameSystemBuilder addStage(
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        m_stages.add( stage );

        return this;
    }

    /**
     * Adds a collection of stages.
     * 
     * @param stages
     *        The stage list; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stages} is {@code null}.
     */
    /* @NonNull */
    public GameSystemBuilder addStages(
        /* @NonNull */
        final List<IStage> stages )
    {
        assertArgumentNotNull( stages, "stages" ); //$NON-NLS-1$

        m_stages.addAll( stages );

        return this;
    }

    /**
     * Sets the game system identifier.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public GameSystemBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Creates a new game system based on the current state of this builder.
     * 
     * @return A new game system; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         game system.
     */
    /* @NonNull */
    public IGameSystem toGameSystem()
    {
        assertStateLegal( m_id != null, Messages.GameSystemBuilder_id_notSet );

        try
        {
            return GameSystem.createGameSystem( m_id, m_roles, m_stages );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.GameSystemBuilder_state_illegal, e );
        }
    }
}
