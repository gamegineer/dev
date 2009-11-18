/*
 * CardDesignRegistryAsCardDesignRegistryTest.java
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
 * Created on Nov 17, 2009 at 10:23:40 PM.
 */

package org.gamegineer.table.internal.core.services.carddesignregistry;

import org.gamegineer.table.core.services.carddesignregistry.AbstractCardDesignRegistryTestCase;
import org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.services.carddesignregistry.CardDesignRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry}
 * interface.
 */
public final class CardDesignRegistryAsCardDesignRegistryTest
    extends AbstractCardDesignRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardDesignRegistryAsCardDesignRegistryTest} class.
     */
    public CardDesignRegistryAsCardDesignRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.services.carddesignregistry.AbstractCardDesignRegistryTestCase#createCardDesignRegistry()
     */
    @Override
    protected ICardDesignRegistry createCardDesignRegistry()
    {
        return new CardDesignRegistry();
    }
}
