/*
 * ContainerAsContainerTest.java
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
 * Created on Aug 1, 2012 at 10:08:49 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractContainerTestCase;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.TestComponentStrategies;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.Container} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.table.core.IContainer}
 * interface.
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
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Container createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new Container( tableEnvironment, TestComponentStrategies.createUniqueContainerStrategy() );
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
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireComponentAdded(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentAdded(
        final Container container )
    {
        fireContainerEventWithComponentPathAndComponentAndInteger( container, "fireComponentAdded" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Container component )
    {
        component.fireComponentBoundsChanged( component.getPath() );
    }

    /**
     * Fires the event associated with the specified {@link Component} method
     * that accepts a {@link ComponentPath}.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireComponentEventWithComponentPath(
        /* @NonNull */
        final Container container,
        /* @NonNull */
        final String methodName )
    {
        assert container != null;
        assert methodName != null;

        try
        {
            final Method method = Component.class.getDeclaredMethod( methodName, ComponentPath.class );
            method.setAccessible( true );
            method.invoke( container, container.getPath() );
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
        final Container component )
    {
        fireComponentEventWithComponentPath( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireComponentRemoved(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentRemoved(
        final Container container )
    {
        fireContainerEventWithComponentPathAndComponentAndInteger( container, "fireComponentRemoved" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Container component )
    {
        fireComponentEventWithComponentPath( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified {@link Container} method
     * that accepts a {@link ComponentPath}.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireContainerEventWithComponentPath(
        /* @NonNull */
        final Container container,
        /* @NonNull */
        final String methodName )
    {
        assert container != null;
        assert methodName != null;

        try
        {
            final Method method = Container.class.getDeclaredMethod( methodName, ComponentPath.class );
            method.setAccessible( true );
            method.invoke( container, container.getPath() );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires the event associated with the specified {@link Container} method
     * that accepts an {@link ComponentPath}, {@link IComponent}, and an
     * integer.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireContainerEventWithComponentPathAndComponentAndInteger(
        /* @NonNull */
        final Container container,
        /* @NonNull */
        final String methodName )
    {
        assert container != null;
        assert methodName != null;

        try
        {
            final Method method = Container.class.getDeclaredMethod( methodName, ComponentPath.class, IComponent.class, Integer.TYPE );
            method.setAccessible( true );
            method.invoke( container, container.getPath(), EasyMock.createMock( IComponent.class ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireContainerLayoutChanged(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireContainerLayoutChanged(
        final Container container )
    {
        fireContainerEventWithComponentPath( container, "fireContainerLayoutChanged" ); //$NON-NLS-1$
    }
}
