/*
 * CardOrientationAsExtensibleEnumTest.java
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
 * Created on Apr 12, 2012 at 8:01:41 PM.
 */

package org.gamegineer.cards.core;

import org.gamegineer.common.core.util.AbstractExtensibleEnumTestCase;
import org.gamegineer.common.core.util.ExtensibleEnum;

/**
 * A fixture for testing the {@link org.gamegineer.cards.core.CardOrientation}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.util.ExtensibleEnum} class
 */
public final class CardOrientationAsExtensibleEnumTest
    extends AbstractExtensibleEnumTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardOrientationAsExtensibleEnumTest} class.
     */
    public CardOrientationAsExtensibleEnumTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.AbstractExtensibleEnumTestCase#getType()
     */
    @Override
    protected Class<? extends ExtensibleEnum> getType()
    {
        return CardOrientation.class;
    }
}
