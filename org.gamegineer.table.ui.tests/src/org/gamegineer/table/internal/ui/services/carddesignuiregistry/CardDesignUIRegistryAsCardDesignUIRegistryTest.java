/*
 * CardDesignUIRegistryAsCardDesignUIRegistryTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Nov 21, 2009 at 10:14:38 PM.
 */

package org.gamegineer.table.internal.ui.services.carddesignuiregistry;

import org.gamegineer.table.ui.services.carddesignuiregistry.AbstractCardDesignUIRegistryTestCase;
import org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.services.carddesignuiregistry.CardDesignUIRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry}
 * interface.
 */
public final class CardDesignUIRegistryAsCardDesignUIRegistryTest
    extends AbstractCardDesignUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardDesignUIRegistryAsCardDesignUIRegistryTest} class.
     */
    public CardDesignUIRegistryAsCardDesignUIRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.services.carddesignuiregistry.AbstractCardDesignUIRegistryTestCase#createCardDesignUIRegistry()
     */
    @Override
    protected ICardDesignUIRegistry createCardDesignUIRegistry()
    {
        return new CardDesignUIRegistry();
    }
}
