/*
 * CardPileStrategyUIAsContainerStrategyUITest.java
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
 * Created on Sep 29, 2012 at 9:49:02 PM.
 */

package org.gamegineer.cards.internal.ui.impl.strategies;

import org.gamegineer.table.ui.IContainerStrategyUI;
import org.gamegineer.table.ui.test.AbstractContainerStrategyUITestCase;

/**
 * A fixture for testing the {@link CardPileStrategyUI} class to ensure it does
 * not violate the contract of the {@link IContainerStrategyUI} interface.
 */
public final class CardPileStrategyUIAsContainerStrategyUITest
    extends AbstractContainerStrategyUITestCase<CardPileStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileStrategyUIAsContainerStrategyUITest} class.
     */
    public CardPileStrategyUIAsContainerStrategyUITest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.test.AbstractComponentStrategyUITestCase#createComponentStrategyUI()
     */
    @Override
    protected CardPileStrategyUI createComponentStrategyUI()
    {
        return new CardPileStrategyUI();
    }
}
