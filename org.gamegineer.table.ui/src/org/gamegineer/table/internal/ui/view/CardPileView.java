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
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.CardPileModelEvent;
import org.gamegineer.table.internal.ui.model.ICardPileModelListener;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;
import org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry;

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

    /** The card pile base design user interface. */
    private final ICardPileBaseDesignUI baseDesignUI_;

    /** The card pile listener for this view. */
    private ICardPileListener cardPileListener_;

    /** The card pile model listener for this view. */
    private ICardPileModelListener cardPileModelListener_;

    /** The collection of card views. */
    private final Map<ICard, CardView> cardViews_;

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
     *        The card pile base design user interface; must not be {@code null}
     *        .
     */
    CardPileView(
        /* @NonNull */
        final CardPileModel model,
        /* @NonNull */
        final ICardPileBaseDesignUI baseDesignUI )
    {
        assert model != null;
        assert baseDesignUI != null;

        baseDesignUI_ = baseDesignUI;
        cardPileListener_ = null;
        cardViews_ = new IdentityHashMap<ICard, CardView>();
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
            createCardView( (ICard)component ); // FIXME: remove cast
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
            deleteCardView( (ICard)component ); // FIXME: remove cast
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
     * Creates a card view for the specified card and adds it to the card pile
     * view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param card
     *        The card; must not be {@code null}.
     */
    private void createCardView(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;
        assert isInitialized();

        final ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry = Activator.getDefault().getCardSurfaceDesignUIRegistry();
        assert cardSurfaceDesignUIRegistry != null;
        final ICardSurfaceDesignUI backDesignUI = cardSurfaceDesignUIRegistry.getCardSurfaceDesignUI( card.getBackDesign().getId() );
        final ICardSurfaceDesignUI faceDesignUI = cardSurfaceDesignUIRegistry.getCardSurfaceDesignUI( card.getFaceDesign().getId() );
        final CardView view = new CardView( model_.getCardModel( card ), backDesignUI, faceDesignUI );
        final CardView oldView = cardViews_.put( card, view );
        assert oldView == null;
        view.initialize( this );
    }

    /**
     * Deletes the card view associated with the specified card and removes it
     * from the card pile view.
     * 
     * <p>
     * This method must only be called after the view is initialized.
     * </p>
     * 
     * @param card
     *        The card; must not be {@code null}.
     */
    private void deleteCardView(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;
        assert isInitialized();

        final CardView view = cardViews_.remove( card );
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
        cardPileListener_ = new CardPileListener();
        model_.getCardPile().addCardPileListener( cardPileListener_ );

        for( final IComponent component : model_.getCardPile().getComponents() )
        {
            createCardView( (ICard)component ); // FIXME: remove cast
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
                final CardView cardView = cardViews_.get( component );
                if( cardView != null )
                {
                    cardView.paint( c, g );
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

        for( final ICard card : new ArrayList<ICard>( cardViews_.keySet() ) )
        {
            deleteCardView( card );
        }

        model_.getCardPile().removeCardPileListener( cardPileListener_ );
        cardPileListener_ = null;
        model_.removeCardPileModelListener( cardPileModelListener_ );
        cardPileModelListener_ = null;
        tableView_ = null;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card pile listener for the card pile view.
     */
    @Immutable
    private final class CardPileListener
        extends org.gamegineer.table.core.CardPileListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code CardPileListener} class.
         */
        CardPileListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.CardPileListener#componentAdded(org.gamegineer.table.core.ContainerContentChangedEvent)
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
         * @see org.gamegineer.table.core.CardPileListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
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
}
