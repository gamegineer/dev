/*
 * CardPileDesignUIFactory.java
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
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.internal.ui.CardPileDesignUI;

/**
 * A factory for creating card pile design user interfaces.
 */
@ThreadSafe
public final class CardPileDesignUIFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesignUIFactory} class.
     */
    private CardPileDesignUIFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile design user interface.
     * 
     * @param id
     *        The card pile design identifier; must not be {@code null}.
     * @param name
     *        The card pile design name; must not be {@code null}.
     * @param icon
     *        The card pile design icon; must not be {@code null}.
     * 
     * @return A new card pile design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesignUI createCardPileDesignUI(
        /* @NonNull */
        final CardPileDesignId id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Icon icon )
    {
        return new CardPileDesignUI( id, name, icon );
    }
}
