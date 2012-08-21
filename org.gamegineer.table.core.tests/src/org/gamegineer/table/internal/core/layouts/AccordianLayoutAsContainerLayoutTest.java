/*
 * AccordianLayoutAsContainerLayoutTest.java
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
 * Created on May 5, 2012 at 9:50:33 PM.
 */

package org.gamegineer.table.internal.core.layouts;

import org.gamegineer.table.core.AbstractContainerLayoutTestCase;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.layouts.AccordianLayout} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.IContainerLayout} interface.
 */
public final class AccordianLayoutAsContainerLayoutTest
    extends AbstractContainerLayoutTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AccordianLayoutAsContainerLayoutTest} class.
     */
    public AccordianLayoutAsContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerLayoutTestCase#createContainerLayout()
     */
    @Override
    protected IContainerLayout createContainerLayout()
    {
        return new AccordianLayout( ContainerLayoutId.fromString( "id" ), 1, 1 ); //$NON-NLS-1$
    }
}
