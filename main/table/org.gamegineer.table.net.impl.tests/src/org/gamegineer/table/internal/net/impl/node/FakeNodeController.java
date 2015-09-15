/*
 * FakeNodeController.java
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
 * Created on Nov 15, 2011 at 9:03:33 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.TableNetworkConfiguration;

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
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#beginConnect(org.gamegineer.table.net.TableNetworkConfiguration)
     */
    @Override
    public Future<@Nullable Void> beginConnect(
        @SuppressWarnings( "unused" )
        final TableNetworkConfiguration configuration )
    {
        return new SynchronousFuture<>();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<@Nullable Void> beginDisconnect()
    {
        return new SynchronousFuture<>();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#endConnect(java.util.concurrent.Future)
     */
    @Override
    public void endConnect(
        @SuppressWarnings( "unused" )
        final Future<@Nullable Void> future )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        @SuppressWarnings( "unused" )
        final Future<@Nullable Void> future )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayer()
     */
    @Nullable
    @Override
    public IPlayer getPlayer()
    {
        return null;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        return nonNull( Collections.<@NonNull IPlayer>emptyList() );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        @SuppressWarnings( "unused" )
        final String playerName )
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#requestControl()
     */
    @Override
    public void requestControl()
    {
        // do nothing
    }
}
