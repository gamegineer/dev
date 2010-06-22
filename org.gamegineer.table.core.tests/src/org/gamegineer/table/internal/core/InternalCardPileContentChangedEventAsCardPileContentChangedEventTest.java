/*
 * InternalCardPileContentChangedEventAsCardPileContentChangedEventTest.java
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
 * Created on Jan 10, 2010 at 10:44:30 PM.
 */

package org.gamegineer.table.internal.core;

import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractCardPileContentChangedEventTestCase;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardPileContentChangedEvent}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPileContentChangedEvent} interface.
 */
public final class InternalCardPileContentChangedEventAsCardPileContentChangedEventTest
    extends AbstractCardPileContentChangedEventTestCase<InternalCardPileContentChangedEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalCardPileContentChangedEventAsCardPileContentChangedEventTest}
     * class.
     */
    public InternalCardPileContentChangedEventAsCardPileContentChangedEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardPileEventTestCase#createCardPileEvent()
     */
    @Override
    protected InternalCardPileContentChangedEvent createCardPileEvent()
    {
        return InternalCardPileContentChangedEvent.createCardPileContentChangedEvent( EasyMock.createMock( ICardPile.class ), EasyMock.createMock( ICard.class ) );
    }
}
