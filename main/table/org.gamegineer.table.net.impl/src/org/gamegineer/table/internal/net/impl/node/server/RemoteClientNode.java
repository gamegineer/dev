/*
 * RemoteClientNode.java
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
 * Created on Apr 10, 2011 at 5:34:50 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server;

import java.util.Collection;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.node.common.messages.CancelControlRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.GiveControlMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.PlayersMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.RequestControlMessage;
import org.gamegineer.table.internal.net.impl.node.server.handlers.CancelControlRequestMessageHandler;
import org.gamegineer.table.internal.net.impl.node.server.handlers.GiveControlMessageHandler;
import org.gamegineer.table.internal.net.impl.node.server.handlers.GoodbyeMessageHandler;
import org.gamegineer.table.internal.net.impl.node.server.handlers.HelloRequestMessageHandler;
import org.gamegineer.table.internal.net.impl.node.server.handlers.RequestControlMessageHandler;
import org.gamegineer.table.net.IPlayer;

/**
 * A remote client node.
 * 
 * <p>
 * This remote node provides a network service that represents the server half
 * of the table network protocol.
 * </p>
 */
@NotThreadSafe
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
    @Nullable
    private byte[] challenge_;

    /**
     * The most-recent salt used to authenticate the client or {@code null} if
     * an authentication request has not yet been sent.
     */
    @Nullable
    private byte[] salt_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteClientNode} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param localNode
     *        The local table network node; must not be {@code null}.
     */
    RemoteClientNode(
        final INodeLayer nodeLayer,
        final IServerNode localNode )
    {
        super( nodeLayer, localNode );

        challenge_ = null;
        salt_ = null;

        registerUncorrelatedMessageHandler( CancelControlRequestMessage.class, CancelControlRequestMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( GiveControlMessage.class, GiveControlMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( GoodbyeMessage.class, GoodbyeMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( HelloRequestMessage.class, HelloRequestMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( RequestControlMessage.class, RequestControlMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController#getChallenge()
     */
    @Nullable
    @Override
    public byte[] getChallenge()
    {
        assert isNodeLayerThread();

        return challenge_;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController#getSalt()
     */
    @Nullable
    @Override
    public byte[] getSalt()
    {
        assert isNodeLayerThread();

        return salt_;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode#getThisAsRemoteNodeType()
     */
    @Override
    protected IRemoteClientNode getThisAsRemoteNodeType()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController#setChallenge(byte[])
     */
    @Override
    public void setChallenge(
        @Nullable
        final byte[] challenge )
    {
        assert isNodeLayerThread();

        challenge_ = challenge;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNode#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<IPlayer> players )
    {
        assert isNodeLayerThread();

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( players );
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController#setSalt(byte[])
     */
    @Override
    public void setSalt(
        @Nullable
        final byte[] salt )
    {
        assert isNodeLayerThread();

        salt_ = salt;
    }
}
