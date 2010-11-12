/*
 * INetworkTableListener.java
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
 * Created on Nov 3, 2010 at 10:30:43 PM.
 */

package org.gamegineer.table.net;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * network table state.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkTableListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when the network connection state has changed.
     * 
     * @param event
     *        The event describing the network table; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void networkConnectionStateChanged(
        /* @NonNull */
        NetworkTableEvent event );
}
