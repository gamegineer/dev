/*
 * CardPileView.java
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
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.CardPileModelEvent;
import org.gamegineer.table.internal.ui.model.ICardPileModelListener;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;

/**
 * A view of a card pile.
 */
@NotThreadSafe
final class CardPileView
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The horizontal padding between the focus border and the card pile in
     * table coordinates.
     */
    private static final int HORIZONTAL_PADDING = 2;

    /**
     * The vertical padding between the focus border and the card pile in table
     * coordinates.
     */
    private static final int VERTICAL_PADDING = 2;

    /** The component surface design user interface for the card pile base. */
    private final ComponentSurfaceDesignUI baseDesignUI_;

    /** The card pile model listener for this view. */
    private ICardPileModelListener cardPileModelListener_;

    /** The card pile component listener for this view. */
    private IComponentListener componentListener_;

    /** The collection of component views. */
    private final Map<IComponent, ComponentView> componentViews_;

    /** The card pile container listener for this view. */
    private IContainerListener containerListener_;

    /** The dirty bounds of this view in table coordinates. */
    private final Rectangle dirtyBounds_;

    /** The model associated with this view. */
    private final CardPileModel model_;

    /** The table view that owns this view. */
    private TableView tableView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     * @param baseDesignUI
     *        The component surface design user interface for the card pile
     *        base; must not be {@code null}.
     */
    CardPileView(
        /* @NonNull */
        final CardPileModel model,
        /* @NonNull */
        final ComponentSurfaceDesignUI baseDesignUI )
    {
        assert model != null;
        assert baseDesignUI != null;

        baseDesignUI_ = baseDesignUI;
        cardPileModelListener_ = null;
        componentListener_ = null;
        componentViews_ = new IdentityHashMap<IComponent, ComponentView>();
        containerListener_ = null;
        dirtyBounds_ = new Rectangle();
        model_ = model;
        tableView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the card pile model has gained or lost the logical focus.
     */
    private void cardPileModelFocusChanged()
    {
        if( isInitialized() )
        {
            tableView_.repaintTable( getBounds() );
        }
    }

    /**
     * Invoked when a new component is added to the container.
     * 
     * @param component
     *        The added component; must not be {@code null}.
     */
    private void componentAdded(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        if( isInitialized() )
        {
            createComponentView( component );
        }
    }

    /**
     * Invoked after the component bounds have changed.
     */
    private void componentBoundsChanged()
    {
        if( isInitialized() )
        {
            final Rectangle viewBounds = getBounds();
            dirtyBounds_.add( viewBounds );
            tableView_.repaintTable( dirtyBounds_ );
            dirtyBounds_.setBounds( viewBounds );
        }
    }

    /**
     * Invoked when a component is removed from the container.
     * 
     * @param component
     *        The removed component; must not be {@code null}.
     */
    private void componentRemoved(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        if( isInitialized() )
        {
            deleteComponentView( component );
        }
    }

    /**
     * Invoked after a component surface design has changed.
     */
    private void componentSurfaceDesignChanged()
    {
        if( isInitialized() )
        {
            tableView_.repaintTable( getBounds() );
        }
    }

    /**
     * Creates a component view for the specified component and adds it to the
     * card pile view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    private void createComponentView(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;
        assert isInitialized();

        final ComponentView view = new ComponentView( model_.getComponentModel( component ) );
        final ComponentView oldView = componentViews_.put( component, view );
        assert oldView == null;
        view.initialize( this );
    }

    /**
     * Deletes the component view associated with the specified component and
     * removes it from the card pile view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param component
     *        The component; must not be {@code null}.
     */
    private void deleteComponentView(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;
        assert isInitialized();

        final ComponentView view = componentViews_.remove( component );
        if( view != null )
        {
            view.uninitialize();
        }
    }

    /**
     * Gets the bounds of this view in table coordinates.
     * 
     * @return The bounds of this view in table coordinates; never {@code null}.
     */
    /* @NonNull */
    Rectangle getBounds()
    {
        final Rectangle bounds = model_.getCardPile().getBounds();
        bounds.grow( HORIZONTAL_PADDING, VERTICAL_PADDING );
        return bounds;
    }

    /**
     * Initializes this view.
     * 
     * <p>
     * This method must only be called when the view is uninitialized.
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
        cardPileModelListener_ = new CardPileModelListener();
        model_.addCardPileModelListener( cardPileModelListener_ );
        componentListener_ = new ComponentListener();
        model_.getCardPile().addComponentListener( componentListener_ );
        containerListener_ = new ContainerListener();
        model_.getCardPile().addContainerListener( containerListener_ );

        for( final IComponent component : model_.getCardPile().getComponents() )
        {
            createComponentView( component );
        }

        tableView_.repaintTable( dirtyBounds_ );
    }

    /**
     * Indicates this view has been initialized.
     * 
     * @return {@code true} if this view has been initialized; otherwise
     *         {@code false}.
     */
    private boolean isInitialized()
    {
        return tableView_ != null;
    }

    /**
     * Paints this view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param c
     *        The component in which to paint; must not be {@code null}.
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     */
    void paint(
        /* @NonNull */
        final Component c,
        /* @NonNull */
        final Graphics g )
    {
        assert c != null;
        assert g != null;
        assert isInitialized();

        final Rectangle viewBounds = getBounds();

        final List<IComponent> components = model_.getCardPile().getComponents();
        if( components.isEmpty() )
        {
            baseDesignUI_.getIcon().paintIcon( c, g, viewBounds.x + HORIZONTAL_PADDING, viewBounds.y + VERTICAL_PADDING );
        }
        else
        {
            for( final IComponent component : components )
            {
                final ComponentView componentView = componentViews_.get( component );
                if( componentView != null )
                {
                    componentView.paint( c, g );
                }
            }
        }

        if( model_.isFocused() )
        {
            final Color oldColor = g.getColor();
            g.setColor( Color.GREEN );
            g.drawRect( viewBounds.x, viewBounds.y, viewBounds.width - 1, viewBounds.height - 1 );
            g.setColor( oldColor );
        }
    }

    /**
     * Repaints the specified region of the card pile.
     * 
     * @param region
     *        The region of the card pile to repaint in table coordinates.
     */
    void repaintCardPile(
        /* @NonNull */
        final Rectangle region )
    {
        assert region != null;

        tableView_.repaintTable( region );
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

        tableView_.repaintTable( dirtyBounds_ );

        for( final IComponent component : new ArrayList<IComponent>( componentViews_.keySet() ) )
        {
            deleteComponentView( component );
        }

        model_.getCardPile().removeContainerListener( containerListener_ );
        containerListener_ = null;
        model_.getCardPile().removeComponentListener( componentListener_ );
        componentListener_ = null;
        model_.removeCardPileModelListener( cardPileModelListener_ );
        cardPileModelListener_ = null;
        tableView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card pile model listener for the card pile view.
     */
    @Immutable
    private final class CardPileModelListener
        extends org.gamegineer.table.internal.ui.model.CardPileModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileModelListener}
         * class.
         */
        CardPileModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.CardPileModelListener#cardPileModelFocusChanged(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
         */
        @Override
        public void cardPileModelFocusChanged(
            final CardPileModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.cardPileModelFocusChanged();
                }
            } );
        }
    }

    /**
     * A component listener for the card pile view.
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
                    CardPileView.this.componentBoundsChanged();
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
                    CardPileView.this.componentSurfaceDesignChanged();
                }
            } );
        }
    }

    /**
     * A container listener for the card pile view.
     */
    @Immutable
    private final class ContainerListener
        extends org.gamegineer.table.core.ContainerListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ContainerListener} class.
         */
        ContainerListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.componentAdded( event.getComponent() );
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    CardPileView.this.componentRemoved( event.getComponent() );
                }
            } );
        }
    }
}
