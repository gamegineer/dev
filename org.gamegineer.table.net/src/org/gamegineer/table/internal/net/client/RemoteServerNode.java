/*
 * RemoteServerNode.java
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
 * Created on Apr 10, 2011 at 5:34:39 PM.
 */

package org.gamegineer.table.internal.net.client;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.common.AbstractRemoteNode;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.common.messages.PlayersMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A remote server node.
 * 
 * <p>
 * This remote node provides a network service that represents the client half
 * of the table network protocol.
 * </p>
 */
@ThreadSafe
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
     * @param localNode
     *        The local table network node; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code localNode} is {@code null}.
     */
    RemoteServerNode(
        /* @NonNull */
        final IClientNode localNode )
    {
        super( localNode );

        registerUncorrelatedMessageHandler( BeginAuthenticationRequestMessage.class, new BeginAuthenticationRequestMessageHandler( this ) );
        registerUncorrelatedMessageHandler( PlayersMessage.class, new PlayersMessageHandler( this ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNode#closed(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void closed(
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        super.closed( error );

        getLocalNode().disconnect( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNode#getThisAsRemoteNodeType()
     */
    @Override
    protected IRemoteServerNode getThisAsRemoteNodeType()
    {
        return this;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNode#opened()
     */
    @Override
    protected void opened()
    {
        assert Thread.holdsLock( getLock() );

        super.opened();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !sendMessage( message, new HelloResponseMessageHandler( this ) ) )
        {
            close( TableNetworkError.TRANSPORT_ERROR );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all message handlers associated with a remote server node.
     */
    @Immutable
    static abstract class AbstractMessageHandler
        extends AbstractRemoteNode.AbstractMessageHandler<IRemoteServerNodeController>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteNodeController
         *        The control interface for the remote node associated with the
         *        message handler; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteNodeController} is {@code null}.
         */
        AbstractMessageHandler(
            /* @NonNull */
            final IRemoteServerNodeController remoteNodeController )
        {
            super( remoteNodeController );
        }
    }
}
