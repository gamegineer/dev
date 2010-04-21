/*
 * CardPileBaseDesigns.java
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
 * A factory for creating various types of card pile base designs suitable for
 * testing.
 */
@ThreadSafe
public final class CardPileBaseDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique card pile base design identifier. */
    private static final AtomicLong nextCardPileBaseDesignId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesigns} class.
     */
    private CardPileBaseDesigns()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card pile base design.
     * 
     * @param cardPileBaseDesign
     *        The card pile base design to clone; must not be {@code null}.
     * 
     * @return A new card pile base design; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign cloneCardPileBaseDesign(
        /* @NonNull */
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$

        return CardPileBaseDesignFactory.createCardPileBaseDesign( cardPileBaseDesign.getId(), cardPileBaseDesign.getSize() );
    }

    /**
     * Creates a new card pile base design with a unique identifier and a
     * default size.
     * 
     * @return A new card pile base design; never {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createUniqueCardPileBaseDesign()
    {
        return createUniqueCardPileBaseDesign( 100, 100 );
    }

    /**
     * Creates a new card pile base design with a unique identifier and the
     * specified size.
     * 
     * @param width
     *        The card pile base design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card pile base design height in table coordinates; must not be
     *        negative.
     * 
     * @return A new card pile base design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createUniqueCardPileBaseDesign(
        final int width,
        final int height )
    {
        return CardPileBaseDesignFactory.createCardPileBaseDesign( getUniqueCardPileBaseDesignId(), width, height );
    }

    /**
     * Gets a unique card pile base design identifier.
     * 
     * @return A unique card pile base design identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static CardPileBaseDesignId getUniqueCardPileBaseDesignId()
    {
        return CardPileBaseDesignId.fromString( String.format( "card-pile-base-design-%1$d", nextCardPileBaseDesignId_.incrementAndGet() ) ); //$NON-NLS-1$
    }
}
