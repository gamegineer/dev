/*
 * ComponentView.java
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
 * Created on Oct 15, 2009 at 10:41:19 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.internal.ui.impl.model.ComponentModel;
import org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent;
import org.gamegineer.table.internal.ui.impl.model.IComponentModelListener;
import org.gamegineer.table.internal.ui.impl.model.ITableEnvironmentModelLock;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.ComponentSurfaceDesignUIRegistry;
import org.gamegineer.table.ui.NoSuchComponentSurfaceDesignUIException;

/**
 * A view of a component.
 */
@NotThreadSafe
class ComponentView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The color used to mask the component surface when it has the hover. */
    private static final Color HOVER_COLOR = new Color( Color.YELLOW.getRGB() + (64 << 24), true );

    /** The model associated with this view. */
    private final ComponentModel componentModel_;

    /** The component model listener for this view. */
    private @Nullable IComponentModelListener componentModelListener_;

    /** The dirty bounds of this view in table coordinates. */
    private final Rectangle dirtyBounds_;

    /** The table view that owns this view. */
    private @Nullable TableView tableView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentView} class.
     * 
     * @param componentModel
     *        The model associated with this view; must not be {@code null}.
     */
    ComponentView(
        final ComponentModel componentModel )
    {
        componentModel_ = componentModel;
        componentModelListener_ = null;
        dirtyBounds_ = new Rectangle();
        tableView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the component bounds have changed.
     */
    private void componentBoundsChanged()
    {
        if( isInitialized() )
        {
            final Rectangle bounds = componentModel_.getComponent().getBounds();
            dirtyBounds_.add( bounds );
            repaint();
            dirtyBounds_.setBounds( bounds );
        }
    }

    /**
     * Invoked after the component model has gained or lost the logical focus.
     */
    private void componentModelFocusChanged()
    {
        if( isInitialized() )
        {
            repaint();
        }
    }

    /**
     * Invoked after the component model has gained or lost the logical hover.
     */
    private void componentModelHoverChanged()
    {
        if( isInitialized() )
        {
            repaint();
        }
    }

    /**
     * Invoked after the component orientation has changed.
     */
    private void componentOrientationChanged()
    {
        if( isInitialized() )
        {
            repaint();
        }
    }

    /**
     * Invoked after a component surface design has changed.
     */
    private void componentSurfaceDesignChanged()
    {
        if( isInitialized() )
        {
            repaint();
        }
    }

    /**
     * Gets the model associated with this view.
     * 
     * <p>
     * Subclasses are not required to call the superclass method.
     * </p>
     * 
     * @return The model associated with this view; never {@code null}.
     */
    ComponentModel getComponentModel()
    {
        return componentModel_;
    }

    /**
     * Gets the user interface for the specified component surface design.
     * 
     * @param componentSurfaceDesign
     *        The component surface design; must not be {@code null}.
     * 
     * @return The user interface for the active component surface design; never
     *         {@code null}.
     */
    private static ComponentSurfaceDesignUI getComponentSurfaceDesignUI(
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        try
        {
            return ComponentSurfaceDesignUIRegistry.getComponentSurfaceDesignUI( componentSurfaceDesign.getId() );
        }
        catch( final NoSuchComponentSurfaceDesignUIException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentView_getComponentSurfaceDesignUI_notAvailable( componentSurfaceDesign.getId() ), e );
        }

        return ViewUtils.createDefaultComponentSurfaceDesignUI( componentSurfaceDesign );
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock; never {@code null}.
     */
    final ITableEnvironmentModelLock getTableEnvironmentModelLock()
    {
        return componentModel_.getTableEnvironmentModel().getLock();
    }

    /**
     * Gets the table view that owns this view.
     * 
     * <p>
     * This method is not intended to be called by clients; it is only for the
     * use of subclasses.
     * </p>
     * 
     * @return The table view that owns this view; never {@code null}.
     */
    final TableView getTableView()
    {
        assert tableView_ != null;
        return tableView_;
    }

    /**
     * Initializes this view.
     * 
     * <p>
     * This method must only be called when the view is uninitialized.
     * Subclasses must call the superclass implementation.
     * </p>
     * 
     * @param tableView
     *        The table view that owns this view; must not be {@code null}.
     */
    void initialize(
        final TableView tableView )
    {
        assert !isInitialized();

        tableView_ = tableView;
        dirtyBounds_.setBounds( componentModel_.getComponent().getBounds() );
        componentModelListener_ = new ComponentModelListener();
        componentModel_.addComponentModelListener( componentModelListener_ );

        repaint();
    }

    /**
     * Indicates this view has been initialized.
     * 
     * @return {@code true} if this view has been initialized; otherwise
     *         {@code false}.
     */
    final boolean isInitialized()
    {
        return tableView_ != null;
    }

    /**
     * Paints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized. This
     * implementation paints the active surface design and the border.
     * Subclasses are not required to call the superclass implementation.
     * </p>
     * 
     * @param component
     *        The component in which to paint; must not be {@code null}.
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     */
    void paint(
        final Component component,
        final Graphics g )
    {
        assert isInitialized();

        final Rectangle bounds;
        final ComponentSurfaceDesign componentSurfaceDesign;
        final boolean isHovered;
        getTableEnvironmentModelLock().lock();
        try
        {
            bounds = componentModel_.getComponent().getBounds();
            componentSurfaceDesign = componentModel_.getComponent().getSurfaceDesign( componentModel_.getComponent().getOrientation() );
            isHovered = componentModel_.isHovered();
        }
        finally
        {
            getTableEnvironmentModelLock().unlock();
        }

        getComponentSurfaceDesignUI( componentSurfaceDesign ).getIcon().paintIcon( component, g, bounds.x, bounds.y );

        if( isHovered )
        {
            // TODO: mask rectangle with the non-transparent pixels in the active surface image
            // so we don't paint over pixels that should be fully transparent
            final Color oldColor = g.getColor();
            g.setColor( HOVER_COLOR );
            g.fillRect( bounds.x, bounds.y, bounds.width, bounds.height );
            g.setColor( oldColor );
        }
    }

    /**
     * Repaints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized. This
     * method is not intended to be called by clients; it is only for the use of
     * subclasses.
     * </p>
     */
    final void repaint()
    {
        assert isInitialized();

        getTableView().repaintTable( dirtyBounds_ );
    }

    /**
     * Uninitializes this view.
     * 
     * <p>
     * This method must only be called after the view is initialized. Subclasses
     * must call the superclass implementation.
     * </p>
     */
    void uninitialize()
    {
        assert isInitialized();

        repaint();

        final IComponentModelListener componentModelListener = componentModelListener_;
        assert componentModelListener != null;
        componentModel_.removeComponentModelListener( componentModelListener );
        componentModelListener_ = null;
        tableView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component model listener for the component view.
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
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentBoundsChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentBoundsChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ComponentView.this.componentBoundsChanged();
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
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ComponentView.this.componentModelFocusChanged();
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
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ComponentView.this.componentModelHoverChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentOrientationChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentOrientationChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ComponentView.this.componentOrientationChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ComponentModelListener#componentSurfaceDesignChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            @SuppressWarnings( "unused" )
            final ComponentModelEvent event )
        {
            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                public void run()
                {
                    ComponentView.this.componentSurfaceDesignChanged();
                }
            } );
        }
    }
}
