/*
 * TableNetworkListener.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Aug 3, 2011 at 8:53:06 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link ITableNetworkListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class TableNetworkListener
    implements ITableNetworkListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkListener} class.
     */
    public TableNetworkListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkConnected(org.gamegineer.table.net.TableNetworkEvent)
     */
    @Override
    public void tableNetworkConnected(
        @SuppressWarnings( "unused" )
        final TableNetworkEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkDisconnected(org.gamegineer.table.net.TableNetworkDisconnectedEvent)
     */
    @Override
    public void tableNetworkDisconnected(
        @SuppressWarnings( "unused" )
        final TableNetworkDisconnectedEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.net.ITableNetworkListener#tableNetworkPlayersUpdated(org.gamegineer.table.net.TableNetworkEvent)
     */
    @Override
    public void tableNetworkPlayersUpdated(
        @SuppressWarnings( "unused" )
        final TableNetworkEvent event )
    {
        // do nothing
    }
}
