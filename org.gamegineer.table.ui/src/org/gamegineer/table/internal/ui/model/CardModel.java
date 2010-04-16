/*
 * CardModel.java
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
 * Created on Dec 25, 2009 at 9:30:44 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ICard;

/**
 * The card model.
 */
@Immutable
public final class CardModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card associated with this model. */
    private final ICard card_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardModel} class.
     * 
     * @param card
     *        The card associated with this model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public CardModel(
        /* @NonNull */
        final ICard card )
    {
        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$

        card_ = card;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card associated with this model.
     * 
     * @return The card associated with this model; never {@code null}.
     */
    /* @NonNull */
    public ICard getCard()
    {
        return card_;
    }
}
