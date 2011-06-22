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

package org.gamegineer.table.internal.net.node.client;

import java.awt.Point;
import java.util.List;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.internal.net.node.AbstractRemoteNode;
import org.gamegineer.table.internal.net.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.PlayersMessage;
import org.gamegineer.table.internal.net.node.common.messages.TableMessage;
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
    // Fields
    // ======================================================================

    /** The proxy for the remote server table. */
    private final ITable tableProxy_;


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

        tableProxy_ = new TableProxy();

        registerUncorrelatedMessageHandler( BeginAuthenticationRequestMessage.class, BeginAuthenticationRequestMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( GoodbyeMessage.class, GoodbyeMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( PlayersMessage.class, PlayersMessageHandler.INSTANCE );
        registerUncorrelatedMessageHandler( TableMessage.class, TableMessageHandler.INSTANCE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#closed(org.gamegineer.table.net.TableNetworkError)
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
     * @see org.gamegineer.table.internal.net.node.IRemoteNode#getTableProxy()
     */
    @Override
    public ITable getTableProxy()
    {
        return tableProxy_;
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
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNode#opened()
     */
    @Override
    protected void opened()
    {
        assert Thread.holdsLock( getLock() );

        super.opened();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !sendMessage( message, HelloResponseMessageHandler.INSTANCE ) )
        {
            close( TableNetworkError.TRANSPORT_ERROR );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A proxy for a remote server table.
     */
    @Immutable
    private final class TableProxy
        implements ITable
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableProxy} class.
         */
        TableProxy()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ITable#addCardPile(org.gamegineer.table.core.ICardPile)
         */
        @Override
        public void addCardPile(
            @SuppressWarnings( "unused" )
            final ICardPile cardPile )
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.core.ITable#addTableListener(org.gamegineer.table.core.ITableListener)
         */
        @Override
        public void addTableListener(
            @SuppressWarnings( "unused" )
            final ITableListener listener )
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
         */
        @Override
        public Object createMemento()
        {
            return null;
        }

        /*
         * @see org.gamegineer.table.core.ITable#getCardPile(java.awt.Point)
         */
        @Override
        public ICardPile getCardPile(
            @SuppressWarnings( "unused" )
            final Point location )
        {
            return null;
        }

        /*
         * @see org.gamegineer.table.core.ITable#getCardPiles()
         */
        @Override
        public List<ICardPile> getCardPiles()
        {
            return null;
        }

        /*
         * @see org.gamegineer.table.core.ITable#removeAllCardPiles()
         */
        @Override
        public void removeAllCardPiles()
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.core.ITable#removeCardPile(org.gamegineer.table.core.ICardPile)
         */
        @Override
        public void removeCardPile(
            @SuppressWarnings( "unused" )
            final ICardPile cardPile )
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.core.ITable#removeTableListener(org.gamegineer.table.core.ITableListener)
         */
        @Override
        public void removeTableListener(
            @SuppressWarnings( "unused" )
            final ITableListener listener )
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
         */
        @Override
        @SuppressWarnings( "unused" )
        public void setMemento(
            final Object memento )
            throws MementoException
        {
            // do nothing
        }
    }
}
