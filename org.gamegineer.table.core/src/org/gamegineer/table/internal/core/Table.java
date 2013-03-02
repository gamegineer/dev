/*
 * Table.java
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
 * Created on Oct 6, 2009 at 11:09:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IDragContext;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.core.strategies.InternalComponentStrategies;

/**
 * Implementation of {@link org.gamegineer.table.core.ITable}.
 */
@ThreadSafe
final class Table
    implements IComponentParent, ITable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component comparator that orders components by their paths. */
    private static final Comparator<IComponent> COMPONENT_COMPARATOR = new Comparator<IComponent>()
    {
        @Override
        public int compare(
            final IComponent component1,
            final IComponent component2 )
        {
            return component1.getPath().compareTo( component2.getPath() );
        }
    };

    /** The name of the memento attribute that stores the tabletop memento. */
    private static final String TABLETOP_MEMENTO_ATTRIBUTE_NAME = "table.tabletop"; //$NON-NLS-1$

    /** The path to the tabletop component. */
    private static final ComponentPath TABLETOP_PATH = new ComponentPath( null, 0 );

    /**
     * The drag context for the active drag-and-drop operation or {@code null}
     * if a drag-and-drop operation is not active.
     */
    @GuardedBy( "getLock()" )
    private IDragContext dragContext_;

    /** The table environment. */
    private final TableEnvironment tableEnvironment_;

    /** The tabletop. */
    private final Container tabletop_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Table} class.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     */
    Table(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        assert tableEnvironment != null;

        dragContext_ = null;
        tableEnvironment_ = tableEnvironment;
        tabletop_ = new Container( tableEnvironment, InternalComponentStrategies.TABLETOP );

        getLock().lock();
        try
        {
            tabletop_.setParent( this );
        }
        finally
        {
            getLock().unlock();
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITable#beginDrag(java.awt.Point, org.gamegineer.table.core.IComponent)
     */
    @Override
    public IDragContext beginDrag(
        final Point location,
        final IComponent component )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            assertArgumentLegal( component.getContainer() != null, "component", NonNlsMessages.Table_beginDrag_component_noContainer ); //$NON-NLS-1$
            assertArgumentLegal( component.getTable() == this, "component", NonNlsMessages.Table_beginDrag_component_notExists ); //$NON-NLS-1$
            assertStateLegal( !isDragActive(), NonNlsMessages.Table_beginDrag_dragActive );

            dragContext_ = DragContext.beginDrag( this, location, (Component)component );
            return dragContext_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public Object createMemento()
    {
        final Map<String, Object> memento = new HashMap<String, Object>();

        getLock().lock();
        try
        {
            memento.put( TABLETOP_MEMENTO_ATTRIBUTE_NAME, tabletop_.createMemento() );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Ends the active drag-and-drop operation.
     * 
     * <p>
     * This method does nothing if a drag-and-drop operation is not active.
     * </p>
     */
    @GuardedBy( "getLock()" )
    void endDrag()
    {
        assert getLock().isHeldByCurrentThread();

        dragContext_ = null;
    }

    /**
     * Creates a new instance of the {@code Table} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new table; must not be
     *        {@code null}.
     * @param memento
     *        The memento representing the initial table state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Table} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    private static Table fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Table table = new Table( tableEnvironment );

        final Object tabletopMemento = MementoUtils.getAttribute( memento, TABLETOP_MEMENTO_ATTRIBUTE_NAME, Object.class );
        table.tabletop_.setMemento( tabletopMemento );

        return table;
    }

    /*
     * @see org.gamegineer.table.internal.core.IComponentParent#getChildPath(org.gamegineer.table.internal.core.Component)
     */
    @Override
    public ComponentPath getChildPath(
        final Component component )
    {
        assert component != null;

        assert component == tabletop_;
        return TABLETOP_PATH;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getComponent(org.gamegineer.table.core.ComponentPath)
     */
    @Override
    public IComponent getComponent(
        final ComponentPath path )
    {
        assertArgumentNotNull( path, "path" ); //$NON-NLS-1$

        final List<ComponentPath> paths = path.toList();
        final ComponentPath tabletopPath = paths.get( 0 );
        assertArgumentLegal( tabletopPath.getIndex() == 0, "path", NonNlsMessages.Table_getComponent_path_notExists ); //$NON-NLS-1$
        if( paths.size() == 1 )
        {
            return tabletop_;
        }

        getLock().lock();
        try
        {
            return tabletop_.getComponent( paths.subList( 1, paths.size() ) );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ITable#getComponent(java.awt.Point)
     */
    @Override
    public IComponent getComponent(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final List<IComponent> components = getComponents( location );
        return components.isEmpty() ? null : components.get( components.size() - 1 );
    }

    /*
     * @see org.gamegineer.table.core.ITable#getComponents(java.awt.Point)
     */
    @Override
    public List<IComponent> getComponents(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final List<IComponent> components = new ArrayList<IComponent>();

        getLock().lock();
        try
        {
            if( tabletop_.hitTest( location, components ) )
            {
                Collections.sort( components, COMPONENT_COMPARATOR );
            }
        }
        finally
        {
            getLock().unlock();
        }

        return components;
    }

    /**
     * Gets the table environment lock.
     * 
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    private ReentrantLock getLock()
    {
        return tableEnvironment_.getLock();
    }

    /*
     * @see org.gamegineer.table.internal.core.IComponentParent#getTable()
     */
    @Override
    public Table getTable()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getTableEnvironment()
     */
    @Override
    public TableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }

    /*
     * @see org.gamegineer.table.core.ITable#getTabletop()
     */
    @Override
    public IContainer getTabletop()
    {
        return tabletop_;
    }

    /**
     * Indicates a drag-and-drop operation is active.
     * 
     * @return {@code true} if a drag-and-drop operation is active; otherwise
     *         {@code false}.
     */
    @GuardedBy( "getLock()" )
    boolean isDragActive()
    {
        assert getLock().isHeldByCurrentThread();

        return dragContext_ != null;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public void setMemento(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            final Table table = fromMemento( tableEnvironment_, memento );

            tabletop_.setMemento( table.tabletop_.createMemento() );
        }
        finally
        {
            getLock().unlock();
        }
    }
}
