/*
 * CardPileDesignUIAsCardPileDesignUITest.java
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
 * Created on Jan 23, 2010 at 9:03:22 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.ui.AbstractCardPileDesignUITestCase;
import org.gamegineer.table.ui.ICardPileDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardPileDesignUI} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.ui.ICardPileDesignUI} interface.
 */
public final class CardPileDesignUIAsCardPileDesignUITest
    extends AbstractCardPileDesignUITestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileDesignUIAsCardPileDesignUITest} class.
     */
    public CardPileDesignUIAsCardPileDesignUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractCardPileDesignUITestCase#createCardPileDesignUI()
     */
    @Override
    protected ICardPileDesignUI createCardPileDesignUI()
    {
        return new CardPileDesignUI( CardPileDesignId.fromString( "id" ), "name", createDummy( Icon.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
