/*
 * CardSurfaceDesignAsCardSurfaceDesignTest.java
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
 * Created on Nov 17, 2009 at 9:33:06 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractCardSurfaceDesignTestCase;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardSurfaceDesign} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardSurfaceDesign} interface.
 */
public final class CardSurfaceDesignAsCardSurfaceDesignTest
    extends AbstractCardSurfaceDesignTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignAsCardSurfaceDesignTest} class.
     */
    public CardSurfaceDesignAsCardSurfaceDesignTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardSurfaceDesignTestCase#createCardSurfaceDesign()
     */
    @Override
    protected ICardSurfaceDesign createCardSurfaceDesign()
    {
        return new CardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), 0, 0 ); //$NON-NLS-1$
    }
}
