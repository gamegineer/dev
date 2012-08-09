/*
 * AbstractContainerLayoutTestCase.java
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
 * Created on May 5, 2012 at 9:04:16 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import java.awt.Point;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainerLayout} interface.
 */
public abstract class AbstractContainerLayoutTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout under test in the fixture. */
    private IContainerLayout layout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayoutTestCase}
     * class.
     */
    protected AbstractContainerLayoutTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container layout to be tested.
     * 
     * @return The container layout to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IContainerLayout createContainerLayout()
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
        layout_ = createContainerLayout();
        assertNotNull( layout_ );
    }

    /**
     * Ensures the {@code getComponentIndex} method returns {@code -1} when the
     * container is empty.
     */
    @Test
    public void testGetComponentIndex_Container_Empty()
    {
        layout_.getComponentIndex( Components.createUniqueContainer( TableEnvironmentFactory.createTableEnvironment() ), new Point( 0, 0 ) );
    }

    /**
     * Ensures the {@code getComponentIndex} method throws an exception when
     * passed a {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentIndex_Container_Null()
    {
        layout_.getComponentIndex( null, new Point( 0, 0 ) );
    }

    /**
     * Ensures the {@code getComponentIndex} method throws an exception when
     * passed a {@code null} location.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentIndex_Location_Null()
    {
        layout_.getComponentIndex( EasyMock.createMock( IContainer.class ), null );
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( layout_.getId() );
    }

    /**
     * Ensures the {@code layout} method throws an exception when passed a
     * {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testLayout_Container_Null()
    {
        layout_.layout( null );
    }
}
