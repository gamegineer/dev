/*
 * CardStrategyFactory.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 1, 2012 at 8:10:30 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.ComponentStrategies;

/**
 * A factory for creating component strategies based on a game of playing cards.
 */
@ThreadSafe
public final class CardStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardStrategyFactory} class.
     */
    private CardStrategyFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a component strategy that represents a card pile.
     * 
     * @return A component strategy that represents a card pile; never
     *         {@code null}.
     */
    /* @NonNull */
    public static IContainerStrategy createCardPileStrategy()
    {
        return ComponentStrategies.CARD_PILE;
    }

    /**
     * Creates a component strategy that represents a card.
     * 
     * @return A component strategy that represents a card; never {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategy createCardStrategy()
    {
        return ComponentStrategies.CARD;
    }
}
