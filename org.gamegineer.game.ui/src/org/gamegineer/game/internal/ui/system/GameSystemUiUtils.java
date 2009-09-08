/*
 * GameSystemUiUtils.java
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
 * Created on Feb 27, 2009 at 11:00:59 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * A collection of useful methods for working with game system user interfaces.
 */
public final class GameSystemUiUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiUtils} class.
     */
    private GameSystemUiUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts a game system user interface is legal.
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code gameSystemUi} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} is {@code null}.
     */
    public static void assertGameSystemUiLegal(
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$

        final List<IRoleUi> roleUis = gameSystemUi.getRoles();
        assertArgumentLegal( !roleUis.isEmpty(), "gameSystemUi", Messages.GameSystemUiUtils_assertGameSystemUiLegal_roleUis_empty ); //$NON-NLS-1$
        assertArgumentLegal( isRoleIdentifierUnionUnique( roleUis, null ), "gameSystemUi", Messages.GameSystemUiUtils_assertGameSystemUiLegal_roleUis_notUnique ); //$NON-NLS-1$
    }

    /**
     * Asserts a role user interface is legal.
     * 
     * @param roleUi
     *        The role user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code roleUi} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code roleUi} is {@code null}.
     */
    public static void assertRoleUiLegal(
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assertArgumentNotNull( roleUi, "roleUi" ); //$NON-NLS-1$
    }

    /**
     * Determines if the union of the role identifiers of the role user
     * interfaces in the specified list and the role identifier of the specified
     * role user interface is unique.
     * 
     * @param roleUis
     *        A list of role user interfaces; must not be {@code null}.
     * @param roleUi
     *        A role user interface; may be {@code null}.
     * 
     * @return {@code true} if the specified union of role identifiers is
     *         unique; otherwise {@code false}.
     */
    private static boolean isRoleIdentifierUnionUnique(
        /* @NonNull */
        final List<IRoleUi> roleUis,
        /* @Nullable */
        final IRoleUi roleUi )
    {
        assert roleUis != null;

        final Set<String> ids = new HashSet<String>();

        for( final IRoleUi ui : roleUis )
        {
            if( !isRoleIdentifierUnionUnique( ids, ui ) )
            {
                return false;
            }
        }

        if( roleUi != null )
        {
            if( !isRoleIdentifierUnionUnique( ids, roleUi ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if the union of the specified role identifier set and the role
     * identifier of the specified role user interface is unique.
     * 
     * @param ids
     *        A set of role identifiers; must not be {@code null}. The role
     *        identifier of the specified role user interface will be added to
     *        this set before returning.
     * @param roleUi
     *        A role user interface; must not be {@code null}.
     * 
     * @return {@code true} if the specified union of role identifiers is
     *         unique; otherwise {@code false}.
     */
    private static boolean isRoleIdentifierUnionUnique(
        /* @NonNull */
        final Set<String> ids,
        /* @NonNull */
        final IRoleUi roleUi )
    {
        assert ids != null;
        assert roleUi != null;

        if( ids.contains( roleUi.getId() ) )
        {
            return false;
        }

        ids.add( roleUi.getId() );
        return true;
    }
}
