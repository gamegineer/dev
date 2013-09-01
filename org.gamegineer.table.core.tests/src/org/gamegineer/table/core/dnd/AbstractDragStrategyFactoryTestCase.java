/*
 * AbstractDragStrategyFactoryTestCase.java
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
 * Created on Mar 9, 2013 at 8:58:32 PM.
 */

package org.gamegineer.table.core.dnd;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.table.core.IComponent;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.dnd.IDragStrategyFactory} interface.
 */
public abstract class AbstractDragStrategyFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The drag strategy factory under test in the fixture. */
    private IDragStrategyFactory dragStrategyFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractDragStrategyFactoryTestCase} class.
     */
    protected AbstractDragStrategyFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the drag strategy factory to be tested.
     * 
     * @return The drag strategy factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IDragStrategyFactory createDragStrategyFactory()
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
        dragStrategyFactory_ = createDragStrategyFactory();
        assertNotNull( dragStrategyFactory_ );
    }

    /**
     * Ensures the {@link IDragStrategyFactory#createDragStrategy} method throws
     * an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateDragStrategy_Component_Null()
    {
        dragStrategyFactory_.createDragStrategy( null, EasyMock.createMock( IDragStrategy.class ) );
    }

    /**
     * Ensures the {@link IDragStrategyFactory#createDragStrategy} method throws
     * an exception when passed a {@code null} successor drag strategy.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateDragStrategy_SuccessorDragStrategy_Null()
    {
        dragStrategyFactory_.createDragStrategy( EasyMock.createMock( IComponent.class ), null );
    }
}
