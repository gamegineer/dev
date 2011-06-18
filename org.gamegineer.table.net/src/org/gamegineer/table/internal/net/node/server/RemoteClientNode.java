/*
 * RemoteClientNode.java
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
 * Created on Apr 10, 2011 at 5:34:50 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.node.AbstractRemoteNode;
import org.gamegineer.table.internal.net.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.PlayersMessage;
import org.gamegineer.table.internal.net.node.common.messages.TableMessage;

/**
 * A remote client node.
 * 
 * <p>
 * This remote node provides a network service that represents the server half
 * of the table network protocol.
 * </p>
 */
@ThreadSafe
final class RemoteClientNode
    extends AbstractRemoteNode<IServerNode, IRemoteClientNode>
    implements IRemoteClientNode, IRemoteClientNodeController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The most-recent challenge used to authenticate the client or {@code null}
     * if an authentication request has not yet been sent.
     */
    @GuardedBy( "getLock()" )
    private byte[] challenge_;

    /**
     * The most-recent salt used to authenticate the client or {@code null} if
     * an authentication request has not yet been sent.
     */
    @GuardedBy( "getLock()" )
    private byte[] salt_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteClientNode} class.
     * 
     * @param localNode
     *        The local table network node; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code localNode} is {@code null}.
     */
    RemoteClientNode(
        /* @NonNull */
        final IServerNode localNode )
    {
        super( localNode );

        challenge_ = null;
        salt_ = null;

        registerUncorrelatedMessageHandler( HelloRequestMessage.class, HelloRequestMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController#getChallenge()
     */
    @Override
    public byte[] getChallenge()
    {
        synchronized( getLock() )
        {
            return challenge_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController#getSalt()
     */
    @Override
    public byte[] getSalt()
    {
        synchronized( getLock() )
        {
            return salt_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#getThisAsRemoteNodeType()
     */
    @Override
    protected IRemoteClientNode getThisAsRemoteNodeType()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController#setChallenge(byte[])
     */
    @Override
    public void setChallenge(
        final byte[] challenge )
    {
        synchronized( getLock() )
        {
            challenge_ = challenge;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNode#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( players );
        synchronized( getLock() )
        {
            sendMessage( message, null );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController#setSalt(byte[])
     */
    @Override
    public void setSalt(
        final byte[] salt )
    {
        synchronized( getLock() )
        {
            salt_ = salt;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IRemoteClientNode#setTableMemento(java.lang.Object)
     */
    @Override
    public void setTableMemento(
        final Object memento )
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        final TableMessage message = new TableMessage();
        message.setMemento( memento );
        synchronized( getLock() )
        {
            sendMessage( message, null );
        }
    }
}
