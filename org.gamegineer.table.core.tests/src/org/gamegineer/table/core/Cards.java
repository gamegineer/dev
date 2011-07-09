/*
 * Cards.java
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
 * Created on Apr 20, 2010 at 9:56:59 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of cards suitable for testing.
 */
@ThreadSafe
public final class Cards
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Cards} class.
     */
    private Cards()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card with unique back and face designs for the specified
     * table.
     * 
     * @param table
     *        The table associated with the new card; must not be {@code null}.
     * 
     * @return A new card; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    /* @NonNull */
    public static ICard createUniqueCard(
        /* @NonNull */
        final ITable table )
    {
        return table.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }
}
