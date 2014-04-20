/*
 * FakeTransportLayerFactory.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Jan 8, 2011 at 8:37:36 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.fake;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory;

/**
 * Fake implementation of {@link ITransportLayerFactory} .
 */
@Immutable
public final class FakeTransportLayerFactory
    implements ITransportLayerFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeTransportLayerFactory}
     * class.
     */
    public FakeTransportLayerFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory#createActiveTransportLayer(org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext)
     */
    @Override
    public ITransportLayer createActiveTransportLayer(
        @SuppressWarnings( "unused" )
        final ITransportLayerContext context )
    {
        return new ActiveTransportLayer();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory#createPassiveTransportLayer(org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext)
     */
    @Override
    public ITransportLayer createPassiveTransportLayer(
        @SuppressWarnings( "unused" )
        final ITransportLayerContext context )
    {
        return new PassiveTransportLayer();
    }
}
