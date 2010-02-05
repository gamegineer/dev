/*
 * ICardPileBaseDesign.java
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
 * Created on Jan 18, 2010 at 10:15:25 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;

/**
 * A card pile base design.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPileBaseDesign
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile base design identifier.
     * 
     * @return The card pile base design identifier; never {@code null}.
     */
    /* @NonNull */
    public CardPileBaseDesignId getId();

    /**
     * Gets the card pile base design size in table coordinates.
     * 
     * @return The card pile base design size in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Dimension getSize();
}
