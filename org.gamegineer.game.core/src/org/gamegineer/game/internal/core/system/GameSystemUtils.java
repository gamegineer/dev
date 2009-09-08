/*
 * GameSystemUtils.java
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
 * Created on Nov 15, 2008 at 10:24:01 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;

/**
 * A collection of useful methods for working with game systems.
 */
public final class GameSystemUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUtils} class.
     */
    private GameSystemUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts a game system is legal.
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code gameSystem} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    public static void assertGameSystemLegal(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        final List<IRole> roles = gameSystem.getRoles();
        assertArgumentLegal( !roles.isEmpty(), "gameSystem", Messages.GameSystemUtils_assertGameSystemLegal_roles_empty ); //$NON-NLS-1$
        assertArgumentLegal( isRoleIdentifierUnionUnique( roles, null ), "gameSystem", Messages.GameSystemUtils_assertGameSystemLegal_roles_notUnique ); //$NON-NLS-1$

        final List<IStage> stages = gameSystem.getStages();
        assertArgumentLegal( !stages.isEmpty(), "gameSystem", Messages.GameSystemUtils_assertGameSystemLegal_stages_empty ); //$NON-NLS-1$
        assertArgumentLegal( isStageIdentifierUnionUnique( null, stages, null ), "gameSystem", Messages.GameSystemUtils_assertGameSystemLegal_stages_notUnique ); //$NON-NLS-1$
    }

    /**
     * Asserts a role is legal.
     * 
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code role} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code role} is {@code null}.
     */
    public static void assertRoleLegal(
        /* @NonNull */
        final IRole role )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$
    }

    /**
     * Asserts a stage is legal.
     * 
     * @param stage
     *        The stage; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code stage} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code stage} is {@code null}.
     */
    public static void assertStageLegal(
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        assertArgumentLegal( stage.getCardinality() >= 0, "stage", Messages.GameSystemUtils_assertStageLegal_cardinality_negative ); //$NON-NLS-1$

        assertArgumentLegal( isStageIdentifierUnionUnique( stage.getId(), stage.getStages(), null ), "stage", Messages.GameSystemUtils_assertStageLegal_stages_notUnique ); //$NON-NLS-1$
    }

    /**
     * Determines if the union of the role identifiers of the roles in the
     * specified list and the role identifier of the specified role is unique.
     * 
     * @param roles
     *        A list of roles; must not be {@code null}.
     * @param role
     *        A role; may be {@code null}.
     * 
     * @return {@code true} if the specified union of role identifiers is
     *         unique; otherwise {@code false}.
     */
    private static boolean isRoleIdentifierUnionUnique(
        /* @NonNull */
        final List<IRole> roles,
        /* @Nullable */
        final IRole role )
    {
        assert roles != null;

        final Set<String> ids = new HashSet<String>();

        for( final IRole aRole : roles )
        {
            if( !isRoleIdentifierUnionUnique( ids, aRole ) )
            {
                return false;
            }
        }

        if( role != null )
        {
            if( !isRoleIdentifierUnionUnique( ids, role ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if the union of the specified role identifier set and the role
     * identifier of the specified role is unique.
     * 
     * @param ids
     *        A set of role identifiers; must not be {@code null}. The role
     *        identifier of the specified role will be added to this set before
     *        returning.
     * @param role
     *        A role; must not be {@code null}.
     * 
     * @return {@code true} if the specified union of role identifiers is
     *         unique; otherwise {@code false}.
     */
    private static boolean isRoleIdentifierUnionUnique(
        /* @NonNull */
        final Set<String> ids,
        /* @NonNull */
        final IRole role )
    {
        assert ids != null;
        assert role != null;

        if( ids.contains( role.getId() ) )
        {
            return false;
        }

        ids.add( role.getId() );
        return true;
    }

    /**
     * Determines if the union of the specified stage identifier, the stage
     * identifiers of the stages in the specified list and all their
     * descendants, and the stage identifiers of the specified stage and all its
     * descendants is unique.
     * 
     * @param id
     *        A stage identifier; may be {@code null}.
     * @param stages
     *        A list of stages; must not be {@code null}.
     * @param stage
     *        A stage; may be {@code null}.
     * 
     * @return {@code true} if the specified union of stage identifiers is
     *         unique; otherwise {@code false}.
     */
    private static boolean isStageIdentifierUnionUnique(
        /* @Nullable */
        final String id,
        /* @NonNull */
        final List<IStage> stages,
        /* @Nullable */
        final IStage stage )
    {
        assert stages != null;

        final Set<String> ids = new HashSet<String>();

        if( id != null )
        {
            ids.add( id );
        }

        for( final IStage aStage : stages )
        {
            if( !isStageIdentifierUnionUnique( ids, aStage ) )
            {
                return false;
            }
        }

        if( stage != null )
        {
            if( !isStageIdentifierUnionUnique( ids, stage ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if the union of the specified stage identifier set and the
     * stage identifiers of the specified stage and all its descendants are
     * unique.
     * 
     * @param ids
     *        A set of stage identifiers; must not be {@code null}. The
     *        identifier of the specified stage and those of its descendants
     *        will be added to this set before returning.
     * @param stage
     *        A stage; must not be {@code null}.
     * 
     * @return {@code true} if the specified union of stage identifiers are
     *         unique; otherwise {@code false}.
     */
    private static boolean isStageIdentifierUnionUnique(
        /* @NonNull */
        final Set<String> ids,
        /* @NonNull */
        final IStage stage )
    {
        assert ids != null;
        assert stage != null;

        if( ids.contains( stage.getId() ) )
        {
            return false;
        }
        ids.add( stage.getId() );

        for( final IStage aStage : stage.getStages() )
        {
            if( !isStageIdentifierUnionUnique( ids, aStage ) )
            {
                return false;
            }
        }

        return true;
    }
}
