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
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            memento.put( ORIGIN_MEMENTO_ATTRIBUTE_NAME, getOrigin() );

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
     * @see org.gamegineer.table.internal.core.Container#getDefaultLayout()
     */
    @Override
    IContainerLayout getDefaultLayout()
    {
        return CardPileLayouts.STACKED;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return getDefaultOrigin();
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
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrigin()
     */
    @Override
    Point getDefaultOrigin()
    {
        return new Point( 0, 0 );
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
}
