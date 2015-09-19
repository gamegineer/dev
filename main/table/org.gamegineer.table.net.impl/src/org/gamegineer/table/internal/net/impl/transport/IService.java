/*
 * IService.java
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
 * Created on Mar 25, 2011 at 10:41:04 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A network service.
 * 
 * <p>
 * A service implements one half of a network protocol.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IService
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when a message has been received from the peer service.
     * 
     * @param messageEnvelope
     *        The message envelope; must not be {@code null}.
     */
    public void messageReceived(
        MessageEnvelope messageEnvelope );

    /**
     * Invoked when the peer service has stopped.
     */
    public void peerStopped();

    /**
     * Invoked when the service has started.
     * 
     * @param context
     *        The service context; must not be {@code null}.
     */
    public void started(
        IServiceContext context );

    /**
     * Invoked when the service has stopped.
     * 
     * @param exception
     *        The exception that caused the service to be stopped or
     *        {@code null} if the service was stopped normally.
     */
    public void stopped(
        @Nullable Exception exception );
}
