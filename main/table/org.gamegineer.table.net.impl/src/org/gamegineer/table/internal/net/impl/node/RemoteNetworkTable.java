/*
 * RemoteNetworkTable.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jul 2, 2011 at 9:05:43 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.impl.node.common.messages.ComponentIncrementMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.TableMessage;

/**
 * Adapts a remote table to {@link INetworkTable}.
 */
@Immutable
final class RemoteNetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The control interface for the remote node associated with the table. */
    private final IRemoteNodeController<@NonNull ?> remoteNodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteNetworkTable} class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        table; must not be {@code null}.
     */
    RemoteNetworkTable(
        final IRemoteNodeController<@NonNull ?> remoteNodeController )
    {
        remoteNodeController_ = remoteNodeController;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#dispose()
     */
    @Override
    public void dispose()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#incrementComponentState(org.gamegineer.table.core.ComponentPath, org.gamegineer.table.internal.net.impl.node.ComponentIncrement)
     */
    @Override
    public void incrementComponentState(
        final ComponentPath componentPath,
        final ComponentIncrement componentIncrement )
    {
        final ComponentIncrementMessage message = new ComponentIncrementMessage();
        message.setIncrement( componentIncrement );
        message.setPath( componentPath );
        remoteNodeController_.sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INetworkTable#setTableState(java.lang.Object)
     */
    @Override
    public void setTableState(
        final Object tableMemento )
    {
        final TableMessage message = new TableMessage();
        message.setMemento( tableMemento );
        remoteNodeController_.sendMessage( message, null );
    }
}
