/*
 * ICard.java
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
 * Created on Oct 9, 2009 at 11:59:10 PM.
 */

package org.gamegineer.table.core;

/**
 * A card.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICard
    extends IComponent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile that contains this card.
     * 
     * @return The card pile that contains this card or {@code null} if this
     *         card is not contained in a card pile.
     */
    /* @Nullable */
    public ICardPile getCardPile();
}
