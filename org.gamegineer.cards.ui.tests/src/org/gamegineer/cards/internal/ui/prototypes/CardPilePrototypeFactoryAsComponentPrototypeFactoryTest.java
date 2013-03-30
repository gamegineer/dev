/*
 * CardPilePrototypeFactoryAsComponentPrototypeFactoryTest.java
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
 * Created on Oct 26, 2012 at 10:20:47 PM.
 */

package org.gamegineer.cards.internal.ui.prototypes;

import org.gamegineer.table.ui.prototype.AbstractComponentPrototypeFactoryTestCase;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.cards.internal.ui.prototypes.CardPilePrototypeFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.prototype.IComponentPrototypeFactory}
 * interface.
 */
public final class CardPilePrototypeFactoryAsComponentPrototypeFactoryTest
    extends AbstractComponentPrototypeFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPilePrototypeFactoryAsComponentPrototypeFactoryTest} class.
     */
    public CardPilePrototypeFactoryAsComponentPrototypeFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.prototype.AbstractComponentPrototypeFactoryTestCase#createComponentPrototypeFactory()
     */
    @Override
    protected IComponentPrototypeFactory createComponentPrototypeFactory()
    {
        return new CardPilePrototypeFactory();
    }
}
