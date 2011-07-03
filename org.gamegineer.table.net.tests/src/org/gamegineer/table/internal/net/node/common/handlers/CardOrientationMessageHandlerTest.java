/*
 * CardOrientationMessageHandlerTest.java
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
 * Created on Jun 30, 2011 at 11:59:36 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.INetworkTable;
import org.gamegineer.table.internal.net.node.INetworkTableManager;
import org.gamegineer.table.internal.net.node.INode;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.node.common.messages.CardOrientationMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.handlers.CardOrientationMessageHandler}
 * class.
 */
public final class CardOrientationMessageHandlerTest
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
     * Initializes a new instance of the {@code
     * CardOrientationMessageHandlerTest} class.
     */
    public CardOrientationMessageHandlerTest()
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
        messageHandler_ = CardOrientationMessageHandler.INSTANCE;
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        messageHandler_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a card
     * orientation message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "unchecked" )
    @Test
    public void testHandleMessage_CardOrienationMessage()
        throws Exception
    {
        final int cardPileIndex = 3;
        final int cardIndex = 1;
        final CardOrientation cardOrientation = CardOrientation.FACE_UP;
        final INetworkTable table = mocksControl_.createMock( INetworkTable.class );
        final INetworkTableManager tableManager = mocksControl_.createMock( INetworkTableManager.class );
        tableManager.setCardOrientation( table, cardPileIndex, cardIndex, cardOrientation );
        final INode localNode = mocksControl_.createMock( INode.class );
        EasyMock.expect( localNode.getTableManager() ).andReturn( tableManager ).anyTimes();
        final IRemoteNodeController remoteNodeController = mocksControl_.createMock( IRemoteNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getTable() ).andReturn( table ).anyTimes();
        mocksControl_.replay();

        final CardOrientationMessage message = new CardOrientationMessage();
        message.setCardIndex( cardIndex );
        message.setCardOrientation( cardOrientation );
        message.setCardPileIndex( cardPileIndex );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
