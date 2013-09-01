/*
 * DefaultContainerStrategyUIAsContainerStrategyUITest.java
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
 * Created on Sep 29, 2012 at 10:06:33 PM.
 */

package org.gamegineer.table.internal.ui.strategies;

import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.AbstractContainerStrategyUITestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.strategies.DefaultContainerStrategyUI}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.IContainerStrategyUI} interface.
 */
public final class DefaultContainerStrategyUIAsContainerStrategyUITest
    extends AbstractContainerStrategyUITestCase<DefaultContainerStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultContainerStrategyUIAsContainerStrategyUITest} class.
     */
    public DefaultContainerStrategyUIAsContainerStrategyUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractComponentStrategyUITestCase#createComponentStrategyUI()
     */
    @Override
    protected DefaultContainerStrategyUI createComponentStrategyUI()
    {
        return new DefaultContainerStrategyUI( ComponentStrategyId.fromString( "id" ) ); //$NON-NLS-1$
    }
}
