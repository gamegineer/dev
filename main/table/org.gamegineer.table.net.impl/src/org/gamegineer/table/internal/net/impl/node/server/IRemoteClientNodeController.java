/*
 * IRemoteClientNodeController.java
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
 * Created on Apr 23, 2011 at 4:17:07 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;

/**
 * The control interface for a remote client node.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteClientNodeController
    extends IRemoteNodeController<@NonNull IServerNode>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the most-recent challenge used to authenticate the client.
     * 
     * @return The most-recent challenge used to authenticate the client or
     *         {@code null} if an authentication request has not yet been sent.
     *         Callers must not modify the returned array.
     */
    public byte @Nullable [] getChallenge();

    /**
     * Gets the most-recent salt used to authenticate the client.
     * 
     * @return The most-recent salt used to authenticate the client or
     *         {@code null} if an authentication request has not yet been sent.
     *         Callers must not modify the returned array.
     */
    public byte @Nullable [] getSalt();

    /**
     * Sets the challenge used to authenticate the client.
     * 
     * @param challenge
     *        The challenge used to authenticate the client or {@code null} to
     *        clear the challenge. Callers must not modify the specified array
     *        after passing it to this method.
     */
    public void setChallenge(
        byte @Nullable [] challenge );

    /**
     * Sets the salt used to authenticate the client.
     * 
     * @param salt
     *        The salt used to authenticate the client or {@code null} to clear
     *        the salt. Callers must not modify the specified array after
     *        passing it to this method.
     */
    public void setSalt(
        byte @Nullable [] salt );
}
