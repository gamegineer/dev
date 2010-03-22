/*
 * CardPileBaseDesignFactory.java
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
 * Created on Jan 18, 2010 at 10:25:42 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.CardPileBaseDesign;

/**
 * A factory for creating card pile base designs.
 */
@ThreadSafe
public final class CardPileBaseDesignFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignFactory}
     * class.
     */
    private CardPileBaseDesignFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile base design.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * @param size
     *        The card pile base design size in table coordinates; no component
     *        may be negative.
     * 
     * @return A new card pile base design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createCardPileBaseDesign(
        /* @NonNull */
        final CardPileBaseDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createCardPileBaseDesign( id, size.width, size.height );
    }

    /**
     * Creates a new card pile base design.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
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
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createCardPileBaseDesign(
        /* @NonNull */
        final CardPileBaseDesignId id,
        final int width,
        final int height )
    {
        return new CardPileBaseDesign( id, width, height );
    }
}