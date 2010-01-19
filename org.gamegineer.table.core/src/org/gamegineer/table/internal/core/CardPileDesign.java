/*
 * CardPileDesign.java
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
 * Created on Jan 18, 2010 at 10:38:03 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.core.ICardPileDesign;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardPileDesign}.
 */
@Immutable
public final class CardPileDesign
    implements ICardPileDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile design identifier. */
    private final CardPileDesignId id_;

    /** The card pile design size in table coordinates. */
    private final Dimension size_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesign} class.
     * 
     * @param id
     *        The card pile design identifier; must not be {@code null}.
     * @param width
     *        The card pile design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card pile design height in table coordinates; must not be
     *        negative.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public CardPileDesign(
        /* @NonNull */
        final CardPileDesignId id,
        final int width,
        final int height )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentLegal( width >= 0, "width", Messages.CardPileDesign_ctor_width_negative ); //$NON-NLS-1$
        assertArgumentLegal( height >= 0, "height", Messages.CardPileDesign_ctor_height_negative ); //$NON-NLS-1$

        id_ = id;
        size_ = new Dimension( width, height );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileDesign#getId()
     */
    public CardPileDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.core.ICardPileDesign#getSize()
     */
    public Dimension getSize()
    {
        return new Dimension( size_ );
    }
}
