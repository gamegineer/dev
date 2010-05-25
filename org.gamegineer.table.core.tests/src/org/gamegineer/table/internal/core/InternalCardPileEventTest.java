/*
 * InternalCardPileEventTest.java
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
 * Created on Jan 18, 2010 at 11:32:15 PM.
 */

package org.gamegineer.table.internal.core;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.ICardPile;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardPileEvent} class.
 */
public final class InternalCardPileEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardPileEventTest}
     * class.
     */
    public InternalCardPileEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCardPileEvent} method throws an exception when
     * passed a {@code null} card pile.
     */
    @Test( expected = AssertionError.class )
    public void testCreateCardPileEvent_CardPile_Null()
    {
        InternalCardPileEvent.createCardPileEvent( null );
    }

    /**
     * Ensures the {@code createCardPileEvent} method does not return {@code
     * null}.
     */
    @Test
    public void testCreateCardPileEvent_ReturnValue_NonNull()
    {
        assertNotNull( InternalCardPileEvent.createCardPileEvent( createMock( ICardPile.class ) ) );
    }
}
