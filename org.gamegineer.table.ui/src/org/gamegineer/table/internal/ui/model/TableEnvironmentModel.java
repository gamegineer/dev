/*
 * TableEnvironmentModel.java
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
 * Created on May 31, 2013 at 10:30:56 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;

/**
 * The table environment model.
 */
@ThreadSafe
public final class TableEnvironmentModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment model lock. */
    private final Object lock_;

    /** The table environment associated with this model. */
    private final ITableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentModel} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with this model; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    public TableEnvironmentModel(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        assertArgumentNotNull( tableEnvironment, "tableEnvironment" ); //$NON-NLS-1$

        lock_ = new Object();
        tableEnvironment_ = tableEnvironment;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component model for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return A new component model for the specified component; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} was created by a table environment other
     *         than the table environment associated with this model.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    /* @NonNull */
    public ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$
        assertArgumentLegal( tableEnvironment_.equals( component.getTableEnvironment() ), "component", NonNlsMessages.TableEnvironmentModel_createComponentModel_componentCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        if( component instanceof IContainer )
        {
            return createContainerModel( (IContainer)component );
        }

        return new ComponentModel( this, component );
    }

    /**
     * Creates a new container model for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @return A new container model for the specified container; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code container} was created by a table environment other
     *         than the table environment associated with this model.
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    /* @NonNull */
    public ContainerModel createContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$
        assertArgumentLegal( tableEnvironment_.equals( container.getTableEnvironment() ), "container", NonNlsMessages.TableEnvironmentModel_createContainerModel_containerCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        return new ContainerModel( this, container );
    }

    /**
     * Creates a new table model for the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * 
     * @return A new table model for the specified table; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code table} was created by a table environment other than
     *         the table environment associated with this model.
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    /* @NonNull */
    public TableModel createTableModel(
        /* @NonNull */
        final ITable table )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentLegal( tableEnvironment_.equals( table.getTableEnvironment() ), "table", NonNlsMessages.TableEnvironmentModel_createTableModel_tableCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        return new TableModel( this, table );
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock; never {@code null}.
     */
    /* @NonNull */
    public Object getLock()
    {
        return lock_;
    }

    /**
     * Gets the table environment associated with this model.
     * 
     * @return The table environment associated with this model; never
     *         {@code null}.
     */
    /* @NonNull */
    public ITableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }
}
