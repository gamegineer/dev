/*
 * CardsComponentStrategyIds.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Sep 28, 2012 at 10:50:19 PM.
 */

package org.gamegineer.cards.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;

/**
 * A collection of component strategy identifiers for the cards game.
 */
@ThreadSafe
public final class CardsComponentStrategyIds
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card component strategy identifier. */
    public static final ComponentStrategyId CARD = ComponentStrategyId.fromString( "org.gamegineer.cards.componentStrategies.card" ); //$NON-NLS-1$

    /** The card pile component strategy identifier. */
    public static final ComponentStrategyId CARD_PILE = ComponentStrategyId.fromString( "org.gamegineer.cards.componentStrategies.cardPile" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardsComponentStrategyIds}
     * class.
     */
    private CardsComponentStrategyIds()
    {
    }
}
