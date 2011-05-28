/*
 * AbstractRemoteNodeAsServiceTest.java
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
 * Created on Apr 23, 2011 at 9:40:52 PM.
 */

package org.gamegineer.table.internal.net.common;

import java.util.Collection;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.INode;
import org.gamegineer.table.internal.net.transport.AbstractServiceTestCase;
import org.gamegineer.table.internal.net.transport.IService;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.transport.IService} interface.
 */
public final class AbstractRemoteNodeAsServiceTest
    extends AbstractServiceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNodeAsServiceTest}
     * class.
     */
    public AbstractRemoteNodeAsServiceTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.AbstractServiceTestCase#createService()
     */
    @Override
    protected IService createService()
    {
        return new AbstractRemoteNode<INode>( EasyMock.createMock( INode.class ) )
        {
            @Override
            public void setPlayers(
                @SuppressWarnings( "unused" )
                final Collection<String> players )
            {
                // do nothing
            }
        };
    }
}
