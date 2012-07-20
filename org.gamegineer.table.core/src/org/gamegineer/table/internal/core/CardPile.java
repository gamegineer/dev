/*
 * CardPile.java
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
 * Created on Jan 14, 2010 at 11:11:09 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardPileLayouts;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ContainerEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerListener;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardPile}.
 */
@ThreadSafe
final class CardPile
    extends Container
    implements ICardPile
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the design of the card pile
     * base.
     */
    private static final String BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "baseDesign"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the collection of cards in
     * the card pile.
     */
    private static final String CARDS_MEMENTO_ATTRIBUTE_NAME = "cards"; //$NON-NLS-1$

    /** The default card pile base design. */
    private static final ComponentSurfaceDesign DEFAULT_BASE_DESIGN = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.CardPile.DEFAULT_BASE_DESIGN" ), 0, 0 ); //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the card pile layout.
     */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card pile origin. */
    private static final String ORIGIN_MEMENTO_ATTRIBUTE_NAME = "origin"; //$NON-NLS-1$

    /** The collection of supported card pile orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardPileOrientation.values( CardPileOrientation.class ) ) );

    /** The design of the card pile base. */
    @GuardedBy( "getLock()" )
    private ComponentSurfaceDesign baseDesign_;

    /** The collection of cards in this card pile ordered from bottom to top. */
    @GuardedBy( "getLock()" )
    private final List<Card> cards_;

    /** The collection of component listeners. */
    private final CopyOnWriteArrayList<IComponentListener> componentListeners_;

    /** The collection of container listeners. */
    private final CopyOnWriteArrayList<IContainerListener> containerListeners_;

    /** The card pile layout. */
    @GuardedBy( "getLock()" )
    private IContainerLayout layout_;

    /** The card pile origin in table coordinates. */
    @GuardedBy( "getLock()" )
    private final Point origin_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPile} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the card pile; must not be
     *        {@code null}.
     */
    CardPile(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );

        baseDesign_ = DEFAULT_BASE_DESIGN;
        cards_ = new ArrayList<Card>();
        componentListeners_ = new CopyOnWriteArrayList<IComponentListener>();
        containerListeners_ = new CopyOnWriteArrayList<IContainerListener>();
        layout_ = CardPileLayouts.STACKED;
        origin_ = new Point( 0, 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainer#addComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public void addComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        addComponents( Collections.singletonList( component ) );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#addComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void addComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardPile_addComponentListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addComponents(java.util.List)
     */
    @Override
    public void addComponents(
        final List<IComponent> components )
    {
        assertArgumentNotNull( components, "components" ); //$NON-NLS-1$

        final List<Card> addedCards = new ArrayList<Card>();
        final int firstCardIndex;
        final boolean cardPileBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();
            firstCardIndex = cards_.size();

            for( final IComponent component : components )
            {
                final Card typedCard = (Card)component;
                if( typedCard == null )
                {
                    throw new IllegalArgumentException( NonNlsMessages.CardPile_addComponents_components_containsNullElement );
                }
                assertArgumentLegal( typedCard.getContainer() == null, "components", NonNlsMessages.CardPile_addComponents_components_containsOwnedComponent ); //$NON-NLS-1$
                assertArgumentLegal( typedCard.getTableEnvironment() == getTableEnvironment(), "components", NonNlsMessages.CardPile_addComponents_components_containsComponentCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

                typedCard.setContainer( this );
                cards_.add( typedCard );
                addedCards.add( typedCard );
            }

            layout_.layout( this );

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !addedCards.isEmpty() || cardPileBoundsChanged )
        {
            addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    int cardIndex = firstCardIndex;
                    for( final ICard card : addedCards )
                    {
                        fireComponentAdded( card, cardIndex++ );
                    }

                    if( cardPileBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#addContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public void addContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.addIfAbsent( listener ), "listener", NonNlsMessages.CardPile_addContainerListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public Object createMemento()
    {
        final Map<String, Object> memento = new HashMap<String, Object>();

        getLock().lock();
        try
        {
            memento.put( BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, baseDesign_ );
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, layout_ );
            memento.put( ORIGIN_MEMENTO_ATTRIBUTE_NAME, new Point( origin_ ) );

            final List<Object> cardMementos = new ArrayList<Object>( cards_.size() );
            for( final ICard card : cards_ )
            {
                cardMementos.add( card.createMemento() );
            }
            memento.put( CARDS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( cardMementos ) );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Fires a component added event.
     * 
     * @param component
     *        The added component; must not be {@code null}.
     * @param componentIndex
     *        The index of the added component; must not be negative.
     */
    private void fireComponentAdded(
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = new ContainerContentChangedEvent( this, component, componentIndex );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.componentAdded( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentAdded_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component bounds changed event.
     */
    private void fireComponentBoundsChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentBoundsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component orientation changed event.
     */
    private void fireComponentOrientationChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentOrientationChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component removed event.
     * 
     * @param component
     *        The removed component; must not be {@code null}.
     * @param componentIndex
     *        The index of the removed component; must not be negative.
     */
    private void fireComponentRemoved(
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        assert component != null;
        assert componentIndex >= 0;
        assert !getLock().isHeldByCurrentThread();

        final ContainerContentChangedEvent event = new ContainerContentChangedEvent( this, component, componentIndex );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.componentRemoved( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentRemoved_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component surface design changed event.
     */
    private void fireComponentSurfaceDesignChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentEvent event = new ComponentEvent( this );
        for( final IComponentListener listener : componentListeners_ )
        {
            try
            {
                listener.componentSurfaceDesignChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_componentSurfaceDesignChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a container layout changed event.
     */
    private void fireContainerLayoutChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ContainerEvent event = new ContainerEvent( this );
        for( final IContainerListener listener : containerListeners_ )
        {
            try
            {
                listener.containerLayoutChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.CardPile_containerLayoutChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Creates a new instance of the {@code CardPile} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new card pile; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial card pile state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code CardPile} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static CardPile fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final CardPile cardPile = new CardPile( tableEnvironment );

        final ComponentSurfaceDesign baseDesign = MementoUtils.getAttribute( memento, BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        cardPile.setSurfaceDesign( CardPileOrientation.BASE, baseDesign );

        final Point location = MementoUtils.getAttribute( memento, ORIGIN_MEMENTO_ATTRIBUTE_NAME, Point.class );
        cardPile.setLocation( location );

        final IContainerLayout layout = MementoUtils.getAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, IContainerLayout.class );
        cardPile.setLayout( layout );

        @SuppressWarnings( "unchecked" )
        final List<Object> cardMementos = MementoUtils.getAttribute( memento, CARDS_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object cardMemento : cardMementos )
        {
            cardPile.addComponent( Card.fromMemento( tableEnvironment, cardMemento ) );
        }

        return cardPile;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getBounds()
     */
    @Override
    public Rectangle getBounds()
    {
        getLock().lock();
        try
        {
            if( cards_.isEmpty() )
            {
                return new Rectangle( origin_, baseDesign_.getSize() );
            }

            final Rectangle topCardBounds = cards_.get( cards_.size() - 1 ).getBounds();
            final Rectangle bottomCardBounds = cards_.get( 0 ).getBounds();
            return topCardBounds.union( bottomCardBounds );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getComponent(int)
     */
    @Override
    public Component getComponent(
        final int index )
    {
        getLock().lock();
        try
        {
            assertArgumentLegal( (index >= 0) && (index < cards_.size()), "index", NonNlsMessages.CardPile_getComponentFromIndex_index_outOfRange ); //$NON-NLS-1$

            return cards_.get( index );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponentCount()
     */
    @Override
    public int getComponentCount()
    {
        getLock().lock();
        try
        {
            return cards_.size();
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getComponentIndex(org.gamegineer.table.internal.core.Component)
     */
    @Override
    int getComponentIndex(
        final Component component )
    {
        assert component != null;
        assert getLock().isHeldByCurrentThread();

        final int index = cards_.indexOf( component );
        assert index != -1;
        return index;
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getComponentIndex(java.awt.Point)
     */
    @Override
    int getComponentIndex(
        final Point location )
    {
        assert location != null;
        assert getLock().isHeldByCurrentThread();

        return layout_.getComponentIndex( this, location );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getComponents()
     */
    @Override
    public List<IComponent> getComponents()
    {
        getLock().lock();
        try
        {
            return new ArrayList<IComponent>( cards_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IContainer#getLayout()
     */
    @Override
    public IContainerLayout getLayout()
    {
        getLock().lock();
        try
        {
            return layout_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getLocation()
     */
    @Override
    public Point getLocation()
    {
        return getBounds().getLocation();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrientation()
     */
    @Override
    public ComponentOrientation getOrientation()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getOrigin()
     */
    @Override
    public Point getOrigin()
    {
        getLock().lock();
        try
        {
            return new Point( origin_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return getBounds().getSize();
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSurfaceDesign(org.gamegineer.table.core.ComponentOrientation)
     */
    @Override
    public ComponentSurfaceDesign getSurfaceDesign(
        final ComponentOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof CardPileOrientation, "orientation", NonNlsMessages.CardPile_orientation_illegal ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == CardPileOrientation.BASE )
            {
                return baseDesign_;
            }

            throw new AssertionError( "unknown card pile orientation" ); //$NON-NLS-1$
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#hasComponents()
     */
    @Override
    boolean hasComponents()
    {
        assert getLock().isHeldByCurrentThread();

        return !cards_.isEmpty();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }

    /**
     * Indicates the specified memento is a {@code CardPile} memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return {@code true} if the specified memento is a {@code CardPile}
     *         memento; otherwise {@code false}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    static boolean isMemento(
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert memento != null;

        return MementoUtils.hasAttribute( memento, BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, CARDS_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, ORIGIN_MEMENTO_ATTRIBUTE_NAME );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent()
     */
    @Override
    public IComponent removeComponent()
    {
        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                return cards_.isEmpty() ? 0 : cards_.size() - 1;
            }
        };
        final List<IComponent> components = removeComponents( componentRangeStrategy );
        assert components.size() <= 1;
        return components.isEmpty() ? null : components.get( 0 );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponent(org.gamegineer.table.core.IComponent)
     */
    @Override
    public void removeComponent(
        final IComponent component )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$

        final int componentIndex;

        getLock().lock();
        try
        {
            assertArgumentLegal( component.getContainer() == this, "component", NonNlsMessages.CardPile_removeComponent_component_notOwned ); //$NON-NLS-1$

            componentIndex = cards_.indexOf( component );
            assert componentIndex != -1;
            final Component typedComponent = cards_.remove( componentIndex );
            assert typedComponent != null;
            typedComponent.setContainer( null );
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentRemoved( component, componentIndex );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#removeComponentListener(org.gamegineer.table.core.IComponentListener)
     */
    @Override
    public void removeComponentListener(
        final IComponentListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( componentListeners_.remove( listener ), "listener", NonNlsMessages.CardPile_removeComponentListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponents()
     */
    @Override
    public List<IComponent> removeComponents()
    {
        return removeComponents( new ComponentRangeStrategy() );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeComponents(java.awt.Point)
     */
    @Override
    public List<IComponent> removeComponents(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        final ComponentRangeStrategy componentRangeStrategy = new ComponentRangeStrategy()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            int getLowerIndex()
            {
                final int componentIndex = getComponentIndex( location );
                return (componentIndex != -1) ? componentIndex : cards_.size();
            }
        };
        return removeComponents( componentRangeStrategy );
    }

    /**
     * Removes all components from this container in the specified range.
     * 
     * @param componentRangeStrategy
     *        The strategy used to determine the range of components to remove;
     *        must not be {@code null}. The strategy will be invoked while the
     *        table lock is held.
     * 
     * @return The collection of components removed from this container; never
     *         {@code null}. The components are returned in order from the
     *         component nearest the bottom of the container to the component
     *         nearest the top of the container.
     */
    /* @NonNull */
    private List<IComponent> removeComponents(
        /* @NonNull */
        final ComponentRangeStrategy componentRangeStrategy )
    {
        assert componentRangeStrategy != null;

        final List<Card> removedCards = new ArrayList<Card>();
        final int upperCardIndex = componentRangeStrategy.getUpperIndex() - 1;
        final boolean cardPileBoundsChanged;

        getLock().lock();
        try
        {
            final Rectangle oldBounds = getBounds();

            removedCards.addAll( cards_.subList( componentRangeStrategy.getLowerIndex(), componentRangeStrategy.getUpperIndex() ) );
            cards_.removeAll( removedCards );
            for( final Card card : removedCards )
            {
                card.setContainer( null );
            }

            final Rectangle newBounds = getBounds();
            cardPileBoundsChanged = !newBounds.equals( oldBounds );
        }
        finally
        {
            getLock().unlock();
        }

        if( !removedCards.isEmpty() || cardPileBoundsChanged )
        {
            addEventNotification( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    // ensure events are fired in order from highest index to lowest index
                    final List<ICard> reversedRemovedCards = new ArrayList<ICard>( removedCards );
                    Collections.reverse( reversedRemovedCards );
                    int cardIndex = upperCardIndex;
                    for( final ICard card : reversedRemovedCards )
                    {
                        fireComponentRemoved( card, cardIndex-- );
                    }

                    if( cardPileBoundsChanged )
                    {
                        fireComponentBoundsChanged();
                    }
                }
            } );
        }

        return new ArrayList<IComponent>( removedCards );
    }

    /*
     * @see org.gamegineer.table.core.IContainer#removeContainerListener(org.gamegineer.table.core.IContainerListener)
     */
    @Override
    public void removeContainerListener(
        final IContainerListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( containerListeners_.remove( listener ), "listener", NonNlsMessages.CardPile_removeContainerListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.IContainer#setLayout(org.gamegineer.table.core.IContainerLayout)
     */
    @Override
    public void setLayout(
        final IContainerLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        final boolean cardPileBoundsChanged;

        getLock().lock();
        try
        {
            layout_ = layout;

            if( cards_.isEmpty() )
            {
                cardPileBoundsChanged = false;
            }
            else
            {
                final Rectangle oldBounds = getBounds();

                layout_.layout( this );

                final Rectangle newBounds = getBounds();
                cardPileBoundsChanged = !newBounds.equals( oldBounds );
            }
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireContainerLayoutChanged();

                if( cardPileBoundsChanged )
                {
                    fireComponentBoundsChanged();
                }
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setLocation(java.awt.Point)
     */
    @Override
    public void setLocation(
        final Point location )
    {
        assertArgumentNotNull( location, "location" ); //$NON-NLS-1$

        translateOrigin( new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldLocation = getLocation();
                return new Dimension( location.x - oldLocation.x, location.y - oldLocation.y );
            }
        } );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public void setMemento(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            final CardPile cardPile = fromMemento( getTableEnvironment(), memento );

            setSurfaceDesign( CardPileOrientation.BASE, cardPile.getSurfaceDesign( CardPileOrientation.BASE ) );
            setOrigin( cardPile.getOrigin() );
            setLayout( cardPile.getLayout() );

            removeComponents();
            addComponents( cardPile.removeComponents() );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrientation(org.gamegineer.table.core.ComponentOrientation)
     */
    @Override
    public void setOrientation(
        final ComponentOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof CardPileOrientation, "orientation", NonNlsMessages.CardPile_orientation_illegal ); //$NON-NLS-1$

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentOrientationChanged();
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setOrigin(java.awt.Point)
     */
    @Override
    public void setOrigin(
        final Point origin )
    {
        assertArgumentNotNull( origin, "origin" ); //$NON-NLS-1$

        translateOrigin( new TranslationOffsetStrategy()
        {
            @Override
            Dimension getOffset()
            {
                final Point oldOrigin = getOrigin();
                return new Dimension( origin.x - oldOrigin.x, origin.y - oldOrigin.y );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#setSurfaceDesign(org.gamegineer.table.core.ComponentOrientation, org.gamegineer.table.core.ComponentSurfaceDesign)
     */
    @Override
    public void setSurfaceDesign(
        final ComponentOrientation orientation,
        final ComponentSurfaceDesign surfaceDesign )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$
        assertArgumentLegal( orientation instanceof CardPileOrientation, "orientation", NonNlsMessages.CardPile_orientation_illegal ); //$NON-NLS-1$
        assertArgumentNotNull( surfaceDesign, "surfaceDesign" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            if( orientation == CardPileOrientation.BASE )
            {
                baseDesign_ = surfaceDesign;
            }
            else
            {
                throw new AssertionError( "unknown card pile orientation" ); //$NON-NLS-1$
            }
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentSurfaceDesignChanged();
            }
        } );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CardPile[" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            sb.append( "baseDesign_=" ); //$NON-NLS-1$
            sb.append( baseDesign_ );
            sb.append( ", origin_=" ); //$NON-NLS-1$
            sb.append( origin_ );
            sb.append( ", cards_.size()=" ); //$NON-NLS-1$
            sb.append( cards_.size() );
            sb.append( ", layout_=" ); //$NON-NLS-1$
            sb.append( layout_ );
        }
        finally
        {
            getLock().unlock();
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }

    /**
     * Translates the card pile origin by the specified amount.
     * 
     * @param translationOffsetStrategy
     *        The strategy used to calculate the amount to translate the origin;
     *        must not be {@code null}. The strategy will be invoked while the
     *        table environment lock is held.
     */
    private void translateOrigin(
        /* @NonNull */
        final TranslationOffsetStrategy translationOffsetStrategy )
    {
        assert translationOffsetStrategy != null;

        getLock().lock();
        try
        {
            final Dimension offset = translationOffsetStrategy.getOffset();
            origin_.translate( offset.width, offset.height );
            for( final ICard card : cards_ )
            {
                final Point cardLocation = card.getLocation();
                cardLocation.translate( offset.width, offset.height );
                card.setLocation( cardLocation );
            }
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireComponentBoundsChanged();
            }
        } );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A strategy to calculate a contiguous range of components in a card pile.
     */
    @Immutable
    private class ComponentRangeStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentRangeStrategy}
         * class.
         */
        ComponentRangeStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the lower index of the component range, inclusive.
         * 
         * <p>
         * The default implementation returns 0.
         * </p>
         * 
         * @return The lower index of the component range, inclusive.
         */
        int getLowerIndex()
        {
            return 0;
        }

        /**
         * Gets the upper index of the component range, exclusive.
         * 
         * <p>
         * The default implementation returns the size of the component
         * collection.
         * </p>
         * 
         * @return The upper index of the component range, exclusive.
         */
        @SuppressWarnings( "synthetic-access" )
        int getUpperIndex()
        {
            return cards_.size();
        }
    }

    /**
     * A strategy to calculate a translation offset.
     */
    @Immutable
    private class TranslationOffsetStrategy
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TranslationOffsetStrategy}
         * class.
         */
        TranslationOffsetStrategy()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the translation offset.
         * 
         * <p>
         * The default implementation returns a zero offset.
         * </p>
         * 
         * @return The translation offset; never {@code null}.
         */
        /* @NonNull */
        Dimension getOffset()
        {
            return new Dimension( 0, 0 );
        }
    }
}
