/*
 * ComponentModelAsComponentModelTest.java
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
 * Created on Jun 1, 2012 at 9:45:36 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.TableEnvironmentFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentModel} class.
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
     * @see org.gamegineer.table.internal.ui.model.AbstractComponentModelTestCase#createComponentModel()
     */
    @Override
    protected ComponentModel createComponentModel()
    {
        return new ComponentModel( Cards.createUniqueCard( TableEnvironmentFactory.createTableEnvironment() ) );
    }
}