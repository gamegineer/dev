/*
 * TabletopOrientation.java
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
 * Created on Jul 5, 2012 at 8:35:32 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;

/**
 * Enumerates the possible orientations of a {@code Tabletop}.
 */
@Immutable
public final class TabletopOrientation
    extends ComponentOrientation
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -6305741270318957331L;

    /** The default orientation. */
    public static final TabletopOrientation DEFAULT = new TabletopOrientation( "default", 0 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Orientation} class.
     * 
     * @param name
     *        The name of the enum constant.
     * @param ordinal
     *        The ordinal of the enum constant.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code ordinal} is negative.
     */
    private TabletopOrientation(
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

        throw new AssertionError( String.format( "unknown tabletop orientation (%s)", name() ) ); //$NON-NLS-1$
    }
}
