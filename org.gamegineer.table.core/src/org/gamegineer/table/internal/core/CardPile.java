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
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardPileLayouts;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainerLayout;

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
     * The name of the memento attribute that stores the collection of
     * components in the card pile.
     */
    private static final String COMPONENTS_MEMENTO_ATTRIBUTE_NAME = "components"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the card pile layout.
     */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card pile origin. */
    private static final String ORIGIN_MEMENTO_ATTRIBUTE_NAME = "origin"; //$NON-NLS-1$

    /** The collection of supported card pile orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardPileOrientation.values( CardPileOrientation.class ) ) );

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

        origin_ = new Point( 0, 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
            memento.put( BASE_DESIGN_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesign( CardPileOrientation.BASE ) );
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, getLayout() );
            memento.put( ORIGIN_MEMENTO_ATTRIBUTE_NAME, new Point( origin_ ) );

            final List<Object> componentMementos = new ArrayList<Object>();
            for( final IComponent component : getComponents() )
            {
                componentMementos.add( component.createMemento() );
            }
            memento.put( COMPONENTS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( componentMementos ) );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
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
        final List<Object> componentMementos = MementoUtils.getAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object componentMemento : componentMementos )
        {
            cardPile.addComponent( Component.fromMemento( tableEnvironment, componentMemento ) );
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
            if( getComponentCount() == 0 )
            {
                return new Rectangle( origin_, getSurfaceDesign( getOrientation() ).getSize() );
            }

            final List<IComponent> components = getComponents();
            final Rectangle topComponentBounds = components.get( components.size() - 1 ).getBounds();
            final Rectangle bottomComponentBounds = components.get( 0 ).getBounds();
            return topComponentBounds.union( bottomComponentBounds );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getDefaultLayout()
     */
    @Override
    IContainerLayout getDefaultLayout()
    {
        return CardPileLayouts.STACKED;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultSurfaceDesigns()
     */
    @Override
    Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        return Collections.<ComponentOrientation, ComponentSurfaceDesign>singletonMap( //
            CardPileOrientation.BASE, //
            new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.CardPile.DEFAULT_BASE_DESIGN" ), 0, 0 ) ); //$NON-NLS-1$
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
            && MementoUtils.hasAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, ORIGIN_MEMENTO_ATTRIBUTE_NAME );
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
            sb.append( getSurfaceDesign( CardPileOrientation.BASE ) );
            sb.append( ", origin_=" ); //$NON-NLS-1$
            sb.append( origin_ );
            sb.append( ", cards_.size()=" ); //$NON-NLS-1$
            sb.append( getComponentCount() );
            sb.append( ", layout_=" ); //$NON-NLS-1$
            sb.append( getLayout() );
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
            for( final IComponent component : getComponents() )
            {
                final Point componentLocation = component.getLocation();
                componentLocation.translate( offset.width, offset.height );
                component.setLocation( componentLocation );
            }
        }
        finally
        {
            getLock().unlock();
        }

        addEventNotification( new Runnable()
        {
            @Override
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
