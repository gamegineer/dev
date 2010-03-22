/*
 * CardPileBaseDesignUIFactory.java
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
 * Created on Jan 23, 2010 at 9:06:37 PM.
 */

package org.gamegineer.table.ui;

import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.ui.CardPileBaseDesignUI;

/**
 * A factory for creating card pile base design user interfaces.
 */
@ThreadSafe
public final class CardPileBaseDesignUIFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignUIFactory}
     * class.
     */
    private CardPileBaseDesignUIFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile base design user interface.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * @param name
     *        The card pile base design name; must not be {@code null}.
     * @param icon
     *        The card pile base design icon; must not be {@code null}.
     * 
     * @return A new card pile base design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesignUI createCardPileBaseDesignUI(
        /* @NonNull */
        final CardPileBaseDesignId id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Icon icon )
    {
        return new CardPileBaseDesignUI( id, name, icon );
    }
}