/*
 * NetworkTableUtils.java
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
 * Created on Jul 15, 2011 at 8:39:13 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.core.NoSuchContainerLayoutException;
import org.gamegineer.table.internal.net.Loggers;

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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table}, {@code componentPath}, or
     *         {@code componentIncrement} is {@code null}.
     */
    public static void incrementComponentState(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ComponentPath componentPath,
        /* @NonNull */
        final ComponentIncrement componentIncrement )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( componentPath, "componentPath" ); //$NON-NLS-1$
        assertArgumentNotNull( componentIncrement, "componentIncrement" ); //$NON-NLS-1$

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
        /* @NonNull */
        final IComponent component,
        /* @NonNull */
        final ComponentIncrement componentIncrement )
    {
        assert component != null;
        assert componentIncrement != null;

        if( componentIncrement.getLocation() != null )
        {
            component.setLocation( componentIncrement.getLocation() );
        }

        if( componentIncrement.getOrientation() != null )
        {
            component.setOrientation( componentIncrement.getOrientation() );
        }

        if( componentIncrement.getSurfaceDesignIds() != null )
        {
            try
            {
                final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<>( componentIncrement.getSurfaceDesignIds().size() );
                for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesignId> entry : componentIncrement.getSurfaceDesignIds().entrySet() )
                {
                    surfaceDesigns.put( entry.getKey(), ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( entry.getValue() ) );
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
        /* @NonNull */
        final IContainer container,
        /* @NonNull */
        final ContainerIncrement containerIncrement )
    {
        assert container != null;
        assert containerIncrement != null;

        if( containerIncrement.getLayoutId() != null )
        {
            try
            {
                container.setLayout( ContainerLayoutRegistry.getContainerLayout( containerIncrement.getLayoutId() ) );
            }
            catch( final NoSuchContainerLayoutException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementContainerState_setLayoutFailed, e );
            }
        }

        if( (containerIncrement.getRemovedComponentIndex() != null) && (containerIncrement.getRemovedComponentCount() != null) )
        {
            @SuppressWarnings( "boxing" )
            final int removedComponentIndex = containerIncrement.getRemovedComponentIndex();
            @SuppressWarnings( "boxing" )
            final int removedComponentCount = containerIncrement.getRemovedComponentCount();
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

        if( (containerIncrement.getAddedComponentIndex() != null) && (containerIncrement.getAddedComponentMementos() != null) )
        {
            @SuppressWarnings( "boxing" )
            int addedComponentIndex = containerIncrement.getAddedComponentIndex();
            for( final Object componentMemento : containerIncrement.getAddedComponentMementos() )
            {
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code tableMemento} is {@code null}.
     */
    public static void setTableState(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final Object tableMemento )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

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