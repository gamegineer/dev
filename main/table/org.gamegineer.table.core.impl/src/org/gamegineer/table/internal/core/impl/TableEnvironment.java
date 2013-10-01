/*
 * TableEnvironment.java
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
 * Created on Jul 6, 2011 at 8:11:30 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.ITableEnvironmentLock;

/**
 * Implementation of {@link ITableEnvironment}.
 */
@ThreadSafe
public final class TableEnvironment
    implements ITableEnvironment
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment context. */
    private final ITableEnvironmentContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironment} class.
     * 
     * @param context
     *        The table environment context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public TableEnvironment(
        /* @NonNull */
        final ITableEnvironmentContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        context_ = context;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createComponent(java.lang.Object)
     */
    @Override
    public IComponent createComponent(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        return ComponentFactory.createComponent( this, memento );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createComponent(org.gamegineer.table.core.IComponentStrategy)
     */
    @Override
    public IComponent createComponent(
        final IComponentStrategy strategy )
    {
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$

        return new Component( this, strategy );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createContainer(org.gamegineer.table.core.IContainerStrategy)
     */
    @Override
    public IContainer createContainer(
        final IContainerStrategy strategy )
    {
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$

        return new Container( this, strategy );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createTable()
     */
    @Override
    public ITable createTable()
    {
        return new Table( this );
    }

    /**
     * Fires the specified event notification.
     * 
     * @param eventNotification
     *        The event notification; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    void fireEventNotification(
        /* @NonNull */
        final Runnable eventNotification )
    {
        assert eventNotification != null;
        assert getLock().isHeldByCurrentThread();

        context_.fireEventNotification( eventNotification );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#getLock()
     */
    @Override
    public ITableEnvironmentLock getLock()
    {
        return context_.getLock();
    }
}
