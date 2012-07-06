/*
 * TabletopAsContainerTest.java
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
 * Created on Jun 28, 2012 at 8:08:19 PM.
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
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Tabletop}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.IContainer} interface.
 */
public final class TabletopAsContainerTest
    extends AbstractContainerTestCase<TableEnvironment, Tabletop>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TabletopAsContainerTest} class.
     */
    public TabletopAsContainerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Tabletop createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new Tabletop( tableEnvironment );
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
        final Tabletop container )
    {
        fireEventWithComponentAndInteger( container, "fireComponentAdded" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Tabletop component )
    {
        fireEvent( component, "fireComponentBoundsChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final Tabletop component )
    {
        fireEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireComponentRemoved(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireComponentRemoved(
        final Tabletop container )
    {
        fireEventWithComponentAndInteger( container, "fireComponentRemoved" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Tabletop component )
    {
        fireEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractContainerTestCase#fireContainerLayoutChanged(org.gamegineer.table.core.IContainer)
     */
    @Override
    protected void fireContainerLayoutChanged(
        final Tabletop container )
    {
        fireEvent( container, "fireContainerLayoutChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified tabletop method.
     * 
     * @param tabletop
     *        The tabletop; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEvent(
        /* @NonNull */
        final Tabletop tabletop,
        /* @NonNull */
        final String methodName )
    {
        assert tabletop != null;
        assert methodName != null;

        try
        {
            final Method method = Tabletop.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( tabletop );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Fires the event associated with the specified tabletop method that
     * accepts an {@link IComponent} and an integer.
     * 
     * @param tabletop
     *        The tabletop; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEventWithComponentAndInteger(
        /* @NonNull */
        final Tabletop tabletop,
        /* @NonNull */
        final String methodName )
    {
        assert tabletop != null;
        assert methodName != null;

        try
        {
            final Method method = Tabletop.class.getDeclaredMethod( methodName, IComponent.class, Integer.TYPE );
            method.setAccessible( true );
            method.invoke( tabletop, EasyMock.createMock( IComponent.class ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
