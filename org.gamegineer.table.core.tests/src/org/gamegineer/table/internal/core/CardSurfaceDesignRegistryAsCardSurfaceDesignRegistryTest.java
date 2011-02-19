/*
 * CardSurfaceDesignRegistryAsCardSurfaceDesignRegistryTest.java
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
 * Created on Nov 17, 2009 at 10:23:40 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractCardSurfaceDesignRegistryTestCase;
import org.gamegineer.table.core.ICardSurfaceDesignRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardSurfaceDesignRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardSurfaceDesignRegistry} interface.
 */
public final class CardSurfaceDesignRegistryAsCardSurfaceDesignRegistryTest
    extends AbstractCardSurfaceDesignRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignRegistryAsCardSurfaceDesignRegistryTest} class.
     */
    public CardSurfaceDesignRegistryAsCardSurfaceDesignRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.cardsurfacedesignregistry.AbstractCardSurfaceDesignRegistryTestCase#createCardSurfaceDesignRegistry()
     */
    @Override
    protected ICardSurfaceDesignRegistry createCardSurfaceDesignRegistry()
    {
        return new CardSurfaceDesignRegistry();
    }
}
