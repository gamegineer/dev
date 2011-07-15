/*
 * CardPileIncrementMessage.java
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
 * Created on Jul 12, 2011 at 8:42:31 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.node.CardPileIncrement;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a node to increment the state of a card pile.
 */
@NotThreadSafe
public final class CardPileIncrementMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 57895247573982620L;

    /**
     * The incremental change to the state of the card pile.
     * 
     * @serial The incremental change to the state of the card pile.
     */
    private CardPileIncrement increment_;

    /**
     * The card pile index.
     * 
     * @serial The card pile index.
     */
    private int index_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileIncrementMessage} class.
     */
    public CardPileIncrementMessage()
    {
        increment_ = new CardPileIncrement();
        index_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the incremental change to the state of the card pile.
     * 
     * @return The incremental change to the state of the card pile; never
     *         {@code null}.
     */
    /* @NonNull */
    public CardPileIncrement getIncrement()
    {
        return increment_;
    }

    /**
     * Gets the card pile index.
     * 
     * @return The card pile index.
     */
    public int getIndex()
    {
        return index_;
    }

    /**
     * Sets the incremental change to the state of the card pile.
     * 
     * @param increment
     *        The incremental change to the state of the card pile; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code increment} is {@code null}.
     */
    public void setIncrement(
        /* @NonNull */
        final CardPileIncrement increment )
    {
        assertArgumentNotNull( increment, "increment" ); //$NON-NLS-1$

        increment_ = increment;
    }

    /**
     * Sets the card pile index.
     * 
     * @param index
     *        The card pile index.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is negative.
     */
    public void setIndex(
        final int index )
    {
        assertArgumentLegal( index >= 0, "index" ); //$NON-NLS-1$

        index_ = index;
    }
}
