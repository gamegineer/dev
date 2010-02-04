/*
 * CardSurfaceDesigns.java
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
 * Created on Nov 15, 2009 at 12:15:42 AM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of card surface designs types suitable
 * for testing.
 */
@ThreadSafe
public final class CardSurfaceDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique card surface design identifier. */
    private static final AtomicLong nextCardSurfaceDesignId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesigns} class.
     */
    private CardSurfaceDesigns()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card surface design.
     * 
     * @param cardSurfaceDesign
     *        The card surface design to clone; must not be {@code null}.
     * 
     * @return A new card surface design; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign cloneCardSurfaceDesign(
        /* @NonNull */
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$

        return CardSurfaceDesignFactory.createCardSurfaceDesign( cardSurfaceDesign.getId(), cardSurfaceDesign.getSize() );
    }

    /**
     * Creates a new card surface design with a unique identifier and a default
     * size.
     * 
     * @return A new card surface design; never {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createUniqueCardSurfaceDesign()
    {
        return createUniqueCardSurfaceDesign( 100, 100 );
    }

    /**
     * Creates a new card surface design with a unique identifier and the
     * specified size.
     * 
     * @param width
     *        The card surface design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card surface design height in table coordinates; must not be
     *        negative.
     * 
     * @return A new card surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createUniqueCardSurfaceDesign(
        final int width,
        final int height )
    {
        return CardSurfaceDesignFactory.createCardSurfaceDesign( getUniqueCardSurfaceDesignId(), width, height );
    }

    /**
     * Gets a unique card surface design identifier.
     * 
     * @return A unique card surface design identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static CardSurfaceDesignId getUniqueCardSurfaceDesignId()
    {
        return CardSurfaceDesignId.fromString( String.format( "card-surface-design-%1$d", nextCardSurfaceDesignId_.incrementAndGet() ) ); //$NON-NLS-1$
    }
}
