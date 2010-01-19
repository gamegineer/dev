/*
 * CardPileDesignAsCardPileDesignTest.java
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
 * Created on Jan 18, 2010 at 10:50:24 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractCardPileDesignTestCase;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.core.ICardPileDesign;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardPileDesign} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPileDesign} interface.
 */
public final class CardPileDesignAsCardPileDesignTest
    extends AbstractCardPileDesignTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileDesignAsCardPileDesignTest} class.
     */
    public CardPileDesignAsCardPileDesignTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardPileDesignTestCase#createCardPileDesign()
     */
    @Override
    protected ICardPileDesign createCardPileDesign()
    {
        return new CardPileDesign( CardPileDesignId.fromString( "id" ), 0, 0 ); //$NON-NLS-1$
    }
}
