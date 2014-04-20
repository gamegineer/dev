/*
 * AbstractPlayerTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Aug 10, 2011 at 7:11:28 PM.
 */

package org.gamegineer.table.net.test;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.EnumSet;
import java.util.Set;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IPlayer} interface.
 */
@NonNullByDefault( false )
public abstract class AbstractPlayerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player under test in the fixture. */
    private IPlayer player_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPlayerTestCase} class.
     */
    protected AbstractPlayerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the player to be tested.
     * 
     * @return The player to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract IPlayer createPlayer()
        throws Exception;

    /**
     * Gets the player under test in the fixture.
     * 
     * @return The player under test in the fixture; never {@code null}.
     */
    @NonNull
    private IPlayer getPlayer()
    {
        assertNotNull( player_ );
        return player_;
    }

    /**
     * Sets the roles for the specified player.
     * 
     * @param player
     *        The player; must not be {@code null}.
     * @param playerRoles
     *        The collection of player roles; must not be {@code null}.
     */
    protected abstract void setPlayerRoles(
        @NonNull
        IPlayer player,
        @NonNull
        Set<PlayerRole> playerRoles );

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        player_ = createPlayer();
        assertNotNull( player_ );
    }

    /**
     * Ensures the {@link IPlayer#getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( getPlayer().getName() );
    }

    /**
     * Ensures the {@link IPlayer#getRoles} method returns a copy of the roles
     * collection.
     */
    @Test
    public void testGetRoles_ReturnValue_Copy()
    {
        assertNotSame( getPlayer().getRoles(), getPlayer().getRoles() );
    }

    /**
     * Ensures the {@link IPlayer#getRoles} method does not return {@code null}.
     */
    @Test
    public void testGetRoles_ReturnValue_NonNull()
    {
        assertNotNull( getPlayer().getRoles() );
    }

    /**
     * Ensures the {@link IPlayer#hasRole} method correctly indicates a player
     * role is absent.
     */
    @Test
    public void testHasRole_Role_Absent()
    {
        setPlayerRoles( getPlayer(), nonNull( EnumSet.complementOf( EnumSet.of( PlayerRole.LOCAL ) ) ) );

        assertFalse( getPlayer().hasRole( PlayerRole.LOCAL ) );
    }

    /**
     * Ensures the {@link IPlayer#hasRole} method correctly indicates a player
     * role is present.
     */
    @Test
    public void testHasRole_Role_Present()
    {
        setPlayerRoles( getPlayer(), nonNull( EnumSet.allOf( PlayerRole.class ) ) );

        assertTrue( getPlayer().hasRole( PlayerRole.LOCAL ) );
    }
}
