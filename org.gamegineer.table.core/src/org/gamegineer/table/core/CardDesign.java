/*
 * CardDesign.java
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
 * Created on Oct 10, 2009 at 11:49:59 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * Identifies the design of a card surface.
 * 
 * <p>
 * An instance of {@code CardDesign} is suitable for use as a key in a hash
 * container.
 * </p>
 */
@Immutable
public final class CardDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The empty card design. */
    public static final CardDesign EMPTY;

    /** The card design name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CardDesign} class.
     */
    static
    {
        EMPTY = new CardDesign( "" ); //$NON-NLS-1$
    }

    /**
     * Initializes a new instance of the {@code CardDesign} class.
     * 
     * @param name
     *        The card design name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public CardDesign(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof CardDesign) )
        {
            return false;
        }

        final CardDesign other = (CardDesign)obj;
        return name_.equals( other.name_ );
    }

    /**
     * Gets the card design name.
     * 
     * @return The card design name; never {@code null}.
     */
    /* @NonNull */
    public String getName()
    {
        return name_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return name_.hashCode();
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format( "CardDesign[name_='%1$s']", name_ ); //$NON-NLS-1$
    }
}
