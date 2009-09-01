/*
 * AbstractStageTestCase.java
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
 * Created on Nov 13, 2008 at 8:28:46 PM.
 */

package org.gamegineer.game.core.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.game.core.system.IStage} interface.
 */
public abstract class AbstractStageTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage under test in the fixture. */
    private IStage m_stage;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStageTestCase} class.
     */
    protected AbstractStageTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the stage to be tested.
     * 
     * @return The stage to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IStage createStage()
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
        m_stage = createStage();
        assertNotNull( m_stage );
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
        m_stage = null;
    }

    /**
     * Ensures the {@code getCardinality} method returns a non-negative number.
     */
    @Test
    public void testGetCardinality_ReturnValue_NonNegative()
    {
        assertTrue( m_stage.getCardinality() >= 0 );
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( m_stage.getId() );
    }

    /**
     * Ensures the {@code getStages} method returns a copy of the stage list.
     */
    @Test
    public void testGetStages_ReturnValue_Copy()
    {
        final List<IStage> stages = m_stage.getStages();
        final int originalStagesSize = stages.size();

        stages.add( null );

        assertEquals( originalStagesSize, m_stage.getStages().size() );
    }

    /**
     * Ensures the {@code getStages} method does not return {@code null}.
     */
    @Test
    public void testGetStages_ReturnValue_NonNull()
    {
        assertNotNull( m_stage.getStages() );
    }

    /**
     * Ensures the {@code getStrategy} method does not return {@code null}.
     */
    @Test
    public void testGetStrategy_ReturnValue_NonNull()
    {
        assertNotNull( m_stage.getStrategy() );
    }
}
