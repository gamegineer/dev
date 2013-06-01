/*
 * ContainerModelAsContainerModelTest.java
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
 * Created on Jun 1, 2012 at 9:54:37 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.lang.reflect.Method;
import org.gamegineer.table.ui.TestComponents;

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
     * @see org.gamegineer.table.internal.ui.model.AbstractComponentModelTestCase#createComponentModel(org.gamegineer.table.internal.ui.model.TableEnvironmentModel)
     */
    @Override
    protected ContainerModel createComponentModel(
        final TableEnvironmentModel tableEnvironmentModel )
    {
        return new ContainerModel( tableEnvironmentModel, TestComponents.createUniqueContainer( tableEnvironmentModel.getTableEnvironment() ) );
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractContainerModelTestCase#fireComponentModelAddedEvent(org.gamegineer.table.internal.ui.model.ContainerModel)
     */
    @Override
    protected void fireComponentModelAddedEvent(
        final ContainerModel containerModel )
    {
        fireContainerModelEventWithComponentModelAndInteger( containerModel, "fireComponentModelAdded" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractContainerModelTestCase#fireComponentModelRemovedEvent(org.gamegineer.table.internal.ui.model.ContainerModel)
     */
    @Override
    protected void fireComponentModelRemovedEvent(
        final ContainerModel containerModel )
    {
        fireContainerModelEventWithComponentModelAndInteger( containerModel, "fireComponentModelRemoved" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractContainerModelTestCase#fireContainerLayoutChangedEvent(org.gamegineer.table.internal.ui.model.ContainerModel)
     */
    @Override
    protected void fireContainerLayoutChangedEvent(
        final ContainerModel containerModel )
    {
        fireContainerModelEvent( containerModel, "fireContainerLayoutChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified {@link ContainerModel}
     * method.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireContainerModelEvent(
        /* @NonNull */
        final ContainerModel containerModel,
        /* @NonNull */
        final String methodName )
    {
        assert containerModel != null;
        assert methodName != null;

        try
        {
            final Method method = ContainerModel.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( containerModel );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires the event associated with the specified {@link ContainerModel}
     * method that accepts a {@link ComponentModel} and an integer.
     * 
     * @param containerModel
     *        The container model; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireContainerModelEventWithComponentModelAndInteger(
        /* @NonNull */
        final ContainerModel containerModel,
        /* @NonNull */
        final String methodName )
    {
        assert containerModel != null;
        assert methodName != null;

        try
        {
            final Method method = ContainerModel.class.getDeclaredMethod( methodName, ComponentModel.class, Integer.TYPE );
            method.setAccessible( true );
            method.invoke( containerModel, new ComponentModel( containerModel.getTableEnvironmentModel(), TestComponents.createUniqueComponent( containerModel.getTableEnvironmentModel().getTableEnvironment() ) ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
