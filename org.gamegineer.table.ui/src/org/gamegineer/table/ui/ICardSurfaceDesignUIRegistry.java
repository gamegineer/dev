/*
 * ICardSurfaceDesignUIRegistry.java
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
 * Created on Nov 20, 2009 at 11:16:02 PM.
 */

package org.gamegineer.table.ui;

import java.util.Collection;
import org.gamegineer.table.core.CardSurfaceDesignId;

/**
 * A service for the management and discovery of card surface design user
 * interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardSurfaceDesignUIRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card surface design user interface with the specified
     * identifier.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The card surface design user interface with the specified
     *         identifier or {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardSurfaceDesignUI getCardSurfaceDesignUI(
        /* @NonNull */
        CardSurfaceDesignId id );

    /**
     * Gets a collection of all card surface design user interfaces registered
     * with this service.
     * 
     * @return A collection of all card surface design user interfaces
     *         registered with this service; never {@code null}. This collection
     *         is a snapshot of the card surface design user interfaces
     *         registered at the time of the call.
     */
    /* @NonNull */
    public Collection<ICardSurfaceDesignUI> getCardSurfaceDesignUIs();

    /**
     * Registers the specified card surface design user interface.
     * 
     * @param cardSurfaceDesignUI
     *        The card surface design user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a card surface design user interface with the same identifier
     *         is already registered.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignUI} is {@code null}.
     */
    public void registerCardSurfaceDesignUI(
        /* @NonNull */
        ICardSurfaceDesignUI cardSurfaceDesignUI );

    /**
     * Unregisters the specified card surface design user interface.
     * 
     * @param cardSurfaceDesignUI
     *        The card surface design user interface; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified card surface design user interface was not
     *         previously registered.
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignUI} is {@code null}.
     */
    public void unregisterCardSurfaceDesignUI(
        /* @NonNull */
        ICardSurfaceDesignUI cardSurfaceDesignUI );
}
