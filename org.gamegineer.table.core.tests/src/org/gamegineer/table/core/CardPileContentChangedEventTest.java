/*
 * CardPileContentChangedEventTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Mar 8, 2011 at 8:29:21 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.CardPileContentChangedEvent} class.
 */
public final class CardPileContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile content changed event under test in the fixture. */
    private CardPileContentChangedEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileContentChangedEventTest}
     * class.
     */
    public CardPileContentChangedEventTest()
    {
        super();
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
        event_ = new CardPileContentChangedEvent( EasyMock.createMock( ICardPile.class ), EasyMock.createMock( ICard.class ), 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Card_Null()
    {
        new CardPileContentChangedEvent( EasyMock.createMock( ICardPile.class ), null, 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed an illegal card
     * index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_CardIndex_Illegal_Negative()
    {
        new CardPileContentChangedEvent( EasyMock.createMock( ICardPile.class ), EasyMock.createMock( ICard.class ), -1 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new CardPileContentChangedEvent( null, EasyMock.createMock( ICard.class ), 0 );
    }

    /**
     * Ensures the {@code getCard} method does not return {@code null}.
     */
    @Test
    public void testGetCard_ReturnValue_NonNull()
    {
        assertNotNull( event_.getCard() );
    }
}
