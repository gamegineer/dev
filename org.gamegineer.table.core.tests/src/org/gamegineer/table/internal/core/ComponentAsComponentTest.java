/*
 * ComponentAsComponentTest.java
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
 * Created on Aug 1, 2012 at 10:08:41 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.gamegineer.table.core.AbstractComponentTestCase;
import org.gamegineer.table.core.ComponentStrategyFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.Component} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.table.core.IComponent}
 * interface.
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
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Component createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new Component( tableEnvironment, ComponentStrategyFactory.createNullComponentStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTableEnvironment()
     */
    @Override
    protected TableEnvironment createTableEnvironment()
    {
        return new TableEnvironment();
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Component component )
    {
        component.fireComponentBoundsChanged();
    }

    /**
     * Fires the event associated with the specified {@link Component} method.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireComponentEvent(
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final String methodName )
    {
        assert component != null;
        assert methodName != null;

        try
        {
            final Method method = Component.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final Component component )
    {
        fireComponentEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Component component )
    {
        fireComponentEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }
}
