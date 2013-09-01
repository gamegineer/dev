/*
 * AbstractAbstractContainerLayoutTestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Nov 30, 2012 at 9:29:30 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.core.AbstractContainerLayout} class.
 */
public abstract class AbstractAbstractContainerLayoutTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout under test in the fixture. */
    private AbstractContainerLayout containerLayout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractContainerLayoutTestCase} class.
     */
    protected AbstractAbstractContainerLayoutTestCase()
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
    protected abstract AbstractContainerLayout createContainerLayout()
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
        containerLayout_ = createContainerLayout();
        assertNotNull( containerLayout_ );
    }

    /**
     * Ensures the {@link AbstractContainerLayout#getComponentOffsetAt} method
     * throws an exception when passed a {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentOffsetAt_Container_Null()
    {
        containerLayout_.getComponentOffsetAt( null, 0 );
    }

    /**
     * Ensures the {@link AbstractContainerLayout#getComponentOffsetAt} method
     * throws an exception when passed an illegal index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponentOffsetAt_Index_Illegal_Negative()
    {
        containerLayout_.getComponentOffsetAt( EasyMock.createMock( IContainer.class ), -1 );
    }
}
