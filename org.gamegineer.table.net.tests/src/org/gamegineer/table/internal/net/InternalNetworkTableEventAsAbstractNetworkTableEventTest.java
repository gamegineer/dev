/*
 * InternalNetworkTableEventAsAbstractNetworkTableEventTest.java
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
 * Created on Nov 9, 2010 at 10:41:40 PM.
 */

package org.gamegineer.table.internal.net;

import org.easymock.EasyMock;
import org.gamegineer.table.net.AbstractAbstractNetworkTableEventTestCase;
import org.gamegineer.table.net.INetworkTable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.InternalNetworkTableEvent} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.net.NetworkTableEvent} class.
 */
public final class InternalNetworkTableEventAsAbstractNetworkTableEventTest
    extends AbstractAbstractNetworkTableEventTestCase<InternalNetworkTableEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalNetworkTableEventAsAbstractNetworkTableEventTest} class.
     */
    public InternalNetworkTableEventAsAbstractNetworkTableEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractAbstractNetworkTableEventTestCase#createNetworkTableEvent()
     */
    @Override
    protected InternalNetworkTableEvent createNetworkTableEvent()
    {
        return InternalNetworkTableEvent.createNetworkTableEvent( EasyMock.createMock( INetworkTable.class ) );
    }
}
