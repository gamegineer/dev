/*
 * CardSurfaceDesignFactory.java
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
 * Created on Nov 20, 2009 at 10:12:24 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.CardSurfaceDesign;

/**
 * A factory for creating card surface designs.
 */
@ThreadSafe
public final class CardSurfaceDesignFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignFactory} class.
     */
    private CardSurfaceDesignFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card surface design.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * @param size
     *        The card surface design size in table coordinates; no component
     *        may be negative.
     * 
     * @return A new card surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createCardSurfaceDesign(
        /* @NonNull */
        final CardSurfaceDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createCardSurfaceDesign( id, size.width, size.height );
    }

    /**
     * Creates a new card surface design.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
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
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createCardSurfaceDesign(
        /* @NonNull */
        final CardSurfaceDesignId id,
        final int width,
        final int height )
    {
        return new CardSurfaceDesign( id, width, height );
    }
}
