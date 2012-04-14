/*
 * CardPileOrientation.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Apr 6, 2012 at 9:43:13 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;

/**
 * Enumerates the possible orientations of a card pile.
 */
@Immutable
public final class CardPileOrientation
    extends ComponentOrientation
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3829915723798616585L;

    /** The default card pile orientation. */
    public static final CardPileOrientation DEFAULT = new CardPileOrientation( "default", 0 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileOrientation} class.
     * 
     * @param name
     *        The name of the enum constant; must not be {@code null}.
     * @param ordinal
     *        The ordinal of the enum constant.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code ordinal} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    private CardPileOrientation(
        /* @NonNull */
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
        if( this == DEFAULT )
        {
            return DEFAULT;
        }

        throw new AssertionError( String.format( "unknown card pile orientation (%s)", name() ) ); //$NON-NLS-1$
    }
}
