/*
 * TableModel.java
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
 * Created on Dec 26, 2009 at 10:32:11 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.common.persistence.serializable.ObjectStreams;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableContentChangedEvent;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkEvent;
import org.gamegineer.table.net.TableNetworkFactory;

/**
 * The table model.
 */
@ThreadSafe
public final class TableModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model listener for this model. */
    private final IComponentModelListener componentModelListener_;

    /** The container model listener for this model. */
    private final IContainerModelListener containerModelListener_;

    /** The collection of container models. */
    @GuardedBy( "lock_" )
    private final Map<IContainer, ContainerModel> containerModels_;

    /** The file to which the model was last saved. */
    @GuardedBy( "lock_" )
    private File file_;

    /** The focused card pile or {@code null} if no card pile has the focus. */
    @GuardedBy( "lock_" )
    private ICardPile focusedCardPile_;

    /** Indicates the table model is dirty. */
    @GuardedBy( "lock_" )
    private boolean isDirty_;

    /** The collection of table model listeners. */
    private final CopyOnWriteArrayList<ITableModelListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /** The offset of the table origin relative to the view origin. */
    @GuardedBy( "lock_" )
    private final Dimension originOffset_;

    /** The table associated with this model. */
    private final ITable table_;

    /** The table network associated with this model. */
    private final ITableNetwork tableNetwork_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModel}.
     */
    public TableModel()
    {
        componentModelListener_ = new ComponentModelListener();
        containerModelListener_ = new ContainerModelListener();
        containerModels_ = new IdentityHashMap<IContainer, ContainerModel>();
        file_ = null;
        focusedCardPile_ = null;
        isDirty_ = false;
        listeners_ = new CopyOnWriteArrayList<ITableModelListener>();
        lock_ = new Object();
        originOffset_ = new Dimension( 0, 0 );
        table_ = TableEnvironmentFactory.createTableEnvironment().createTable();
        tableNetwork_ = TableNetworkFactory.createTableNetwork();

        table_.addTableListener( new TableListener() );
        tableNetwork_.addTableNetworkListener( new TableNetworkListener() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified table model listener to this table model.
     * 
     * @param listener
     *        The table model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered table model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addTableModelListener(
        /* @NonNull */
        final ITableModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.TableModel_addTableModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Creates a container model for the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @return The container model; never {@code null}.
     */
    /* @NonNull */
    private ContainerModel createContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        assert container != null;

        final ContainerModel containerModel = new ContainerModel( container );
        containerModels_.put( container, containerModel );
        containerModel.addComponentModelListener( componentModelListener_ );
        containerModel.addContainerModelListener( containerModelListener_ );
        return containerModel;
    }

    /**
     * Fires a table changed event.
     */
    private void fireTableChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model dirty flag changed event.
     */
    private void fireTableModelDirtyFlagChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelDirtyFlagChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableModelDirtyFlagChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model file changed event.
     */
    private void fireTableModelFileChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelFileChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableModelFileChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model focus changed event.
     */
    private void fireTableModelFocusChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableModelFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model origin offset changed event.
     */
    private void fireTableModelOriginOffsetChanged()
    {
        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelOriginOffsetChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableModelOriginOffsetChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the container model associated with the specified container.
     * 
     * @param container
     *        The container; must not be {@code null}.
     * 
     * @return The container model associated with the specified container;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code container} does not exist in the table associated with
     *         this model.
     * @throws java.lang.NullPointerException
     *         If {@code container} is {@code null}.
     */
    /* @NonNull */
    public ContainerModel getContainerModel(
        /* @NonNull */
        final IContainer container )
    {
        assertArgumentNotNull( container, "container" ); //$NON-NLS-1$

        final ContainerModel containerModel;
        synchronized( lock_ )
        {
            containerModel = containerModels_.get( container );
        }

        assertArgumentLegal( containerModel != null, "container", NonNlsMessages.TableModel_getContainerModel_container_absent ); //$NON-NLS-1$
        return containerModel;
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
     * Gets the focused card pile.
     * 
     * @return The focused card pile or {@code null} if no card pile has the
     *         focus.
     */
    /* @Nullable */
    public ICardPile getFocusedCardPile()
    {
        synchronized( lock_ )
        {
            return focusedCardPile_;
        }
    }

    /**
     * Gets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @return The offset of the table origin relative to the view origin in
     *         table coordinates; never {@code null}.
     */
    /* @NonNull */
    public Dimension getOriginOffset()
    {
        synchronized( lock_ )
        {
            return new Dimension( originOffset_ );
        }
    }

    /**
     * Gets the table associated with this model.
     * 
     * @return The table associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ITable getTable()
    {
        return table_;
    }

    /**
     * Gets the table network associated with this model.
     * 
     * @return The table network associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ITableNetwork getTableNetwork()
    {
        return tableNetwork_;
    }

    /**
     * Indicates the table model is dirty.
     * 
     * @return {@code true} if the table model is dirty; otherwise {@code false}
     *         .
     */
    public boolean isDirty()
    {
        synchronized( lock_ )
        {
            return isDirty_;
        }
    }

    /**
     * Indicates the table model is editable.
     * 
     * @return {@code true} if the table model is editable; otherwise
     *         {@code false}.
     */
    public boolean isEditable()
    {
        if( tableNetwork_.isConnected() )
        {
            final IPlayer localPlayer = tableNetwork_.getLocalPlayer();
            if( localPlayer != null )
            {
                return localPlayer.hasRole( PlayerRole.EDITOR );
            }
        }

        return true;
    }

    /**
     * Opens a new empty table.
     */
    void open()
    {
        synchronized( lock_ )
        {
            table_.removeCardPiles();

            file_ = null;
            focusedCardPile_ = null;
            isDirty_ = false;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }

        fireTableChanged();
        fireTableModelFileChanged();
        fireTableModelFocusChanged();
        fireTableModelOriginOffsetChanged();
        fireTableModelDirtyFlagChanged();
    }

    /**
     * Opens an existing table from the specified file.
     * 
     * @param file
     *        The file from which the table will be opened; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while opening the file.
     */
    void open(
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assert file != null;

        synchronized( lock_ )
        {
            setTableMemento( table_, file );

            file_ = file;
            focusedCardPile_ = null;
            isDirty_ = false;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }

        fireTableChanged();
        fireTableModelFileChanged();
        fireTableModelFocusChanged();
        fireTableModelOriginOffsetChanged();
        fireTableModelDirtyFlagChanged();
    }

    /**
     * Reads a table memento from the specified file.
     * 
     * @param file
     *        The file from which the table memento will be read; must not be
     *        {@code null}.
     * 
     * @return The table memento that was read from the specified file; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while reading the file.
     */
    /* @NonNull */
    private static Object readTableMemento(
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
                return inputStream.readObject();
            }
            finally
            {
                inputStream.close();
            }
        }
        catch( final ClassNotFoundException e )
        {
            throw new ModelException( NonNlsMessages.TableModel_readTableMemento_error( file ), e );
        }
        catch( final IOException e )
        {
            throw new ModelException( NonNlsMessages.TableModel_readTableMemento_error( file ), e );
        }
    }

    /**
     * Removes the specified table model listener from this table model.
     * 
     * @param listener
     *        The table model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered table model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeTableModelListener(
        /* @NonNull */
        final ITableModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.TableModel_removeTableModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Saves the table to the specified file.
     * 
     * @param file
     *        The file to which the table will be saved; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while saving the file.
     */
    void save(
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assert file != null;

        synchronized( lock_ )
        {
            writeTableMemento( file, table_.createMemento() );

            file_ = file;
            isDirty_ = false;
        }

        fireTableModelFileChanged();
        fireTableModelDirtyFlagChanged();
    }

    /**
     * Sets the focus to the specified card pile.
     * 
     * @param cardPile
     *        The card pile to receive the focus or {@code null} if no card pile
     *        should have the focus.
     */
    public void setFocus(
        /* @Nullable */
        final ICardPile cardPile )
    {
        final boolean cardPileFocusChanged;
        final ContainerModel oldFocusedContainerModel;
        final ContainerModel newFocusedContainerModel;

        synchronized( lock_ )
        {
            if( cardPile != focusedCardPile_ )
            {
                cardPileFocusChanged = true;
                oldFocusedContainerModel = (focusedCardPile_ != null) ? containerModels_.get( focusedCardPile_ ) : null;
                newFocusedContainerModel = (cardPile != null) ? containerModels_.get( cardPile ) : null;
                focusedCardPile_ = cardPile;
            }
            else
            {
                cardPileFocusChanged = false;
                oldFocusedContainerModel = null;
                newFocusedContainerModel = null;
            }
        }

        if( cardPileFocusChanged )
        {
            if( oldFocusedContainerModel != null )
            {
                oldFocusedContainerModel.setFocused( false );
            }
            if( newFocusedContainerModel != null )
            {
                newFocusedContainerModel.setFocused( true );
            }
        }
    }

    /**
     * Sets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @param originOffset
     *        The offset of the table origin relative to the view origin in
     *        table coordinates; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code originOffset} is {@code null}.
     */
    public void setOriginOffset(
        /* @NonNull */
        final Dimension originOffset )
    {
        assertArgumentNotNull( originOffset, "originOffset" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            originOffset_.setSize( originOffset );
        }

        fireTableModelOriginOffsetChanged();
    }

    /**
     * Reads a table memento from the specified file and uses it to set the
     * state of the specified table.
     * 
     * @param table
     *        The table that will receive the memento; must not be {@code null}.
     * @param file
     *        The file from which the table memento will be read; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while reading the file or setting the memento.
     */
    private static void setTableMemento(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final File file )
        throws ModelException
    {
        assert table != null;
        assert file != null;

        try
        {
            table.setMemento( readTableMemento( file ) );
        }
        catch( final MementoException e )
        {
            throw new ModelException( NonNlsMessages.TableModel_setTableMemento_error, e );
        }
    }

    /**
     * Writes the specified table memento to the specified file.
     * 
     * @param file
     *        The file to which the table memento will be written; must not be
     *        {@code null}.
     * @param memento
     *        The table memento to be written; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs while writing the file.
     */
    private static void writeTableMemento(
        /* @NonNull */
        final File file,
        /* @NonNull */
        final Object memento )
        throws ModelException
    {
        assert file != null;
        assert memento != null;

        try
        {
            final ObjectOutputStream outputStream = ObjectStreams.createPlatformObjectOutputStream( new FileOutputStream( file ) );
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
            throw new ModelException( NonNlsMessages.TableModel_writeTableMemento_error( file ), e );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component model listener for the table model.
     */
    @Immutable
    private final class ComponentModelListener
        extends org.gamegineer.table.internal.ui.model.ComponentModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentModelListener}
         * class.
         */
        ComponentModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                isDirty_ = true;
            }

            fireTableChanged();
            fireTableModelDirtyFlagChanged();
        }
    }

    /**
     * A container model listener for the table model.
     */
    @Immutable
    private final class ContainerModelListener
        extends org.gamegineer.table.internal.ui.model.ContainerModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerModelListener}
         * class.
         */
        ContainerModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.ContainerModelListener#containerModelFocusChanged(org.gamegineer.table.internal.ui.model.ContainerModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void containerModelFocusChanged(
            final ContainerModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableModelFocusChanged();
        }
    }

    /**
     * A table listener for the table model.
     */
    @Immutable
    private final class TableListener
        extends org.gamegineer.table.core.TableListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableListener} class.
         */
        TableListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.TableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileAdded(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                createContainerModel( event.getCardPile() );
                isDirty_ = true;
            }

            fireTableChanged();
            fireTableModelDirtyFlagChanged();
        }

        /*
         * @see org.gamegineer.table.core.TableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void cardPileRemoved(
            final TableContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            final ICardPile cardPile = event.getCardPile();
            final boolean clearFocusedCardPile;
            synchronized( lock_ )
            {
                final ContainerModel containerModel = containerModels_.remove( cardPile );
                if( containerModel != null )
                {
                    containerModel.removeComponentModelListener( componentModelListener_ );
                    containerModel.removeContainerModelListener( containerModelListener_ );
                }

                clearFocusedCardPile = (focusedCardPile_ == cardPile);
                isDirty_ = true;
            }

            if( clearFocusedCardPile )
            {
                setFocus( null );
            }

            fireTableChanged();
            fireTableModelDirtyFlagChanged();
        }
    }

    /**
     * A table network listener for the table model.
     */
    @Immutable
    private final class TableNetworkListener
        extends org.gamegineer.table.net.TableNetworkListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableNetworkListener} class.
         */
        TableNetworkListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkConnected(org.gamegineer.table.net.TableNetworkEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void tableNetworkConnected(
            final TableNetworkEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableChanged();
        }

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkDisconnected(org.gamegineer.table.net.TableNetworkDisconnectedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void tableNetworkDisconnected(
            final TableNetworkDisconnectedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableChanged();
        }

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkPlayersUpdated(org.gamegineer.table.net.TableNetworkEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void tableNetworkPlayersUpdated(
            final TableNetworkEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableChanged();
        }
    }
}
