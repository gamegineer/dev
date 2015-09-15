/*
 * TableModel.java
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
 * Created on Dec 26, 2009 at 10:32:11 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.common.persistence.serializable.ObjectStreams;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.version.IVersionControl;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.internal.ui.impl.prototype.IEvaluationContextProvider;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkEvent;

/**
 * The table model.
 */
@ThreadSafe
public final class TableModel
    implements IComponentModelParent, IEvaluationContextProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component model comparator that orders component models by their
     * paths.
     */
    private static final Comparator<ComponentModel> COMPONENT_MODEL_COMPARATOR = new Comparator<ComponentModel>()
    {
        @Override
        public int compare(
            @Nullable
            final ComponentModel componentModel1,
            @Nullable
            final ComponentModel componentModel2 )
        {
            assert componentModel1 != null;
            assert componentModel2 != null;
            assert componentModel1.getLock().isHeldByCurrentThread();

            final ComponentPath componentPath1 = componentModel1.getPath();
            assert componentPath1 != null;
            final ComponentPath componentPath2 = componentModel2.getPath();
            assert componentPath2 != null;
            return componentPath1.compareTo( componentPath2 );
        }
    };

    /** The path to the tabletop component model. */
    private static final ComponentPath TABLETOP_MODEL_PATH = new ComponentPath( null, 0 );

    /** The table revision number at which the table model was last committed. */
    @GuardedBy( "getLock()" )
    private long committedTableRevisionNumber_;

    /** The file to which the model was last saved. */
    @GuardedBy( "getLock()" )
    @Nullable
    private File file_;

    /**
     * The model associated with the focused component or {@code null} if no
     * component has the focus.
     */
    @GuardedBy( "getLock()" )
    @Nullable
    private ComponentModel focusedComponentModel_;

    /**
     * The model associated with the hovered component or {@code null} if no
     * component has the hover.
     */
    @GuardedBy( "getLock()" )
    @Nullable
    private ComponentModel hoveredComponentModel_;

    /** The collection of table model listeners. */
    private final CopyOnWriteArrayList<ITableModelListener> listeners_;

    /** The offset of the table origin relative to the view origin. */
    @GuardedBy( "getLock()" )
    private final Dimension originOffset_;

    /** The table associated with this model. */
    private final ITable table_;

    /** The table environment model associated with this model. */
    private final TableEnvironmentModel tableEnvironmentModel_;

    /** The table network associated with this model. */
    private final ITableNetwork tableNetwork_;

    /** The tabletop model. */
    private final ContainerModel tabletopModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModel} class.
     * 
     * @param tableEnvironmentModel
     *        The table environment model associated with this model; must not
     *        be {@code null}.
     * @param table
     *        The table associated with this model; must not be {@code null}.
     * @param tableNetwork
     *        The table network associated with this model; must not be
     *        {@code null}.
     */
    TableModel(
        final TableEnvironmentModel tableEnvironmentModel,
        final ITable table,
        final ITableNetwork tableNetwork )
    {
        assert tableEnvironmentModel.getTableEnvironment().equals( table.getTableEnvironment() );

        file_ = null;
        focusedComponentModel_ = null;
        hoveredComponentModel_ = null;
        listeners_ = new CopyOnWriteArrayList<>();
        originOffset_ = new Dimension( 0, 0 );
        table_ = table;
        tableEnvironmentModel_ = tableEnvironmentModel;
        tableNetwork_ = tableNetwork;
        tabletopModel_ = new ContainerModel( tableEnvironmentModel, table_.getTabletop() );

        tableNetwork_.addTableNetworkListener( new TableNetworkListener() );

        tabletopModel_.initialize( this );
        tabletopModel_.addComponentModelListener( new ComponentModelListener() );
        tabletopModel_.addContainerModelListener( new ContainerModelListener() );

        committedTableRevisionNumber_ = getTableRevisionNumber();
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
     */
    public void addTableModelListener(
        final ITableModelListener listener )
    {
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.TableModel_addTableModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires the specified event notification.
     * 
     * @param eventNotification
     *        The event notification; must not be {@code null}.
     */
    private void fireEventNotification(
        final Runnable eventNotification )
    {
        tableEnvironmentModel_.fireEventNotification( eventNotification );
    }

    /**
     * Fires a table changed event.
     */
    private void fireTableChanged()
    {
        assert !getLock().isHeldByCurrentThread();

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
        assert !getLock().isHeldByCurrentThread();

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
        assert !getLock().isHeldByCurrentThread();

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
        assert !getLock().isHeldByCurrentThread();

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
     * Fires a table model hover changed event.
     */
    private void fireTableModelHoverChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final TableModelEvent event = new TableModelEvent( this );
        for( final ITableModelListener listener : listeners_ )
        {
            try
            {
                listener.tableModelHoverChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableModel_tableModelHoverChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table model origin offset changed event.
     */
    private void fireTableModelOriginOffsetChanged()
    {
        assert !getLock().isHeldByCurrentThread();

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

    /*
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelParent#getChildPath(org.gamegineer.table.internal.ui.impl.model.ComponentModel)
     */
    @Nullable
    @Override
    public ComponentPath getChildPath(
        final ComponentModel componentModel )
    {
        assert componentModel != null;

        assert componentModel == tabletopModel_;
        return TABLETOP_MODEL_PATH;
    }

    /**
     * Gets the component model in this table model at the specified path.
     * 
     * @param path
     *        The component path; must not be {@code null}.
     * 
     * @return The component model in this table model at the specified path or
     *         {@code null} if no component model exists at the specified path.
     */
    @Nullable
    public ComponentModel getComponentModel(
        final ComponentPath path )
    {
        final List<ComponentPath> paths = path.toList();
        final ComponentPath tabletopPath = paths.get( 0 );
        if( tabletopPath.getIndex() != 0 )
        {
            return null;
        }
        else if( paths.size() == 1 )
        {
            return tabletopModel_;
        }

        getLock().lock();
        try
        {
            return tabletopModel_.getComponentModel( paths.subList( 1, paths.size() ) );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the component models in this table model at the specified location.
     * 
     * <p>
     * Note that the returned component models may have been moved by the time
     * this method returns to the caller. Therefore, callers should not cache
     * the results of this method for an extended period of time.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The collection of component models in this table model at the
     *         specified location; never {@code null}. The component models are
     *         returned in order from the bottom-most component model to the
     *         top-most component model; never {@code null}.
     */
    public List<ComponentModel> getComponentModels(
        final Point location )
    {
        final List<ComponentModel> componentModels = new ArrayList<>();

        getLock().lock();
        try
        {
            if( tabletopModel_.hitTest( location, componentModels ) )
            {
                Collections.sort( componentModels, COMPONENT_MODEL_COMPARATOR );
            }
        }
        finally
        {
            getLock().unlock();
        }

        return componentModels;
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelParent#getEvaluationContext()
     */
    @Override
    public EvaluationContext getEvaluationContext()
    {
        final EvaluationContext evaluationContext = new EvaluationContext( null, EvaluationContext.UNDEFINED_VARIABLE );

        final ComponentModel focusedComponentModel = getFocusedComponentModel();
        evaluationContext.addVariable( EvaluationContextVariables.FOCUSED_COMPONENT, (focusedComponentModel != null) ? focusedComponentModel.getComponent() : EvaluationContext.UNDEFINED_VARIABLE );

        return evaluationContext;
    }

    /**
     * Gets the file to which this model was last saved.
     * 
     * @return The file to which this model was last saved or {@code null} if
     *         this model has not yet been saved.
     */
    @Nullable
    public File getFile()
    {
        getLock().lock();
        try
        {
            return file_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the top-most focusable component model at the specified location.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The top-most focusable component model at the specified location
     *         or {@code null} if no focusable component model is at that
     *         location.
     */
    @Nullable
    public ComponentModel getFocusableComponentModel(
        final Point location )
    {
        return getFocusableComponentModel( location, null );
    }

    /**
     * Gets the focusable component model at the specified location along the
     * specified vector.
     * 
     * <p>
     * This method starts the search from the origin of the specified vector and
     * works its way along the specified axis until it finds a focusable
     * component model. If no focusable component model is found when the end of
     * the axis is reached, the search wraps around to the end of the opposite
     * axis.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * @param searchVector
     *        The vector along which the search will proceed or {@code null} to
     *        return the top-most focusable component model.
     * 
     * @return The focusable component model at the specified location or
     *         {@code null} if no focusable component model is at that location.
     */
    @Nullable
    public ComponentModel getFocusableComponentModel(
        final Point location,
        @Nullable
        final ComponentModelVector searchVector )
    {
        ComponentModel focusableComponentModel = null;

        getLock().lock();
        try
        {
            final List<ComponentModel> componentModels = getComponentModels( location );
            if( !componentModels.isEmpty() )
            {
                final ComponentModel searchOrigin;
                final ComponentAxis searchDirection;
                if( (searchVector != null) //
                    && (searchVector.getOrigin().getPath() != null) //
                    && searchVector.getOrigin().getComponent().getBounds().contains( location ) )
                {
                    searchOrigin = searchVector.getOrigin();
                    searchDirection = searchVector.getDirection();
                }
                else
                {
                    searchOrigin = componentModels.get( 0 );
                    searchDirection = ComponentAxis.PRECEDING;
                }

                final int originIndex = Collections.binarySearch( componentModels, searchOrigin, COMPONENT_MODEL_COMPARATOR );
                if( originIndex >= 0 )
                {
                    int index = originIndex;
                    do
                    {
                        index = getNextSearchIndex( componentModels, index, searchDirection );
                        final ComponentModel componentModel = componentModels.get( index );
                        if( (componentModel != null) && componentModel.isFocusable() )
                        {
                            focusableComponentModel = componentModel;
                        }

                    } while( (focusableComponentModel == null) && (index != originIndex) );
                }
            }
        }
        finally
        {
            getLock().unlock();
        }

        return focusableComponentModel;
    }

    /**
     * Gets the focused component model.
     * 
     * @return The focused component model or {@code null} if no component model
     *         has the focus.
     */
    @Nullable
    public ComponentModel getFocusedComponentModel()
    {
        getLock().lock();
        try
        {
            return focusedComponentModel_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets hovered component model.
     * 
     * @return The hovered component model or {@code null} if no component model
     *         has the hover.
     */
    @Nullable
    public ComponentModel getHoveredComponentModel()
    {
        getLock().lock();
        try
        {
            return hoveredComponentModel_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock; never {@code null}.
     */
    private ITableEnvironmentModelLock getLock()
    {
        return tableEnvironmentModel_.getLock();
    }

    /**
     * Gets the next search index for the specified search criteria.
     * 
     * @param componentModels
     *        The list of component models being searched in ascending z-order
     *        order; must not be {@code null}.
     * @param index
     *        The current search index.
     * @param searchAxis
     *        The search axis; must not be {@code null}.
     * 
     * @return The next search index.
     */
    private static int getNextSearchIndex(
        final List<ComponentModel> componentModels,
        final int index,
        final ComponentAxis searchAxis )
    {
        switch( searchAxis )
        {
            case PRECEDING:
                return (index > 0) ? (index - 1) : (componentModels.size() - 1);

            case FOLLOWING:
                return (index < (componentModels.size() - 1)) ? (index + 1) : 0;

            default:
                throw new AssertionError( "unsupported component axis: " + searchAxis ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @return The offset of the table origin relative to the view origin in
     *         table coordinates; never {@code null}.
     */
    public Dimension getOriginOffset()
    {
        getLock().lock();
        try
        {
            return new Dimension( originOffset_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelParent#getParent()
     */
    @Nullable
    @Override
    public IComponentModelParent getParent()
    {
        return null;
    }

    /**
     * Gets the table associated with this model.
     * 
     * @return The table associated with this model; never {@code null}.
     */
    public ITable getTable()
    {
        return table_;
    }

    /**
     * Gets the table environment model associated with this model.
     * 
     * @return The table environment model associated with this model; never
     *         {@code null}.
     */
    public TableEnvironmentModel getTableEnvironmentModel()
    {
        return tableEnvironmentModel_;
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelParent#getTableModel()
     */
    @Nullable
    @Override
    public TableModel getTableModel()
    {
        return this;
    }

    /**
     * Gets the table network associated with this model.
     * 
     * @return The table network associated with this model; never {@code null}.
     */
    public ITableNetwork getTableNetwork()
    {
        return tableNetwork_;
    }

    /**
     * Gets the current table revision number.
     * 
     * @return The current table revision number.
     */
    private long getTableRevisionNumber()
    {
        final IVersionControl versionControl = table_.getExtension( IVersionControl.class );
        if( versionControl == null )
        {
            Loggers.getDefaultLogger().severe( NonNlsMessages.TableModel_getTableRevisionNumber_versionControlNotAvailable );
            return -1L;
        }

        return versionControl.getRevisionNumber();
    }

    /**
     * Gets the tabletop model.
     * 
     * @return The tabletop model; never {@code null}.
     */
    public ContainerModel getTabletopModel()
    {
        return tabletopModel_;
    }

    /**
     * Indicates the table model is dirty.
     * 
     * @return {@code true} if the table model is dirty; otherwise {@code false}
     *         .
     */
    public boolean isDirty()
    {
        getLock().lock();
        try
        {
            return committedTableRevisionNumber_ != getTableRevisionNumber();
        }
        finally
        {
            getLock().unlock();
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
        getLock().lock();
        try
        {
            table_.getTabletop().removeAllComponents();

            committedTableRevisionNumber_ = getTableRevisionNumber();
            file_ = null;
            focusedComponentModel_ = null;
            hoveredComponentModel_ = null;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }
        finally
        {
            getLock().unlock();
        }

        fireEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireTableChanged();
                fireTableModelFileChanged();
                fireTableModelFocusChanged();
                fireTableModelHoverChanged();
                fireTableModelOriginOffsetChanged();
                fireTableModelDirtyFlagChanged();
            }
        } );
    }

    /**
     * Opens an existing table from the specified file.
     * 
     * @param file
     *        The file from which the table will be opened; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while opening the file.
     */
    void open(
        final File file )
        throws ModelException
    {
        getLock().lock();
        try
        {
            setTableMemento( table_, file );

            committedTableRevisionNumber_ = getTableRevisionNumber();
            file_ = file;
            focusedComponentModel_ = null;
            hoveredComponentModel_ = null;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }
        finally
        {
            getLock().unlock();
        }

        fireEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireTableChanged();
                fireTableModelFileChanged();
                fireTableModelFocusChanged();
                fireTableModelHoverChanged();
                fireTableModelOriginOffsetChanged();
                fireTableModelDirtyFlagChanged();
            }
        } );
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
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while reading the file.
     */
    private static Object readTableMemento(
        final File file )
        throws ModelException
    {
        try( final ObjectInputStream inputStream = ObjectStreams.createPlatformObjectInputStream( new FileInputStream( file ) ) )
        {
            final Object tableMemento = inputStream.readObject();
            assert tableMemento != null;
            return tableMemento;
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
     */
    public void removeTableModelListener(
        final ITableModelListener listener )
    {
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.TableModel_removeTableModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Saves the table to the specified file.
     * 
     * @param file
     *        The file to which the table will be saved; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while saving the file.
     */
    void save(
        final File file )
        throws ModelException
    {
        getLock().lock();
        try
        {
            writeTableMemento( file, table_.createMemento() );

            committedTableRevisionNumber_ = getTableRevisionNumber();
            file_ = file;
        }
        finally
        {
            getLock().unlock();
        }

        fireEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireTableModelFileChanged();
                fireTableModelDirtyFlagChanged();
            }
        } );
    }

    /**
     * Sets the focus to the specified component model.
     * 
     * @param componentModel
     *        The component model to receive the focus or {@code null} if no
     *        component model should have the focus.
     */
    public void setFocus(
        @Nullable
        final ComponentModel componentModel )
    {
        final boolean componentModelFocusChanged;

        getLock().lock();
        try
        {
            final ComponentModel oldFocusedComponentModel;
            final ComponentModel newFocusedComponentModel;
            if( componentModel != focusedComponentModel_ )
            {
                componentModelFocusChanged = true;
                oldFocusedComponentModel = focusedComponentModel_;
                newFocusedComponentModel = componentModel;
                focusedComponentModel_ = componentModel;
            }
            else
            {
                componentModelFocusChanged = false;
                oldFocusedComponentModel = null;
                newFocusedComponentModel = null;
            }

            if( componentModelFocusChanged )
            {
                if( oldFocusedComponentModel != null )
                {
                    oldFocusedComponentModel.setFocused( false );
                }
                if( newFocusedComponentModel != null )
                {
                    newFocusedComponentModel.setFocused( true );
                }
            }
        }
        finally
        {
            getLock().unlock();
        }

        if( componentModelFocusChanged )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    fireTableModelFocusChanged();
                }
            } );
        }
    }

    /**
     * Sets the hover to the specified component model.
     * 
     * @param componentModel
     *        The component model to receive the hover or {@code null} if no
     *        component model should have the hover.
     */
    public void setHover(
        @Nullable
        final ComponentModel componentModel )
    {
        final boolean componentModelHoverChanged;

        getLock().lock();
        try
        {
            final ComponentModel oldHoveredComponentModel;
            final ComponentModel newHoveredComponentModel;
            if( componentModel != hoveredComponentModel_ )
            {
                componentModelHoverChanged = true;
                oldHoveredComponentModel = hoveredComponentModel_;
                newHoveredComponentModel = componentModel;
                hoveredComponentModel_ = componentModel;
            }
            else
            {
                componentModelHoverChanged = false;
                oldHoveredComponentModel = null;
                newHoveredComponentModel = null;
            }

            if( componentModelHoverChanged )
            {
                if( oldHoveredComponentModel != null )
                {
                    oldHoveredComponentModel.setHover( false );
                }
                if( newHoveredComponentModel != null )
                {
                    newHoveredComponentModel.setHover( true );
                }
            }
        }
        finally
        {
            getLock().unlock();
        }

        if( componentModelHoverChanged )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    fireTableModelHoverChanged();
                }
            } );
        }
    }

    /**
     * Sets the offset of the table origin relative to the view origin in table
     * coordinates.
     * 
     * @param originOffset
     *        The offset of the table origin relative to the view origin in
     *        table coordinates; must not be {@code null}.
     */
    public void setOriginOffset(
        final Dimension originOffset )
    {
        getLock().lock();
        try
        {
            originOffset_.setSize( originOffset );
        }
        finally
        {
            getLock().unlock();
        }

        fireEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireTableModelOriginOffsetChanged();
            }
        } );
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
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while reading the file or setting the memento.
     */
    private static void setTableMemento(
        final ITable table,
        final File file )
        throws ModelException
    {
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
     * @throws org.gamegineer.table.internal.ui.impl.model.ModelException
     *         If an error occurs while writing the file.
     */
    private static void writeTableMemento(
        final File file,
        final Object memento )
        throws ModelException
    {
        try( final ObjectOutputStream outputStream = ObjectStreams.createPlatformObjectOutputStream( new FileOutputStream( file ) ) )
        {
            outputStream.writeObject( memento );
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
    @SuppressWarnings( "synthetic-access" )
    private final class ComponentModelListener
        extends org.gamegineer.table.internal.ui.impl.model.ComponentModelListener
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
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableChanged();
                    fireTableModelDirtyFlagChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentModelFocusChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableModelFocusChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentModelHoverChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentModelHoverChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableModelHoverChanged();
                }
            } );
        }
    }

    /**
     * A container model listener for the table model.
     */
    @Immutable
    private final class ContainerModelListener
        extends org.gamegineer.table.internal.ui.impl.model.ContainerModelListener
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
    }

    /**
     * A table network listener for the table model.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
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
        public void tableNetworkConnected(
            @SuppressWarnings( "unused" )
            final TableNetworkEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkDisconnected(org.gamegineer.table.net.TableNetworkDisconnectedEvent)
         */
        @Override
        public void tableNetworkDisconnected(
            @SuppressWarnings( "unused" )
            final TableNetworkDisconnectedEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.net.TableNetworkListener#tableNetworkPlayersUpdated(org.gamegineer.table.net.TableNetworkEvent)
         */
        @Override
        public void tableNetworkPlayersUpdated(
            @SuppressWarnings( "unused" )
            final TableNetworkEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireTableChanged();
                }
            } );
        }
    }
}
