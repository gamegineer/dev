/*
 * Dispatcher.java
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
 * Created on Jan 8, 2011 at 8:37:14 PM.
 */

package org.gamegineer.table.internal.net.fake;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.connection.IDispatcher;
import org.gamegineer.table.internal.net.connection.IEventHandler;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.connection.IDispatcher}.
 */
@ThreadSafe
final class Dispatcher
    implements IDispatcher<Object, Object>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dispatcher} class.
     */
    Dispatcher()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#close()
     */
    @Override
    public void close()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#open()
     */
    @Override
    public void open()
    {
        // do nothing
    }

    @Override
    public void registerEventHandler(
        final IEventHandler<Object, Object> eventHandler )
    {
        assertArgumentNotNull( eventHandler, "eventHandler" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.IDispatcher#unregisterEventHandler(org.gamegineer.table.internal.net.connection.IEventHandler)
     */
    @Override
    public void unregisterEventHandler(
        final IEventHandler<Object, Object> eventHandler )
    {
        assertArgumentNotNull( eventHandler, "eventHandler" ); //$NON-NLS-1$
    }
}
