/*
 * CardPileViewAsCardPileModelListenerTest.java
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
 * Created on Jan 26, 2010 at 11:48:25 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.ui.model.AbstractCardPileModelListenerTestCase;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.ICardPileModelListener;
import org.gamegineer.table.ui.CardPileBaseDesignUIs;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardPileView} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.internal.ui.model.ICardPileModelListener}
 * interface.
 */
public final class CardPileViewAsCardPileModelListenerTest
    extends AbstractCardPileModelListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileViewAsCardPileModelListenerTest} class.
     */
    public CardPileViewAsCardPileModelListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractCardPileModelListenerTestCase#createCardPileModelListener()
     */
    @Override
    protected ICardPileModelListener createCardPileModelListener()
    {
        final ITable table = TableFactory.createTable();
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createCardPileBaseDesignUI( cardPileBaseDesign );
        final ICardPile cardPile = table.createCardPile();
        cardPile.setBaseDesign( cardPileBaseDesign );
        return new CardPileView( new CardPileModel( cardPile ), cardPileBaseDesignUI );
    }
}
