/*
 * CardPileEvent.java
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
 * Created on Jan 10, 2010 at 8:54:32 PM.
 */

package org.gamegineer.table.core;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * Superclass for all event objects fired by a table.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public abstract class CardPileEvent
    extends EventObject
    implements ICardPileEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -1807051243128926659L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileEvent} class.
     * 
     * @param source
     *        The card pile that fired the event; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    protected CardPileEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ICardPile source )
    {
        super( source );
    }
}
