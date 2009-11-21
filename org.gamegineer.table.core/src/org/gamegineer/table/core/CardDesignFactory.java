/*
 * CardDesignFactory.java
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
 * Created on Nov 20, 2009 at 10:12:24 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.CardDesign;

/**
 * A factory for creating card designs.
 */
@ThreadSafe
public final class CardDesignFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignFactory} class.
     */
    private CardDesignFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card design.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * @param size
     *        The card design size in table coordinates; no component may be
     *        negative.
     * 
     * @return A new card design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static ICardDesign createCardDesign(
        /* @NonNull */
        final CardDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createCardDesign( id, size.width, size.height );
    }

    /**
     * Creates a new card design.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * @param width
     *        The card design width in table coordinates; must not be negative.
     * @param height
     *        The card design height in table coordinates; must not be negative.
     * 
     * @return A new card design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardDesign createCardDesign(
        /* @NonNull */
        final CardDesignId id,
        final int width,
        final int height )
    {
        return new CardDesign( id, width, height );
    }
}
