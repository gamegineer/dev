/*
 * TabletopStrategyUIAsContainerStrategyUITest.java
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
 * Created on Sep 29, 2012 at 9:49:14 PM.
 */

package org.gamegineer.table.internal.ui.strategies;

import org.gamegineer.table.ui.AbstractContainerStrategyUITestCase;
import org.gamegineer.table.ui.IContainerStrategyUI;

/**
 * A fixture for testing the {@link TabletopStrategyUI} class to ensure it does
 * not violate the contract of the {@link IContainerStrategyUI} interface.
 */
public final class TabletopStrategyUIAsContainerStrategyUITest
    extends AbstractContainerStrategyUITestCase<TabletopStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TabletopStrategyUIAsContainerStrategyUITest} class.
     */
    public TabletopStrategyUIAsContainerStrategyUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractComponentStrategyUITestCase#createComponentStrategyUI()
     */
    @Override
    protected TabletopStrategyUI createComponentStrategyUI()
    {
        return new TabletopStrategyUI();
    }
}
