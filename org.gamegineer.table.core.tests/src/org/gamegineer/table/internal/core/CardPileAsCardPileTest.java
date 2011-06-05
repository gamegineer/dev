/*
 * CardPileAsCardPileTest.java
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
 * Created on Jan 14, 2010 at 11:13:29 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractCardPileTestCase;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPile;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public final class CardPileAsCardPileTest
    extends AbstractCardPileTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileAsCardPileTest} class.
     */
    public CardPileAsCardPileTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardPileTestCase#createCardPile()
     */
    @Override
    protected ICardPile createCardPile()
    {
        return new CardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractCardPileTestCase#createCardPile(java.lang.Object)
     */
    @Override
    protected ICardPile createCardPile(
        final Object memento )
        throws Exception
    {
        return CardPile.fromMemento( memento );
    }
}
