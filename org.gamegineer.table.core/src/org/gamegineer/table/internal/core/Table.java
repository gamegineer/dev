/*
 * Table.java
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
 * Created on Oct 6, 2009 at 11:09:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.core.strategies.ComponentStrategies;

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

    /** The name of the memento attribute that stores the tabletop memento. */
    private static final String TABLETOP_MEMENTO_ATTRIBUTE_NAME = "table.tabletop"; //$NON-NLS-1$

    /** The path to the tabletop component. */
    private static final ComponentPath TABLETOP_PATH = new ComponentPath( null, 0 );

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

        tableEnvironment_ = tableEnvironment;
        tabletop_ = new Container( tableEnvironment, ComponentStrategies.TABLETOP );

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

        getLock().lock();
        try
        {
            return tabletop_.getComponent( location );
        }
        finally
        {
            getLock().unlock();
        }
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
