/*
 * AbstractClientMessageHandler.java
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
 * Created on May 30, 2011 at 8:44:06 PM.
 */

package org.gamegineer.table.internal.net.node.client.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.AbstractMessageHandler;
import org.gamegineer.table.internal.net.node.client.IRemoteServerNodeController;

/**
 * Superclass for all client message handlers that communicate with a remote
 * server node.
 */
@Immutable
public abstract class AbstractClientMessageHandler
    extends AbstractMessageHandler<IRemoteServerNodeController>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractClientMessageHandler}
     * class.
     */
    protected AbstractClientMessageHandler()
    {
        super( IRemoteServerNodeController.class );
    }
}
