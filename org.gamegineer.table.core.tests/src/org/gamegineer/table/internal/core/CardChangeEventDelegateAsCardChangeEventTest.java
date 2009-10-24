/*
 * CardChangeEventDelegateAsCardChangeEventTest.java
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
 * Created on Oct 16, 2009 at 11:06:15 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.table.core.AbstractCardChangeEventTestCase;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardChangeEventDelegate} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardChangeEvent} interface.
 */
public final class CardChangeEventDelegateAsCardChangeEventTest
    extends AbstractCardChangeEventTestCase<CardChangeEventDelegate>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardChangeEventDelegateAsCardChangeEventTest} class.
     */
    public CardChangeEventDelegateAsCardChangeEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractTableEventTestCase#createTableEvent()
     */
    @Override
    protected CardChangeEventDelegate createTableEvent()
    {
        return new CardChangeEventDelegate( createDummy( ITable.class ), createDummy( ICard.class ) );
    }
}