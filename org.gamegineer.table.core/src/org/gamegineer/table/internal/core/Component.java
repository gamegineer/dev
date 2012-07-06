/*
 * Component.java
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
 * Created on Jul 4, 2012 at 8:09:29 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.IComponent;

/**
 * Implementation of {@link org.gamegineer.table.core.IComponent}.
 */
@ThreadSafe
abstract class Component
    implements IComponent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The container that contains this component or {@code null} if this
     * component is not contained in a container.
     */
    @GuardedBy( "getLock()" )
    private Container container_;

    /** The table environment associated with the component. */
    private final TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Component} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the component; must not be
     *        {@code null}.
     */
    Component(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        assert tableEnvironment != null;

        container_ = null;
        tableEnvironment_ = tableEnvironment;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds an event notification to the table environment associated with the
     * component.
     * 
     * @param notification
     *        The event notification; must not be {@code null}.
     */
    final void addEventNotification(
        /* @NonNull */
        final Runnable notification )
    {
        tableEnvironment_.addEventNotification( notification );
    }

    /**
     * Creates a new component from the specified memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial component state; must not be
     *        {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Component fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        if( Card.isMemento( memento ) )
        {
            return Card.fromMemento( tableEnvironment, memento );
        }
        else if( CardPile.isMemento( memento ) )
        {
            return CardPile.fromMemento( tableEnvironment, memento );
        }

        throw new MementoException( NonNlsMessages.Component_fromMemento_unknown );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getContainer()
     */
    @Override
    public final Container getContainer()
    {
        getLock().lock();
        try
        {
            return container_;
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
    final ReentrantLock getLock()
    {
        return tableEnvironment_.getLock();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getTableEnvironment()
     */
    @Override
    public final TableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }

    /**
     * Sets the container that contains this component.
     * 
     * @param container
     *        The container that contains this component or {@code null} if this
     *        component is not contained in a container.
     */
    @GuardedBy( "getLock()" )
    final void setContainer(
        /* @Nullable */
        final Container container )
    {
        assert getLock().isHeldByCurrentThread();

        container_ = container;
    }
}
