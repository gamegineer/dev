/*
 * FakeServiceContext.java
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
 * Created on May 17, 2011 at 8:48:00 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * Fake implementation of
 * {@link org.gamegineer.table.internal.net.transport.IServiceContext}.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.IServiceContext#sendMessage(org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    public void sendMessage(
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.IServiceContext#stopService()
     */
    @Override
    public void stopService()
    {
        // do nothing
    }
}
