/*
 * NullContainerLayoutAsContainerLayoutTest.java
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
 * Created on Oct 3, 2013 at 10:53:15 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.table.core.test.AbstractContainerLayoutTestCase;

/**
 * A fixture for testing the {@link ContainerLayouts#NULL} singleton to ensure
 * it does not violate the contract of the {@link IContainerLayout} interface.
 */
public final class NullContainerLayoutAsContainerLayoutTest
    extends AbstractContainerLayoutTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code NullContainerLayoutAsContainerLayoutTest} class.
     */
    public NullContainerLayoutAsContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractContainerLayoutTestCase#createContainerLayout()
     */
    @Override
    protected IContainerLayout createContainerLayout()
    {
        return ContainerLayouts.NULL;
    }
}
