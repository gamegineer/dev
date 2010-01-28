/*
 * CardPileViewAsCardPileListenerTest.java
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
 * Created on Jan 26, 2010 at 11:48:41 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.core.AbstractCardPileListenerTestCase;
import org.gamegineer.table.core.CardPileDesigns;
import org.gamegineer.table.core.CardPileFactory;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileDesign;
import org.gamegineer.table.core.ICardPileListener;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.ui.CardPileDesignUIs;
import org.gamegineer.table.ui.ICardPileDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardPileView} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPileListener} interface.
 */
public final class CardPileViewAsCardPileListenerTest
    extends AbstractCardPileListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileViewAsCardPileListenerTest} class.
     */
    public CardPileViewAsCardPileListenerTest()
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
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createCardPileDesignUI( cardPileDesign );
        final ICardPile cardPile = CardPileFactory.createCardPile( cardPileDesign );
        return new CardPileView( new CardPileModel( cardPile ), cardPileDesignUI );
    }
}
