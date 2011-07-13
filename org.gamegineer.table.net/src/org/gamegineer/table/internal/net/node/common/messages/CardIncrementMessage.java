/*
 * CardIncrementMessage.java
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
 * Created on Jun 30, 2011 at 10:29:37 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.node.CardIncrement;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a node to increment the state of a card.
 */
@NotThreadSafe
public final class CardIncrementMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3587077290756850335L;

    /**
     * The card index.
     * 
     * @serial The card index.
     */
    private int cardIndex_;

    /**
     * The table-relative index of the card pile that contains the card.
     * 
     * @serial The table-relative index of the card pile that contains the card.
     */
    private int cardPileIndex_;

    /**
     * The incremental change to the state of the card.
     * 
     * @serial The incremental change to the state of the card.
     */
    private CardIncrement increment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardIncrementMessage} class.
     */
    public CardIncrementMessage()
    {
        cardIndex_ = 0;
        cardPileIndex_ = 0;
        increment_ = new CardIncrement();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card index.
     * 
     * @return The card index.
     */
    public int getCardIndex()
    {
        return cardIndex_;
    }

    /**
     * Gets the table-relative index of the card pile that contains the card.
     * 
     * @return The table-relative index of the card pile that contains the card.
     */
    public int getCardPileIndex()
    {
        return cardPileIndex_;
    }

    /**
     * Gets the incremental change to the state of the card.
     * 
     * @return The incremental change to the state of the card; never {@code
     *         null}.
     */
    /* @NonNull */
    public CardIncrement getIncrement()
    {
        return increment_;
    }

    /**
     * Sets the card index.
     * 
     * @param cardIndex
     *        The card index.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardIndex} is negative.
     */
    public void setCardIndex(
        final int cardIndex )
    {
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$

        cardIndex_ = cardIndex;
    }

    /**
     * Sets the table-relative index of the card pile that contains the card.
     * 
     * @param cardPileIndex
     *        The table-relative index of the card pile that contains the card.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileIndex} is negative.
     */
    public void setCardPileIndex(
        final int cardPileIndex )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$

        cardPileIndex_ = cardPileIndex;
    }

    /**
     * Sets the incremental change to the state of the card.
     * 
     * @param increment
     *        The incremental change to the state of the card; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code increment} is {@code null}.
     */
    public void setIncrement(
        /* @NonNull */
        final CardIncrement increment )
    {
        assertArgumentNotNull( increment, "increment" ); //$NON-NLS-1$

        increment_ = increment;
    }
}
