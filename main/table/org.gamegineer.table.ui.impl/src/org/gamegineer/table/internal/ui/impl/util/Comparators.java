/*
 * Comparators.java
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
 * Created on Oct 18, 2011 at 7:53:31 PM.
 */

package org.gamegineer.table.internal.ui.impl.util;

import java.util.Comparator;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.IPlayer;

/**
 * A collection of commonly-used comparators.
 */
@ThreadSafe
public final class Comparators
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A comparator that compares players by name. */
    public static final Comparator<IPlayer> PLAYER_BY_NAME = new Comparator<IPlayer>()
    {
        @Override
        public int compare(
            final IPlayer o1,
            final IPlayer o2 )
        {
            return o1.getName().compareTo( o2.getName() );
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Comparators} class.
     */
    private Comparators()
    {
    }
}
