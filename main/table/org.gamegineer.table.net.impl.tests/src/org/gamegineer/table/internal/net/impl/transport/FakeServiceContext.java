/*
 * FakeServiceContext.java
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
 * Created on May 17, 2011 at 8:48:00 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import net.jcip.annotations.Immutable;

/**
 * Fake implementation of {@link IServiceContext}.
 */
@Immutable
public class FakeServiceContext
    implements IServiceContext
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeServiceContext} class.
     */
    public FakeServiceContext()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IServiceContext#sendMessage(org.gamegineer.table.internal.net.impl.transport.IMessage)
     */
    @Override
    public void sendMessage(
        @SuppressWarnings( "unused" )
        final IMessage message )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.IServiceContext#stopService()
     */
    @Override
    public void stopService()
    {
        // do nothing
    }
}
