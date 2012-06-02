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
final class ComponentView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component listener for this view. */
    private IComponentListener componentListener_;

    /** The component model listener for this view. */
    private IComponentModelListener componentModelListener_;

    /** The container view that owns this view. */
    private ContainerView containerView_;

    /** The model associated with this view. */
    private final ComponentModel model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    ComponentView(
        /* @NonNull */
        final ComponentModel model )
    {
        assert model != null;

        componentListener_ = null;
        componentModelListener_ = null;
        containerView_ = null;
        model_ = model;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the component orientation has changed.
     */
    private void componentOrientationChanged()
    {
        if( isInitialized() )
        {
            containerView_.repaintContainer( getBounds() );
        }
    }

    /**
     * Invoked after a component surface design has changed.
     */
    private void componentSurfaceDesignChanged()
    {
        if( isInitialized() )
        {
            containerView_.repaintContainer( getBounds() );
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
        final ComponentSurfaceDesign componentSurfaceDesign = model_.getComponent().getSurfaceDesign( model_.getComponent().getOrientation() );

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
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        return model_.getComponent().getBounds();
    }

    /**
     * Initializes this view.
     * 
     * <p>
     * This method must only be called when the view is uninitialized.
     * </p>
     * 
     * @param containerView
     *        The container view that owns this view; must not be {@code null}.
     */
    void initialize(
        /* @NonNull */
        final ContainerView containerView )
    {
        assert containerView != null;
        assert !isInitialized();

        containerView_ = containerView;
        componentModelListener_ = new ComponentModelListener();
        model_.addComponentModelListener( componentModelListener_ );
        componentListener_ = new ComponentListener();
        model_.getComponent().addComponentListener( componentListener_ );

        containerView_.repaintContainer( getBounds() );
    }

    /**
     * Indicates this view has been initialized.
     * 
     * @return {@code true} if this view has been initialized; otherwise
     *         {@code false}.
     */
    private boolean isInitialized()
    {
        return containerView_ != null;
    }

    /**
     * Paints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
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
     * Uninitializes this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     */
    void uninitialize()
    {
        assert isInitialized();

        containerView_.repaintContainer( getBounds() );

        model_.getComponent().removeComponentListener( componentListener_ );
        componentListener_ = null;
        model_.removeComponentModelListener( componentModelListener_ );
        componentModelListener_ = null;
        containerView_ = null;
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
