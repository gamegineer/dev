/*
 * ComponentView.java
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
 * Created on Oct 15, 2009 at 10:41:19 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.ComponentModelEvent;
import org.gamegineer.table.internal.ui.model.IComponentModelListener;
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

    /** The default padding. */
    private static final Dimension DEFAULT_PADDING = new Dimension( 0, 0 );

    /** The model associated with this view. */
    private final ComponentModel componentModel_;

    /** The component model listener for this view. */
    private IComponentModelListener componentModelListener_;

    /** The dirty bounds of this view in table coordinates. */
    private final Rectangle dirtyBounds_;

    /**
     * The amount of padding between the component surface and the border in
     * table coordinates.
     */
    private final Dimension padding_;

    /** The table view that owns this view. */
    private TableView tableView_;


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
        /* @NonNull */
        final ComponentModel componentModel )
    {
        this( componentModel, DEFAULT_PADDING );
    }

    /**
     * Initializes a new instance of the {@code ComponentView} class with the
     * specified amount of padding.
     * 
     * <p>
     * This constructor is not intended to be called by clients; it is only for
     * the use of subclasses.
     * </p>
     * 
     * @param componentModel
     *        The model associated with this view; must not be {@code null}.
     * @param padding
     *        The amount of padding between the component surface and the
     *        border in table coordinates; must not be negative.
     */
    ComponentView(
        /* @NonNull */
        final ComponentModel componentModel,
        /* @NonNull */
        final Dimension padding )
    {
        assert componentModel != null;
        assert padding != null;
        assert (padding.width >= 0) && (padding.height >= 0);

        componentModel_ = componentModel;
        componentModelListener_ = null;
        dirtyBounds_ = new Rectangle();
        padding_ = new Dimension( padding );
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
            final Rectangle viewBounds = getBounds();
            dirtyBounds_.add( viewBounds );
            repaint();
            dirtyBounds_.setBounds( viewBounds );
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
     * Gets the active component surface design user interface.
     * 
     * @return The active component surface design user interface; never
     *         {@code null}.
     */
    /* @NonNull */
    private ComponentSurfaceDesignUI getActiveComponentSurfaceDesignUI()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = componentModel_.getComponent().getSurfaceDesign( componentModel_.getComponent().getOrientation() );
        try
        {
            return ComponentSurfaceDesignUIRegistry.getComponentSurfaceDesignUI( componentSurfaceDesign.getId() );
        }
        catch( final NoSuchComponentSurfaceDesignUIException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentView_getActiveComponentSurfaceDesignUI_notAvailable( componentSurfaceDesign.getId() ), e );
        }

        return ViewUtils.createDefaultComponentSurfaceDesignUI( componentSurfaceDesign );
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    final Rectangle getBounds()
    {
        final Rectangle bounds = componentModel_.getComponent().getBounds();
        bounds.grow( padding_.width, padding_.height );
        return bounds;
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
    /* @NonNull */
    ComponentModel getComponentModel()
    {
        return componentModel_;
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
    /* @NonNull */
    final TableView getTableView()
    {
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
        /* @NonNull */
        final TableView tableView )
    {
        assert tableView != null;
        assert !isInitialized();

        tableView_ = tableView;
        dirtyBounds_.setBounds( getBounds() );
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
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final Graphics g )
    {
        assert component != null;
        assert g != null;
        assert isInitialized();

        final Rectangle viewBounds = getBounds();
        paintSurface( component, g, viewBounds );
        paintBorder( g, viewBounds );
    }

    /**
     * Paints the component border.
     * 
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     * @param viewBounds
     *        The view bounds; must not be {@code null}.
     */
    private void paintBorder(
        /* @NonNull */
        final Graphics g,
        /* @NonNull */
        final Rectangle viewBounds )
    {
        assert g != null;
        assert viewBounds != null;

        final Color borderColor;
        if( componentModel_.isFocused() )
        {
            borderColor = Color.GREEN;
        }
        else if( componentModel_.isHovered() )
        {
            borderColor = Color.YELLOW;
        }
        else
        {
            borderColor = null;
        }

        if( borderColor != null )
        {
            final Color oldColor = g.getColor();
            g.setColor( borderColor );
            g.drawRect( viewBounds.x, viewBounds.y, viewBounds.width - 1, viewBounds.height - 1 );
            g.setColor( oldColor );
        }
    }

    /**
     * Paints the component surface.
     * 
     * @param component
     *        The component in which to paint; must not be {@code null}.
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     * @param viewBounds
     *        The view bounds; must not be {@code null}.
     */
    private void paintSurface(
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final Graphics g,
        /* @NonNull */
        final Rectangle viewBounds )
    {
        assert component != null;
        assert g != null;
        assert viewBounds != null;

        getActiveComponentSurfaceDesignUI().getIcon().paintIcon( component, g, viewBounds.x + padding_.width, viewBounds.y + padding_.height );
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

        tableView_.repaintTable( dirtyBounds_ );
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

        componentModel_.removeComponentModelListener( componentModelListener_ );
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
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentBoundsChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentBoundsChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ComponentView.this.componentBoundsChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentModelFocusChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ComponentView.this.componentModelFocusChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentModelHoverChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentModelHoverChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ComponentView.this.componentModelHoverChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentOrientationChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ComponentView.this.componentOrientationChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.internal.ui.model.ComponentModelListener#componentSurfaceDesignChanged(org.gamegineer.table.internal.ui.model.ComponentModelEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    ComponentView.this.componentSurfaceDesignChanged();
                }
            } );
        }
    }
}
