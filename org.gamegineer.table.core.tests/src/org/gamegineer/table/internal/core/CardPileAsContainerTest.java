/*
 * CardPileAsContainerTest.java
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
import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractContainerTestCase;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public final class CardPileAsContainerTest
    extends AbstractContainerTestCase<TableEnvironment, CardPile>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileAsContainerTest} class.
     */
    public CardPileAsContainerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected CardPile createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new CardPile( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTableEnvironment()
     */
    @Override
    protected TableEnvironment createTableEnvironment()
    {
        return new TableEnvironment();
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#createUniqueComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected IComponent createUniqueComponent(
        final TableEnvironment tableEnvironment )
    {
        return Cards.createUniqueCard( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#createUniqueContainer(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected IContainer createUniqueContainer(
        final TableEnvironment tableEnvironment )
    {
        return CardPiles.createUniqueCardPile( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireComponentAdded(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentAdded(
        final CardPile container )
    {
        fireEventWithComponentAndInteger( container, "fireComponentAdded" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final CardPile component )
    {
        component.fireComponentBoundsChanged();
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final CardPile component )
    {
        component.fireComponentOrientationChanged();
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireComponentRemoved(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentRemoved(
        final CardPile container )
    {
        fireEventWithComponentAndInteger( container, "fireComponentRemoved" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final CardPile component )
    {
        component.fireComponentSurfaceDesignChanged();
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireContainerLayoutChanged(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireContainerLayoutChanged(
        final CardPile container )
    {
        fireEvent( container, "fireContainerLayoutChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified card pile method.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEvent(
        /* @NonNull */
        final CardPile cardPile,
        /* @NonNull */
        final String methodName )
    {
        assert cardPile != null;
        assert methodName != null;

        try
        {
            final Method method = CardPile.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( cardPile );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires the event associated with the specified card pile method that
     * accepts an {@link IComponent} and an integer.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEventWithComponentAndInteger(
        /* @NonNull */
        final CardPile cardPile,
        /* @NonNull */
        final String methodName )
    {
        assert cardPile != null;
        assert methodName != null;

        try
        {
            final Method method = CardPile.class.getDeclaredMethod( methodName, IComponent.class, Integer.TYPE );
            method.setAccessible( true );
            method.invoke( cardPile, EasyMock.createMock( IComponent.class ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
