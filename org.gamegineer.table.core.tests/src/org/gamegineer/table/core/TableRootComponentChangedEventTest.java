/*
 * TableRootComponentChangedEventTest.java
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
 * Created on Jun 30, 2012 at 9:52:10 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.TableRootComponentChangedEvent} class.
 */
public final class TableRootComponentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table root component changed event under test in the fixture. */
    private TableRootComponentChangedEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableRootComponentChangedEventTest} class.
     */
    public TableRootComponentChangedEventTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        event_ = new TableRootComponentChangedEvent( EasyMock.createMock( ITable.class ), EasyMock.createMock( IComponent.class ), EasyMock.createMock( IComponent.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * new root component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_NewRootComponent_Null()
    {
        new TableRootComponentChangedEvent( EasyMock.createMock( ITable.class ), null, EasyMock.createMock( IComponent.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * old root component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_OldRootComponent_Null()
    {
        new TableRootComponentChangedEvent( EasyMock.createMock( ITable.class ), EasyMock.createMock( IComponent.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new TableRootComponentChangedEvent( null, EasyMock.createMock( IComponent.class ), EasyMock.createMock( IComponent.class ) );
    }

    /**
     * Ensures the {@code getNewRootComponent} method does not return
     * {@code null}.
     */
    @Test
    public void testGetNewRootComponent_ReturnValue_NonNull()
    {
        assertNotNull( event_.getNewRootComponent() );
    }

    /**
     * Ensures the {@code getOldRootComponent} method does not return
     * {@code null}.
     */
    @Test
    public void testGetOldRootComponent_ReturnValue_NonNull()
    {
        assertNotNull( event_.getOldRootComponent() );
    }
}
