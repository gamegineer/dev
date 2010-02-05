/*
 * CardPileBaseDesign.java
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
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.ICardPileBaseDesign;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardPileBaseDesign}.
 */
@Immutable
public final class CardPileBaseDesign
    implements ICardPileBaseDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile base design identifier. */
    private final CardPileBaseDesignId id_;

    /** The card pile base design size in table coordinates. */
    private final Dimension size_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesign} class.
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
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public CardPileBaseDesign(
        /* @NonNull */
        final CardPileBaseDesignId id,
        final int width,
        final int height )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentLegal( width >= 0, "width", Messages.CardPileBaseDesign_ctor_width_negative ); //$NON-NLS-1$
        assertArgumentLegal( height >= 0, "height", Messages.CardPileBaseDesign_ctor_height_negative ); //$NON-NLS-1$

        id_ = id;
        size_ = new Dimension( width, height );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileBaseDesign#getId()
     */
    public CardPileBaseDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.core.ICardPileBaseDesign#getSize()
     */
    public Dimension getSize()
    {
        return new Dimension( size_ );
    }
}
