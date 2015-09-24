/*
 * AbstractDragStrategyTestCase.java
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
 * Created on Mar 8, 2013 at 9:59:24 PM.
 */

package org.gamegineer.table.core.dnd.test;

import java.util.Optional;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IDragStrategy} interface.
 */
public abstract class AbstractDragStrategyTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag strategy under test in the fixture. */
    private Optional<IDragStrategy> dragStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragStrategyTestCase}
     * class.
     */
    protected AbstractDragStrategyTestCase()
    {
        dragStrategy_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the drag strategy to be tested.
     * 
     * @return The drag strategy to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IDragStrategy createDragStrategy()
        throws Exception;

    /**
     * Gets the drag strategy under test in the fixture.
     * 
     * @return The drag strategy under test in the fixture.
     */
    protected final IDragStrategy getDragStrategy()
    {
        return dragStrategy_.get();
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
        dragStrategy_ = Optional.of( createDragStrategy() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
