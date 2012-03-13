/*
 * AbstractPredicateTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 23, 2009 at 10:42:46 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.util.IPredicate} interface.
 */
public abstract class AbstractPredicateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The predicate under test in the fixture. */
    private IPredicate<?> predicate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPredicateTestCase}
     * class.
     */
    protected AbstractPredicateTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the predicate to be tested.
     * 
     * @return The predicate to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IPredicate<?> createPredicate()
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
        predicate_ = createPredicate();
        assertNotNull( predicate_ );
    }

    /**
     * Ensures the {@code evaluate} method does not throw an exception when
     * passed a {@code null} object.
     */
    @Test
    public void testEvaluate_Object_Null()
    {
        predicate_.evaluate( null );
    }
}
