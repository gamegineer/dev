/*
 * NetworkTableUtils.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Jul 15, 2011 at 8:39:13 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.awt.Point;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.core.NoSuchContainerLayoutException;
import org.gamegineer.table.internal.net.impl.Loggers;

/**
 * A collection of useful methods for working with network tables.
 */
@ThreadSafe
public final class NetworkTableUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableUtils} class.
     */
    private NetworkTableUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Increments the state of the component at the specified path associated
     * with the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param componentPath
     *        The component path; must not be {@code null}.
     * @param componentIncrement
     *        The incremental change to the state of the component; must not be
     *        {@code null}.
     */
    public static void incrementComponentState(
        final ITable table,
        final ComponentPath componentPath,
        final ComponentIncrement componentIncrement )
    {
        table.getTableEnvironment().getLock().lock();
        try
        {
            final IComponent component = table.getComponent( componentPath );
            if( component != null )
            {
                incrementComponentState( component, componentIncrement );
                if( (component instanceof IContainer) && (componentIncrement instanceof ContainerIncrement) )
                {
                    incrementContainerState( (IContainer)component, (ContainerIncrement)componentIncrement );
                }
            }
        }
        finally
        {
            table.getTableEnvironment().getLock().unlock();
        }
    }

    /**
     * Increments the state of the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * @param componentIncrement
     *        The incremental change to the state of the component; must not be
     *        {@code null}.
     */
    private static void incrementComponentState(
        final IComponent component,
        final ComponentIncrement componentIncrement )
    {
        final Point location = componentIncrement.getLocation();
        if( location != null )
        {
            component.setLocation( location );
        }

        final ComponentOrientation orientation = componentIncrement.getOrientation();
        if( orientation != null )
        {
            component.setOrientation( orientation );
        }

        final Map<ComponentOrientation, ComponentSurfaceDesignId> surfaceDesignIds = componentIncrement.getSurfaceDesignIds();
        if( surfaceDesignIds != null )
        {
            try
            {
                final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<>( surfaceDesignIds.size() );
                for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesignId> entry : surfaceDesignIds.entrySet() )
                {
                    final ComponentSurfaceDesignId surfaceDesignId = entry.getValue();
                    assert surfaceDesignId != null;
                    surfaceDesigns.put( entry.getKey(), ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( surfaceDesignId ) );
                }

                component.setSurfaceDesigns( surfaceDesigns );
            }
            catch( final NoSuchComponentSurfaceDesignException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementComponentState_setSurfaceDesignsFailed, e );
            }
        }
    }

    /**
     * Increments the state of the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * @param containerIncrement
     *        The incremental change to the state of the container; must not be
     *        {@code null}.
     */
    private static void incrementContainerState(
        final IContainer container,
        final ContainerIncrement containerIncrement )
    {
        final ContainerLayoutId layoutId = containerIncrement.getLayoutId();
        if( layoutId != null )
        {
            try
            {
                container.setLayout( ContainerLayoutRegistry.getContainerLayout( layoutId ) );
            }
            catch( final NoSuchContainerLayoutException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementContainerState_setLayoutFailed, e );
            }
        }

        final Integer boxedRemovedComponentIndex = containerIncrement.getRemovedComponentIndex();
        final Integer boxedRemovedComponentCount = containerIncrement.getRemovedComponentCount();
        if( (boxedRemovedComponentIndex != null) && (boxedRemovedComponentCount != null) )
        {
            @SuppressWarnings( "boxing" )
            final int removedComponentIndex = boxedRemovedComponentIndex;
            @SuppressWarnings( "boxing" )
            final int removedComponentCount = boxedRemovedComponentCount;
            if( (removedComponentIndex == 0) && (removedComponentCount == container.getComponentCount()) )
            {
                container.removeAllComponents();
            }
            else
            {
                for( int index = 0; index < removedComponentCount; ++index )
                {
                    container.removeComponent( removedComponentIndex );
                }
            }
        }

        final Integer boxedAddedComponentIndex = containerIncrement.getAddedComponentIndex();
        final List<Object> addedComponentMementos = containerIncrement.getAddedComponentMementos();
        if( (boxedAddedComponentIndex != null) && (addedComponentMementos != null) )
        {
            @SuppressWarnings( "boxing" )
            int addedComponentIndex = boxedAddedComponentIndex;
            for( final Object componentMemento : addedComponentMementos )
            {
                assert componentMemento != null;

                try
                {
                    container.addComponent( container.getTableEnvironment().createComponent( componentMemento ), addedComponentIndex++ );
                }
                catch( final MementoException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementContainerState_setComponentStateFailed, e );
                }
            }
        }
    }

    /**
     * Sets the state of the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param tableMemento
     *        The memento containing the table state; must not be {@code null}.
     */
    public static void setTableState(
        final ITable table,
        final Object tableMemento )
    {
        try
        {
            table.setMemento( tableMemento );
        }
        catch( final MementoException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_setTableState_failed, e );
        }
    }
}
