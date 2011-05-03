/*
 * IRemoteTableGateway.java
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
 * Created on Apr 22, 2011 at 4:54:20 PM.
 */

package org.gamegineer.table.internal.net.common;

import net.jcip.annotations.GuardedBy;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.NetworkTableError;

/**
 * A gateway to a remote table that is connected to the network table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteTableGateway
    extends ITableGateway
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the table gateway.
     * 
     * @param error
     *        The error that caused the table gateway to be closed or {@code
     *        null} if the table gateway was closed normally.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     */
    @GuardedBy( "getLock()" )
    public void close(
        /* @Nullable */
        NetworkTableError error );

    /**
     * Gets the context associated with the table gateway.
     * 
     * @return The context associated with the table gateway; never {@code null}
     *         .
     */
    /* @NonNull */
    public ITableGatewayContext getContext();

    /**
     * Gets the instance lock for the table gateway.
     * 
     * @return The instance lock for the table gateway; never {@code null}.
     */
    /* @NonNull */
    public Object getLock();

    /**
     * @throws java.lang.IllegalStateException
     *         If the player has not been authenticated.
     * 
     * @see org.gamegineer.table.internal.net.ITableGateway#getPlayerName()
     */
    @Override
    public String getPlayerName();

    /**
     * Sends the specified message to the table gateway peer.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * @param messageHandler
     *        The message handler to be invoked for any response to the
     *        specified message or {@code null} if no response is expected.
     * 
     * @return {@code true} if the message was sent successfully; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public boolean sendMessage(
        /* @NonNull */
        IMessage message,
        /* @Nullable */
        IMessageHandler messageHandler );

    /**
     * Sets the name of the player associated with the table gateway.
     * 
     * @param playerName
     *        The name of the player associated with the table gateway or
     *        {@code null} if the network is not connected.
     */
    @GuardedBy( "getLock()" )
    public void setPlayerName(
        /* @Nullable */
        String playerName );


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A remote table gateway message handler.
     * 
     * @noextend This interface is not intended to be extended by clients.
     */
    public interface IMessageHandler
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Handles the specified message for the associated remote table
         * gateway.
         * 
         * <p>
         * This method will be invoked by the table gateway while its instance
         * lock is held. Thus, message handlers may assume any methods they
         * invoke on the table gateway will be thread-safe and atomic.
         * </p>
         * 
         * @param message
         *        The message; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code message} is {@code null}.
         */
        public void handleMessage(
            /* @NonNull */
            IMessage message );
    }
}
