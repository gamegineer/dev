/*
 * CardDesignUIAsCardDesignUITest.java
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
 * Created on Nov 20, 2009 at 11:59:57 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.ui.AbstractCardDesignUITestCase;
import org.gamegineer.table.ui.ICardDesignUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardDesignUI} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.table.ui.ICardDesignUI}
 * interface.
 */
public final class CardDesignUIAsCardDesignUITest
    extends AbstractCardDesignUITestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignUIAsCardDesignUITest}
     * class.
     */
    public CardDesignUIAsCardDesignUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.AbstractCardDesignUITestCase#createCardDesignUI()
     */
    @Override
    protected ICardDesignUI createCardDesignUI()
    {
        return new CardDesignUI( CardDesignId.fromString( "id" ), "name", createDummy( Icon.class ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
