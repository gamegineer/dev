/*
 * GameSystem.java
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
 * Created on Nov 15, 2008 at 11:16:15 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;

/**
 * Implementation of {@link org.gamegineer.game.core.system.IGameSystem}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameSystem
    implements IGameSystem
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private final String id_;

    /** The role list. */
    private final List<IRole> roles_;

    /** The stage list. */
    private final List<IStage> stages_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystem} class.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param roles
     *        The role list; must not be {@code null}.
     * @param stages
     *        The stage list; must not be {@code null}.
     */
    private GameSystem(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final List<IRole> roles,
        /* @NonNull */
        final List<IStage> stages )
    {
        assert id != null;
        assert roles != null;
        assert stages != null;

        id_ = id;
        roles_ = new ArrayList<IRole>( roles );
        stages_ = new ArrayList<IStage>( stages );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code GameSystem} class.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param roles
     *        The role list; must not be {@code null}.
     * @param stages
     *        The stage list; must not be {@code null}.
     * 
     * @return A new game system; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal game system.
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code roles}, or {@code stages} is {@code null}.
     */
    /* @NonNull */
    public static GameSystem createGameSystem(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final List<IRole> roles,
        /* @NonNull */
        final List<IStage> stages )
    {
        // TODO: See comment in GameConfiguration.createGameConfiguration().

        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( roles, "roles" ); //$NON-NLS-1$
        assertArgumentNotNull( stages, "stages" ); //$NON-NLS-1$

        final GameSystem gameSystem = new GameSystem( id, roles, stages );
        GameSystemUtils.assertGameSystemLegal( gameSystem );
        return gameSystem;
    }

    /*
     * @see org.gamegineer.game.core.system.IGameSystem#getId()
     */
    public String getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.game.core.system.IGameSystem#getRoles()
     */
    public List<IRole> getRoles()
    {
        return new ArrayList<IRole>( roles_ );
    }

    /*
     * @see org.gamegineer.game.core.system.IGameSystem#getStages()
     */
    public List<IStage> getStages()
    {
        return new ArrayList<IStage>( stages_ );
    }
}
