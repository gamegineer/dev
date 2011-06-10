/*
 * CardPileAsMementoOriginatorTest.java
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
 * Created on Jun 6, 2011 at 8:26:05 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.table.core.Assert.assertCardPileEquals;
import java.awt.Point;
import org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.CardPileLayout;
import org.gamegineer.table.core.Cards;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.util.memento.IMementoOriginator} interface.
 */
public final class CardPileAsMementoOriginatorTest
    extends AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileAsMementoOriginatorTest}
     * class.
     */
    public CardPileAsMementoOriginatorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#assertMementoOriginatorEquals(org.gamegineer.common.core.util.memento.IMementoOriginator, org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void assertMementoOriginatorEquals(
        final IMementoOriginator expected,
        final IMementoOriginator actual )
    {
        final CardPile expectedCardPile = (CardPile)expected;
        final CardPile actualCardPile = (CardPile)actual;
        assertCardPileEquals( expectedCardPile, actualCardPile );
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#createMementoOriginator()
     */
    @Override
    protected IMementoOriginator createMementoOriginator()
    {
        final CardPile cardPile = new CardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );
        cardPile.setBaseLocation( new Point( 0, 0 ) );
        cardPile.setLayout( CardPileLayout.STACKED );
        return cardPile;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.AbstractMementoOriginatorTestCase#initializeMementoOriginator(org.gamegineer.common.core.util.memento.IMementoOriginator)
     */
    @Override
    protected void initializeMementoOriginator(
        final IMementoOriginator mementoOriginator )
    {
        final CardPile cardPile = (CardPile)mementoOriginator;
        cardPile.setBaseLocation( new Point( Integer.MAX_VALUE, Integer.MIN_VALUE ) );
        cardPile.setLayout( CardPileLayout.ACCORDIAN_DOWN );
        cardPile.addCard( Cards.createUniqueCard() );
    }
}
