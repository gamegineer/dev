/*
 * AbstractGameSystemSourceTestCase.java
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
 * Created on Dec 1, 2008 at 10:31:11 PM.
 */

package org.gamegineer.server.core.system;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Collection;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.server.core.system.IGameSystemSource} interface.
 */
public abstract class AbstractGameSystemSourceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system source under test in the fixture. */
    private IGameSystemSource m_source;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameSystemSourceTestCase} class.
     */
    protected AbstractGameSystemSourceTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system source to be tested.
     * 
     * @return The game system source to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameSystemSource createGameSystemSource()
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
        m_source = createGameSystemSource();
        assertNotNull( m_source );
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
        m_source = null;
    }

    /**
     * Ensures the {@code getGameSystems} method returns a copy of the game
     * system collection.
     */
    @Test
    public void testGetGameSystems_ReturnValue_Copy()
    {
        final Collection<IGameSystem> gameSystems = m_source.getGameSystems();
        final int expectedGameSystemsSize = gameSystems.size();

        gameSystems.add( createDummy( IGameSystem.class ) );

        assertEquals( expectedGameSystemsSize, m_source.getGameSystems().size() );
    }

    /**
     * Ensures the {@code getGameSystems} method does not return {@code null}.
     */
    @Test
    public void testGetGameSystems_ReturnValue_NonNull()
    {
        assertNotNull( m_source.getGameSystems() );
    }
}
