/*
 * MainModel.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.ui.impl.Loggers;

/**
 * The top-level model.
 */
@ThreadSafe
public final class MainModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of main model listeners. */
    private final CopyOnWriteArrayList<IMainModelListener> listeners_;

    /** The preferences model. */
    private final PreferencesModel preferencesModel_;

    /** The table model. */
    private final TableModel tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModel} class.
     * 
     * @param tableModel
     *        The table model.
     */
    public MainModel(
        final TableModel tableModel )
    {
        listeners_ = new CopyOnWriteArrayList<>();
        preferencesModel_ = new PreferencesModel();
        tableModel_ = tableModel;

        tableModel_.addTableModelListener( new TableModelListener() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified main model listener to this main model.
     * 
     * @param listener
     *        The main model listener.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered main model listener.
     */
    public void addMainModelListener(
        final IMainModelListener listener )
    {
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.MainModel_addMainModelListener_listener_registered ); //$NON-NLS-1$
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainModel_mainModelStateChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the preferences model.
     * 
     * @return The preferences model.
     */
    public PreferencesModel getPreferencesModel()
    {
        return preferencesModel_;
    }

    /**
     * Gets the table model.
     * 
     * @return The table model.
     */
    public TableModel getTableModel()
    {
        return tableModel_;
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
        tableModel_.open();
    }

    /**
     * Opens an existing table from the specified file.
     * 
     * @param file
     *        The file from which the table will be opened.
     * 
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while opening the file.
     */
    public void openTable(
        final File file )
        throws ModelException
    {
        try
        {
            tableModel_.open( file );
        }
        catch( final ModelException e )
        {
            preferencesModel_.getFileHistoryPreferences().removeFile( file );
            throw e;
        }

        preferencesModel_.getFileHistoryPreferences().addFile( file );
    }

    /**
     * Removes the specified main model listener from this main model.
     * 
     * @param listener
     *        The main model listener.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered main model listener.
     */
    public void removeMainModelListener(
        final IMainModelListener listener )
    {
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.MainModel_removeMainModelListener_listener_notRegistered ); //$NON-NLS-1$
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
     *        The file to which the table will be saved.
     * 
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while saving the file.
     */
    public void saveTable(
        final File file )
        throws ModelException
    {
        try
        {
            tableModel_.save( file );
        }
        catch( final ModelException e )
        {
            preferencesModel_.getFileHistoryPreferences().removeFile( file );
            throw e;
        }

        preferencesModel_.getFileHistoryPreferences().addFile( file );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A table model listener for the main model.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class TableModelListener
        extends org.gamegineer.table.internal.ui.impl.model.TableModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableModelListener} class.
         */
        TableModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.TableModelListener#tableChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
         */
        @Override
        public void tableChanged(
            final TableModelEvent event )
        {
            fireMainModelStateChanged();
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.TableModelListener#tableModelDirtyFlagChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
         */
        @Override
        public void tableModelDirtyFlagChanged(
            final TableModelEvent event )
        {
            fireMainModelStateChanged();
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.TableModelListener#tableModelFileChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
         */
        @Override
        public void tableModelFileChanged(
            final TableModelEvent event )
        {
            fireMainModelStateChanged();
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.TableModelListener#tableModelFocusChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
         */
        @Override
        public void tableModelFocusChanged(
            final TableModelEvent event )
        {
            fireMainModelStateChanged();
        }
    }
}
