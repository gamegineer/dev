/*
 * CardDesigns.java
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
 * Created on Nov 15, 2009 at 12:15:42 AM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of card designs types suitable for
 * testing.
 */
@ThreadSafe
public final class CardDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique card design identifier. */
    private static final AtomicLong nextCardDesignId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesigns} class.
     */
    private CardDesigns()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card design with the specified identifier.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * 
     * @return A new card design; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardDesign createCardDesign(
        /* @NonNull */
        final CardDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return new FakeCardDesign( id );
    }

    /**
     * Creates a new card design with a unique identifier.
     * 
     * @return A new card design; never {@code null}.
     */
    /* @NonNull */
    public static ICardDesign createUniqueCardDesign()
    {
        return createCardDesign( getUniqueCardDesignId() );
    }

    /**
     * Gets a unique card design identifier.
     * 
     * @return A unique card design identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    public static CardDesignId getUniqueCardDesignId()
    {
        return CardDesignId.fromString( String.format( "card-design-%1$d", nextCardDesignId_.incrementAndGet() ) ); //$NON-NLS-1$
    }
}
