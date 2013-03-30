/*
 * CardPileStrategyUI.java
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
 * Created on Sep 28, 2012 at 10:32:17 PM.
 */

package org.gamegineer.cards.internal.ui.strategies;

import net.jcip.annotations.Immutable;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.table.ui.AbstractContainerStrategyUI;

/**
 * A component strategy user interface that represents a card pile.
 */
@Immutable
final class CardPileStrategyUI
    extends AbstractContainerStrategyUI
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileStrategyUI} class.
     */
    CardPileStrategyUI()
    {
        super( CardsComponentStrategyIds.CARD_PILE );
    }
}
