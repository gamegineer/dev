/*
 * CardPileModelAsCardModelListenerTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Apr 16, 2010 at 11:07:41 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.gamegineer.table.core.CardPiles;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.CardPileModel} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.internal.ui.model.ICardModelListener} interface.
 */
public final class CardPileModelAsCardModelListenerTest
    extends AbstractCardModelListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileModelAsCardModelListenerTest} class.
     */
    public CardPileModelAsCardModelListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractCardModelListenerTestCase#createCardModelListener()
     */
    @Override
    protected ICardModelListener createCardModelListener()
    {
        return new CardPileModel( CardPiles.createUniqueCardPile() );
    }
}
