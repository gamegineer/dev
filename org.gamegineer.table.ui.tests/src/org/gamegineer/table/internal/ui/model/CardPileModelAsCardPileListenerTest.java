/*
 * CardPileModelAsCardPileListenerTest.java
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
 * Created on Jan 27, 2010 at 11:02:19 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.gamegineer.table.core.AbstractCardPileListenerTestCase;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.core.TableFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.CardPileModel} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPileListener} interface.
 */
public final class CardPileModelAsCardPileListenerTest
    extends AbstractCardPileListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileModelAsCardPileListenerTest} class.
     */
    public CardPileModelAsCardPileListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardPileListenerTestCase#createCardPileListener()
     */
    @Override
    protected ICardPileListener createCardPileListener()
    {
        return new CardPileModel( CardPiles.createUniqueCardPile( TableFactory.createTable() ) );
    }
}
