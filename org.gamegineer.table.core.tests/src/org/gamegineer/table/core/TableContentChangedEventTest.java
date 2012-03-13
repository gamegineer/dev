/*
 * TableContentChangedEventTest.java
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
 * Created on Mar 8, 2011 at 8:41:29 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.TableContentChangedEvent} class.
 */
public final class TableContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table content changed event under test in the fixture. */
    private TableContentChangedEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableContentChangedEventTest}
     * class.
     */
    public TableContentChangedEventTest()
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
        event_ = new TableContentChangedEvent( EasyMock.createMock( ITable.class ), EasyMock.createMock( ICardPile.class ), 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card pile.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_CardPile_Null()
    {
        new TableContentChangedEvent( EasyMock.createMock( ITable.class ), null, 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed an illegal card
     * pile index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_CardPileIndex_Illegal_Negative()
    {
        new TableContentChangedEvent( EasyMock.createMock( ITable.class ), EasyMock.createMock( ICardPile.class ), -1 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new TableContentChangedEvent( null, EasyMock.createMock( ICardPile.class ), 0 );
    }

    /**
     * Ensures the {@code getCardPile} method does not return {@code null}.
     */
    @Test
    public void testGetCardPile_ReturnValue_NonNull()
    {
        assertNotNull( event_.getCardPile() );
    }
}
