/*
 * ICardPileDesignRegistry.java
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

package org.gamegineer.table.core.services.cardpiledesignregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.core.ICardPileDesign;

/**
 * A service for the management and discovery of card pile designs.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPileDesignRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile design with the specified identifier.
     * 
     * @param id
     *        The card pile design identifier; must not be {@code null}.
     * 
     * @return The card pile design with the specified identifier or {@code
     *         null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardPileDesign getCardPileDesign(
        /* @NonNull */
        CardPileDesignId id );

    /**
     * Gets a collection of all card pile designs registered with this service.
     * 
     * @return A collection of all card pile designs registered with this
     *         service; never {@code null}. This collection is a snapshot of the
     *         card pile designs registered at the time of the call.
     */
    /* @NonNull */
    public Collection<ICardPileDesign> getCardPileDesigns();

    /**
     * Registers the specified card pile design.
     * 
     * <p>
     * This method does nothing if the specified card pile design or a card pile
     * design with the same identifier was previously registered.
     * </p>
     * 
     * @param cardPileDesign
     *        The card pile design; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesign} is {@code null}.
     */
    public void registerCardPileDesign(
        /* @NonNull */
        ICardPileDesign cardPileDesign );

    /**
     * Unregisters the specified card pile design.
     * 
     * <p>
     * This method does nothing if the specified card pile design was not
     * previously registered.
     * </p>
     * 
     * @param cardPileDesign
     *        The card pile design; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesign} is {@code null}.
     */
    public void unregisterCardPileDesign(
        /* @NonNull */
        ICardPileDesign cardPileDesign );
}
