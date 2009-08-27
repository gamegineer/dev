/*
 * GameSystemUiUtilsTest.java
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
 * Created on Feb 27, 2009 at 11:03:01 PM.
 */

package org.gamegineer.game.internal.ui.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.GameSystemUiUtils} class.
 */
public final class GameSystemUiUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiUtilsTest} class.
     */
    public GameSystemUiUtilsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game system user interface.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal game system user interface.
     * </p>
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param name
     *        The game system name; must not be {@code null}.
     * @param roleUis
     *        The role user interface list; must not be {@code null}.
     * 
     * @return A new game system user interface; never {@code null}.
     */
    /* @NonNull */
    private static IGameSystemUi createGameSystemUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final List<IRoleUi> roleUis )
    {
        assert id != null;
        assert name != null;
        assert roleUis != null;

        return new IGameSystemUi()
        {
            public String getId()
            {
                return id;
            }

            public String getName()
            {
                return name;
            }

            public List<IRoleUi> getRoles()
            {
                return new ArrayList<IRoleUi>( roleUis );
            }
        };
    }

    /**
     * Creates a role user interface.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal role user interface.
     * </p>
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * @param name
     *        The role name; must not be {@code null}.
     * 
     * @return A new role user interface; never {@code null}.
     */
    /* @NonNull */
    private static IRoleUi createRoleUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name )
    {
        assert name != null;

        return new IRoleUi()
        {
            public String getId()
            {
                return id;
            }

            public String getName()
            {
                return name;
            }
        };
    }

    /**
     * Ensures the {@code assertGameSystemUiLegal} method throws an exception
     * when passed an illegal game system user interface which has an empty role
     * list.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemUiLegal_GameSystemUi_Illegal_Roles_Empty()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final List<IRoleUi> roleUis = Collections.emptyList();

        GameSystemUiUtils.assertGameSystemUiLegal( createGameSystemUi( id, name, roleUis ) );
    }

    /**
     * Ensures the {@code assertGameSystemUiLegal} method throws an exception
     * when passed an illegal game system user interface which has a role user
     * interface list that contains non-unique role identifiers.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemUiLegal_GameSystemUi_Illegal_Roles_NonUniqueRoleIds()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final List<IRoleUi> roleUis = GameSystemUis.createRoleUiList( GameSystems.createNonUniqueRoleList() );

        GameSystemUiUtils.assertGameSystemUiLegal( createGameSystemUi( id, name, roleUis ) );
    }

    /**
     * Ensures the {@code assertGameSystemUiLegal} method does not throw an
     * exception when passed a legal game system user interface.
     */
    @Test
    public void testAssertGameSystemUiLegal_GameSystemUi_Legal()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final List<IRoleUi> roleUis = GameSystemUis.createRoleUiList( GameSystems.createUniqueRoleList() );

        GameSystemUiUtils.assertGameSystemUiLegal( createGameSystemUi( id, name, roleUis ) );
    }

    /**
     * Ensures the {@code assertGameSystemUiLegal} method throws an exception
     * when passed a {@code null} game system user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertGameSystemUiLegal_GameSystemUi_Null()
    {
        GameSystemUiUtils.assertGameSystemUiLegal( null );
    }

    /**
     * Ensures the {@code assertRoleUiLegal} method does not throw an exception
     * when passed a legal role user interface.
     */
    @Test
    public void testAssertRoleUiLegal_RoleUi_Legal()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$

        GameSystemUiUtils.assertRoleUiLegal( createRoleUi( id, name ) );
    }

    /**
     * Ensures the {@code assertRoleUiLegal} method throws an exception when
     * passed a {@code null} role user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertRoleUiLegal_RoleUi_Null()
    {
        GameSystemUiUtils.assertRoleUiLegal( null );
    }
}
