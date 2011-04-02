/*
 * INetworkServiceHandler.java
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
 * Created on Mar 25, 2011 at 10:41:04 PM.
 */

package org.gamegineer.table.internal.net;

/**
 * A network service handler
 * 
 * <p>
 * A network service handler implements one half of the network table protocol.
 * </p>
 */
public interface INetworkServiceHandler
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when a message has been received from the peer service handler.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code messageEnvelope} is {@code null}.
     */
    public void messageReceived(
        /* @NonNull */
        INetworkServiceContext context,
        /* @NonNull */
        MessageEnvelope messageEnvelope );

    /**
     * Invoked when the peer service handler has stopped.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public void peerStopped(
        /* @NonNull */
        INetworkServiceContext context );

    /**
     * Invoked when the service handler has started.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public void started(
        /* @NonNull */
        INetworkServiceContext context );

    /**
     * Invoked when the service handler has stopped.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public void stopped(
        /* @NonNull */
        INetworkServiceContext context );
}
