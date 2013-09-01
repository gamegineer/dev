/*
 * AbsoluteLayoutAsAbstractContainerLayoutTest.java
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
 * Created on Nov 30, 2012 at 9:34:16 PM.
 */

package org.gamegineer.table.internal.core.layouts;

import org.gamegineer.table.core.AbstractAbstractContainerLayoutTestCase;
import org.gamegineer.table.core.AbstractContainerLayout;
import org.gamegineer.table.core.ContainerLayoutId;

/**
 * A fixture for testing the {@link AbsoluteLayout} class to ensure it does not
 * violate the contract of the {@link AbstractContainerLayout} class.
 */
public final class AbsoluteLayoutAsAbstractContainerLayoutTest
    extends AbstractAbstractContainerLayoutTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbsoluteLayoutAsAbstractContainerLayoutTest} class.
     */
    public AbsoluteLayoutAsAbstractContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractAbstractContainerLayoutTestCase#createContainerLayout()
     */
    @Override
    protected AbstractContainerLayout createContainerLayout()
    {
        return new AbsoluteLayout( ContainerLayoutId.fromString( "id" ) ); //$NON-NLS-1$
    }
}
