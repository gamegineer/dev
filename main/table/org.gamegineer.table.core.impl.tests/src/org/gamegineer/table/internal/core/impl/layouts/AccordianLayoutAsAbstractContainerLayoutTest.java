/*
 * AccordianLayoutAsAbstractContainerLayoutTest.java
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
 * Created on Nov 30, 2012 at 9:35:47 PM.
 */

package org.gamegineer.table.internal.core.impl.layouts;

import java.awt.Dimension;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase;

/**
 * A fixture for testing the {@link AccordianLayout} class to ensure it does not
 * violate the contract of the {@link AbstractContainerLayout} class.
 */
public final class AccordianLayoutAsAbstractContainerLayoutTest
    extends AbstractAbstractContainerLayoutTestCase<AccordianLayout>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AccordianLayoutAsAbstractContainerLayoutTest} class.
     */
    public AccordianLayoutAsAbstractContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase#createContainerLayout()
     */
    @Override
    protected AccordianLayout createContainerLayout()
    {
        return new AccordianLayout( ContainerLayoutId.fromString( "id" ), 1, 1 ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractAbstractContainerLayoutTestCase#getComponentOffsetAt(org.gamegineer.table.core.AbstractContainerLayout, org.gamegineer.table.core.IContainer, int)
     */
    @Override
    protected Dimension getComponentOffsetAt(
        final AccordianLayout containerLayout,
        final IContainer container,
        final int index )
    {
        return containerLayout.getComponentOffsetAt( container, index );
    }
}
