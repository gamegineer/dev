/*
 * ComponentStrategyUIs.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Mar 29, 2013 at 10:09:58 PM.
 */

package org.gamegineer.cards.internal.ui.strategies;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IContainerStrategyUI;

/**
 * A collection of common component strategy user interfaces.
 */
@ThreadSafe
public final class ComponentStrategyUIs
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card component strategy user interface. */
    public static final IComponentStrategyUI CARD = new CardStrategyUI();

    /** The card pile container strategy user interface. */
    public static final IContainerStrategyUI CARD_PILE = new CardPileStrategyUI();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyUIs} class.
     */
    private ComponentStrategyUIs()
    {
    }
}
