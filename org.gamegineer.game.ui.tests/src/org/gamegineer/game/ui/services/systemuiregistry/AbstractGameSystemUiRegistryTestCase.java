/*
 * AbstractGameSystemUiRegistryTestCase.java
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
 * Created on Mar 3, 2009 at 11:01:45 PM.
 */

package org.gamegineer.game.ui.services.systemuiregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.ui.system.GameSystemUis;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry}
 * interface.
 */
public abstract class AbstractGameSystemUiRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface registry under test in the fixture. */
    private IGameSystemUiRegistry m_registry;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameSystemUiRegistryTestCase} class.
     */
    protected AbstractGameSystemUiRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system user interface registry to be tested.
     * 
     * @return The game system user interface registry to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameSystemUiRegistry createGameSystemUiRegistry()
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
        m_registry = createGameSystemUiRegistry();
        assertNotNull( m_registry );
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
        m_registry = null;
    }

    /**
     * Ensures the {@code getGameSystemUi} method returns the correct value when
     * passed a game system identifier that is absent.
     */
    @Test
    public void testGetGameSystemUi_Id_Absent()
    {
        assertNull( m_registry.getGameSystemUi( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystemUi_Id_Null()
    {
        m_registry.getGameSystemUi( null );
    }

    /**
     * Ensures the {@code getGameSystemUi} method returns the correct value when
     * passed a game system identifier that is present.
     */
    @Test
    public void testGetGameSystemUi_Id_Present()
    {
        final IGameSystemUi expectedGameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        m_registry.registerGameSystemUi( expectedGameSystemUi );

        final IGameSystemUi actualGameSystemUi = m_registry.getGameSystemUi( expectedGameSystemUi.getId() );

        assertSame( expectedGameSystemUi, actualGameSystemUi );
    }

    /**
     * Ensures the {@code getGameSystemUis} method returns a copy of the
     * registered game system user interface collection.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_Copy()
    {
        final Collection<IGameSystemUi> gameSystemUis = m_registry.getGameSystemUis();
        final int expectedGameSystemUisSize = gameSystemUis.size();

        gameSystemUis.add( GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() ) );

        assertEquals( expectedGameSystemUisSize, m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code getGameSystemUis} method returns a snapshot of the
     * registered game system user interface collection.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_Snapshot()
    {
        final Collection<IGameSystemUi> gameSystemUis = m_registry.getGameSystemUis();
        m_registry.registerGameSystemUi( GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() ) );

        assertTrue( gameSystemUis.size() != m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code registerGameSystemUi} method throws an exception when
     * passed a {@code null} game system user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterGameSystemUi_GameSystemUi_Null()
    {
        m_registry.registerGameSystemUi( null );
    }

    /**
     * Ensures the {@code registerGameSystemUi} method properly ignores a game
     * system user interface whose identifier has already been registered.
     */
    @Test
    public void testRegisterGameSystemUi_GameSystemUi_Registered_DifferentInstance()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        final int originalGameSystemUiCount = m_registry.getGameSystemUis().size();
        m_registry.registerGameSystemUi( gameSystemUi );

        m_registry.registerGameSystemUi( GameSystemUis.createGameSystemUi( GameSystems.createGameSystem( gameSystemUi.getId() ) ) );

        assertTrue( m_registry.getGameSystemUis().contains( gameSystemUi ) );
        assertEquals( originalGameSystemUiCount + 1, m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code registerGameSystemUi} method properly ignores a game
     * system user interface instance that has already been registered.
     */
    @Test
    public void testRegisterGameSystemUi_GameSystemUi_Registered_SameInstance()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        final int originalGameSystemUiCount = m_registry.getGameSystemUis().size();
        m_registry.registerGameSystemUi( gameSystemUi );

        m_registry.registerGameSystemUi( gameSystemUi );

        assertTrue( m_registry.getGameSystemUis().contains( gameSystemUi ) );
        assertEquals( originalGameSystemUiCount + 1, m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code registerGameSystemUi} method registers an unregistered
     * game system user interface.
     */
    @Test
    public void testRegisterGameSystemUi_GameSystemUi_Unregistered()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );

        m_registry.registerGameSystemUi( gameSystemUi );

        assertTrue( m_registry.getGameSystemUis().contains( gameSystemUi ) );
    }

    /**
     * Ensures the {@code unregisterGameSystemUi} method throws an exception
     * when passed a {@code null} game system user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterGameSystemUi_GameSystemUi_Null()
    {
        m_registry.unregisterGameSystemUi( null );
    }

    /**
     * Ensures the {@code unregisterGameSystemUi} method ignores a game system
     * user interface whose identifier was previously registered but by a
     * different game system user interface instance.
     */
    @Test
    public void testUnregisterGameSystemUi_GameSystemUi_Registered_DifferentInstance()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        final int originalGameSystemUiCount = m_registry.getGameSystemUis().size();
        m_registry.registerGameSystemUi( gameSystemUi );
        assertEquals( originalGameSystemUiCount + 1, m_registry.getGameSystemUis().size() );

        m_registry.unregisterGameSystemUi( GameSystemUis.createGameSystemUi( GameSystems.createGameSystem( gameSystemUi.getId() ) ) );

        assertEquals( originalGameSystemUiCount + 1, m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code unregisterGameSystemUi} method unregisters a
     * previously registered game system user interface.
     */
    @Test
    public void testUnregisterGameSystemUi_GameSystemUi_Registered_SameInstance()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        final int originalGameSystemUiCount = m_registry.getGameSystemUis().size();
        m_registry.registerGameSystemUi( gameSystemUi );
        assertEquals( originalGameSystemUiCount + 1, m_registry.getGameSystemUis().size() );

        m_registry.unregisterGameSystemUi( gameSystemUi );

        assertEquals( originalGameSystemUiCount, m_registry.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code unregisterGameSystemUi} properly ignores a game system
     * user interface that was not previously registered.
     */
    @Test
    public void testUnregisterGameSystemUi_GameSystemUi_Unregistered()
    {
        final IGameSystemUi gameSystemUi = GameSystemUis.createGameSystemUi( GameSystems.createUniqueGameSystem() );
        final int originalGameSystemUiCount = m_registry.getGameSystemUis().size();

        m_registry.unregisterGameSystemUi( gameSystemUi );

        assertEquals( originalGameSystemUiCount, m_registry.getGameSystemUis().size() );
    }
}
