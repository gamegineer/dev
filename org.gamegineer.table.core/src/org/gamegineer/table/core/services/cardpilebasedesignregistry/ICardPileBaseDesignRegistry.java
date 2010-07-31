/*
 * ICardPileBaseDesignRegistry.java
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
 * Created on Jan 19, 2010 at 11:13:54 PM.
 */

package org.gamegineer.table.core.services.cardpilebasedesignregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;

/**
 * A service for the management and discovery of card pile base designs.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPileBaseDesignRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile base design with the specified identifier.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The card pile base design with the specified identifier or
     *         {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardPileBaseDesign getCardPileBaseDesign(
        /* @NonNull */
        CardPileBaseDesignId id );

    /**
     * Gets a collection of all card pile base designs registered with this
     * service.
     * 
     * @return A collection of all card pile base designs registered with this
     *         service; never {@code null}. This collection is a snapshot of the
     *         card pile base designs registered at the time of the call.
     */
    /* @NonNull */
    public Collection<ICardPileBaseDesign> getCardPileBaseDesigns();

    /**
     * Registers the specified card pile base design.
     * 
     * @param cardPileBaseDesign
     *        The card pile base design; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a card pile base design with the same identifier is already
     *         registered.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesign} is {@code null}.
     */
    public void registerCardPileBaseDesign(
        /* @NonNull */
        ICardPileBaseDesign cardPileBaseDesign );

    /**
     * Unregisters the specified card pile base design.
     * 
     * @param cardPileBaseDesign
     *        The card pile base design; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified card pile base design was not previously
     *         registered.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesign} is {@code null}.
     */
    public void unregisterCardPileBaseDesign(
        /* @NonNull */
        ICardPileBaseDesign cardPileBaseDesign );
}
