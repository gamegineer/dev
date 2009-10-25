/*
 * CardEventDelegateAsCardEventTest.java
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
 * Created on Oct 24, 2009 at 10:03:14 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.table.core.AbstractCardEventTestCase;
import org.gamegineer.table.core.ICard;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardEventDelegate} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardEvent} interface.
 */
public final class CardEventDelegateAsCardEventTest
    extends AbstractCardEventTestCase<CardEventDelegate>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardEventDelegateAsCardEventTest} class.
     */
    public CardEventDelegateAsCardEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractCardEventTestCase#createCardEvent()
     */
    @Override
    protected CardEventDelegate createCardEvent()
    {
        return new CardEventDelegate( createDummy( ICard.class ) );
    }
}
