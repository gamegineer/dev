/*
 * CardPileAsCardPileTest.java
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
 * Created on Jan 14, 2010 at 11:13:29 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.gamegineer.table.core.AbstractCardPileTestCase;
import org.gamegineer.table.core.CardPileListener;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.ContainerContentChangedEvent;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerListener;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public final class CardPileAsCardPileTest
    extends AbstractCardPileTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileAsCardPileTest} class.
     */
    public CardPileAsCardPileTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#addContainerListener(org.gamegineer.table.core.IContainer, org.gamegineer.table.core.IContainerListener)
     */
    @Override
    protected void addContainerListener(
        final ICardPile container,
        final IContainerListener listener )
    {
        container.addCardPileListener( new CardPileListener()
        {
            @Override
            public void componentAdded(
                final ContainerContentChangedEvent event )
            {
                listener.componentAdded( event );
            }

            @Override
            public void componentRemoved(
                final ContainerContentChangedEvent event )
            {
                listener.componentRemoved( event );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITable)
     */
    @Override
    protected ICardPile createComponent(
        final ITable table )
    {
        return new CardPile( ((Table)table).getTableContext() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTable()
     */
    @Override
    protected ITable createTable()
    {
        return new Table();
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#createUniqueComponent(org.gamegineer.table.core.ITable)
     */
    @Override
    protected IComponent createUniqueComponent(
        final ITable table )
    {
        return Cards.createUniqueCard( table );
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#createUniqueContainer(org.gamegineer.table.core.ITable)
     */
    @Override
    protected IContainer createUniqueContainer(
        final ITable table )
    {
        return CardPiles.createUniqueCardPile( table );
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#createUniqueTable()
     */
    @Override
    protected ITable createUniqueTable()
    {
        return createTable();
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final ICardPile component )
    {
        try
        {
            final Method method = CardPile.class.getDeclaredMethod( "fireComponentBoundsChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final ICardPile component )
    {
        try
        {
            final Method method = CardPile.class.getDeclaredMethod( "fireComponentOrientationChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final ICardPile component )
    {
        try
        {
            final Method method = CardPile.class.getDeclaredMethod( "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
