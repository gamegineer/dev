/*
 * NetworkTableUtilsTest.java
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
 * Created on Jul 15, 2011 at 9:05:22 PM.
 */

package org.gamegineer.table.internal.net.node;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.NetworkTableUtils} class.
 */
public final class NetworkTableUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableUtilsTest} class.
     */
    public NetworkTableUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code incrementComponentState} method throws an exception
     * when passed a {@code null} component increment.
     */
    @Test( expected = NullPointerException.class )
    public void testIncrementComponentState_ComponentIncrement_Null()
    {
        NetworkTableUtils.incrementComponentState( EasyMock.createMock( ITable.class ), new ComponentPath( null, 0 ), null );
    }

    /**
     * Ensures the {@code incrementComponentState} method throws an exception
     * when passed a {@code null} component path.
     */
    @Test( expected = NullPointerException.class )
    public void testIncrementComponentState_ComponentPath_Null()
    {
        NetworkTableUtils.incrementComponentState( EasyMock.createMock( ITable.class ), null, new ComponentIncrement() );
    }

    /**
     * Ensures the {@code incrementComponentState} method throws an exception
     * when passed a {@code null} table.
     */
    @Test( expected = NullPointerException.class )
    public void testIncrementComponentState_Table_Null()
    {
        NetworkTableUtils.incrementComponentState( null, new ComponentPath( null, 0 ), new ComponentIncrement() );
    }

    /**
     * Ensures the {@code incrementTableState} method throws an exception when
     * passed a {@code null} table.
     */
    @Test( expected = NullPointerException.class )
    public void testIncrementTableState_Table_Null()
    {
        NetworkTableUtils.incrementTableState( null, new TableIncrement() );
    }

    /**
     * Ensures the {@code incrementTableState} method throws an exception when
     * passed a {@code null} table increment.
     */
    @Test( expected = NullPointerException.class )
    public void testIncrementTableState_TableIncrement_Null()
    {
        NetworkTableUtils.incrementTableState( EasyMock.createMock( ITable.class ), null );
    }

    /**
     * Ensures the {@code setTableState} method throws an exception when passed
     * a {@code null} table.
     */
    @Test( expected = NullPointerException.class )
    public void testSetTableState_Table_Null()
    {
        NetworkTableUtils.setTableState( null, new Object() );
    }

    /**
     * Ensures the {@code setTableState} method throws an exception when passed
     * a {@code null} table memento.
     */
    @Test( expected = NullPointerException.class )
    public void testSetTableState_TableMemento_Null()
    {
        NetworkTableUtils.setTableState( EasyMock.createMock( ITable.class ), null );
    }
}
