/*
 * CardAsCardTest.java
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
 * Created on Oct 11, 2009 at 9:53:43 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.gamegineer.table.core.AbstractCardTestCase;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Card}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICard} interface.
 */
public final class CardAsCardTest
    extends AbstractCardTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardAsCardTest} class.
     */
    public CardAsCardTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITable)
     */
    @Override
    protected ICard createComponent(
        final ITable table )
    {
        return new Card( ((Table)table).getTableContext() );
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
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final ICard component )
    {
        try
        {
            final Method method = Card.class.getDeclaredMethod( "fireComponentBoundsChanged" ); //$NON-NLS-1$
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
        final ICard component )
    {
        try
        {
            final Method method = Card.class.getDeclaredMethod( "fireComponentOrientationChanged" ); //$NON-NLS-1$
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
        final ICard component )
    {
        try
        {
            final Method method = Card.class.getDeclaredMethod( "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
