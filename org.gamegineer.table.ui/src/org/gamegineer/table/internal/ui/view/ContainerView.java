/*
 * ContainerView.java
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
 * Created on Jan 26, 2010 at 11:47:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.ContainerModel;
import org.gamegineer.table.internal.ui.model.ContainerModelContentChangedEvent;
import org.gamegineer.table.internal.ui.model.ContainerModelEvent;
import org.gamegineer.table.internal.ui.model.IContainerModelListener;
import org.gamegineer.table.internal.ui.model.TableModelUtils;

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
     * The horizontal padding between the focus border and the container in
     * table coordinates.
     */
    private static final int HORIZONTAL_PADDING = 2;

    /**
     * The vertical padding between the focus border and the container in table
     * coordinates.
     */
    private static final int VERTICAL_PADDING = 2;

    /**
     * The collection of component views. The key is the component model. The
     * value is the component view.
     */
    private final Map<ComponentModel, ComponentView> componentViews_;

    /** The model associated with this view. */
    private final ContainerModel containerModel_;

    /** The container model listener for this view. */
    private IContainerModelListener containerModelListener_;


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
        /* @NonNull */
        final ContainerModel containerModel )
    {
        super( containerModel );

        componentViews_ = new IdentityHashMap<ComponentModel, ComponentView>();
        containerModel_ = containerModel;
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
     */
    private void componentModelAdded(
        /* @NonNull */
        final ComponentModel componentModel )
    {
        assert componentModel != null;

        if( isInitialized() )
        {
            createComponentView( componentModel );
        }
    }

    /**
     * Invoked when a component model is removed from the container model.
     * 
     * @param componentModel
     *        The removed component model; must not be {@code null}.
     */
    private void componentModelRemoved(
        /* @NonNull */
        final ComponentModel componentModel )
    {
        assert componentModel != null;

        if( isInitialized() )
        {
            deleteComponentView( componentModel );
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
     * Creates a component view for the specified component model and adds it to
     * the container view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param componentModel
     *        The component model; must not be {@code null}.
     */
    private void createComponentView(
        /* @NonNull */
        final ComponentModel componentModel )
    {
        assert componentModel != null;
        assert isInitialized();

        final ComponentView view = ComponentViewFactory.createComponentView( componentModel );
        final ComponentView oldView = componentViews_.put( componentModel, view );
        assert oldView == null;
        view.initialize( getTableView() );
    }

    /**
     * Deletes the component view associated with the specified component model
     * and removes it from the container view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param componentModel
     *        The component model; must not be {@code null}.
     */
    private void deleteComponentView(
        /* @NonNull */
        final ComponentModel componentModel )
    {
        assert componentModel != null;
        assert isInitialized();

        final ComponentView view = componentViews_.remove( componentModel );
        if( view != null )
        {
            view.uninitialize();
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.view.ComponentView#getBounds()
     */
    @Override
    Rectangle getBounds()
    {
        final Rectangle bounds = super.getBounds();
        bounds.grow( HORIZONTAL_PADDING, VERTICAL_PADDING );
        return bounds;
    }

    /*
     * @see org.gamegineer.table.internal.ui.view.ComponentView#initialize(org.gamegineer.table.internal.ui.view.TableView)
     */
    @Override
    void initialize(
        final TableView tableView )
    {
        super.initialize( tableView );

        containerModelListener_ = new ContainerModelListener();

        final List<ComponentModel> componentModels = TableModelUtils.addContainerModelListenerAndGetComponentModels( containerModel_, containerModelListener_ );
        for( final ComponentModel componentModel : componentModels )
        {
            createComponentView( componentModel );
        }

        repaint();
    }

    /*
     * @see org.gamegineer.table.internal.ui.view.ComponentView#paint(java.awt.Component, java.awt.Graphics)
     */
    @Override
    void paint(
        final Component c,
        final Graphics g )
    {
        assert c != null;
        assert g != null;
        assert isInitialized();

        final Rectangle clipBounds = g.getClipBounds();
        final Rectangle viewBounds = getBounds();

        getActiveComponentSurfaceDesignUI().getIcon().paintIcon( c, g, viewBounds.x + HORIZONTAL_PADDING, viewBounds.y + VERTICAL_PADDING );

        for( final ComponentModel componentModel : containerModel_.getComponentModels() )
        {
            final ComponentView componentView = componentViews_.get( componentModel );
            if( componentView != null )
            {
                if( clipBounds.intersects( componentView.getBounds() ) )
                {
                    componentView.paint( c, g );
                }
            }
        }

        if( containerModel_.isFocused() )
        {
            final Color oldColor = g.getColor();
            g.setColor( Color.GREEN );
            g.drawRect( viewBounds.x, viewBounds.y, viewBounds.width - 1, viewBounds.height - 1 );
            g.setColor( oldColor );
        }
    }

    /*
     * @see org.gamegineer.table.internal.ui.view.ComponentView#uninitialize()
     */
    @Override
    void uninitialize()
    {
        assert isInitialized();

        repaint();

        for( final ComponentModel componentModel : new ArrayList<ComponentModel>( componentViews_.keySet() ) )
        {
            deleteComponentView( componentModel );
        }

        containerModel_.removeContainerModelListener( containerModelListener_ );
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
         * @see org.gamegineer.table.internal.ui.model.ContainerModelListener#componentModelAdded(org.gamegineer.table.internal.ui.model.ContainerModelContentChangedEvent)
         */
        @Override
        public void componentModelAdded(
            final ContainerModelContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ContainerView.this.componentModelAdded( event.getComponentModel() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ContainerModelListener#componentModelRemoved(org.gamegineer.table.internal.ui.model.ContainerModelContentChangedEvent)
         */
        @Override
        public void componentModelRemoved(
            final ContainerModelContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ContainerView.this.componentModelRemoved( event.getComponentModel() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ContainerModelListener#containerLayoutChanged(org.gamegineer.table.internal.ui.model.ContainerModelEvent)
         */
        @Override
        public void containerLayoutChanged(
            final ContainerModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ContainerView.this.containerLayoutChanged();
                }
            } );
        }
    }
}
