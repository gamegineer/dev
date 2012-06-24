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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.IComponentModelListener;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;

/**
 * A view of a component.
 */
@NotThreadSafe
class ComponentView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component listener for this view. */
    private IComponentListener componentListener_;

    /** The model associated with this view. */
    private final ComponentModel componentModel_;

    /** The component model listener for this view. */
    private IComponentModelListener componentModelListener_;

    /** The dirty bounds of this view in table coordinates. */
    private final Rectangle dirtyBounds_;

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
        assert componentModel != null;

        componentListener_ = null;
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
            final Rectangle viewBounds = getBounds();
            dirtyBounds_.add( viewBounds );
            repaint();
            dirtyBounds_.setBounds( viewBounds );
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
     * <p>
     * This method is not intended to be called by clients; it is only for the
     * use of subclasses.
     * </p>
     * 
     * @return The active component surface design user interface; never
     *         {@code null}.
     */
    /* @NonNull */
    final ComponentSurfaceDesignUI getActiveComponentSurfaceDesignUI()
    {
        final ComponentSurfaceDesign componentSurfaceDesign = componentModel_.getComponent().getSurfaceDesign( componentModel_.getComponent().getOrientation() );

        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry = Activator.getDefault().getComponentSurfaceDesignUIRegistry();
        if( componentSurfaceDesignUIRegistry != null )
        {
            final ComponentSurfaceDesignUI componentSurfaceDesignUI = componentSurfaceDesignUIRegistry.getComponentSurfaceDesignUI( componentSurfaceDesign.getId() );
            if( componentSurfaceDesignUI != null )
            {
                return componentSurfaceDesignUI;
            }
        }

        return ViewUtils.createDefaultComponentSurfaceDesignUI( componentSurfaceDesign );
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * <p>
     * Subclasses are not required to call the superclass method.
     * </p>
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        return componentModel_.getComponent().getBounds();
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
        componentListener_ = new ComponentListener();
        componentModel_.getComponent().addComponentListener( componentListener_ );

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
     * This method must only be called after the view is initialized. Subclasses
     * are not required to call the superclass implementation.
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
        getActiveComponentSurfaceDesignUI().getIcon().paintIcon( component, g, viewBounds.x, viewBounds.y );
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

        componentModel_.getComponent().removeComponentListener( componentListener_ );
        componentListener_ = null;
        componentModel_.removeComponentModelListener( componentModelListener_ );
        componentModelListener_ = null;
        tableView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component listener for the component view.
     */
    @Immutable
    private final class ComponentListener
        extends org.gamegineer.table.core.ComponentListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListener} class.
         */
        ComponentListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            final ComponentEvent event )
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
         * @see org.gamegineer.table.core.ComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            final ComponentEvent event )
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
         * @see org.gamegineer.table.core.ComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
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
    }
}