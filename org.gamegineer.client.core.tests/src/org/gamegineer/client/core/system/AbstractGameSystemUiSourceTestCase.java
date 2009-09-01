/*
 * AbstractGameSystemUiSourceTestCase.java
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
 * Created on Mar 6, 2009 at 11:20:00 PM.
 */

package org.gamegineer.client.core.system;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Collection;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.core.system.IGameSystemUiSource} interface.
 */
public abstract class AbstractGameSystemUiSourceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface source under test in the fixture. */
    private IGameSystemUiSource m_source;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractGameSystemUiSourceTestCase} class.
     */
    protected AbstractGameSystemUiSourceTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game system user interface source to be tested.
     * 
     * @return The game system user interface source to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IGameSystemUiSource createGameSystemUiSource()
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
        m_source = createGameSystemUiSource();
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
     * Ensures the {@code getGameSystemUis} method returns a copy of the game
     * system user interface collection.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_Copy()
    {
        final Collection<IGameSystemUi> gameSystemUis = m_source.getGameSystemUis();
        final int expectedGameSystemUisSize = gameSystemUis.size();

        gameSystemUis.add( createDummy( IGameSystemUi.class ) );

        assertEquals( expectedGameSystemUisSize, m_source.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code getGameSystemUis} method does not return {@code null}.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_NonNull()
    {
        assertNotNull( m_source.getGameSystemUis() );
    }
}
