/*
 * AbstractGameSystemRegistryTestCase.java
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
 * Created on Feb 16, 2009 at 10:34:01 PM.
 */

package org.gamegineer.game.core.services.systemregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry}
 * interface.
 */
public abstract class AbstractGameSystemRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system registry under test in the fixture. */
    private IGameSystemRegistry registry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameSystemRegistryTestCase} class.
     */
    protected AbstractGameSystemRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system registry to be tested.
     * 
     * @return The game system registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameSystemRegistry createGameSystemRegistry()
        throws Exception;

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
        registry_ = createGameSystemRegistry();
        assertNotNull( registry_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        registry_ = null;
    }

    /**
     * Ensures the {@code getGameSystem} method returns the correct value when
     * passed a game system identifier that is absent.
     */
    @Test
    public void testGetGameSystem_Id_Absent()
    {
        assertNull( registry_.getGameSystem( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystem} method throws an exception when passed
     * a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystem_Id_Null()
    {
        registry_.getGameSystem( null );
    }

    /**
     * Ensures the {@code getGameSystem} method returns the correct value when
     * passed a game system identifier that is present.
     */
    @Test
    public void testGetGameSystem_Id_Present()
    {
        final IGameSystem expectedGameSystem = GameSystems.createUniqueGameSystem();
        registry_.registerGameSystem( expectedGameSystem );

        final IGameSystem actualGameSystem = registry_.getGameSystem( expectedGameSystem.getId() );

        assertSame( expectedGameSystem, actualGameSystem );
    }

    /**
     * Ensures the {@code getGameSystems} method returns a copy of the
     * registered game system collection.
     */
    @Test
    public void testGetGameSystems_ReturnValue_Copy()
    {
        final Collection<IGameSystem> gameSystems = registry_.getGameSystems();
        final int expectedGameSystemsSize = gameSystems.size();

        gameSystems.add( GameSystems.createUniqueGameSystem() );

        assertEquals( expectedGameSystemsSize, registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code getGameSystems} method returns a snapshot of the
     * registered game system collection.
     */
    @Test
    public void testGetGameSystems_ReturnValue_Snapshot()
    {
        final Collection<IGameSystem> gameSystems = registry_.getGameSystems();
        registry_.registerGameSystem( GameSystems.createUniqueGameSystem() );

        assertTrue( gameSystems.size() != registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code registerGameSystem} method throws an exception when
     * passed a {@code null} game system.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterGameSystem_GameSystem_Null()
    {
        registry_.registerGameSystem( null );
    }

    /**
     * Ensures the {@code registerGameSystem} method properly ignores a game
     * system whose identifier has already been registered.
     */
    @Test
    public void testRegisterGameSystem_GameSystem_Registered_DifferentInstance()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final int originalGameSystemCount = registry_.getGameSystems().size();
        registry_.registerGameSystem( gameSystem );

        registry_.registerGameSystem( GameSystems.createGameSystem( gameSystem.getId() ) );

        assertTrue( registry_.getGameSystems().contains( gameSystem ) );
        assertEquals( originalGameSystemCount + 1, registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code registerGameSystem} method properly ignores a game
     * system instance that has already been registered.
     */
    @Test
    public void testRegisterGameSystem_GameSystem_Registered_SameInstance()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final int originalGameSystemCount = registry_.getGameSystems().size();
        registry_.registerGameSystem( gameSystem );

        registry_.registerGameSystem( gameSystem );

        assertTrue( registry_.getGameSystems().contains( gameSystem ) );
        assertEquals( originalGameSystemCount + 1, registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code registerGameSystem} method registers an unregistered
     * game system.
     */
    @Test
    public void testRegisterGameSystem_GameSystem_Unregistered()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();

        registry_.registerGameSystem( gameSystem );

        assertTrue( registry_.getGameSystems().contains( gameSystem ) );
    }

    /**
     * Ensures the {@code unregisterGameSystem} method throws an exception when
     * passed a {@code null} game system.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterGameSystem_GameSystem_Null()
    {
        registry_.unregisterGameSystem( null );
    }

    /**
     * Ensures the {@code unregisterGameSystem} method ignores a game system
     * whose identifier was previously registered but by a different game system
     * instance.
     */
    @Test
    public void testUnregisterGameSystem_GameSystem_Registered_DifferentInstance()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final int originalGameSystemCount = registry_.getGameSystems().size();
        registry_.registerGameSystem( gameSystem );
        assertEquals( originalGameSystemCount + 1, registry_.getGameSystems().size() );

        registry_.unregisterGameSystem( GameSystems.createGameSystem( gameSystem.getId() ) );

        assertEquals( originalGameSystemCount + 1, registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code unregisterGameSystem} method unregisters a previously
     * registered game system.
     */
    @Test
    public void testUnregisterGameSystem_GameSystem_Registered_SameInstance()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final int originalGameSystemCount = registry_.getGameSystems().size();
        registry_.registerGameSystem( gameSystem );
        assertEquals( originalGameSystemCount + 1, registry_.getGameSystems().size() );

        registry_.unregisterGameSystem( gameSystem );

        assertEquals( originalGameSystemCount, registry_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code unregisterGameSystem} properly ignores a game system
     * that was not previously registered.
     */
    @Test
    public void testUnregisterGameSystem_GameSystem_Unregistered()
    {
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final int originalGameSystemCount = registry_.getGameSystems().size();

        registry_.unregisterGameSystem( gameSystem );

        assertEquals( originalGameSystemCount, registry_.getGameSystems().size() );
    }
}
