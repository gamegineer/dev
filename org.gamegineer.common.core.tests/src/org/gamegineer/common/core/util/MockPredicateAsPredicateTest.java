/*
 * MockPredicateAsPredicateTest.java
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
 * Created on Oct 23, 2009 at 11:31:00 PM.
 */

package org.gamegineer.common.core.util;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.util.MockPredicate} class to ensure it does
 * not violate the contract of the
 * {@link org.gamegineer.common.core.util.IPredicate} interface.
 */
public final class MockPredicateAsPredicateTest
    extends AbstractPredicateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockPredicateAsPredicateTest}
     * class.
     */
    public MockPredicateAsPredicateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.AbstractPredicateTestCase#createPredicate()
     */
    @Override
    protected IPredicate<?> createPredicate()
    {
        return new MockPredicate<Object>();
    }
}
