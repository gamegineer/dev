/*
 * TableIncrementMessageHandlerTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 12, 2011 at 9:00:25 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.INetworkTable;
import org.gamegineer.table.internal.net.node.INode;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.node.ITableManager;
import org.gamegineer.table.internal.net.node.TableIncrement;
import org.gamegineer.table.internal.net.node.common.messages.TableIncrementMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.handlers.TableIncrementMessageHandler}
 * class.
 */
public final class TableIncrementMessageHandlerTest
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
     * Initializes a new instance of the
     * {@code TableIncrementMessageHandlerTest} class.
     */
    public TableIncrementMessageHandlerTest()
    {
        super();
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
        messageHandler_ = TableIncrementMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a table
     * increment message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "rawtypes" )
    @Test
    public void testHandleMessage_TableIncrementMessage()
        throws Exception
    {
        final TableIncrement tableIncrement = new TableIncrement();
        final INetworkTable table = mocksControl_.createMock( INetworkTable.class );
        final ITableManager tableManager = mocksControl_.createMock( ITableManager.class );
        tableManager.incrementTableState( table, tableIncrement );
        final INode localNode = mocksControl_.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl_.replay();

        final TableIncrementMessage message = new TableIncrementMessage();
        message.setIncrement( tableIncrement );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
