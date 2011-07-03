/*
 * AbstractCommonMessageHandler.java
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
 * Created on Jul 2, 2011 at 9:53:19 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.AbstractMessageHandler;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;

/**
 * Superclass for all message handlers that communicate with both a remote
 * client and server node.
 */
@Immutable
@SuppressWarnings( "unchecked" )
public abstract class AbstractCommonMessageHandler
    extends AbstractMessageHandler<IRemoteNodeController>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommonMessageHandler}
     * class.
     */
    protected AbstractCommonMessageHandler()
    {
        super( IRemoteNodeController.class );
    }
}
