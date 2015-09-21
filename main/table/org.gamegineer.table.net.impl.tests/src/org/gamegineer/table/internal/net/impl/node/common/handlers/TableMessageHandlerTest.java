/*
 * TableMessageHandlerTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 16, 2011 at 11:34:34 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;
import org.gamegineer.table.internal.net.impl.node.INode;
import org.gamegineer.table.internal.net.impl.node.IRemoteNode;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.impl.node.ITableManager;
import org.gamegineer.table.internal.net.impl.node.common.messages.TableMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableMessageHandler} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class TableMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private IMessageHandler messageHandler_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableMessageHandlerTest} class.
     */
    public TableMessageHandlerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        messageHandler_ = TableMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link TableMessageHandler#handleMessage} method correctly
     * handles a table message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_TableMessage()
        throws Exception
    {
        final Object tableMemento = new Object();
        final INetworkTable table = mocksControl_.createMock( INetworkTable.class );
        final ITableManager tableManager = mocksControl_.createMock( ITableManager.class );
        tableManager.setTableState( table, tableMemento );
        final INode<IRemoteNode> localNode = mocksControl_.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController<INode<IRemoteNode>> remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl_.replay();

        final TableMessage message = new TableMessage();
        message.setMemento( tableMemento );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
