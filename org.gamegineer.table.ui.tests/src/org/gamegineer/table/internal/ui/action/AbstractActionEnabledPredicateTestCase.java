/*
 * AbstractActionEnabledPredicateTestCase.java
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
 * Created on Oct 20, 2009 at 11:31:08 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.ui.action.IActionEnabledPredicate}
 * interface.
 */
public abstract class AbstractActionEnabledPredicateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action enabled predicate under test in the fixture. */
    private IActionEnabledPredicate predicate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractActionEnabledPredicateTestCase} class.
     */
    protected AbstractActionEnabledPredicateTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the action enabled predicate to be tested.
     * 
     * @return The action enabled predicate to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IActionEnabledPredicate createActionEnabledPredicate()
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
        predicate_ = createActionEnabledPredicate();
        assertNotNull( predicate_ );
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
        predicate_ = null;
    }

    /**
     * Ensures the {@code isActionEnabled} throws an exception when passed a
     * {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testIsActionEnabled_Action_Null()
    {
        predicate_.isActionEnabled( null );
    }
}
