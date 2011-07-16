/*
 * CardViewAsCardListenerTest.java
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
 * Created on Oct 24, 2009 at 10:45:09 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.core.AbstractCardListenerTestCase;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardListener;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.ui.model.CardModel;
import org.gamegineer.table.ui.CardSurfaceDesignUIs;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardView} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardListener} interface.
 */
public final class CardViewAsCardListenerTest
    extends AbstractCardListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardViewAsCardListenerTest}
     * class.
     */
    public CardViewAsCardListenerTest()
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
        final ITable table = TableFactory.createTable();
        final ICardSurfaceDesign backDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        final ICardSurfaceDesignUI backDesignUI = CardSurfaceDesignUIs.createCardSurfaceDesignUI( backDesign );
        final ICardSurfaceDesign faceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        final ICardSurfaceDesignUI faceDesignUI = CardSurfaceDesignUIs.createCardSurfaceDesignUI( faceDesign );
        final ICard card = table.createCard();
        card.setSurfaceDesigns( backDesign, faceDesign );
        return new CardView( new CardModel( card ), backDesignUI, faceDesignUI );
    }
}
