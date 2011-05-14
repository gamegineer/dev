/*
 * TableNetworkStrategyContexts.java
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
 * A collection of useful methods for working with table network strategy
 * contexts.
 */
@ThreadSafe
public final class TableNetworkStrategyContexts
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkStrategyContexts}
     * class.
     */
    private TableNetworkStrategyContexts()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new fake table network strategy context.
     * 
     * @return A new fake table network strategy context; never {@code null}.
     */
    /* @NonNull */
    public static ITableNetworkStrategyContext createFakeTableNetworkStrategyContext()
    {
        final IMocksControl mocksControl = EasyMock.createControl();
        final ITableNetworkStrategyContext context = mocksControl.createMock( ITableNetworkStrategyContext.class );
        EasyMock.expect( context.getTransportLayerFactory() ).andReturn( new FakeTransportLayerFactory() ).anyTimes();
        mocksControl.replay();
        return context;
    }
}
