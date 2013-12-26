/*
 * CardPrototypeFactoryAsComponentPrototypeFactoryTest.java
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
 * Created on Oct 24, 2012 at 8:11:26 PM.
 */

package org.gamegineer.cards.internal.ui.impl.prototypes;

import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;
import org.gamegineer.table.ui.prototype.test.AbstractComponentPrototypeFactoryTestCase;

/**
 * A fixture for testing the {@link CardPrototypeFactory} class to ensure it
 * does not violate the contract of the {@link IComponentPrototypeFactory}
 * interface.
 */
public final class CardPrototypeFactoryAsComponentPrototypeFactoryTest
    extends AbstractComponentPrototypeFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPrototypeFactoryAsComponentPrototypeFactoryTest} class.
     */
    public CardPrototypeFactoryAsComponentPrototypeFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.prototype.test.AbstractComponentPrototypeFactoryTestCase#createComponentPrototypeFactory()
     */
    @Override
    protected IComponentPrototypeFactory createComponentPrototypeFactory()
    {
        return new CardPrototypeFactory();
    }
}
