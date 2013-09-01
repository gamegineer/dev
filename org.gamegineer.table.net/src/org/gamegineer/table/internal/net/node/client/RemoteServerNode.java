/*
 * RemoteServerNode.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.node.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.node.AbstractRemoteNode;
import org.gamegineer.table.internal.net.node.INodeLayer;
import org.gamegineer.table.internal.net.node.client.handlers.BeginAuthenticationRequestMessageHandler;
import org.gamegineer.table.internal.net.node.client.handlers.GoodbyeMessageHandler;
import org.gamegineer.table.internal.net.node.client.handlers.HelloResponseMessageHandler;
import org.gamegineer.table.internal.net.node.client.handlers.PlayersMessageHandler;
import org.gamegineer.table.internal.net.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.CancelControlRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.GiveControlMessage;
import org.gamegineer.table.internal.net.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.PlayersMessage;
import org.gamegineer.table.internal.net.node.common.messages.RequestControlMessage;
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code nodeLayer} or {@code localNode} is {@code null}.
     */
    RemoteServerNode(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
        final IClientNode localNode )
    {
        super( nodeLayer, localNode );

        registerUncorrelatedMessageHandler( BeginAuthenticationRequestMessage.class, BeginAuthenticationRequestMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( GoodbyeMessage.class, GoodbyeMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( PlayersMessage.class, PlayersMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.client.IRemoteServerNode#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        assert isNodeLayerThread();

        final CancelControlRequestMessage message = new CancelControlRequestMessage();
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#closed(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void closed(
        final TableNetworkError error )
    {
        assert isNodeLayerThread();

        super.closed( error );

        getLocalNode().disconnect( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#getThisAsRemoteNodeType()
     */
    @Override
    protected IRemoteServerNode getThisAsRemoteNodeType()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.client.IRemoteServerNode#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        final GiveControlMessage message = new GiveControlMessage();
        message.setPlayerName( playerName );
        sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#opened()
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
     * @see org.gamegineer.table.internal.net.node.client.IRemoteServerNode#requestControl()
     */
    @Override
    public void requestControl()
    {
        assert isNodeLayerThread();

        final RequestControlMessage message = new RequestControlMessage();
        sendMessage( message, null );
    }
}
