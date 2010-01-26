/*
 * InternalTableContentChangedEventTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 16, 2009 at 11:17:35 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalTableContentChangedEvent}
 * class.
 */
public final class InternalTableContentChangedEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalTableContentChangedEventTest} class.
     */
    public InternalTableContentChangedEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICard)} method
     * throws an exception when passed a {@code null} card.
     */
    @Test( expected = AssertionError.class )
    public void testCreateTableContentChangedEventFromCard_Card_Null()
    {
        InternalTableContentChangedEvent.createTableContentChangedEvent( createDummy( ITable.class ), (ICard)null );
    }

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICard)} method
     * does not return {@code null}.
     */
    @Test
    public void testCreateTableContentChangedEventFromCard_ReturnValue_NonNull()
    {
        assertNotNull( InternalTableContentChangedEvent.createTableContentChangedEvent( createDummy( ITable.class ), createDummy( ICard.class ) ) );
    }

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICard)} method
     * throws an exception when passed a {@code null} table.
     */
    @Test( expected = AssertionError.class )
    public void testCreateTableContentChangedEventFromCard_Table_Null()
    {
        InternalTableContentChangedEvent.createTableContentChangedEvent( null, createDummy( ICard.class ) );
    }

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICardPile)}
     * method throws an exception when passed a {@code null} card pile.
     */
    @Test( expected = AssertionError.class )
    public void testCreateTableContentChangedEventFromCardPile_CardPile_Null()
    {
        InternalTableContentChangedEvent.createTableContentChangedEvent( createDummy( ITable.class ), (ICardPile)null );
    }

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICardPile)}
     * method does not return {@code null}.
     */
    @Test
    public void testCreateTableContentChangedEventFromCardPile_ReturnValue_NonNull()
    {
        assertNotNull( InternalTableContentChangedEvent.createTableContentChangedEvent( createDummy( ITable.class ), createDummy( ICardPile.class ) ) );
    }

    /**
     * Ensures the {@code createTableContentChangedEvent(ITable, ICardPile)}
     * method throws an exception when passed a {@code null} table.
     */
    @Test( expected = AssertionError.class )
    public void testCreateTableContentChangedEventFromCardPile_Table_Null()
    {
        InternalTableContentChangedEvent.createTableContentChangedEvent( null, createDummy( ICardPile.class ) );
    }
}
