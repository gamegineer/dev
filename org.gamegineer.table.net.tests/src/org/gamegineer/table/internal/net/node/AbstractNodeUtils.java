/*
 * AbstractNodeUtils.java
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
 * Created on May 27, 2011 at 10:13:19 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with instances of the
 * {@link AbstractNode} class.
 */
@ThreadSafe
public final class AbstractNodeUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNodeUtils} class.
     */
    private AbstractNodeUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates a remote node for the specified player is bound to the
     * specified local table network node.
     * 
     * @param node
     *        The local table network node; must not be {@code null}.
     * @param playerName
     *        The name of the player associated with the remote node; must not
     *        be {@code null}.
     * 
     * @return {@code true} if a remote node for the specified player is bound
     *         to the specified local table network node; otherwise {@code
     *         false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} or {@code playerName} is {@code null}.
     */
    public static boolean isRemoteNodeBound(
        /* @NonNull */
        final AbstractNode<?> node,
        /* @NonNull */
        final String playerName )
    {
        assertArgumentNotNull( node, "node" ); //$NON-NLS-1$
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$

        for( final IRemoteNode remoteNode : node.getRemoteNodes() )
        {
            if( playerName.equals( remoteNode.getPlayerName() ) )
            {
                return true;
            }
        }

        return false;
    }
}