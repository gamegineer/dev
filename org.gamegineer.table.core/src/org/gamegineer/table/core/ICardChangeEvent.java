/*
 * ICardChangeEvent.java
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
 * Created on Oct 16, 2009 at 9:55:39 PM.
 */

package org.gamegineer.table.core;

/**
 * The interface that defines the behavior for all event objects used to notify
 * listeners that a card has been added, modified, or removed.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardChangeEvent
    extends ITableEvent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card associated with the event.
     * 
     * @return The card associated with the event; never {@code null}.
     */
    /* @NonNull */
    public ICard getCard();
}