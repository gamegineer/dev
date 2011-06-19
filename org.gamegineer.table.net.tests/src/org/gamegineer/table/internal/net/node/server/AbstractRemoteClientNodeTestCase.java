/*
 * AbstractRemoteClientNodeTestCase.java
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
 * Created on May 27, 2011 at 11:16:29 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import org.gamegineer.table.internal.net.node.AbstractRemoteNodeTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.server.IRemoteClientNode}
 * interface.
 * 
 * @param <T>
 *        The type of the remote client node.
 */
public abstract class AbstractRemoteClientNodeTestCase<T extends IRemoteClientNode>
    extends AbstractRemoteNodeTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractRemoteClientNodeTestCase} class.
     */
    protected AbstractRemoteClientNodeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code setPlayers} method throws an exception when passed a
     * {@code null} players collection.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPlayers_Players_Null()
    {
        getRemoteNode().setPlayers( null );
    }
}
