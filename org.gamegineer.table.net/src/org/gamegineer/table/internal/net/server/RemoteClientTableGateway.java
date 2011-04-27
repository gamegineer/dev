/*
 * RemoteClientTableGateway.java
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

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway;
import org.gamegineer.table.internal.net.transport.messages.HelloRequestMessage;

/**
 * A gateway to a remote client table.
 * 
 * <p>
 * This gateway provides a network service that represents the server half of
 * the network table protocol.
 * </p>
 */
@ThreadSafe
final class RemoteClientTableGateway
    extends AbstractRemoteTableGateway
    implements IRemoteClientTableGateway
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
     * Initializes a new instance of the {@code RemoteClientTableGateway} class.
     * 
     * @param context
     *        The table gateway context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    RemoteClientTableGateway(
        /* @NonNull */
        final ITableGatewayContext context )
    {
        super( context );

        challenge_ = null;
        salt_ = null;

        registerUncorrelatedMessageHandler( HelloRequestMessage.class, new HelloRequestMessageHandler( this ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableGateway#getChallenge()
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
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableGateway#getSalt()
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
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableGateway#setChallenge(byte[])
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
     * @see org.gamegineer.table.internal.net.server.IRemoteClientTableGateway#setSalt(byte[])
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
     * gateway.
     */
    @Immutable
    static abstract class AbstractMessageHandler
        extends AbstractRemoteTableGateway.AbstractMessageHandler<IRemoteClientTableGateway>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteTableGateway
         *        The remote table gateway associated with the message handler;
         *        must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableGateway} is {@code null}.
         */
        AbstractMessageHandler(
            /* @NonNull */
            final IRemoteClientTableGateway remoteTableGateway )
        {
            super( remoteTableGateway );
        }
    }
}
