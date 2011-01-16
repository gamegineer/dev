/*
 * Acceptor.java
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
 * Created on Jan 8, 2011 at 8:37:02 PM.
 */

package org.gamegineer.table.internal.net.fake;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.connection.IAcceptor;
import org.gamegineer.table.net.INetworkTableConfiguration;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.connection.IAcceptor}.
 */
@ThreadSafe
final class Acceptor
    implements IAcceptor<Object, Object>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Acceptor} class.
     */
    Acceptor()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.IAcceptor#bind(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void bind(
        final INetworkTableConfiguration configuration )
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#close()
     */
    @Override
    public void close()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#getEvents()
     */
    @Override
    public int getEvents()
    {
        return 0;
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#getTransportHandle()
     */
    @Override
    public Object getTransportHandle()
    {
        return null;
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IEventHandler#handleEvent(java.lang.Object)
     */
    @Override
    public void handleEvent(
        final Object event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }
}
