/*
 * PassiveTransportLayerAsTransportLayerTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jan 18, 2011 at 9:01:05 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.transport.AbstractTransportLayerTestCase;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;

/**
 * A fixture for testing the {@link PassiveTransportLayer} class to ensure it
 * does not violate the contract of the {@link ITransportLayer} interface.
 */
public final class PassiveTransportLayerAsTransportLayerTest
    extends AbstractTransportLayerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PassiveTransportLayerAsTransportLayerTest} class.
     */
    public PassiveTransportLayerAsTransportLayerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.AbstractTransportLayerTestCase#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
        throws Exception
    {
        return new TransportLayerProxy( new PassiveTransportLayer.Factory().createTransportLayer( EasyMock.createMock( ITransportLayerContext.class ) ) );
    }
}