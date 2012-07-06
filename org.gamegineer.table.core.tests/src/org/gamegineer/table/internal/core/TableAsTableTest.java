/*
 * TableAsTableTest.java
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
 * Created on Oct 6, 2009 at 11:11:27 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractTableTestCase;
import org.gamegineer.table.core.ICardPile;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Table}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ITable} interface.
 */
public final class TableAsTableTest
    extends AbstractTableTestCase<TableEnvironment, Table>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAsTableTest} class.
     */
    public TableAsTableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractTableTestCase#createTable(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Table createTable(
        final TableEnvironment tableEnvironment )
    {
        return new Table( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.AbstractTableTestCase#createTableEnvironment()
     */
    @Override
    protected TableEnvironment createTableEnvironment()
    {
        return new TableEnvironment();
    }

    /*
     * @see org.gamegineer.table.core.AbstractTableTestCase#fireCardPileAdded(org.gamegineer.table.core.ITable)
     */
    @Override
    protected void fireCardPileAdded(
        final Table table )
    {
        try
        {
            final Method method = Table.class.getDeclaredMethod( "fireCardPileAdded", ICardPile.class, Integer.TYPE ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( table, EasyMock.createMock( ICardPile.class ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractTableTestCase#fireCardPileRemoved(org.gamegineer.table.core.ITable)
     */
    @Override
    protected void fireCardPileRemoved(
        final Table table )
    {
        try
        {
            final Method method = Table.class.getDeclaredMethod( "fireCardPileRemoved", ICardPile.class, Integer.TYPE ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( table, EasyMock.createMock( ICardPile.class ), Integer.valueOf( 0 ) );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }
}
