/*
 * ComponentModelAsComponentModelTest.java
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
 * Created on Jun 1, 2012 at 9:45:36 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import org.gamegineer.table.ui.test.TestComponents;

/**
 * A fixture for testing the {@link ComponentModel} class.
 */
public final class ComponentModelAsComponentModelTest
    extends AbstractComponentModelTestCase<ComponentModel>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentModelAsComponentModelTest} class.
     */
    public ComponentModelAsComponentModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.impl.model.AbstractComponentModelTestCase#createComponentModel(org.gamegineer.table.internal.ui.impl.model.TableEnvironmentModel)
     */
    @Override
    protected ComponentModel createComponentModel(
        final TableEnvironmentModel tableEnvironmentModel )
    {
        return new ComponentModel( tableEnvironmentModel, TestComponents.createUniqueComponent( tableEnvironmentModel.getTableEnvironment() ) );
    }
}
