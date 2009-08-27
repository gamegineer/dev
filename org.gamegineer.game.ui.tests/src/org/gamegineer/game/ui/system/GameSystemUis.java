/*
 * GameSystemUis.java
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
 * Created on Feb 27, 2009 at 10:12:48 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.internal.ui.Services;

/**
 * A factory for creating various types of game system user interfaces suitable
 * for testing.
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class GameSystemUis
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUis} class.
     */
    private GameSystemUis()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system user interface.
     * 
     * <p>
     * The returned game system user interface contains legal values for all
     * fields.
     * </p>
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * 
     * @return A new game system user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    /* @NonNull */
    public static IGameSystemUi createGameSystemUi(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        return createGameSystemUiBuilder( gameSystem ).toGameSystemUi();
    }

    /**
     * Creates a new game system user interface builder.
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * 
     * @return A new game system user interface builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemUiBuilder createGameSystemUiBuilder(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        return createIncompleteGameSystemUiBuilder( gameSystem, EnumSet.noneOf( GameSystemUiAttribute.class ) );
    }

    /**
     * Creates a new illegal game system user interface.
     * 
     * <p>
     * The returned game system user interface has an illegal value in one or
     * more fields.
     * </p>
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * 
     * @return A new illegal game system user interface; never {@code null}.
     */
    /* @NonNull */
    public static IGameSystemUi createIllegalGameSystemUi(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        // NB: Our choice of illegal game system is to allocate a duplicate role.
        final IGameSystemUi gameSystemUi = createGameSystemUi( gameSystem );
        return new IGameSystemUi()
        {
            public String getId()
            {
                return gameSystem.getId();
            }

            public String getName()
            {
                return gameSystem.getId() + "-name"; //$NON-NLS-1$
            }

            public List<IRoleUi> getRoles()
            {
                final List<IRoleUi> roleUis = new ArrayList<IRoleUi>( gameSystemUi.getRoles() );
                roleUis.add( roleUis.get( 0 ) );
                return roleUis;
            }
        };
    }

    /**
     * Creates an incomplete game system user interface builder that does not
     * have a value set for the specified attribute.
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game system user interface builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} or {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemUiBuilder createIncompleteGameSystemUiBuilder(
        /* @NonNull */
        final IGameSystem gameSystem,
        /* @NonNull */
        final GameSystemUiAttribute missingAttribute )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteGameSystemUiBuilder( gameSystem, EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete game system user interface builder that does not
     * have a value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete game system user interface builder.
     * </p>
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game system user interface builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} or {@code missingAttributes} is
     *         {@code null}.
     */
    /* @NonNull */
    public static GameSystemUiBuilder createIncompleteGameSystemUiBuilder(
        /* @NonNull */
        final IGameSystem gameSystem,
        /* @NonNull */
        final EnumSet<GameSystemUiAttribute> missingAttributes )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final GameSystemUiBuilder builder = new GameSystemUiBuilder();

        if( !missingAttributes.contains( GameSystemUiAttribute.ID ) )
        {
            builder.setId( gameSystem.getId() );
        }
        if( !missingAttributes.contains( GameSystemUiAttribute.NAME ) )
        {
            builder.setName( gameSystem.getId() + "-name" ); //$NON-NLS-1$
        }
        if( !missingAttributes.contains( GameSystemUiAttribute.ROLES ) )
        {
            builder.addRoles( createRoleUiList( gameSystem.getRoles() ) );
        }

        return builder;
    }

    /**
     * Creates an incomplete role user interface builder that does not have a
     * value set for the specified attribute.
     * 
     * @param role
     *        The role on which the user interface is based; must not be
     *        {@code null}.
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A role user interface builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} or {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static RoleUiBuilder createIncompleteRoleUiBuilder(
        /* @NonNull */
        final IRole role,
        /* @NonNull */
        final RoleUiAttribute missingAttribute )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteRoleUiBuilder( role, EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete role user interface builder that does not have a
     * value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete role user interface builder.
     * </p>
     * 
     * @param role
     *        The role on which the user interface is based; must not be
     *        {@code null}.
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A role user interface builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} or {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static RoleUiBuilder createIncompleteRoleUiBuilder(
        /* @NonNull */
        final IRole role,
        /* @NonNull */
        final EnumSet<RoleUiAttribute> missingAttributes )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final RoleUiBuilder builder = new RoleUiBuilder();

        if( !missingAttributes.contains( RoleUiAttribute.ID ) )
        {
            builder.setId( role.getId() );
        }
        if( !missingAttributes.contains( RoleUiAttribute.NAME ) )
        {
            builder.setName( role.getId() + "-name" ); //$NON-NLS-1$
        }

        return builder;
    }

    /**
     * Creates a new role user interface for the specified role.
     * 
     * @param role
     *        The role; must not be {@code null}.
     * 
     * @return A new role user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} is {@code null}.
     */
    /* @NonNull */
    public static IRoleUi createRoleUi(
        /* @NonNull */
        final IRole role )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$

        final RoleUiBuilder builder = new RoleUiBuilder();
        builder.setId( role.getId() );
        builder.setName( role.getId() + "-name" ); //$NON-NLS-1$
        return builder.toRoleUi();
    }

    /**
     * Creates a role user interface list for the specified role list.
     * 
     * @param roles
     *        A role list; must not be {@code null}.
     * 
     * @return A role user interface list; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roles} is {@code null}.
     */
    /* @NonNull */
    public static List<IRoleUi> createRoleUiList(
        /* @NonNull */
        final List<IRole> roles )
    {
        assertArgumentNotNull( roles, "roles" ); //$NON-NLS-1$

        final List<IRoleUi> roleUis = new ArrayList<IRoleUi>();
        for( final IRole role : roles )
        {
            roleUis.add( createRoleUi( role ) );
        }
        return roleUis;
    }

    /**
     * Gets the game system user interface that has been registered with the
     * specified identifier.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system user interface that has been registered with the
     *         specified identifier or {@code null} if no such identifier
     *         exists.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public static IGameSystemUi getGameSystemUi(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return Services.getDefault().getGameSystemUiRegistry().getGameSystemUi( id );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Identifies the attributes of a game system user interface.
     */
    public enum GameSystemUiAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The game system identifier. */
        ID,

        /** The game system name attribute. */
        NAME,

        /** The game system roles attribute. */
        ROLES,
    }

    /**
     * Identifies the attributes of a role user interface.
     */
    public enum RoleUiAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The role identifier attribute. */
        ID,

        /** The role name attribute. */
        NAME,
    }
}
