/*
 * INetworkTable.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 3, 2010 at 10:19:24 PM.
 */

package org.gamegineer.table.net;

import java.util.concurrent.Future;

/**
 * A network adapter for a virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface INetworkTable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified network table listener to this network table.
     * 
     * @param listener
     *        The network table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered network table
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addNetworkTableListener(
        /* @NonNull */
        INetworkTableListener listener );

    /**
     * Begins an asynchronous operation to disconnect from the network.
     * 
     * <p>
     * The operation does nothing if the network is not connected.
     * </p>
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     */
    /* @NonNull */
    public Future<Void> beginDisconnect();

    /**
     * Begins an asynchronous operation to host the network table.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     */
    /* @NonNull */
    public Future<Void> beginHost(
        /* @NonNull */
        INetworkTableConfiguration configuration );

    /**
     * Begins an asynchronous operation to join another network table.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     */
    public Future<Void> beginJoin(
        /* @NonNull */
        INetworkTableConfiguration configuration );

    /**
     * Ends an asynchronous operation to disconnect from the network.
     * 
     * @param token
     *        The asynchronous completion token for the operation; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code token} is not a token for the expected operation.
     * @throws java.lang.InterruptedException
     *         If the thread is interrupted while waiting for the operation to
     *         complete.
     * @throws java.lang.NullPointerException
     *         If {@code token} is {@code null}.
     * @throws java.util.concurrent.CancellationException
     *         If the operation was cancelled.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    public void endDisconnect(
        /* @NonNull */
        Future<Void> token )
        throws NetworkTableException, InterruptedException;

    /**
     * Ends an asynchronous operation to host the network table associated
     * 
     * @param token
     *        The asynchronous completion token for the operation; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code token} is not a token for the expected operation.
     * @throws java.lang.InterruptedException
     *         If the thread is interrupted while waiting for the operation to
     *         complete.
     * @throws java.lang.NullPointerException
     *         If {@code token} is {@code null}.
     * @throws java.util.concurrent.CancellationException
     *         If the operation was cancelled.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    public void endHost(
        /* @NonNull */
        Future<Void> token )
        throws NetworkTableException, InterruptedException;

    /**
     * Ends an asynchronous operation to join another network table.
     * 
     * @param token
     *        The asynchronous completion token for the operation; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code token} is not a token for the expected operation.
     * @throws java.lang.InterruptedException
     *         If the thread is interrupted while waiting for the operation to
     *         complete.
     * @throws java.lang.NullPointerException
     *         If {@code token} is {@code null}.
     * @throws java.util.concurrent.CancellationException
     *         If the operation was cancelled.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    public void endJoin(
        /* @NonNull */
        Future<Void> token )
        throws NetworkTableException, InterruptedException;

    /**
     * Indicates the network is connected.
     * 
     * @return {@code true} if the network is connected; otherwise {@code false}
     *         .
     */
    public boolean isConnected();

    /**
     * Removes the specified network table listener from this network table.
     * 
     * @param listener
     *        The network table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered network table listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeNetworkTableListener(
        /* @NonNull */
        INetworkTableListener listener );
}
