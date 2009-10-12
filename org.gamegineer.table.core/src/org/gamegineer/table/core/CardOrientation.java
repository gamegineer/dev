/*
 * CardOrientation.java
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
 * Created on Oct 11, 2009 at 9:35:50 PM.
 */

package org.gamegineer.table.core;

/**
 * Enumerates the possible orientations of a card.
 */
public enum CardOrientation
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /** Indicates the card is oriented such that its back is up. */
    BACK_UP,

    /** Indicates the card is oriented such that its face is up. */
    FACE_UP;


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the inverse of this orientation.
     * 
     * @return The inverse of this orientation; never {@code null}.
     */
    /* @NonNull */
    public CardOrientation inverse()
    {
        switch( this )
        {
            case BACK_UP:
                return FACE_UP;

            case FACE_UP:
                return BACK_UP;
        }

        throw new AssertionError( "unknown orientation: " + name() ); //$NON-NLS-1$
    }
}
