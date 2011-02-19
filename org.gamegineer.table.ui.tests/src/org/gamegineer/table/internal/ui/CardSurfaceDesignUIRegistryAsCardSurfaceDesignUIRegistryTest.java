/*
 * CardSurfaceDesignUIRegistryAsCardSurfaceDesignUIRegistryTest.java
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
 * Created on Nov 21, 2009 at 10:14:38 PM.
 */

package org.gamegineer.table.internal.ui;

import org.gamegineer.table.ui.AbstractCardSurfaceDesignUIRegistryTestCase;
import org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardSurfaceDesignUIRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry} interface.
 */
public final class CardSurfaceDesignUIRegistryAsCardSurfaceDesignUIRegistryTest
    extends AbstractCardSurfaceDesignUIRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignUIRegistryAsCardSurfaceDesignUIRegistryTest} class.
     */
    public CardSurfaceDesignUIRegistryAsCardSurfaceDesignUIRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.services.cardsurfacedesignuiregistry.AbstractCardSurfaceDesignUIRegistryTestCase#createCardSurfaceDesignUIRegistry()
     */
    @Override
    protected ICardSurfaceDesignUIRegistry createCardSurfaceDesignUIRegistry()
    {
        return new CardSurfaceDesignUIRegistry();
    }
}
