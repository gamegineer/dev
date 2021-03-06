/*
 * AbstractAbstractContainerLayoutTestCase.java
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
 * Created on Nov 30, 2012 at 9:29:30 PM.
 */

package org.gamegineer.table.core.test;

import java.awt.Dimension;
import java.util.Optional;
import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.IContainer;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link AbstractContainerLayout} class.
 * 
 * @param <ContainerLayoutType>
 *        The type of the container layout.
 */
public abstract class AbstractAbstractContainerLayoutTestCase<ContainerLayoutType extends AbstractContainerLayout>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout under test in the fixture. */
    private Optional<ContainerLayoutType> containerLayout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractContainerLayoutTestCase} class.
     */
    protected AbstractAbstractContainerLayoutTestCase()
    {
        containerLayout_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container layout to be tested.
     * 
     * @return The container layout to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ContainerLayoutType createContainerLayout()
        throws Exception;

    /**
     * Gets the offset from the container origin in table coordinates of the
     * component at the specified index for the specified container layout.
     * 
     * @param containerLayout
     *        The container layout.
     * @param container
     *        The container.
     * @param index
     *        The component index.
     * 
     * @return The offset from the container origin in table coordinates of the
     *         component at the specified index for the specified container
     *         layout.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is negative.
     */
    protected abstract Dimension getComponentOffsetAt(
        ContainerLayoutType containerLayout,
        IContainer container,
        int index );

    /**
     * Gets the container layout under test in the fixture.
     * 
     * @return The container layout under test in the fixture.
     */
    protected final ContainerLayoutType getContainerLayout()
    {
        return containerLayout_.get();
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
        containerLayout_ = Optional.of( createContainerLayout() );
    }

    /**
     * Ensures the {@link AbstractContainerLayout#getComponentOffsetAt} method
     * throws an exception when passed an illegal index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetComponentOffsetAt_Index_Illegal_Negative()
    {
        getComponentOffsetAt( getContainerLayout(), EasyMock.createMock( IContainer.class ), -1 );
    }
}
