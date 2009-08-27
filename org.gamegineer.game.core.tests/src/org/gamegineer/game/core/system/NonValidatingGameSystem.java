/*
 * NonValidatingGameSystem.java
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
 * Created on Nov 20, 2008 at 10:56:24 PM.
 */

package org.gamegineer.game.core.system;

import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * An implementation of {@link org.gamegineer.game.core.system.IGameSystem} that
 * does not validate its attributes.
 * 
 * <p>
 * This implementation allows malformed game systems and thus may violate the
 * contract of {@code IGameSystem}. It is only intended to be used during
 * testing.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NonValidatingGameSystem
    implements IGameSystem
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private final String m_id;

    /** The role list. */
    private final List<IRole> m_roles;

    /** The stage list. */
    private final List<IStage> m_stages;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingGameSystem} class.
     * 
     * @param id
     *        The game system identifier; may be {@code null}.
     * @param roles
     *        The role list; may be {@code null}.
     * @param stages
     *        The stage list; may be {@code null}.
     */
    public NonValidatingGameSystem(
        /* @Nullable */
        final String id,
        /* @Nullable */
        final List<IRole> roles,
        /* @Nullable */
        final List<IStage> stages )
    {
        m_id = id;
        m_roles = (roles != null) ? new ArrayList<IRole>( roles ) : null;
        m_stages = (stages != null) ? new ArrayList<IStage>( stages ) : null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @return The game system identifier; may be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IGameSystem#getId()
     */
    /* @Nullable */
    public String getId()
    {
        return m_id;
    }

    /**
     * @return A list view of the game roles; may be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IGameSystem#getRoles()
     */
    /* @Nullable */
    public List<IRole> getRoles()
    {
        return (m_roles != null) ? new ArrayList<IRole>( m_roles ) : null;
    }

    /**
     * @return A list view of the game stages; may be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IGameSystem#getStages()
     */
    /* @Nullable */
    public List<IStage> getStages()
    {
        return (m_stages != null) ? new ArrayList<IStage>( m_stages ) : null;
    }
}
