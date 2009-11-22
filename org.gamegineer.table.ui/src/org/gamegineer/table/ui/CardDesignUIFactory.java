/*
 * CardDesignUIFactory.java
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
 * Created on Nov 21, 2009 at 12:18:33 AM.
 */

package org.gamegineer.table.ui;

import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.internal.ui.CardDesignUI;

/**
 * A factory for creating card design user interfaces.
 */
@ThreadSafe
public final class CardDesignUIFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignUIFactory} class.
     */
    private CardDesignUIFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card design user interface.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * @param name
     *        The card design name; must not be {@code null}.
     * @param icon
     *        The card design icon; must not be {@code null}.
     * 
     * @return A new card design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code icon} is {@code null}.
     */
    /* @NonNull */
    public static ICardDesignUI createCardDesignUI(
        /* @NonNull */
        final CardDesignId id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Icon icon )
    {
        return new CardDesignUI( id, name, icon );
    }
}
