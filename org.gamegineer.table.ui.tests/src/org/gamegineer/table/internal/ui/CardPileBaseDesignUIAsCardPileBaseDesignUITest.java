/*
 * CardPileBaseDesignUIAsCardPileBaseDesignUITest.java
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
 * Created on Jan 23, 2010 at 9:03:22 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.ui.AbstractCardPileBaseDesignUITestCase;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardPileBaseDesignUI} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.ui.ICardPileBaseDesignUI} interface.
 */
public final class CardPileBaseDesignUIAsCardPileBaseDesignUITest
    extends AbstractCardPileBaseDesignUITestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileBaseDesignUIAsCardPileBaseDesignUITest} class.
     */
    public CardPileBaseDesignUIAsCardPileBaseDesignUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractCardPileBaseDesignUITestCase#createCardPileBaseDesignUI()
     */
    @Override
    protected ICardPileBaseDesignUI createCardPileBaseDesignUI()
    {
        return new CardPileBaseDesignUI( CardPileBaseDesignId.fromString( "id" ), "name", createDummy( Icon.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}