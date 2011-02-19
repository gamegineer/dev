/*
 * CardPileBaseDesignUIRegistryAsCardPileBaseDesignUIRegistryTest.java
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
 * Created on Jan 23, 2010 at 9:50:52 PM.
 */

package org.gamegineer.table.internal.ui;

import org.gamegineer.table.ui.AbstractCardPileBaseDesignUIRegistryTestCase;
import org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardPileBaseDesignUIRegistry} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry} interface.
 */
public final class CardPileBaseDesignUIRegistryAsCardPileBaseDesignUIRegistryTest
    extends AbstractCardPileBaseDesignUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileBaseDesignUIRegistryAsCardPileBaseDesignUIRegistryTest} class.
     */
    public CardPileBaseDesignUIRegistryAsCardPileBaseDesignUIRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.cardpilebasedesignuiregistry.AbstractCardPileBaseDesignUIRegistryTestCase#createCardPileBaseDesignUIRegistry()
     */
    @Override
    protected ICardPileBaseDesignUIRegistry createCardPileBaseDesignUIRegistry()
    {
        return new CardPileBaseDesignUIRegistry();
    }
}
