/*
 * NetworkTableUtils.java
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
 * Created on Jul 15, 2011 at 8:39:13 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
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
            incrementComponentState( component, componentIncrement );
            if( (component instanceof IContainer) && (componentIncrement instanceof ContainerIncrement) )
            {
                incrementContainerState( (IContainer)component, (ContainerIncrement)componentIncrement );
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

        if( componentIncrement.getSurfaceDesigns() != null )
        {
            for( final Map.Entry<ComponentOrientation, ComponentSurfaceDesign> entry : componentIncrement.getSurfaceDesigns().entrySet() )
            {
                component.setSurfaceDesign( entry.getKey(), entry.getValue() );
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

        if( containerIncrement.getLayout() != null )
        {
            container.setLayout( containerIncrement.getLayout() );
        }

        if( containerIncrement.getRemovedComponentCount() != null )
        {
            @SuppressWarnings( "boxing" )
            final int removedComponentCount = containerIncrement.getRemovedComponentCount();
            if( removedComponentCount == container.getComponentCount() )
            {
                container.removeComponents();
            }
            else
            {
                for( int index = 0; index < removedComponentCount; ++index )
                {
                    container.removeComponent();
                }
            }
        }

        if( containerIncrement.getAddedComponentMementos() != null )
        {
            for( final Object componentMemento : containerIncrement.getAddedComponentMementos() )
            {
                try
                {
                    container.addComponent( container.getTableEnvironment().createComponent( componentMemento ) );
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
