/*
 * CardSurfaceDesignUIAsCardSurfaceDesignUITest.java
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
 * Created on Nov 20, 2009 at 11:59:57 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.ui.AbstractCardSurfaceDesignUITestCase;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardSurfaceDesignUI} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.ui.ICardSurfaceDesignUI} interface.
 */
public final class CardSurfaceDesignUIAsCardSurfaceDesignUITest
    extends AbstractCardSurfaceDesignUITestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignUIAsCardSurfaceDesignUITest} class.
     */
    public CardSurfaceDesignUIAsCardSurfaceDesignUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractCardSurfaceDesignUITestCase#createCardSurfaceDesignUI()
     */
    @Override
    protected ICardSurfaceDesignUI createCardSurfaceDesignUI()
    {
        return new CardSurfaceDesignUI( CardSurfaceDesignId.fromString( "id" ), "name", createDummy( Icon.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
