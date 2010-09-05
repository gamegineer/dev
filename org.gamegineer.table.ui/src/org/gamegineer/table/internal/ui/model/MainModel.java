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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MalformedMementoException;
import org.gamegineer.common.persistence.schemes.serializable.ObjectStreams;
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

    /** The file to which the model was last saved. */
    @GuardedBy( "lock_" )
    private File file_;

    /** Indicates the main model is dirty. */
    @GuardedBy( "lock_" )
    private boolean isDirty_;

    /** The collection of main model listeners. */
    private final CopyOnWriteArrayList<IMainModelListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /** The preferences model. */
    private final PreferencesModel preferencesModel_;

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
        file_ = null;
        isDirty_ = false;
        listeners_ = new CopyOnWriteArrayList<IMainModelListener>();
        preferencesModel_ = new PreferencesModel();
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
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.MainModel_mainModelDirtyFlagChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a main model file changed event.
     */
    private void fireMainModelFileChanged()
    {
        final MainModelEvent event = new MainModelEvent( this );
        for( final IMainModelListener listener : listeners_ )
        {
            try
            {
                listener.mainModelFileChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.MainModel_mainModelFileChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.MainModel_mainModelStateChanged_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.MainModel_tableClosed_unexpectedException, e );
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
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.MainModel_tableOpened_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the file to which this model was last saved.
     * 
     * @return The file to which this model was last saved or {@code null} if
     *         this model has not yet been saved.
     */
    /* @Nullable */
    public File getFile()
    {
        synchronized( lock_ )
        {
            return file_;
        }
    }

    /**
     * Gets the preferences model.
     * 
     * @return The preferences model; never {@code null}.
     */
    /* @NonNull */
    public PreferencesModel getPreferencesModel()
    {
        return preferencesModel_;
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
     * Loads the main model from persistent storage.
     */
    public void load()
    {
        preferencesModel_.load();
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
            file_ = null;
            isDirty_ = false;
        }

        if( closedTableModel != null )
        {
            fireTableClosed( closedTableModel );
        }
        fireTableOpened( openedTableModel );
        fireMainModelFileChanged();
        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
    }

    /**
     * Opens an existing table from the specified file.
     * 
     * @param file
     *        The file from which the table will be opened; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code file} is {@code null}.
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while opening the file.
     */
    public void openTable(
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assertArgumentNotNull( file, "file" ); //$NON-NLS-1$

        final TableModel closedTableModel, openedTableModel;
        synchronized( lock_ )
        {
            final ITable table;
            try
            {
                table = readTable( file );
            }
            catch( final ModelException e )
            {
                preferencesModel_.getFileHistoryPreferences().removeFile( file );
                throw e;
            }

            closedTableModel = tableModel_;
            if( closedTableModel != null )
            {
                closedTableModel.removeTableModelListener( this );
            }

            openedTableModel = tableModel_ = new TableModel( table );
            openedTableModel.addTableModelListener( this );
            file_ = file;
            preferencesModel_.getFileHistoryPreferences().addFile( file );
            isDirty_ = false;
        }

        if( closedTableModel != null )
        {
            fireTableClosed( closedTableModel );
        }
        fireTableOpened( openedTableModel );
        fireMainModelFileChanged();
        fireMainModelDirtyFlagChanged();
        fireMainModelStateChanged();
    }

    /**
     * Reads a table from the specified file.
     * 
     * @param file
     *        The file from which the table will be read; must not be {@code
     *        null}.
     * 
     * @return The table that was read from the specified file; never {@code
     *         null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while reading the file.
     */
    /* @NonNull */
    private static ITable readTable(
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assert file != null;

        try
        {
            final ObjectInputStream inputStream = ObjectStreams.createPlatformObjectInputStream( new FileInputStream( file ) );
            try
            {
                final IMemento memento = (IMemento)inputStream.readObject();
                return TableFactory.createTable( memento );
            }
            finally
            {
                inputStream.close();
            }
        }
        catch( final ClassNotFoundException e )
        {
            throw new ModelException( Messages.MainModel_readTable_error( file ), e );
        }
        catch( final IOException e )
        {
            throw new ModelException( Messages.MainModel_readTable_error( file ), e );
        }
        catch( final MalformedMementoException e )
        {
            throw new ModelException( Messages.MainModel_readTable_error( file ), e );
        }
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
     * Stores the main model to persistent storage.
     */
    public void save()
    {
        preferencesModel_.save();
    }

    /**
     * Saves the current table to the specified file.
     * 
     * @param file
     *        The file to which the table will be saved; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code file} is {@code null}.
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while saving the file.
     */
    public void saveTable(
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assertArgumentNotNull( file, "file" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            try
            {
                writeTable( file, tableModel_.getTable() );
            }
            catch( final ModelException e )
            {
                preferencesModel_.getFileHistoryPreferences().removeFile( file );
                throw e;
            }

            file_ = file;
            preferencesModel_.getFileHistoryPreferences().addFile( file );
            isDirty_ = false;
        }

        fireMainModelFileChanged();
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

    /**
     * Writes the specified table to the specified file.
     * 
     * @param file
     *        The file to which the table will be written; must not be {@code
     *        null}.
     * @param table
     *        The table to be written; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while writing the file.
     */
    private static void writeTable(
        /* @NonNull */
        final File file,
        /* @NonNull */
        final ITable table )
        throws ModelException
    {
        assert file != null;
        assert table != null;

        try
        {
            final ObjectOutputStream outputStream = ObjectStreams.createPlatformObjectOutputStream( new FileOutputStream( file ) );
            try
            {
                outputStream.writeObject( table.getMemento() );
            }
            finally
            {
                outputStream.close();
            }
        }
        catch( final IOException e )
        {
            throw new ModelException( Messages.MainModel_writeTable_error( file ), e );
        }
    }
}
