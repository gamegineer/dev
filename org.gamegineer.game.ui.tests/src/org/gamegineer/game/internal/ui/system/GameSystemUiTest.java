/*
 * GameSystemUiTest.java
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
 * Created on Feb 28, 2009 at 12:41:54 AM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.GameSystemUi} class.
 */
public final class GameSystemUiTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default game system identifier. */
    private static final String DEFAULT_ID = "id"; //$NON-NLS-1$

    /** The default game system name. */
    private static final String DEFAULT_NAME = "name"; //$NON-NLS-1$

    /** The default role user interface list. */
    private static final List<IRoleUi> DEFAULT_ROLE_UIS = GameSystemUis.createRoleUiList( GameSystems.createUniqueRoleList() );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiTest} class.
     */
    public GameSystemUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameSystemUi} method throws an exception when
     * one or more of its arguments results in an illegal game system user
     * interface.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game system user interface
     * attributes is located in the {@code GameSystemUiUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameSystemUi_GameSystemUi_Illegal()
    {
        final IGameSystemUi illegalGameSystemUi = GameSystemUis.createIllegalGameSystemUi( GameSystems.createUniqueGameSystem() );

        GameSystemUi.createGameSystemUi( illegalGameSystemUi.getId(), illegalGameSystemUi.getName(), illegalGameSystemUi.getRoles() );
    }

    /**
     * Ensures the {@code createGameSystemUi} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUi_Id_Null()
    {
        GameSystemUi.createGameSystemUi( null, DEFAULT_NAME, DEFAULT_ROLE_UIS );
    }

    /**
     * Ensures the {@code createGameSystemUi} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUi_Name_Null()
    {
        GameSystemUi.createGameSystemUi( DEFAULT_ID, null, DEFAULT_ROLE_UIS );
    }

    /**
     * Ensures the {@code createGameSystemUi} method makes a copy of the role
     * user interface list.
     */
    @Test
    public void testCreateGameSystemUi_RoleUis_Copy()
    {
        final List<IRoleUi> roleUis = GameSystemUis.createRoleUiList( GameSystems.createUniqueRoleList() );
        final GameSystemUi gameSystemUi = GameSystemUi.createGameSystemUi( DEFAULT_ID, DEFAULT_NAME, roleUis );
        final int originalRoleUisSize = roleUis.size();

        roleUis.add( null );

        assertEquals( originalRoleUisSize, gameSystemUi.getRoles().size() );
    }

    /**
     * Ensures the {@code createGameSystemUi} method throws an exception when
     * passed a {@code null} role user interface list.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystemUi_RoleUis_Null()
    {
        GameSystemUi.createGameSystemUi( DEFAULT_ID, DEFAULT_NAME, null );
    }
}
