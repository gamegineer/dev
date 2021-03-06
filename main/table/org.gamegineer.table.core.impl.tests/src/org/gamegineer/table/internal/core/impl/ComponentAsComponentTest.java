/*
 * ComponentAsComponentTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Aug 1, 2012 at 10:08:41 PM.
 */

package org.gamegineer.table.internal.core.impl;

import java.lang.reflect.Method;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.test.AbstractComponentTestCase;
import org.gamegineer.table.core.test.TestComponentStrategies;

/**
 * A fixture for testing the {@link Component} class to ensure it does not
 * violate the contract of the {@link IComponent} interface.
 */
public final class ComponentAsComponentTest
    extends AbstractComponentTestCase<TableEnvironment, Component>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentAsComponentTest} class.
     */
    public ComponentAsComponentTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Component createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new Component( tableEnvironment, TestComponentStrategies.createUniqueComponentStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#createTableEnvironment(org.gamegineer.table.core.ITableEnvironmentContext)
     */
    @Override
    protected TableEnvironment createTableEnvironment(
        final ITableEnvironmentContext context )
    {
        return new TableEnvironment( context );
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Component component )
    {
        component.getLock().lock();
        try
        {
            component.fireComponentBoundsChanged();
        }
        finally
        {
            component.getLock().unlock();
        }
    }

    /**
     * Fires the component event associated with the specified {@link Component}
     * method.
     * 
     * @param component
     *        The component.
     * @param methodName
     *        The name of the method associated with the event.
     */
    private static void fireComponentEvent(
        final Component component,
        final String methodName )
    {
        try
        {
            final Method method = Component.class.getDeclaredMethod( methodName );
            method.setAccessible( true );

            component.getLock().lock();
            try
            {
                method.invoke( component );
            }
            finally
            {
                component.getLock().unlock();
            }
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final Component component )
    {
        fireComponentEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Component component )
    {
        fireComponentEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }
}
