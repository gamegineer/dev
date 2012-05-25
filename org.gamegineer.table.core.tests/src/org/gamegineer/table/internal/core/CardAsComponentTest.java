/*
 * CardAsComponentTest.java
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
import org.gamegineer.table.core.AbstractComponentTestCase;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Card}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.IComponent} interface.
 */
public final class CardAsComponentTest
    extends AbstractComponentTestCase<Card>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardAsComponentTest} class.
     */
    public CardAsComponentTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITable)
     */
    @Override
    protected Card createComponent(
        final ITable table )
    {
        return new Card( ((Table)table).getTableEnvironment() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTable()
     */
    @Override
    protected ITable createTable()
    {
        return new Table( new TableEnvironment() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final Card component )
    {
        fireEvent( component, "fireComponentBoundsChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final Card component )
    {
        fireEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final Card component )
    {
        fireEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified card method.
     * 
     * @param card
     *        The card; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEvent(
        /* @NonNull */
        final Card card,
        /* @NonNull */
        final String methodName )
    {
        assert card != null;
        assert methodName != null;

        try
        {
            final Method method = Card.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( card );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
