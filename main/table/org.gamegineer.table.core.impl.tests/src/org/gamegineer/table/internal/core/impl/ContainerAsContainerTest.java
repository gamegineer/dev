/*
 * ContainerAsContainerTest.java
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
 * Created on Aug 1, 2012 at 10:08:49 PM.
 */

package org.gamegineer.table.internal.core.impl;

import java.lang.reflect.Method;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.test.AbstractContainerTestCase;
import org.gamegineer.table.core.test.TestComponentStrategies;

/**
 * A fixture for testing the {@link Container} class to ensure it does not
 * violate the contract of the {@link IContainer} interface.
 */
public final class ContainerAsContainerTest
    extends AbstractContainerTestCase<TableEnvironment, Container>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerAsContainerTest} class.
     */
    public ContainerAsContainerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Container createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new Container( tableEnvironment, TestComponentStrategies.createUniqueContainerStrategy() );
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
     * @see org.gamegineer.table.core.test.AbstractContainerTestCase#fireComponentAdded(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentAdded(
        final Container container )
    {
        fireContainerContentChangedEvent( container, "fireComponentAdded" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Container component )
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
     * @param container
     *        The container.
     * @param methodName
     *        The name of the method associated with the event.
     */
    private static void fireComponentEvent(
        final Container container,
        final String methodName )
    {
        try
        {
            final Method method = Component.class.getDeclaredMethod( methodName );
            method.setAccessible( true );

            container.getLock().lock();
            try
            {
                method.invoke( container );
            }
            finally
            {
                container.getLock().unlock();
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
        final Container component )
    {
        fireComponentEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractContainerTestCase#fireComponentRemoved(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentRemoved(
        final Container container )
    {
        fireContainerContentChangedEvent( container, "fireComponentRemoved" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Container component )
    {
        fireComponentEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the container content changed event associated with the specified
     * {@link Container} method.
     * 
     * @param container
     *        The container.
     * @param methodName
     *        The name of the method associated with the event.
     */
    private static void fireContainerContentChangedEvent(
        final Container container,
        final String methodName )
    {
        try
        {
            final Method method = Container.class.getDeclaredMethod( methodName, Component.class, int.class );
            method.setAccessible( true );

            container.getLock().lock();
            try
            {
                method.invoke( container, new Component( container.getTableEnvironment(), ComponentStrategies.NULL_COMPONENT ), Integer.valueOf( 0 ) );
            }
            finally
            {
                container.getLock().unlock();
            }
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires the container event associated with the specified {@link Container}
     * method.
     * 
     * @param container
     *        The container.
     * @param methodName
     *        The name of the method associated with the event.
     */
    private static void fireContainerEvent(
        final Container container,
        final String methodName )
    {
        try
        {
            final Method method = Container.class.getDeclaredMethod( methodName );
            method.setAccessible( true );

            container.getLock().lock();
            try
            {
                method.invoke( container );
            }
            finally
            {
                container.getLock().unlock();
            }
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractContainerTestCase#fireContainerLayoutChanged(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireContainerLayoutChanged(
        final Container container )
    {
        fireContainerEvent( container, "fireContainerLayoutChanged" ); //$NON-NLS-1$
    }
}
