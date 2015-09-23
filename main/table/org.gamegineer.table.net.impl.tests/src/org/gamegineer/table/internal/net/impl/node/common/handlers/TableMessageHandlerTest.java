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

import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
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
public final class TableMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableMessageHandlerTest} class.
     */
    public TableMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture; never {@code null}
     *         .
     */
    private IMessageHandler getMessageHandler()
    {
        return TableMessageHandler.INSTANCE;
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
    }

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
        mocksControl_ = Optional.of( EasyMock.createControl() );
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
        final IMocksControl mocksControl = getMocksControl();
        final Object tableMemento = new Object();
        final INetworkTable table = mocksControl.createMock( INetworkTable.class );
        final ITableManager tableManager = mocksControl.createMock( ITableManager.class );
        tableManager.setTableState( table, tableMemento );
        final INode<IRemoteNode> localNode = mocksControl.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController<INode<IRemoteNode>> remoteNodeController = mocksControl.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl.replay();

        final TableMessage message = new TableMessage();
        message.setMemento( tableMemento );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }
}
