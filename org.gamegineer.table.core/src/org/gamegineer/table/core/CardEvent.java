/*
 * CardEvent.java
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
 * Created on Oct 24, 2009 at 9:14:12 PM.
 */

package org.gamegineer.table.core;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a card.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class CardEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 2812928653096081948L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardEvent} class.
     * 
     * @param source
     *        The card that fired the event; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    public CardEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ICard source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card that fired the event.
     * 
     * @return The card that fired the event; never {@code null}.
     */
    /* @NonNull */
    public final ICard getCard()
    {
        return (ICard)getSource();
    }
}
