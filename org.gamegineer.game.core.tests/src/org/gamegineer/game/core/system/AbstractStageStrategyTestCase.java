/*
 * AbstractStageStrategyTestCase.java
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
 * Created on Aug 4, 2008 at 10:36:07 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.system.IStageStrategy} interface.
 */
public abstract class AbstractStageStrategyTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage strategy under test in the fixture. */
    private IStageStrategy m_strategy;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStageStrategyTestCase}
     * class.
     */
    protected AbstractStageStrategyTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the stage strategy to be tested.
     * 
     * @return The stage strategy to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IStageStrategy createStageStrategy()
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
        m_strategy = createStageStrategy();
        assertNotNull( m_strategy );
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
        m_strategy = null;
    }

    /**
     * Ensures the {@code activate} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testActivate_Context_Null()
        throws Exception
    {
        m_strategy.activate( createDummy( IStage.class ), null );
    }

    /**
     * Ensures the {@code activate} method throws an exception when passed a
     * {@code null} stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testActivate_Stage_Null()
        throws Exception
    {
        m_strategy.activate( null, createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code deactivate} method throws an exception when passed a
     * {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testDeactivate_Context_Null()
        throws Exception
    {
        m_strategy.deactivate( createDummy( IStage.class ), null );
    }

    /**
     * Ensures the {@code deactivate} method throws an exception when passed a
     * {@code null} stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testDeactivate_Stage_Null()
        throws Exception
    {
        m_strategy.deactivate( null, createDummy( IEngineContext.class ) );
    }

    /**
     * Ensures the {@code isComplete} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testIsComplete_Context_Null()
    {
        m_strategy.isComplete( createDummy( IStage.class ), null );
    }

    /**
     * Ensures the {@code isComplete} method throws an exception when passed a
     * {@code null} stage.
     */
    @Test( expected = NullPointerException.class )
    public void testIsComplete_Stage_Null()
    {
        m_strategy.isComplete( null, createDummy( IEngineContext.class ) );
    }
}
