/*
 * InternalTableContentChangedEventAsTableContentChangedEventTest.java
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
 * Created on Oct 16, 2009 at 11:21:33 PM.
 */

package org.gamegineer.table.internal.core;

import org.easymock.EasyMock;
import org.gamegineer.table.core.AbstractTableContentChangedEventTestCase;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalTableContentChangedEvent}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ITableContentChangedEvent} interface.
 */
public final class InternalTableContentChangedEventAsTableContentChangedEventTest
    extends AbstractTableContentChangedEventTestCase<InternalTableContentChangedEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalTableContentChangedEventAsTableContentChangedEventTest} class.
     */
    public InternalTableContentChangedEventAsTableContentChangedEventTest()
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
    protected InternalTableContentChangedEvent createTableEvent()
    {
        return InternalTableContentChangedEvent.createTableContentChangedEvent( EasyMock.createMock( ITable.class ), EasyMock.createMock( ICardPile.class ) );
    }
}
