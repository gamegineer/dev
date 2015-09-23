/*
 * CardOrientation.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.cards.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ComponentOrientation;

/**
 * Enumerates the possible orientations of a card.
 */
@Immutable
public final class CardOrientation
    extends ComponentOrientation
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 2260531739748011721L;

    /** The card back orientation. */
    public static final CardOrientation BACK = new CardOrientation( "back", 0 ); //$NON-NLS-1$

    /** The card face orientation. */
    public static final CardOrientation FACE = new CardOrientation( "face", 1 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardOrientation} class.
     * 
     * @param name
     *        The name of the enum constant.
     * @param ordinal
     *        The ordinal of the enum constant.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code ordinal} is negative.
     */
    private CardOrientation(
        final String name,
        final int ordinal )
    {
        super( name, ordinal );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ComponentOrientation#inverse()
     */
    @Override
    public ComponentOrientation inverse()
    {
        if( this == BACK )
        {
            return FACE;
        }
        else if( this == FACE )
        {
            return BACK;
        }

        throw new AssertionError( String.format( "unknown card orientation (%s)", name() ) ); //$NON-NLS-1$
    }
}
