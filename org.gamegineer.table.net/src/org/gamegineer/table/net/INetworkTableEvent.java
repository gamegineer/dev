/*
 * INetworkTableEvent.java
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
 * Created on Nov 9, 2010 at 10:17:33 PM.
 */

package org.gamegineer.table.net;

/**
 * The interface that defines the behavior of all event objects fired by a
 * network table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface INetworkTableEvent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the network table that fired the event.
     * 
     * @return The network table that fired the event; never {@code null}.
     */
    /* @NonNull */
    public INetworkTable getNetworkTable();
}
