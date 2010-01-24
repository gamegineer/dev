/*
 * CardPileDesignUIRegistryAsCardPileDesignUIRegistryTest.java
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
 * Created on Jan 23, 2010 at 9:50:52 PM.
 */

package org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry;

import org.gamegineer.table.ui.services.cardpiledesignuiregistry.AbstractCardPileDesignUIRegistryTestCase;
import org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry.CardPileDesignUIRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry}
 * interface.
 */
public final class CardPileDesignUIRegistryAsCardPileDesignUIRegistryTest
    extends AbstractCardPileDesignUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileDesignUIRegistryAsCardPileDesignUIRegistryTest} class.
     */
    public CardPileDesignUIRegistryAsCardPileDesignUIRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.services.cardpiledesignuiregistry.AbstractCardPileDesignUIRegistryTestCase#createCardPileDesignUIRegistry()
     */
    @Override
    protected ICardPileDesignUIRegistry createCardPileDesignUIRegistry()
    {
        return new CardPileDesignUIRegistry();
    }
}
