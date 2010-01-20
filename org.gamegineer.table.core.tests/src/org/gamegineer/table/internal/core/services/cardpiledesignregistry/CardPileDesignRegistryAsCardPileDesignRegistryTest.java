/*
 * CardPileDesignRegistryAsCardPileDesignRegistryTest.java
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
 * Created on Jan 19, 2010 at 11:39:33 PM.
 */

package org.gamegineer.table.internal.core.services.cardpiledesignregistry;

import org.gamegineer.table.core.services.cardpiledesignregistry.AbstractCardPileDesignRegistryTestCase;
import org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.services.cardpiledesignregistry.CardPileDesignRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry}
 * interface.
 */
public final class CardPileDesignRegistryAsCardPileDesignRegistryTest
    extends AbstractCardPileDesignRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileDesignRegistryAsCardPileDesignRegistryTest} class.
     */
    public CardPileDesignRegistryAsCardPileDesignRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.services.cardpiledesignregistry.AbstractCardPileDesignRegistryTestCase#createCardPileDesignRegistry()
     */
    @Override
    protected ICardPileDesignRegistry createCardPileDesignRegistry()
    {
        return new CardPileDesignRegistry();
    }
}
