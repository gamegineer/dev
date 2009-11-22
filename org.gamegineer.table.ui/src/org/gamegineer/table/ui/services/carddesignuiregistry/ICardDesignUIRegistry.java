/*
 * ICardDesignUIRegistry.java
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
 * Created on Nov 20, 2009 at 11:16:02 PM.
 */

package org.gamegineer.table.ui.services.carddesignuiregistry;

import java.util.Collection;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.ui.ICardDesignUI;

/**
 * A service for the management and discovery of card design user interfaces.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardDesignUIRegistry
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card design user interface with the specified identifier.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * 
     * @return The card design user interface with the specified identifier or
     *         {@code null} if no such identifier is registered.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public ICardDesignUI getCardDesignUI(
        /* @NonNull */
        CardDesignId id );

    /**
     * Gets a collection of all card design user interfaces registered with this
     * service.
     * 
     * @return A collection of all card design user interfaces registered with
     *         this service; never {@code null}. This collection is a snapshot
     *         of the card design user interfaces registered at the time of the
     *         call.
     */
    /* @NonNull */
    public Collection<ICardDesignUI> getCardDesignUIs();

    /**
     * Registers the specified card design user interface.
     * 
     * <p>
     * This method does nothing if the specified card design user interface or a
     * card design user interface with the same identifier was previously
     * registered.
     * </p>
     * 
     * @param cardDesignUI
     *        The card design user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardDesignUI} is {@code null}.
     */
    public void registerCardDesignUI(
        /* @NonNull */
        ICardDesignUI cardDesignUI );

    /**
     * Unregisters the specified card design user interface.
     * 
     * <p>
     * This method does nothing if the specified card design user interface was
     * not previously registered.
     * </p>
     * 
     * @param cardDesignUI
     *        The card design user interface; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardDesignUI} is {@code null}.
     */
    public void unregisterCardDesignUI(
        /* @NonNull */
        ICardDesignUI cardDesignUI );
}
