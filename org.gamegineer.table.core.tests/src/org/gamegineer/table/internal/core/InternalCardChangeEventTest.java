/*
 * InternalCardChangeEventTest.java
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
 * Created on Oct 16, 2009 at 11:17:35 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardChangeEvent} class.
 */
public final class InternalCardChangeEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardChangeEventTest}
     * class.
     */
    public InternalCardChangeEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCardChangeEvent} method throws an exception when
     * passed a {@code null} card.
     */
    @Test( expected = AssertionError.class )
    public void testCreateCardChangeEvent_Card_Null()
    {
        InternalCardChangeEvent.createCardChangeEvent( createDummy( ITable.class ), null );
    }

    /**
     * Ensures the {@code createCardChangeEvent} method does not return {@code
     * null}.
     */
    @Test
    public void testCreateCardChangeEvent_ReturnValue_NonNull()
    {
        assertNotNull( InternalCardChangeEvent.createCardChangeEvent( createDummy( ITable.class ), createDummy( ICard.class ) ) );
    }

    /**
     * Ensures the {@code createCardChangeEvent} method throws an exception when
     * passed a {@code null} table.
     */
    @Test( expected = AssertionError.class )
    public void testCreateCardChangeEvent_Table_Null()
    {
        InternalCardChangeEvent.createCardChangeEvent( null, createDummy( ICard.class ) );
    }
}
