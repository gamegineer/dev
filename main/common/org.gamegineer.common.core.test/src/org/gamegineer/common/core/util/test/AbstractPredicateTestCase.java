/*
 * AbstractPredicateTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.common.core.util.test;

import java.util.Optional;
import org.gamegineer.common.core.util.IPredicate;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IPredicate} interface.
 */
public abstract class AbstractPredicateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The predicate under test in the fixture. */
    private Optional<IPredicate<?>> predicate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPredicateTestCase}
     * class.
     */
    protected AbstractPredicateTestCase()
    {
        predicate_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the predicate to be tested.
     * 
     * @return The predicate to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IPredicate<?> createPredicate()
        throws Exception;

    /**
     * Gets the predicate under test in the fixture.
     * 
     * @return The predicate under test in the fixture.
     */
    protected final IPredicate<?> getPredicate()
    {
        return predicate_.get();
    }

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
        predicate_ = Optional.of( createPredicate() );
    }

    /**
     * Ensures the {@link IPredicate#evaluate} method does not throw an
     * exception when passed a {@code null} object.
     */
    @Test
    public void testEvaluate_Object_Null()
    {
        getPredicate().evaluate( null );
    }
}
