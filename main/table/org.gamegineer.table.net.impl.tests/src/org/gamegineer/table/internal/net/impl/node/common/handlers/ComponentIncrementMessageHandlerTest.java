/*
 * ComponentIncrementMessageHandlerTest.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Jun 30, 2011 at 11:59:36 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.handlers;

import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.impl.node.ComponentIncrement;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;
import org.gamegineer.table.internal.net.impl.node.INode;
import org.gamegineer.table.internal.net.impl.node.IRemoteNode;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.impl.node.ITableManager;
import org.gamegineer.table.internal.net.impl.node.common.messages.ComponentIncrementMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentIncrementMessageHandler} class.
 */
public final class ComponentIncrementMessageHandlerTest
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
     * Initializes a new instance of the
     * {@code ComponentIncrementMessageHandlerTest} class.
     */
    public ComponentIncrementMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture.
     */
    private IMessageHandler getMessageHandler()
    {
        return ComponentIncrementMessageHandler.INSTANCE;
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
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
     * Ensures the {@link ComponentIncrementMessageHandler#handleMessage} method
     * correctly handles a component increment message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_ComponentIncrementMessage()
        throws Exception
    {
        final IMocksControl mocksControl = getMocksControl();
        final ComponentPath componentPath = new ComponentPath( new ComponentPath( ComponentPath.ROOT, 1 ), 2 );
        final ComponentIncrement componentIncrement = new ComponentIncrement();
        final INetworkTable table = mocksControl.createMock( INetworkTable.class );
        final ITableManager tableManager = mocksControl.createMock( ITableManager.class );
        tableManager.incrementComponentState( table, componentPath, componentIncrement );
        final INode<IRemoteNode> localNode = mocksControl.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController<INode<IRemoteNode>> remoteNodeController = mocksControl.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl.replay();

        final ComponentIncrementMessage message = new ComponentIncrementMessage();
        message.setIncrement( componentIncrement );
        message.setPath( componentPath );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }
}
