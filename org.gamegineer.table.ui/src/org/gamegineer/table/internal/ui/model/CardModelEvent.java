/*
 * CardModelEvent.java
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
 * Created on Dec 25, 2009 at 9:46:35 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a card model.
 */
@ThreadSafe
public final class CardModelEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1754225832033072604L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardModelEvent} class.
     * 
     * @param source
     *        The card model that fired the event; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    public CardModelEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final CardModel source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card model that fired the event.
     * 
     * @return The card model that fired the event; never {@code null}.
     */
    /* @NonNull */
    public CardModel getCardModel()
    {
        return (CardModel)getSource();
    }
}
