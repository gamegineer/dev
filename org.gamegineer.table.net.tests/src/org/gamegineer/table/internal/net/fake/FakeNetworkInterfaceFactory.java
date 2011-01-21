/*
 * FakeNetworkInterfaceFactory.java
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
 * Created on Jan 8, 2011 at 8:37:36 PM.
 */

package org.gamegineer.table.internal.net.fake;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.INetworkInterface;
import org.gamegineer.table.internal.net.INetworkInterfaceFactory;
import org.gamegineer.table.internal.net.NetworkTable;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.INetworkInterfaceFactory} .
 */
@Immutable
public final class FakeNetworkInterfaceFactory
    implements INetworkInterfaceFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeNetworkInterfaceFactory}
     * class.
     */
    public FakeNetworkInterfaceFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterfaceFactory#createClientNetworkInterface(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    public INetworkInterface createClientNetworkInterface(
        final NetworkTable networkTable )
    {
        assertArgumentNotNull( networkTable, "networkTable" ); //$NON-NLS-1$

        return new ClientNetworkInterface();
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterfaceFactory#createServerNetworkInterface(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    public INetworkInterface createServerNetworkInterface(
        final NetworkTable networkTable )
    {
        assertArgumentNotNull( networkTable, "networkTable" ); //$NON-NLS-1$

        return new ServerNetworkInterface();
    }
}