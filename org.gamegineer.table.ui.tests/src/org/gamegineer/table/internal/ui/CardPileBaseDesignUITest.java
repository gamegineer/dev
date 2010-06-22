/*
 * CardPileBaseDesignUITest.java
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
 * Created on Jan 23, 2010 at 9:03:29 PM.
 */

package org.gamegineer.table.internal.ui;

import javax.swing.Icon;
import org.easymock.EasyMock;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.CardPileBaseDesignUI} class.
 */
public final class CardPileBaseDesignUITest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignUITest} class.
     */
    public CardPileBaseDesignUITest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * icon.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Icon_Null()
    {
        new CardPileBaseDesignUI( CardPileBaseDesignId.fromString( "id" ), "name", null ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new CardPileBaseDesignUI( null, "name", EasyMock.createMock( Icon.class ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Name_Null()
    {
        new CardPileBaseDesignUI( CardPileBaseDesignId.fromString( "id" ), null, EasyMock.createMock( Icon.class ) ); //$NON-NLS-1$
    }
}
