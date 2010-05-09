/*
 * CardPileBaseDesignProxy.java
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
 * Created on Apr 27, 2010 at 10:35:29 PM.
 */

package org.gamegineer.table.internal.core.persistence.schemes.serializable;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.core.CardPileBaseDesign;

/**
 * A serializable proxy for the {@code CardPileBaseDesign} class.
 */
@NotThreadSafe
public final class CardPileBaseDesignProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8230792419874139320L;

    /**
     * The card pile base design height in table coordinates.
     * 
     * @serial
     */
    private int height_;

    /**
     * The card pile base design identifier.
     * 
     * @serial
     */
    private String id_;

    /**
     * The card pile base design width in table coordinates.
     * 
     * @serial
     */
    private int width_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignProxy} class.
     */
    @SuppressWarnings( "unused" )
    private CardPileBaseDesignProxy()
    {
        height_ = 0;
        id_ = null;
        width_ = 0;
    }

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignProxy} class
     * from the specified {@code CardPileBaseDesign} instance.
     * 
     * @param cardPileBaseDesign
     *        The {@code CardPileBaseDesign} instance; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesign} is {@code null}.
     */
    public CardPileBaseDesignProxy(
        /* @NonNull */
        final CardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$

        id_ = cardPileBaseDesign.getId().toString();
        final Dimension size = cardPileBaseDesign.getSize();
        height_ = size.height;
        width_ = size.width;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a replacement object for this instance after it has been
     * deserialized.
     * 
     * @return A replacement object for this instance after it has been
     *         deserialized; never {@code null}.
     */
    /* @NonNull */
    private Object readResolve()
    {
        return new CardPileBaseDesign( CardPileBaseDesignId.fromString( id_ ), width_, height_ );
    }
}
