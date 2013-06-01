/*
 * TableModel.java
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
 * Created on Dec 26, 2009 at 10:32:11 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.expressions.EvaluationContext;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.common.persistence.serializable.ObjectStreams;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.prototype.IEvaluationContextProvider;
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
    implements IComponentModelParent, IEvaluationContextProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The file to which the model was last saved. */
    @GuardedBy( "lock_" )
    private File file_;

    /**
     * The model associated with the focused component or {@code null} if no
     * component has the focus.
     */
    @GuardedBy( "lock_" )
    private ComponentModel focusedComponentModel_;

    /**
     * The model associated with the hovered component or {@code null} if no
     * component has the hover.
     */
    @GuardedBy( "lock_" )
    private ComponentModel hoveredComponentModel_;

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

    /** The tabletop model. */
    private final ContainerModel tabletopModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModel} class.
     * 
     * @param table
     *        The table associated with this model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public TableModel(
        /* @NonNull */
        final ITable table )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$

        file_ = null;
        focusedComponentModel_ = null;
        hoveredComponentModel_ = null;
        isDirty_ = false;
        listeners_ = new CopyOnWriteArrayList<ITableModelListener>();
        lock_ = new Object();
        originOffset_ = new Dimension( 0, 0 );
        table_ = table;
        tableNetwork_ = TableNetworkFactory.createTableNetwork();
        tabletopModel_ = new ContainerModel( table_.getTabletop() );

        tableNetwork_.addTableNetworkListener( new TableNetworkListener() );

        tabletopModel_.initialize( this );
        tabletopModel_.addComponentModelListener( new ComponentModelListener() );
        tabletopModel_.addContainerModelListener( new ContainerModelListener() );
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
     * Fires a table changed event.
     */
    private void fireTableChanged()
    {
        assert !Thread.holdsLock( lock_ );

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
        assert !Thread.holdsLock( lock_ );

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
        assert !Thread.holdsLock( lock_ );

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
        assert !Thread.holdsLock( lock_ );

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
        assert !Thread.holdsLock( lock_ );

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
        assert !Thread.holdsLock( lock_ );

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
     * Gets the component model in this table model at the specified path.
     * 
     * @param path
     *        The component path; must not be {@code null}.
     * 
     * @return The component model in this table model at the specified path;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component model exists at the specified path.
     * @throws java.lang.NullPointerException
     *         If {@code path} is {@code null}.
     */
    /* @NonNull */
    public ComponentModel getComponentModel(
        /* @NonNull */
        final ComponentPath path )
    {
        assertArgumentNotNull( path, "path" ); //$NON-NLS-1$

        final List<ComponentPath> paths = path.toList();
        final ComponentPath tabletopPath = paths.get( 0 );
        assertArgumentLegal( tabletopPath.getIndex() == 0, "path", NonNlsMessages.TableModel_getComponentModel_path_notExists ); //$NON-NLS-1$
        if( paths.size() == 1 )
        {
            return tabletopModel_;
        }

        return tabletopModel_.getComponentModel( paths.subList( 1, paths.size() ) );
    }

    /**
     * Gets the component model in this table model associated with the
     * specified component.
     * 
     * @param component
     *        The component; may be {@code null}.
     * 
     * @return The component model in this table model associated with the
     *         specified component or {@code null} if {@code component} is
     *         {@code null} or {@code component} is not associated with a table.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If no component model is associated with the specified component.
     */
    /* @Nullable */
    private ComponentModel getComponentModel(
        /* @Nullable */
        final IComponent component )
    {
        if( component == null )
        {
            return null;
        }

        final ComponentPath componentPath = component.getPath();
        if( componentPath == null )
        {
            return null;
        }

        final ComponentModel componentModel = getComponentModel( componentPath );
        if( componentModel == null )
        {
            return null;
        }

        assertArgumentLegal( component == componentModel.getComponent(), "component", NonNlsMessages.TableModel_getComponentModel_component_notExists ); //$NON-NLS-1$
        return componentModel;
    }

    /*
     * @see org.gamegineer.table.internal.ui.prototype.IEvaluationContextProvider#getEvaluationContext()
     */
    @Override
    public EvaluationContext getEvaluationContext()
    {
        final EvaluationContext evaluationContext = new EvaluationContext( null, EvaluationContext.UNDEFINED_VARIABLE );

        final IComponent focusedComponent = getFocusedComponent();
        evaluationContext.addVariable( EvaluationContextVariables.FOCUSED_COMPONENT, (focusedComponent != null) ? focusedComponent : EvaluationContext.UNDEFINED_VARIABLE );

        return evaluationContext;
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
     * Gets the top-most focusable component at the specified location.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The top-most focusable component at the specified location or
     *         {@code null} if no focusable component is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public IComponent getFocusableComponent(
        /* @NonNull */
        final Point location )
    {
        return getFocusableComponent( location, null );
    }

    /**
     * Gets the focusable component at the specified location along the
     * specified vector.
     * 
     * <p>
     * This method starts the search from the origin of the specified vector and
     * works its way along the specified axis until it finds a focusable
     * component. If no focusable component is found when the end of the axis is
     * reached, the search wraps around to the end of the opposite axis.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * @param searchVector
     *        The vector along which the search will proceed or {@code null} to
     *        return the top-most focusable component.
     * 
     * @return The focusable component at the specified location or {@code null}
     *         if no focusable component is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public IComponent getFocusableComponent(
        /* @NonNull */
        final Point location,
        /* @Nullable */
        final ComponentVector searchVector )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            return getFocusableComponent( location, searchVector, IComponent.class );
        }
    }

    /**
     * Gets the focusable component of the specified type at the specified
     * location along the specified vector.
     * 
     * <p>
     * The contract of this method is identical to
     * {@link #getFocusableComponent(Point, ComponentVector)} with the addition
     * it will only return a focusable component of the specified type.
     * </p>
     * 
     * @param <T>
     *        The component type.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * @param searchVector
     *        The vector along which the search will proceed or {@code null} to
     *        return the top-most focusable component of the specified type.
     * @param type
     *        The type of the component; must not be {@code null}.
     * 
     * @return The focusable component of the specified type at the specified
     *         location or {@code null} if no focusable component of the
     *         specified type is at that location.
     */
    @GuardedBy( "lock_" )
    /* @Nullable */
    private <T extends IComponent> T getFocusableComponent(
        /* @NonNull */
        final Point location,
        /* @Nullable */
        final ComponentVector searchVector,
        /* @NonNull */
        final Class<T> type )
    {
        assert location != null;
        assert type != null;
        assert Thread.holdsLock( lock_ );

        T focusableComponent = null;

        final List<IComponent> components = table_.getComponents( location );
        if( !components.isEmpty() )
        {
            final IComponent searchOrigin;
            final ComponentAxis searchDirection;
            if( (searchVector != null) && searchVector.getOrigin().getBounds().contains( location ) )
            {
                searchOrigin = searchVector.getOrigin();
                searchDirection = searchVector.getDirection();
            }
            else
            {
                searchOrigin = components.get( 0 );
                searchDirection = ComponentAxis.PRECEDING;
            }

            final int originIndex = Collections.binarySearch( //
                components, //
                searchOrigin, //
                new Comparator<IComponent>()
                {
                    @Override
                    public int compare(
                        final IComponent o1,
                        final IComponent o2 )
                    {
                        return o1.getPath().compareTo( o2.getPath() );
                    }
                } );
            if( originIndex >= 0 )
            {
                int index = originIndex;
                do
                {
                    index = getNextSearchIndex( components, index, searchDirection );
                    final ComponentModel componentModel = getComponentModel( components.get( index ) );
                    if( (componentModel != null) && componentModel.isFocusable() && type.isInstance( componentModel.getComponent() ) )
                    {
                        focusableComponent = type.cast( componentModel.getComponent() );
                    }

                } while( (focusableComponent == null) && (index != originIndex) );
            }
        }

        return focusableComponent;
    }

    /**
     * Gets the top-most focusable container at the specified location.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The top-most focusable container at the specified location or
     *         {@code null} if no focusable container is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public IContainer getFocusableContainer(
        /* @NonNull */
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            return getFocusableComponent( location, null, IContainer.class );
        }
    }

    /**
     * Gets the focused component.
     * 
     * @return The focused component or {@code null} if no component has the
     *         focus.
     */
    /* @Nullable */
    public IComponent getFocusedComponent()
    {
        final ComponentModel focusedComponentModel = getFocusedComponentModel();
        return (focusedComponentModel != null) ? focusedComponentModel.getComponent() : null;
    }

    /**
     * Gets the model associated with the focused component.
     * 
     * @return The model associated with the focused component or {@code null}
     *         if no component has the focus.
     */
    /* @Nullable */
    public ComponentModel getFocusedComponentModel()
    {
        synchronized( lock_ )
        {
            return focusedComponentModel_;
        }
    }

    /**
     * Gets the hovered component.
     * 
     * @return The hovered component or {@code null} if no component has the
     *         hover.
     */
    /* @Nullable */
    public IComponent getHoveredComponent()
    {
        final ComponentModel hoveredComponentModel = getHoveredComponentModel();
        return (hoveredComponentModel != null) ? hoveredComponentModel.getComponent() : null;
    }

    /**
     * Gets the model associated with the hovered component.
     * 
     * @return The model associated with the hovered component or {@code null}
     *         if no component has the hover.
     */
    /* @Nullable */
    public ComponentModel getHoveredComponentModel()
    {
        synchronized( lock_ )
        {
            return hoveredComponentModel_;
        }
    }

    /**
     * Gets the next search index for the specified search criteria.
     * 
     * @param components
     *        The list of components being searched in ascending z-order order;
     *        must not be {@code null}.
     * @param index
     *        The current search index.
     * @param searchAxis
     *        The search axis; must not be {@code null}.
     * 
     * @return The next search index.
     */
    private static int getNextSearchIndex(
        /* @NonNull */
        final List<IComponent> components,
        final int index,
        /* @NonNull */
        final ComponentAxis searchAxis )
    {
        assert components != null;
        assert searchAxis != null;

        switch( searchAxis )
        {
            case PRECEDING:
                return (index > 0) ? (index - 1) : (components.size() - 1);

            case FOLLOWING:
                return (index < (components.size() - 1)) ? (index + 1) : 0;

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
    /* @NonNull */
    public Dimension getOriginOffset()
    {
        synchronized( lock_ )
        {
            return new Dimension( originOffset_ );
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IComponentModelParent#getParent()
     */
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
    /* @NonNull */
    public ITable getTable()
    {
        return table_;
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IComponentModelParent#getTableModel()
     */
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
    /* @NonNull */
    public ITableNetwork getTableNetwork()
    {
        return tableNetwork_;
    }

    /**
     * Gets the tabletop model.
     * 
     * @return The tabletop model; never {@code null}.
     */
    /* @NonNull */
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
        table_.getTabletop().removeAllComponents();

        synchronized( lock_ )
        {
            file_ = null;
            focusedComponentModel_ = null;
            hoveredComponentModel_ = null;
            isDirty_ = false;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }

        fireTableChanged();
        fireTableModelFileChanged();
        fireTableModelFocusChanged();
        fireTableModelHoverChanged();
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

        setTableMemento( table_, file );

        synchronized( lock_ )
        {
            file_ = file;
            focusedComponentModel_ = null;
            hoveredComponentModel_ = null;
            isDirty_ = false;
            originOffset_.setSize( new Dimension( 0, 0 ) );
        }

        fireTableChanged();
        fireTableModelFileChanged();
        fireTableModelFocusChanged();
        fireTableModelHoverChanged();
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
     * Sets the focus to the specified component.
     * 
     * @param component
     *        The component to receive the focus or {@code null} if no component
     *        should have the focus.
     */
    public void setFocus(
        /* @Nullable */
        final IComponent component )
    {
        final ComponentModel componentModel = getComponentModel( component );
        final boolean componentFocusChanged;
        final ComponentModel oldFocusedComponentModel;
        final ComponentModel newFocusedComponentModel;

        synchronized( lock_ )
        {
            if( componentModel != focusedComponentModel_ )
            {
                componentFocusChanged = true;
                oldFocusedComponentModel = focusedComponentModel_;
                newFocusedComponentModel = componentModel;
                focusedComponentModel_ = componentModel;
            }
            else
            {
                componentFocusChanged = false;
                oldFocusedComponentModel = null;
                newFocusedComponentModel = null;
            }
        }

        if( componentFocusChanged )
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

    /**
     * Sets the hover to the specified component.
     * 
     * @param component
     *        The component to receive the hover or {@code null} if no component
     *        should have the hover.
     */
    public void setHover(
        /* @Nullable */
        final IComponent component )
    {
        final ComponentModel componentModel = getComponentModel( component );
        final boolean componentHoverChanged;
        final ComponentModel oldHoveredComponentModel;
        final ComponentModel newHoveredComponentModel;

        synchronized( lock_ )
        {
            if( componentModel != hoveredComponentModel_ )
            {
                componentHoverChanged = true;
                oldHoveredComponentModel = hoveredComponentModel_;
                newHoveredComponentModel = componentModel;
                hoveredComponentModel_ = componentModel;
            }
            else
            {
                componentHoverChanged = false;
                oldHoveredComponentModel = null;
                newHoveredComponentModel = null;
            }
        }

        if( componentHoverChanged )
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

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentModelFocusChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableModelFocusChanged();
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelHoverChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentModelHoverChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireTableModelHoverChanged();
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
