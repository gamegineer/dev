/*
 * InternalCardChangeEventAsAbstractCardChangeEventTest.java
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
 * Created on Oct 16, 2009 at 11:23:41 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.table.core.AbstractAbstractCardChangeEventTestCase;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.InternalCardChangeEvent} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.CardChangeEvent} class.
 */
public final class InternalCardChangeEventAsAbstractCardChangeEventTest
    extends AbstractAbstractCardChangeEventTestCase<InternalCardChangeEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalCardChangeEventAsAbstractCardChangeEventTest} class.
     */
    public InternalCardChangeEventAsAbstractCardChangeEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractAbstractTableEventTestCase#createTableEvent()
     */
    @Override
    protected InternalCardChangeEvent createTableEvent()
    {
        return InternalCardChangeEvent.createCardChangeEvent( createDummy( ITable.class ), createDummy( ICard.class ) );
    }
}
