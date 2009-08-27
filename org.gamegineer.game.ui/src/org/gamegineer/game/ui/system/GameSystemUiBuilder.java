/*
 * GameSystemUiBuilder.java
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
 * Created on Feb 27, 2009 at 11:07:20 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.game.internal.ui.system.GameSystemUi;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.ui.system.IGameSystemUi}.
 * 
 * <p>
 * Each game system user interface built by an instance of this class is
 * immutable and thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class GameSystemUiBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private String m_id;

    /** The game system name. */
    private String m_name;

    /** The role user interface list. */
    private final List<IRoleUi> m_roleUis;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiBuilder} class with
     * an empty game system user interface.
     */
    public GameSystemUiBuilder()
    {
        m_id = null;
        m_name = null;
        m_roleUis = new ArrayList<IRoleUi>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a role.
     * 
     * @param roleUi
     *        The role user interface; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleUi} is {@code null}.
     */
    /* @NonNull */
    public GameSystemUiBuilder addRole(
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assertArgumentNotNull( roleUi, "roleUi" ); //$NON-NLS-1$

        m_roleUis.add( roleUi );

        return this;
    }

    /**
     * Adds a collection of roles.
     * 
     * @param roleUis
     *        The role user interface list; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleUis} is {@code null}.
     */
    /* @NonNull */
    public GameSystemUiBuilder addRoles(
        /* @NonNull */
        final List<IRoleUi> roleUis )
    {
        assertArgumentNotNull( roleUis, "roleUis" ); //$NON-NLS-1$

        m_roleUis.addAll( roleUis );

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
    public GameSystemUiBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Sets the game system name.
     * 
     * @param name
     *        The game system name; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public GameSystemUiBuilder setName(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        m_name = name;

        return this;
    }

    /**
     * Creates a new game system user interface based on the current state of
     * this builder.
     * 
     * @return A new game system user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         game system user interface.
     */
    /* @NonNull */
    public IGameSystemUi toGameSystemUi()
    {
        assertStateLegal( m_id != null, Messages.GameSystemUiBuilder_id_notSet );
        assertStateLegal( m_name != null, Messages.GameSystemUiBuilder_name_notSet );

        try
        {
            return GameSystemUi.createGameSystemUi( m_id, m_name, m_roleUis );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.GameSystemUiBuilder_state_illegal, e );
        }
    }
}
