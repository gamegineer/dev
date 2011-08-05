/*
 * CardListenerAsCardListenerTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Aug 2, 2011 at 8:09:58 PM.
 */

package org.gamegineer.table.core;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardListener}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardListener} interface.
 */
public final class CardListenerAsCardListenerTest
    extends AbstractCardListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardListenerAsCardListenerTest}
     * class.
     */
    public CardListenerAsCardListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardListenerTestCase#createCardListener()
     */
    @Override
    protected ICardListener createCardListener()
    {
        return new CardListener();
    }
}
