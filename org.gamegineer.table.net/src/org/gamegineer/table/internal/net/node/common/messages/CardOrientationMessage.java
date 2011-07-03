/*
 * CardOrientationMessage.java
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
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a node to indicate the orientation of a card has changed.
 */
@NotThreadSafe
public final class CardOrientationMessage
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
     * The card orientation.
     * 
     * @serial The card orientation.
     */
    private CardOrientation cardOrientation_;

    /**
     * The card pile index.
     * 
     * @serial The card pile index.
     */
    private int cardPileIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardOrientationMessage} class.
     */
    public CardOrientationMessage()
    {
        cardIndex_ = 0;
        cardOrientation_ = CardOrientation.BACK_UP;
        cardPileIndex_ = 0;
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
     * Gets the card orientation.
     * 
     * @return The card orientation; never {@code null}.
     */
    /* @NonNull */
    public CardOrientation getCardOrientation()
    {
        return cardOrientation_;
    }

    /**
     * Gets the card pile index.
     * 
     * @return The card pile index.
     */
    public int getCardPileIndex()
    {
        return cardPileIndex_;
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
     * Sets the card orientation.
     * 
     * @param cardOrientation
     *        The card orientation; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardOrientation} is {@code null}.
     */
    public void setCardOrientation(
        /* @NonNull */
        final CardOrientation cardOrientation )
    {
        assertArgumentNotNull( cardOrientation, "cardOrientation" ); //$NON-NLS-1$

        cardOrientation_ = cardOrientation;
    }

    /**
     * Sets the card pile index.
     * 
     * @param cardPileIndex
     *        The card pile index.
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
}
