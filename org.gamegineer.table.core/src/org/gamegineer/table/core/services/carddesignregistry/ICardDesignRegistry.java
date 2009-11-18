/*
 * ICardDesignRegistry.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Nov 11, 2009 at 9:10:06 PM.
 */

package org.gamegineer.table.core.services.carddesignregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.core.ICardDesign;

/**
 * A service for the management and discovery of card designs.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardDesignRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card design with the specified identifier.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * 
     * @return The card design with the specified identifier or {@code null} if
     *         no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardDesign getCardDesign(
        /* @NonNull */
        CardDesignId id );

    /**
     * Gets a collection of all card designs registered with this service.
     * 
     * @return A collection of all card designs registered with this service;
     *         never {@code null}. This collection is a snapshot of the card
     *         designs registered at the time of the call.
     */
    /* @NonNull */
    public Collection<ICardDesign> getCardDesigns();

    /**
     * Registers the specified card design.
     * 
     * <p>
     * This method does nothing if the specified card design or a card design
     * with the same identifier was previously registered.
     * </p>
     * 
     * @param cardDesign
     *        The card design; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardDesign} is {@code null}.
     */
    public void registerCardDesign(
        /* @NonNull */
        ICardDesign cardDesign );

    /**
     * Unregisters the specified card design.
     * 
     * <p>
     * This method does nothing if the specified card design was not previously
     * registered.
     * </p>
     * 
     * @param cardDesign
     *        The card design; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardDesign} is {@code null}.
     */
    public void unregisterCardDesign(
        /* @NonNull */
        ICardDesign cardDesign );
}
