/*
 * TableNetworkControllers.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 18, 2011 at 8:17:49 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.transport.fake.FakeTransportLayerFactory;

/**
 * A collection of useful methods for working with table network controllers.
 */
@ThreadSafe
public final class TableNetworkControllers
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkControllers} class.
     */
    private TableNetworkControllers()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new fake table network controller.
     * 
     * @return A new fake table network controller; never {@code null}.
     */
    /* @NonNull */
    public static ITableNetworkController createFakeTableNetworkController()
    {
        final IMocksControl mocksControl = EasyMock.createNiceControl();
        final ITableNetworkController tableNetworkController = mocksControl.createMock( ITableNetworkController.class );
        EasyMock.expect( tableNetworkController.getTransportLayerFactory() ).andReturn( new FakeTransportLayerFactory() ).anyTimes();
        mocksControl.replay();
        return tableNetworkController;
    }
}
