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
 * Created on Mar 11, 2009 at 10:08:53 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.game.core.system.GameSystems;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.ui.system.GameSystemUiUtils} class.
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
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} game system user interface factory.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystemUi_GameSystemUiFactory_Null()
    {
        GameSystemUiUtils.getGameSystemUi( null, "id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystemUi} method does not return {@code null}
     * when passed a known game system identifier.
     */
    @Test
    public void testGetGameSystemUi_Id_Known()
    {
        final GameSystemUiUtils.IGameSystemUiFactory factory = new GameSystemUiUtils.IGameSystemUiFactory()
        {
            public IGameSystemUi getGameSystemUi(
                final String id )
            {
                return GameSystemUis.createGameSystemUi( GameSystems.createGameSystem( id ) );
            }
        };

        assertNotNull( GameSystemUiUtils.getGameSystemUi( factory, "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} game system identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystemUi_Id_Null()
    {
        GameSystemUiUtils.getGameSystemUi( createDummy( GameSystemUiUtils.IGameSystemUiFactory.class ), null );
    }

    /**
     * Ensures the {@code getGameSystemUi} method does not return {@code null}
     * when passed an unknown game system identifier.
     */
    @Test
    public void testGetGameSystemUi_Id_Unknown()
    {
        final GameSystemUiUtils.IGameSystemUiFactory factory = new GameSystemUiUtils.IGameSystemUiFactory()
        {
            public IGameSystemUi getGameSystemUi(
                @SuppressWarnings( "unused" )
                final String id )
            {
                return null;
            }
        };

        assertNotNull( GameSystemUiUtils.getGameSystemUi( factory, "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getRoleUi} method throws an exception when passed a
     * {@code null} game system user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testGetRoleUi_GameSystemUi_Null()
    {
        GameSystemUiUtils.getRoleUi( null, "id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getRoleUi} method does not return {@code null} when
     * passed a known role identifier.
     */
    @Test
    public void testGetRoleUi_Id_Known()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );

        assertNotNull( GameSystemUiUtils.getRoleUi( gameSystemUi, gameSystemUi.getRoles().get( 0 ).getId() ) );
    }

    /**
     * Ensures the {@code getRoleUi} method throws an exception when passed a
     * {@code null} role identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetRoleUi_Id_Null()
    {
        GameSystemUiUtils.getRoleUi( createDummy( IGameSystemUi.class ), null );
    }

    /**
     * Ensures the {@code getRoleUi} method does not return {@code null} when
     * passed an unknown role identifier.
     */
    @Test
    public void testGetRoleUi_Id_Unknown()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );

        assertNotNull( GameSystemUiUtils.getRoleUi( gameSystemUi, "unknown-id" ) ); //$NON-NLS-1$
    }
}
