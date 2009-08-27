/*
 * GameSystemTest.java
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
 * Created on Nov 15, 2008 at 11:34:34 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.GameSystem} class.
 */
public final class GameSystemTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default game system identifier. */
    private static final String DEFAULT_ID = "id"; //$NON-NLS-1$

    /** The default role list. */
    private static final List<IRole> DEFAULT_ROLES = GameSystems.createUniqueRoleList();

    /** The default stage list. */
    private static final List<IStage> DEFAULT_STAGES = GameSystems.createUniqueStageList( 0 );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemTest} class.
     */
    public GameSystemTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameSystem} method throws an exception when one
     * or more of its arguments results in an illegal game system.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game system attributes is located in
     * the {@code GameSystemUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameSystem_GameSystem_Illegal()
    {
        final IGameSystem illegalGameSystem = GameSystems.createIllegalGameSystem();

        GameSystem.createGameSystem( illegalGameSystem.getId(), illegalGameSystem.getRoles(), illegalGameSystem.getStages() );
    }

    /**
     * Ensures the {@code createGameSystem} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystem_Id_Null()
    {
        GameSystem.createGameSystem( null, DEFAULT_ROLES, DEFAULT_STAGES );
    }

    /**
     * Ensures the {@code createGameSystem} method makes a copy of the role
     * list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCreateGameSystem_Roles_Copy()
    {
        final List<IRole> roles = GameSystems.createUniqueRoleList();
        final GameSystem gameSystem = GameSystem.createGameSystem( DEFAULT_ID, roles, DEFAULT_STAGES );
        final int originalRolesSize = roles.size();

        roles.add( null );

        assertEquals( originalRolesSize, gameSystem.getRoles().size() );
    }

    /**
     * Ensures the {@code createGameSystem} method throws an exception when
     * passed a {@code null} role list.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystem_Roles_Null()
    {
        GameSystem.createGameSystem( DEFAULT_ID, null, DEFAULT_STAGES );
    }

    /**
     * Ensures the {@code createGameSystem} method makes a copy of the stage
     * list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCreateGameSystem_Stages_Copy()
    {
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );
        final GameSystem gameSystem = GameSystem.createGameSystem( DEFAULT_ID, DEFAULT_ROLES, stages );
        final int originalStagesSize = stages.size();

        stages.add( null );

        assertEquals( originalStagesSize, gameSystem.getStages().size() );
    }

    /**
     * Ensures the {@code createGameSystem} method throws an exception when
     * passed a {@code null} stage list.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameSystem_Stages_Null()
    {
        GameSystem.createGameSystem( DEFAULT_ID, DEFAULT_ROLES, null );
    }
}
