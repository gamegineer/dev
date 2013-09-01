/*
 * AbstractDragStrategyTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.core.dnd;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.dnd.IDragStrategy} interface.
 */
public abstract class AbstractDragStrategyTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag strategy under test in the fixture. */
    private IDragStrategy dragStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDragStrategyTestCase}
     * class.
     */
    protected AbstractDragStrategyTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the drag strategy to be tested.
     * 
     * @return The drag strategy to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IDragStrategy createDragStrategy()
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
        dragStrategy_ = createDragStrategy();
        assertNotNull( dragStrategy_ );
    }

    /**
     * Ensures the {@link IDragStrategy#canDrop} method throws an exception when
     * passed a {@code null} drop container.
     */
    @Test( expected = NullPointerException.class )
    public void testCanDrop_DropContainer_Null()
    {
        dragStrategy_.canDrop( null );
    }

    /**
     * Ensures the {@link IDragStrategy#getDragComponents} method does not
     * return {@code null}.
     */
    @Test
    public void testGetDragComponents_ReturnValue_NonNull()
    {
        assertNotNull( dragStrategy_.getDragComponents() );
    }
}
