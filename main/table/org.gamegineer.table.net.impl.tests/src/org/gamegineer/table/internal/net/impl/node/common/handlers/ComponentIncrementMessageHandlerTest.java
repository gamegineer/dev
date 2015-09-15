/*
 * ComponentIncrementMessageHandlerTest.java
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
 * Created on Jun 30, 2011 at 11:59:36 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.impl.node.ComponentIncrement;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;
import org.gamegineer.table.internal.net.impl.node.INode;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.impl.node.ITableManager;
import org.gamegineer.table.internal.net.impl.node.common.messages.ComponentIncrementMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentIncrementMessageHandler} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class ComponentIncrementMessageHandlerTest
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
     * {@code ComponentIncrementMessageHandlerTest} class.
     */
    public ComponentIncrementMessageHandlerTest()
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
        messageHandler_ = ComponentIncrementMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link ComponentIncrementMessageHandler#handleMessage} method
     * correctly handles a component increment message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "rawtypes" )
    @Test
    public void testHandleMessage_ComponentIncrementMessage()
        throws Exception
    {
        final ComponentPath componentPath = new ComponentPath( new ComponentPath( new ComponentPath( null, 0 ), 1 ), 2 );
        final ComponentIncrement componentIncrement = new ComponentIncrement();
        final INetworkTable table = mocksControl_.createMock( INetworkTable.class );
        final ITableManager tableManager = mocksControl_.createMock( ITableManager.class );
        tableManager.incrementComponentState( table, componentPath, componentIncrement );
        final INode localNode = mocksControl_.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl_.replay();

        final ComponentIncrementMessage message = new ComponentIncrementMessage();
        message.setIncrement( componentIncrement );
        message.setPath( componentPath );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
