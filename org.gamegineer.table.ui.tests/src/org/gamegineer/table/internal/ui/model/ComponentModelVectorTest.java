/*
 * ComponentModelVectorTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jun 4, 2013 at 8:16:37 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.ui.TestComponents;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModelVector} class.
 */
public final class ComponentModelVectorTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelVectorTest} class.
     */
    public ComponentModelVectorTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentModelVector#ComponentModelVector} constructor
     * throws an exception when passed a {@code null} direction.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Direction_Null()
    {
        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TableEnvironmentFactory.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );

        new ComponentModelVector( tableEnvironmentModel.createComponentModel( TestComponents.createUniqueComponent( tableEnvironmentModel.getTableEnvironment() ) ), null );
    }

    /**
     * Ensures the {@link ComponentModelVector#ComponentModelVector} constructor
     * throws an exception when passed a {@code null} origin.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Origin_Null()
    {
        new ComponentModelVector( null, ComponentAxis.PRECEDING );
    }
}
