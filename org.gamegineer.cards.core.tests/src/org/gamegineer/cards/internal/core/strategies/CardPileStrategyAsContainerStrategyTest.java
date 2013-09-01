/*
 * CardPileStrategyAsContainerStrategyTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Aug 1, 2012 at 8:19:40 PM.
 */

package org.gamegineer.cards.internal.core.strategies;

import org.gamegineer.table.core.AbstractContainerStrategyTestCase;
import org.gamegineer.table.core.IContainerStrategy;

/**
 * A fixture for testing the {@link CardPileStrategy} class to ensure it does
 * not violate the contract of the {@link IContainerStrategy} interface.
 */
public final class CardPileStrategyAsContainerStrategyTest
    extends AbstractContainerStrategyTestCase<CardPileStrategy>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileStrategyAsContainerStrategyTest} class.
     */
    public CardPileStrategyAsContainerStrategyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentStrategyTestCase#createComponentStrategy()
     */
    @Override
    protected CardPileStrategy createComponentStrategy()
    {
        return new CardPileStrategy();
    }
}
