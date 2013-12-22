/*
 * AbstractContainerStrategyUITest.java
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
 * Created on Mar 29, 2013 at 10:21:34 PM.
 */

package org.gamegineer.table.ui.test;

import org.gamegineer.table.ui.AbstractContainerStrategyUI;
import org.junit.Test;

/**
 * A fixture for testing the {@link AbstractContainerStrategyUI} class.
 */
public final class AbstractContainerStrategyUITest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerStrategyUITest}
     * class.
     */
    public AbstractContainerStrategyUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the
     * {@link AbstractContainerStrategyUI#AbstractContainerStrategyUI}
     * constructor throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new AbstractContainerStrategyUI( null )
        {
            // no overrides
        };
    }
}