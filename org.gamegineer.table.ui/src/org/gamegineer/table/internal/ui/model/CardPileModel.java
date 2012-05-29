/*
 * CardPileModel.java
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
 * Created on Jan 26, 2010 at 10:41:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * The card pile model.
 */
@ThreadSafe
public final class CardPileModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile associated with this model. */
    private final ICardPile cardPile_;

    /** The component model listener for this model. */
    private final IComponentModelListener componentModelListener_;

    /** The collection of component models. */
    @GuardedBy( "lock_" )
    private final Map<IComponent, ComponentModel> componentModels_;

    /** Indicates the associated card pile has the focus. */
    @GuardedBy( "lock_" )
    private boolean isFocused_;

    /** The collection of card pile model listeners. */
    private final CopyOnWriteArrayList<ICardPileModelListener> listeners_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileModel} class.
     * 
     * @param cardPile
     *        The card pile associated with this model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public CardPileModel(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$

        cardPile_ = cardPile;
        componentModelListener_ = new ComponentModelListener();
        componentModels_ = new IdentityHashMap<IComponent, ComponentModel>();
        isFocused_ = false;
        listeners_ = new CopyOnWriteArrayList<ICardPileModelListener>();
        lock_ = new Object();

        cardPile_.addComponentListener( new ComponentListener() );
        cardPile_.addContainerListener( new ContainerListener() );

        for( final IComponent component : cardPile.getComponents() )
        {
            createComponentModel( component );
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card pile model listener to this card pile model.
     * 
     * @param listener
     *        The card pile model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered card pile model
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardPileModelListener(
        /* @NonNull */
        final ICardPileModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardPileModel_addCardPileModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Creates a component model for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The component model; never {@code null}.
     */
    /* @NonNull */
    private ComponentModel createComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assert component != null;

        final ComponentModel componentModel = new ComponentModel( component );
        componentModels_.put( component, componentModel );
        componentModel.addComponentModelListener( componentModelListener_ );
        return componentModel;
    }

    /**
     * Fires a card pile changed event.
     */
    private void fireCardPileChanged()
    {
        final CardPileModelEvent event = new CardPileModelEvent( this );
        for( final ICardPileModelListener listener : listeners_ )
        {
            try
            {
                listener.cardPileChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileModel_cardPileChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a card pile model focus changed event.
     */
    private void fireCardPileModelFocusChanged()
    {
        final CardPileModelEvent event = new CardPileModelEvent( this );
        for( final ICardPileModelListener listener : listeners_ )
        {
            try
            {
                listener.cardPileModelFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPileModel_cardPileModelFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Gets the card pile associated with this model.
     * 
     * @return The card pile associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ICardPile getCardPile()
    {
        return cardPile_;
    }

    /**
     * Gets the component model associated with the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The component model associated with the specified component;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} does not exist in the card pile associated
     *         with this model.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    /* @NonNull */
    public ComponentModel getComponentModel(
        /* @NonNull */
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final ComponentModel componentModel;
        synchronized( lock_ )
        {
            componentModel = componentModels_.get( component );
        }

        assertArgumentLegal( componentModel != null, "component", NonNlsMessages.CardPileModel_getComponentModel_component_absent ); //$NON-NLS-1$
        return componentModel;
    }

    /**
     * Indicates the associated card pile has the focus.
     * 
     * @return {@code true} if the associated card pile has the focus; otherwise
     *         {@code false}.
     */
    public boolean isFocused()
    {
        synchronized( lock_ )
        {
            return isFocused_;
        }
    }

    /**
     * Removes the specified card pile model listener from this card pile model.
     * 
     * @param listener
     *        The card pile model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered card pile model listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeCardPileModelListener(
        /* @NonNull */
        final ICardPileModelListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.CardPileModel_removeCardPileModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Sets or removes the focus from the associated card pile.
     * 
     * @param isFocused
     *        {@code true} if the associated card pile has the focus; otherwise
     *        {@code false}.
     */
    public void setFocused(
        final boolean isFocused )
    {
        synchronized( lock_ )
        {
            isFocused_ = isFocused;
        }

        fireCardPileModelFocusChanged();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A card pile component listener for the card pile model.
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
        @SuppressWarnings( "synthetic-access" )
        public void componentBoundsChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentOrientationChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentSurfaceDesignChanged(
            final ComponentEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }
    }

    /**
     * A component model listener for the card pile model.
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

            fireCardPileChanged();
        }
    }

    /**
     * A card pile container listener for the card pile model.
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
        @SuppressWarnings( "synthetic-access" )
        public void componentAdded(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                createComponentModel( event.getComponent() );
            }

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#componentRemoved(org.gamegineer.table.core.ContainerContentChangedEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void componentRemoved(
            final ContainerContentChangedEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            synchronized( lock_ )
            {
                final ComponentModel componentModel = componentModels_.remove( event.getComponent() );
                if( componentModel != null )
                {
                    componentModel.removeComponentModelListener( componentModelListener_ );
                }
            }

            fireCardPileChanged();
        }

        /*
         * @see org.gamegineer.table.core.ContainerListener#containerLayoutChanged(org.gamegineer.table.core.ContainerEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void containerLayoutChanged(
            final ContainerEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            fireCardPileChanged();
        }
    }
}
