/*
 * AbstractAbstractNodeAsNodeTestCase.java
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
 * Created on May 27, 2011 at 9:02:14 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.gamegineer.table.internal.net.AbstractNodeTestCase;
import org.gamegineer.table.internal.net.INode;
import org.gamegineer.table.internal.net.IRemoteNode;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.INode} interface via extension of
 * the {@link org.gamegineer.table.internal.net.common.AbstractNode} class.
 */
public abstract class AbstractAbstractNodeAsNodeTestCase
    extends AbstractNodeTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractNodeAsNodeTestCase} class.
     */
    protected AbstractAbstractNodeAsNodeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNodeTestCase#isRemoteNodeBound(org.gamegineer.table.internal.net.INode, java.lang.String)
     */
    @Override
    protected final boolean isRemoteNodeBound(
        final INode node,
        final String playerName )
    {
        for( final IRemoteNode remoteNode : ((AbstractNode)node).getRemoteNodes() )
        {
            if( playerName.equals( remoteNode.getPlayerName() ) )
            {
                return true;
            }
        }

        return false;
    }
}
