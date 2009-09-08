/*
 * GameAsStateListenerTest.java
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
 * Created on Jul 22, 2008 at 11:11:37 PM.
 */

package org.gamegineer.game.internal.core;

import org.gamegineer.engine.core.extensions.stateeventmediator.AbstractStateListenerTestCase;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.game.core.config.Configurations;
import org.junit.After;
import org.junit.Before;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Game}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener}
 * interface.
 */
public final class GameAsStateListenerTest
    extends AbstractStateListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game under test in the fixture. */
    private Game game_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameAsStateListenerTest} class.
     */
    public GameAsStateListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.AbstractStateListenerTestCase#createStateListener()
     */
    @Override
    protected IStateListener createStateListener()
        throws Exception
    {
        return game_;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.AbstractStateListenerTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        game_ = Game.createGame( Configurations.createMinimalGameConfiguration() );

        super.setUp();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.AbstractStateListenerTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        game_.shutdown();
        game_ = null;
    }
}
