/*
 * ITableGateway.java
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
 * Created on Apr 8, 2011 at 9:19:53 PM.
 */

package org.gamegineer.table.internal.net;

/**
 * A gateway to a table that is connected to the network table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableGateway
{
    // ======================================================================
    // Methods
    // ======================================================================

    // TODO: define responsibilities
    //
    // - encapsulates a local or remote table
    // - responsible for communicating events associated with the encapsulated
    //   table to its associated network table (via a table gateway context)
    // - for remote tables, responsible for communicating events sent by the
    //   network table over the network to the remote table
    // - for local tables, responsible for communicating events sent by the
    //   network table to the local table

    /**
     * Gets the name of the player associated with the table gateway.
     * 
     * @return The name of the player associated with the table gateway; never
     *         {@code null}.
     */
    /* @NonNull */
    public String getPlayerName();
}
