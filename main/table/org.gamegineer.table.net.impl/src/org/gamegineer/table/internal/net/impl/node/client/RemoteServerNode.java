/*
 * RemoteServerNode.java
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
 * Created on Apr 10, 2011 at 5:34:39 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.node.client.handlers.BeginAuthenticationRequestMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.handlers.GoodbyeMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.handlers.HelloResponseMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.handlers.PlayersMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.CancelControlRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.GiveControlMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.PlayersMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.RequestControlMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A remote server node.
 * 
 * <p>
 * This remote node provides a network service that represents the client half
 * of the table network protocol.
 * </p>
 */
@NotThreadSafe
final class RemoteServerNode
    extends AbstractRemoteNode<IClientNode, IRemoteServerNode>
    implements IRemoteServerNode, IRemoteServerNodeController
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteServerNode} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param localNode
     *        The local table network node; must not be {@code null}.
     */
    RemoteServerNode(
        final INodeLayer nodeLayer,
        final IClientNode localNode )
    {
        super( nodeLayer, localNode );

        registerUncorrelatedMessageHandler( nonNull( BeginAuthenticationRequestMessage.class ), BeginAuthenticationRequestMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( nonNull( GoodbyeMessage.class ), GoodbyeMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( nonNull( PlayersMessage.class ), PlayersMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNode#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        assert isNodeLayerThread();

        final CancelControlRequestMessage message = new CancelControlRequestMessage();
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode#closed(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void closed(
        final @Nullable TableNetworkError error )
    {
        assert isNodeLayerThread();

        super.closed( error );

        getLocalNode().disconnect( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode#getThisAsRemoteNodeType()
     */
    @Override
    protected IRemoteServerNode getThisAsRemoteNodeType()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNode#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        assert isNodeLayerThread();

        final GiveControlMessage message = new GiveControlMessage();
        message.setPlayerName( playerName );
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractRemoteNode#opened()
     */
    @Override
    protected void opened()
    {
        assert isNodeLayerThread();

        super.opened();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        sendMessage( message, HelloResponseMessageHandler.INSTANCE );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNode#requestControl()
     */
    @Override
    public void requestControl()
    {
        assert isNodeLayerThread();

        final RequestControlMessage message = new RequestControlMessage();
        sendMessage( message, null );
    }
}
