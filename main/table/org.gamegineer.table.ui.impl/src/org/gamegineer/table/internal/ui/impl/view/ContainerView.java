/*
 * ContainerView.java
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
 * Created on Jan 26, 2010 at 11:47:48 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.ui.impl.model.ComponentModel;
import org.gamegineer.table.internal.ui.impl.model.ContainerModel;
import org.gamegineer.table.internal.ui.impl.model.ContainerModelContentChangedEvent;
import org.gamegineer.table.internal.ui.impl.model.ContainerModelEvent;
import org.gamegineer.table.internal.ui.impl.model.IContainerModelListener;

/**
 * A view of a container.
 */
@NotThreadSafe
final class ContainerView
    extends ComponentView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of component views ordered from the component at the
     * bottom of the container to the component at the top of the container.
     */
    private final List<ComponentView> componentViews_;

    /** The container model listener for this view. */
    private @Nullable IContainerModelListener containerModelListener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerView} class.
     * 
     * @param containerModel
     *        The model associated with this view; must not be {@code null}.
     */
    ContainerView(
        final ContainerModel containerModel )
    {
        super( containerModel );

        componentViews_ = new ArrayList<>();
        containerModelListener_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when a new component model is added to the container model.
     * 
     * @param componentModel
     *        The added component model; must not be {@code null}.
     * @param componentModelIndex
     *        The index of the added component model; must not be negative.
     */
    private void componentModelAdded(
        final ComponentModel componentModel,
        final int componentModelIndex )
    {
        assert componentModelIndex >= 0;

        if( isInitialized() )
        {
            createComponentView( componentModel, componentModelIndex );
        }
    }

    /**
     * Invoked when a component model is removed from the container model.
     * 
     * @param componentModelIndex
     *        The index of the removed component model; must not be negative.
     */
    private void componentModelRemoved(
        final int componentModelIndex )
    {
        assert componentModelIndex >= 0;

        if( isInitialized() )
        {
            deleteComponentView( componentModelIndex );
        }
    }

    /**
     * Invoked after the container layout has changed.
     */
    private void containerLayoutChanged()
    {
        if( isInitialized() )
        {
            repaint();
        }
    }

    /**
     * Creates a component view for the specified component model.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param componentModel
     *        The component model; must not be {@code null}.
     * @param componentModelIndex
     *        The component model index; must not be negative.
     */
    private void createComponentView(
        final ComponentModel componentModel,
        final int componentModelIndex )
    {
        assert componentModelIndex >= 0;
        assert isInitialized();

        final ComponentView view = ComponentViewFactory.createComponentView( componentModel );
        componentViews_.add( componentModelIndex, view );
        view.initialize( getTableView() );
    }

    /**
     * Deletes the component view associated with the component model at the
     * specified index.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param componentModelIndex
     *        The component model index; must not be negative.
     */
    private void deleteComponentView(
        final int componentModelIndex )
    {
        assert componentModelIndex >= 0;
        assert isInitialized();

        final ComponentView view = componentViews_.remove( componentModelIndex );
        view.uninitialize();
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.view.ComponentView#getComponentModel()
     */
    @Override
    ContainerModel getComponentModel()
    {
        return (ContainerModel)super.getComponentModel();
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.view.ComponentView#initialize(org.gamegineer.table.internal.ui.impl.view.TableView)
     */
    @Override
    void initialize(
        final TableView tableView )
    {
        super.initialize( tableView );

        final IContainerModelListener containerModelListener = containerModelListener_ = new ContainerModelListener();

        final List<ComponentModel> componentModels;
        getTableEnvironmentModelLock().lock();
        try
        {
            getComponentModel().addContainerModelListener( containerModelListener );
            componentModels = getComponentModel().getComponentModels();
        }
        finally
        {
            getTableEnvironmentModelLock().unlock();
        }

        int componentModelIndex = 0;
        for( final ComponentModel componentModel : componentModels )
        {
            createComponentView( componentModel, componentModelIndex++ );
        }

        repaint();
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.view.ComponentView#paint(java.awt.Component, java.awt.Graphics)
     */
    @Override
    void paint(
        final Component component,
        final Graphics g )
    {
        super.paint( component, g );

        final Rectangle clipBounds = g.getClipBounds();
        for( final ComponentView componentView : componentViews_ )
        {
            if( clipBounds.intersects( componentView.getComponentModel().getComponent().getBounds() ) )
            {
                componentView.paint( component, g );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.impl.view.ComponentView#uninitialize()
     */
    @Override
    void uninitialize()
    {
        assert isInitialized();

        repaint();

        for( int index = componentViews_.size() - 1; index >= 0; --index )
        {
            deleteComponentView( index );
        }

        final IContainerModelListener containerModelListener = containerModelListener_;
        assert containerModelListener != null;
        getComponentModel().removeContainerModelListener( containerModelListener );
        containerModelListener_ = null;

        super.uninitialize();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A container model listener for the container view.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
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


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ContainerModelListener#componentModelAdded(org.gamegineer.table.internal.ui.impl.model.ContainerModelContentChangedEvent)
         */
        @Override
        public void componentModelAdded(
            final ContainerModelContentChangedEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ContainerView.this.componentModelAdded( event.getComponentModel(), event.getComponentModelIndex() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ContainerModelListener#componentModelRemoved(org.gamegineer.table.internal.ui.impl.model.ContainerModelContentChangedEvent)
         */
        @Override
        public void componentModelRemoved(
            final ContainerModelContentChangedEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ContainerView.this.componentModelRemoved( event.getComponentModelIndex() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ContainerModelListener#containerLayoutChanged(org.gamegineer.table.internal.ui.impl.model.ContainerModelEvent)
         */
        @Override
        public void containerLayoutChanged(
            @SuppressWarnings( "unused" )
            final ContainerModelEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ContainerView.this.containerLayoutChanged();
                }
            } );
        }
    }
}
