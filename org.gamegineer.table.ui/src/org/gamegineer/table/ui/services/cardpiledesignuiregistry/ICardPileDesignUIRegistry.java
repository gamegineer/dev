/*
 * ICardPileDesignUIRegistry.java
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
 * Created on Jan 23, 2010 at 9:29:27 PM.
 */

package org.gamegineer.table.ui.services.cardpiledesignuiregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.ui.ICardPileDesignUI;

/**
 * A service for the management and discovery of card pile design user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPileDesignUIRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile design user interface with the specified identifier.
     * 
     * @param id
     *        The card pile design identifier; must not be {@code null}.
     * 
     * @return The card pile design user interface with the specified identifier
     *         or {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardPileDesignUI getCardPileDesignUI(
        /* @NonNull */
        CardPileDesignId id );

    /**
     * Gets a collection of all card pile design user interfaces registered with
     * this service.
     * 
     * @return A collection of all card pile design user interfaces registered
     *         with this service; never {@code null}. This collection is a
     *         snapshot of the card pile design user interfaces registered at
     *         the time of the call.
     */
    /* @NonNull */
    public Collection<ICardPileDesignUI> getCardPileDesignUIs();

    /**
     * Registers the specified card pile design user interface.
     * 
     * <p>
     * This method does nothing if the specified card pile design user interface
     * or a card pile design user interface with the same identifier was
     * previously registered.
     * </p>
     * 
     * @param cardPileDesignUI
     *        The card pile design user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesignUI} is {@code null}.
     */
    public void registerCardPileDesignUI(
        /* @NonNull */
        ICardPileDesignUI cardPileDesignUI );

    /**
     * Unregisters the specified card pile design user interface.
     * 
     * <p>
     * This method does nothing if the specified card pile design user interface
     * was not previously registered.
     * </p>
     * 
     * @param cardPileDesignUI
     *        The card pile design user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesignUI} is {@code null}.
     */
    public void unregisterCardPileDesignUI(
        /* @NonNull */
        ICardPileDesignUI cardPileDesignUI );
}
