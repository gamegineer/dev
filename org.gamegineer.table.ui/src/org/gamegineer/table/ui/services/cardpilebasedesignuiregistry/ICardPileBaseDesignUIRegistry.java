/*
 * ICardPileBaseDesignUIRegistry.java
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

package org.gamegineer.table.ui.services.cardpilebasedesignuiregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;

/**
 * A service for the management and discovery of card pile base design user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPileBaseDesignUIRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile base design user interface with the specified
     * identifier.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The card pile base design user interface with the specified
     *         identifier or {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardPileBaseDesignUI getCardPileBaseDesignUI(
        /* @NonNull */
        CardPileBaseDesignId id );

    /**
     * Gets a collection of all card pile base design user interfaces registered
     * with this service.
     * 
     * @return A collection of all card pile base design user interfaces
     *         registered with this service; never {@code null}. This collection
     *         is a snapshot of the card pile base design user interfaces
     *         registered at the time of the call.
     */
    /* @NonNull */
    public Collection<ICardPileBaseDesignUI> getCardPileBaseDesignUIs();

    /**
     * Registers the specified card pile base design user interface.
     * 
     * <p>
     * This method does nothing if the specified card pile base design user
     * interface or a card pile base design user interface with the same
     * identifier was previously registered.
     * </p>
     * 
     * @param cardPileBaseDesignUI
     *        The card pile base design user interface; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignUI} is {@code null}.
     */
    public void registerCardPileBaseDesignUI(
        /* @NonNull */
        ICardPileBaseDesignUI cardPileBaseDesignUI );

    /**
     * Unregisters the specified card pile base design user interface.
     * 
     * <p>
     * This method does nothing if the specified card pile base design user
     * interface was not previously registered.
     * </p>
     * 
     * @param cardPileBaseDesignUI
     *        The card pile base design user interface; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignUI} is {@code null}.
     */
    public void unregisterCardPileBaseDesignUI(
        /* @NonNull */
        ICardPileBaseDesignUI cardPileBaseDesignUI );
}
