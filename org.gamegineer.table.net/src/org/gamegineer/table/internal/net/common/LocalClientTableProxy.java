/*
 * LocalClientTableProxy.java
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
 * Created on Apr 10, 2011 at 5:34:26 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableProxy;

// TODO: add ITable ctor parameter

/**
 * A proxy for a local client table.
 */
@ThreadSafe
public final class LocalClientTableProxy
    implements ITableProxy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the player associated with the table. */
    private final String playerName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalClientTableProxy} class.
     * 
     * @param playerName
     *        The name of the player associated with the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    public LocalClientTableProxy(
        /* @NonNull */
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$

        playerName_ = playerName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.ITableProxy#getPlayerName()
     */
    @Override
    public String getPlayerName()
    {
        return playerName_;
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableProxy#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$

        // do nothing
    }
}
