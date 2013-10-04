/*
 * NullContainerLayoutAsAbstractContainerLayoutTest.java
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
 * Created on Oct 3, 2013 at 10:52:50 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase;

/**
 * A fixture for testing the {@link ContainerLayouts.NullContainerLayout} class
 * to ensure it does not violate the contract of the
 * {@link AbstractContainerLayout} class.
 */
public final class NullContainerLayoutAsAbstractContainerLayoutTest
    extends AbstractAbstractContainerLayoutTestCase<ContainerLayouts.NullContainerLayout>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code NullContainerLayoutAsAbstractContainerLayoutTest} class.
     */
    public NullContainerLayoutAsAbstractContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase#createContainerLayout()
     */
    @Override
    protected ContainerLayouts.NullContainerLayout createContainerLayout()
    {
        return new ContainerLayouts.NullContainerLayout();
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase#getComponentOffsetAt(org.gamegineer.table.core.AbstractContainerLayout, org.gamegineer.table.core.IContainer, int)
     */
    @Override
    protected Dimension getComponentOffsetAt(
        final ContainerLayouts.NullContainerLayout containerLayout,
        final IContainer container,
        final int index )
    {
        return containerLayout.getComponentOffsetAt( container, index );
    }
}
