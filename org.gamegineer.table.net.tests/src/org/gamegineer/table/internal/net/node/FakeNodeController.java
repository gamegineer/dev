/*
 * FakeNodeController.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Nov 15, 2011 at 9:03:33 PM.
 */

package org.gamegineer.table.internal.net.node;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;

/**
 * Fake implementation of {@link INodeController}.
 */
@Immutable
public final class FakeNodeController
    implements INodeController
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeNodeController} class.
     */
    public FakeNodeController()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginConnect(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public Future<Void> beginConnect(
        @SuppressWarnings( "unused" )
        final ITableNetworkConfiguration configuration )
    {
        return new SynchronousFuture<Void>();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<Void> beginDisconnect()
    {
        return new SynchronousFuture<Void>();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#endConnect(java.util.concurrent.Future)
     */
    @Override
    public void endConnect(
        @SuppressWarnings( "unused" )
        final Future<Void> future )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        @SuppressWarnings( "unused" )
        final Future<Void> future )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
     */
    @Override
    public IPlayer getPlayer()
    {
        return null;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        return Collections.emptyList();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        @SuppressWarnings( "unused" )
        final String playerName )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#requestControl()
     */
    @Override
    public void requestControl()
    {
        // do nothing
    }
}
