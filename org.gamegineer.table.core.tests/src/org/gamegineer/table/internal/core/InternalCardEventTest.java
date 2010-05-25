/*
 * InternalCardEventTest.java
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
 * Created on Oct 24, 2009 at 10:18:04 PM.
 */

package org.gamegineer.table.internal.core;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.core.ICard;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardEvent} class.
 */
public final class InternalCardEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardEventTest} class.
     */
    public InternalCardEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCardEvent} method throws an exception when
     * passed a {@code null} card.
     */
    @Test( expected = AssertionError.class )
    public void testCreateCardEvent_Card_Null()
    {
        InternalCardEvent.createCardEvent( null );
    }

    /**
     * Ensures the {@code createCardEvent} method does not return {@code null}.
     */
    @Test
    public void testCreateCardEvent_ReturnValue_NonNull()
    {
        assertNotNull( InternalCardEvent.createCardEvent( createMock( ICard.class ) ) );
    }
}
