/*
 * DefaultComponentStrategyUIAsComponentStrategyUITest.java
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
 * Created on Sep 29, 2012 at 10:06:19 PM.
 */

package org.gamegineer.table.internal.ui.strategies;

import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.AbstractComponentStrategyUITestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.strategies.DefaultComponentStrategyUI}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.IComponentStrategyUI} interface.
 */
public final class DefaultComponentStrategyUIAsComponentStrategyUITest
    extends AbstractComponentStrategyUITestCase<DefaultComponentStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultComponentStrategyUIAsComponentStrategyUITest} class.
     */
    public DefaultComponentStrategyUIAsComponentStrategyUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractComponentStrategyUITestCase#createComponentStrategyUI()
     */
    @Override
    protected DefaultComponentStrategyUI createComponentStrategyUI()
    {
        return new DefaultComponentStrategyUI( ComponentStrategyId.fromString( "id" ) ); //$NON-NLS-1$
    }
}
