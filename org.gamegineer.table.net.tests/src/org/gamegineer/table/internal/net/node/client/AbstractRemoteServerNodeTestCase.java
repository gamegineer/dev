/*
 * AbstractRemoteServerNodeTestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on May 27, 2011 at 11:14:46 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import org.gamegineer.table.internal.net.node.AbstractRemoteNodeTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.client.IRemoteServerNode}
 * interface.
 * 
 * @param <T>
 *        The type of the remote server node.
 */
public abstract class AbstractRemoteServerNodeTestCase<T extends IRemoteServerNode>
    extends AbstractRemoteNodeTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractRemoteServerNodeTestCase} class.
     */
    protected AbstractRemoteServerNodeTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link IRemoteServerNode#giveControl} method throws an
     * exception when passed a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testGiveControl_PlayerName_Null()
    {
        getRemoteNode().giveControl( null );
    }
}
