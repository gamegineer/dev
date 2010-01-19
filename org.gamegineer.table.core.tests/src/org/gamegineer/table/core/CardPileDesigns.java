/*
 * CardPileDesigns.java
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
 * Created on Jan 18, 2010 at 11:49:40 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of card pile designs types suitable for
 * testing.
 */
@ThreadSafe
public final class CardPileDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique card pile design identifier. */
    private static final AtomicLong nextCardPileDesignId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesigns} class.
     */
    private CardPileDesigns()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card pile design.
     * 
     * @param cardPileDesign
     *        The card pile design to clone; must not be {@code null}.
     * 
     * @return A new card pile design; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesign cloneCardPileDesign(
        /* @NonNull */
        final ICardPileDesign cardPileDesign )
    {
        assertArgumentNotNull( cardPileDesign, "cardPileDesign" ); //$NON-NLS-1$

        return CardPileDesignFactory.createCardPileDesign( cardPileDesign.getId(), cardPileDesign.getSize() );
    }

    /**
     * Creates a new card pile design with a unique identifier and a default
     * size.
     * 
     * @return A new card pile design; never {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesign createUniqueCardPileDesign()
    {
        return createUniqueCardPileDesign( 100, 100 );
    }

    /**
     * Creates a new card pile design with a unique identifier and the specified
     * size.
     * 
     * @param width
     *        The card pile design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card pile design height in table coordinates; must not be
     *        negative.
     * 
     * @return A new card pile design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     */
    /* @NonNull */
    public static ICardPileDesign createUniqueCardPileDesign(
        final int width,
        final int height )
    {
        return CardPileDesignFactory.createCardPileDesign( getUniqueCardPileDesignId(), width, height );
    }

    /**
     * Gets a unique card pile design identifier.
     * 
     * @return A unique card pile design identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static CardPileDesignId getUniqueCardPileDesignId()
    {
        return CardPileDesignId.fromString( String.format( "card-pile-design-%1$d", nextCardPileDesignId_.incrementAndGet() ) ); //$NON-NLS-1$
    }
}
