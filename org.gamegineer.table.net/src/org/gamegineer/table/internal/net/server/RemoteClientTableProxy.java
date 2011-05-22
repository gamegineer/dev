/*
 * RemoteClientTableProxy.java
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

package org.gamegineer.table.internal.net.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableProxy;
import org.gamegineer.table.internal.net.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.common.messages.PlayersMessage;

/**
 * A proxy for a remote client table.
 * 
 * <p>
 * This proxy provides a network service that represents the server half of the
 * table network protocol.
 * </p>
 */
@ThreadSafe
final class RemoteClientTableProxy
    extends AbstractRemoteTableProxy
    implements IRemoteClientTableProxyController
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
     * Initializes a new instance of the {@code RemoteClientTableProxy} class.
     * 
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    RemoteClientTableProxy(
        /* @NonNull */
        final ITableNetworkNode node )
    {
        super( node );

        challenge_ = null;
        salt_ = null;

        registerUncorrelatedMessageHandler( HelloRequestMessage.class, new HelloRequestMessageHandler( this ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableProxyController#getChallenge()
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
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableProxyController#getSalt()
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
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableProxyController#setChallenge(byte[])
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
     * @see org.gamegineer.table.internal.net.ITableProxy#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( getNode().getPlayers() );
        synchronized( getLock() )
        {
            sendMessage( message, null );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableProxyController#setSalt(byte[])
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


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all message handlers associated with a remote client table
     * proxy.
     */
    @Immutable
    static abstract class AbstractMessageHandler
        extends AbstractRemoteTableProxy.AbstractMessageHandler<IRemoteClientTableProxyController>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteTableProxyController
         *        The control interface for the remote table proxy associated
         *        with the message handler; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableProxyController} is {@code null}.
         */
        AbstractMessageHandler(
            /* @NonNull */
            final IRemoteClientTableProxyController remoteTableProxyController )
        {
            super( remoteTableProxyController );
        }
    }
}
