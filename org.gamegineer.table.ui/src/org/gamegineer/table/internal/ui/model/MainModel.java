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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MalformedMementoException;
import org.gamegineer.common.persistence.schemes.serializable.ObjectInputStream;
import org.gamegineer.common.persistence.schemes.serializable.ObjectOutputStream;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.ui.ITableAdvisor;
import org.osgi.framework.Version;

/**
 * The top-level model.
 */
@ThreadSafe
public final class MainModel
    implements ITableModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor. */
    private final ITableAdvisor advisor_;

    /** The name of the file to which the model was last saved. */
    @GuardedBy( "lock_" )
    private String fileName_;

    /** Indicates the main model is dirty. */
    @GuardedBy( "lock_" )
    private boolean isDirty_;

    /** The collection of main model listeners. */
    private final CopyOnWriteArrayList<IMainModelListener> listeners_;

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
        fileName_ = null;
        isDirty_ = false;
        listeners_ = new CopyOnWriteArrayList<IMainModelListener>();
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
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addMainModelListener(
        /* @NonNull */
        final IMainModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.MainModel_addMainModelListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardPileFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void cardPileFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        setDirty();
    }

    /**
     * Fires a main model dirty flag changed event.
     */
    private void fireMainModelDirtyFlagChanged()
    {
        final MainModelEvent event = new MainModelEvent( this );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.mainModelDirtyFlagChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_mainModelDirtyFlagChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a main model file name changed event.
     */
    private void fireMainModelFileNameChanged()
    {
        final MainModelEvent event = new MainModelEvent( this );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.mainModelFileNameChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_mainModelFileNameChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a main model state changed event.
     */
    private void fireMainModelStateChanged()
    {
        final MainModelEvent event = new MainModelEvent( this );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.mainModelStateChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_mainModelStateChanged_unexpectedException, e );
            }
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

        final MainModelContentChangedEvent event = new MainModelContentChangedEvent( this, tableModel );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.tableClosed( event );
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

        final MainModelContentChangedEvent event = new MainModelContentChangedEvent( this, tableModel );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.tableOpened( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.MainModel_tableOpened_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the name of the file to which this model was last saved.
     * 
     * @return The name of the file to which this model was last saved or
     *         {@code null} if this model has not yet been saved.
     */
    /* @Nullable */
    public String getFileName()
    {
        synchronized( lock_ )
        {
            return fileName_;
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
     * Gets a value indicating if the main model is dirty.
     * 
     * @return {@code true} if the main model is dirty; otherwise {@code false}.
     */
    public boolean isDirty()
    {
        synchronized( lock_ )
        {
            return isDirty_;
        }
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
            if( closedTableModel != null )
            {
                closedTableModel.removeTableModelListener( this );
            }

            openedTableModel = tableModel_ = new TableModel( TableFactory.createTable() );
            openedTableModel.addTableModelListener( this );
            fileName_ = null;
            isDirty_ = false;
        }

        if( closedTableModel != null )
        {
            fireTableClosed( closedTableModel );
        }
        fireTableOpened( openedTableModel );
        fireMainModelFileNameChanged();
        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
    }

    /**
     * Opens an existing table from the specified file.
     * 
     * @param fileName
     *        The name of the file from which the table will be opened; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fileName} is {@code null}.
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while opening the file.
     */
    public void openTable(
        /* @NonNull */
        final String fileName )
        throws ModelException
    {
        assertArgumentNotNull( fileName, "fileName" ); //$NON-NLS-1$

        final ITable table;
        try
        {
            final ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream( fileName ) );
            try
            {
                final IMemento memento = (IMemento)inputStream.readObject();
                table = TableFactory.createTable( memento );
            }
            finally
            {
                inputStream.close();
            }
        }
        catch( final ClassNotFoundException e )
        {
            throw new ModelException( Messages.MainModel_openTable_error( fileName ), e );
        }
        catch( final IOException e )
        {
            throw new ModelException( Messages.MainModel_openTable_error( fileName ), e );
        }
        catch( final MalformedMementoException e )
        {
            throw new ModelException( Messages.MainModel_openTable_error( fileName ), e );
        }

        final TableModel closedTableModel, openedTableModel;
        synchronized( lock_ )
        {
            closedTableModel = tableModel_;
            if( closedTableModel != null )
            {
                closedTableModel.removeTableModelListener( this );
            }

            openedTableModel = tableModel_ = new TableModel( table );
            openedTableModel.addTableModelListener( this );
            fileName_ = fileName;
            isDirty_ = false;
        }

        if( closedTableModel != null )
        {
            fireTableClosed( closedTableModel );
        }
        fireTableOpened( openedTableModel );
        fireMainModelFileNameChanged();
        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
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
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.MainModel_removeMainModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Saves the current table to the specified file.
     * 
     * @param fileName
     *        The name of the file to which the table will be saved; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code fileName} is {@code null}.
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while saving the file.
     */
    public void saveTable(
        /* @NonNull */
        final String fileName )
        throws ModelException
    {
        assertArgumentNotNull( fileName, "fileName" ); //$NON-NLS-1$

        final IMemento memento;
        synchronized( lock_ )
        {
            memento = tableModel_.getTable().getMemento();
        }

        try
        {
            final ObjectOutputStream outputStream = new ObjectOutputStream( new FileOutputStream( fileName ) );
            try
            {
                outputStream.writeObject( memento );
            }
            finally
            {
                outputStream.close();
            }
        }
        catch( final IOException e )
        {
            throw new ModelException( Messages.MainModel_saveTable_error( fileName ), e );
        }

        synchronized( lock_ )
        {
            fileName_ = fileName;
            isDirty_ = false;
        }

        fireMainModelFileNameChanged();
        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
    }

    /**
     * Marks the main model as dirty.
     */
    private void setDirty()
    {
        synchronized( lock_ )
        {
            isDirty_ = true;
        }

        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableModelStateChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableModelStateChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        setDirty();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#tableOriginOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    @Override
    public void tableOriginOffsetChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        setDirty();
    }
}
