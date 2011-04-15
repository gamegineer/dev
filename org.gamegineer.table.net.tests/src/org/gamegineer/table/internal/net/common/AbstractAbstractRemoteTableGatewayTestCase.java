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

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public abstract class AbstractAbstractRemoteTableGatewayTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The remote table gateway under test in the fixture. */
    private AbstractRemoteTableGateway remoteTableGateway_;


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
     * Creates the remote table gateway to be tested.
     * 
     * @return The remote table gateway to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract AbstractRemoteTableGateway createRemoteTableGateway()
        throws Exception;

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
        remoteTableGateway_ = createRemoteTableGateway();
        assertNotNull( remoteTableGateway_ );
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
        remoteTableGateway_ = null;
    }

    /**
     * Ensures the {@code messageReceivedInternal} method throws an exception
     * when passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceivedInternal_Context_Null()
    {
        remoteTableGateway_.messageReceivedInternal( null, EasyMock.createMock( IMessage.class ) );
    }

    /**
     * Ensures the {@code messageReceivedInternal} method throws an exception
     * when passed a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceivedInternal_Message_Null()
    {
        remoteTableGateway_.messageReceivedInternal( EasyMock.createMock( IServiceContext.class ), null );
    }

    /**
     * Ensures the {@code peerStoppedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testPeerStoppedInternal_Context_Null()
    {
        remoteTableGateway_.peerStoppedInternal( null );
    }

    /**
     * Ensures the {@code startedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStartedInternal_Context_Null()
    {
        remoteTableGateway_.startedInternal( null );
    }

    /**
     * Ensures the {@code stoppedInternal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStoppedInternal_Context_Null()
    {
        remoteTableGateway_.stoppedInternal( null );
    }
}
