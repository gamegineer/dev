/*
 * CardPileOrientationAsExtensibleEnumTest.java
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
 * Created on Apr 6, 2012 at 9:45:53 PM.
 */

package org.gamegineer.cards.core;

import org.gamegineer.common.core.util.ExtensibleEnum;
import org.gamegineer.common.core.util.test.AbstractExtensibleEnumTestCase;

/**
 * A fixture for testing the {@link CardPileOrientation} class to ensure it does
 * not violate the contract of the {@link ExtensibleEnum} class
 */
public final class CardPileOrientationAsExtensibleEnumTest
    extends AbstractExtensibleEnumTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileOrientationAsExtensibleEnumTest} class.
     */
    public CardPileOrientationAsExtensibleEnumTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.test.AbstractExtensibleEnumTestCase#getType()
     */
    @Override
    protected Class<? extends ExtensibleEnum> getType()
    {
        return CardPileOrientation.class;
    }
}