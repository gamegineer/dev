/*
 * ContainerModelAsContainerModelTest.java
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
 * Created on Jun 1, 2012 at 9:54:37 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.lang.reflect.Method;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.core.TestComponents;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ContainerModel} class.
 */
public final class ContainerModelAsContainerModelTest
    extends AbstractContainerModelTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerModelAsContainerModelTest} class.
     */
    public ContainerModelAsContainerModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractComponentModelTestCase#createComponentModel()
     */
    @Override
    protected ContainerModel createComponentModel()
    {
        return new ContainerModel( TestComponents.createUniqueContainer( TableEnvironmentFactory.createTableEnvironment() ) );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractContainerModelTestCase#fireContainerChangedEvent(org.gamegineer.table.internal.ui.model.ContainerModel)
     */
    @Override
    protected void fireContainerChangedEvent(
        final ContainerModel containerModel )
    {
        try
        {
            final Method method = ContainerModel.class.getDeclaredMethod( "fireContainerChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( containerModel );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
