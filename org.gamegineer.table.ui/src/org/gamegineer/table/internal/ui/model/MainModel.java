/*
 * MainModel.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 6, 2009 at 11:17:00 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.ui.ITableAdvisor;
import org.osgi.framework.Version;

/**
 * The top-level model.
 */
@ThreadSafe
public final class MainModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor. */
    private final ITableAdvisor advisor_;

    /** The main model listener. */
    @GuardedBy( "lock_" )
    private IMainModelListener listener_;

    /** The instance lock. */
    private final Object lock_;

    /** The table model. */
    @GuardedBy( "lock_" )
    private TableModel tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModel} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public MainModel(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        lock_ = new Object();
        advisor_ = advisor;
        listener_ = null;
        tableModel_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified main model listener to this main model.
     * 
     * @param listener
     *        The main model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered main model listener.
     * @throws java.lang.IllegalStateException
     *         If a main model listener is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addMainModelListener(
        /* @NonNull */
        final IMainModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ != listener, "listener", Messages.MainModel_addMainModelListener_listener_registered ); //$NON-NLS-1$
            assertStateLegal( listener_ == null, Messages.MainModel_addMainModelListener_tooManyListeners );

            listener_ = listener;
        }
    }

    /**
     * Fires a table closed event.
     * 
     * @param tableModel
     *        The table model that was closed; must not be {@code null}.
     */
    private void fireTableClosed(
        /* @NonNull */
        final TableModel tableModel )
    {
        assert tableModel != null;

        final IMainModelListener listener = getMainModelListener();
        if( listener != null )
        {
            try
            {
                listener.tableClosed( new MainModelContentChangedEvent( this, tableModel ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_tableClosed_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table opened event.
     * 
     * @param tableModel
     *        The table model that was opened; must not be {@code null}.
     */
    private void fireTableOpened(
        /* @NonNull */
        final TableModel tableModel )
    {
        assert tableModel != null;

        final IMainModelListener listener = getMainModelListener();
        if( listener != null )
        {
            try
            {
                listener.tableOpened( new MainModelContentChangedEvent( this, tableModel ) );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_tableOpened_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the main model listener.
     * 
     * @return The main model listener; may be {@code null}.
     */
    /* @Nullable */
    private IMainModelListener getMainModelListener()
    {
        synchronized( lock_ )
        {
            return listener_;
        }
    }

    /**
     * Gets the table model.
     * 
     * @return The table model or {@code null} if no table model is open.
     */
    /* @Nullable */
    public TableModel getTableModel()
    {
        synchronized( lock_ )
        {
            return tableModel_;
        }
    }

    /**
     * Gets the model version.
     * 
     * @return The model version.
     */
    /* @NonNull */
    public Version getVersion()
    {
        return advisor_.getApplicationVersion();
    }

    /**
     * Opens a new empty table.
     */
    public void openTable()
    {
        final TableModel closedTableModel, openedTableModel;
        synchronized( lock_ )
        {
            closedTableModel = tableModel_;
            openedTableModel = tableModel_ = new TableModel( TableFactory.createTable() );
        }

        if( closedTableModel != null )
        {
            fireTableClosed( closedTableModel );
        }

        fireTableOpened( openedTableModel );
    }

    /**
     * Removes the specified main model listener from this main model.
     * 
     * @param listener
     *        The main model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered main model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeMainModelListener(
        /* @NonNull */
        final IMainModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( listener_ == listener, "listener", Messages.MainModel_removeMainModelListener_listener_notRegistered ); //$NON-NLS-1$

            listener_ = null;
        }
    }
}
