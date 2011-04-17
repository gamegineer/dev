/*
 * AbstractAbstractRemoteTableGatewayTestCase.java
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
 * Created on Apr 14, 2011 at 11:58:10 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.AbstractTableGatewayTestCase;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public abstract class AbstractAbstractRemoteTableGatewayTestCase
    extends AbstractTableGatewayTestCase<AbstractRemoteTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractRemoteTableGatewayTestCase} class.
     */
    protected AbstractAbstractRemoteTableGatewayTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code messageReceivedInternal} method throws an exception
     * when passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceivedInternal_Context_Null()
    {
        getTableGateway().messageReceivedInternal( null, EasyMock.createMock( IMessage.class ) );
    }

    /**
     * Ensures the {@code messageReceivedInternal} method throws an exception
     * when passed a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceivedInternal_Message_Null()
    {
        getTableGateway().messageReceivedInternal( EasyMock.createMock( IServiceContext.class ), null );
    }

    /**
     * Ensures the {@code peerStoppedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testPeerStoppedInternal_Context_Null()
    {
        getTableGateway().peerStoppedInternal( null );
    }

    /**
     * Ensures the {@code startedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStartedInternal_Context_Null()
    {
        getTableGateway().startedInternal( null );
    }

    /**
     * Ensures the {@code stoppedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStoppedInternal_Context_Null()
    {
        getTableGateway().stoppedInternal( null );
    }
}
