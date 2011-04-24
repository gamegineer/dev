/*
 * AbstractRemoteTableGatewayTestCase.java
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
 * Created on Apr 23, 2011 at 9:12:20 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.AbstractTableGatewayTestCase;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.common.IRemoteTableGateway}
 * interface.
 * 
 * @param <T>
 *        The type of the remote table gateway.
 */
public abstract class AbstractRemoteTableGatewayTestCase<T extends IRemoteTableGateway>
    extends AbstractTableGatewayTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /** The remote table gateway under test in the fixture. */
    private T remoteTableGateway_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractRemoteTableGatewayTestCase} class.
     */
    protected AbstractRemoteTableGatewayTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the remote table gateway to be tested.
     * 
     * @return The remote table gateway to be tested; never {@code null}. The
     *         returned remote table gateway should be in a disconnected state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createRemoteTableGateway()
        throws Exception;

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableGatewayTestCase#createTableGateway()
     */
    @Override
    protected final T createTableGateway()
        throws Exception
    {
        final T remoteTableGateway = createRemoteTableGateway();
        synchronized( remoteTableGateway.getLock() )
        {
            remoteTableGateway.setPlayerName( "playerName" ); //$NON-NLS-1$
        }
        return remoteTableGateway;
    }

    /**
     * Gets the remote table gateway under test in the fixture.
     * 
     * @return The remote table gateway under test in the fixture; never {@code
     *         null}.
     */
    /* @NonNull */
    protected final T getRemoteTableGateway()
    {
        assertNotNull( remoteTableGateway_ );
        return remoteTableGateway_;
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableGatewayTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        remoteTableGateway_ = createRemoteTableGateway();
        assertNotNull( remoteTableGateway_ );

        super.setUp();
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableGatewayTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        remoteTableGateway_ = null;
    }

    /**
     * Ensures the {@code close} method throws an exception when the network is
     * not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testClose_Disconnected()
    {
        synchronized( remoteTableGateway_.getLock() )
        {
            remoteTableGateway_.close();
        }
    }

    /**
     * Ensures the {@code getContext} method does not return {@code null}.
     */
    @Test
    public void testGetContext_ReturnValue_NonNull()
    {
        assertNotNull( remoteTableGateway_.getContext() );
    }

    /**
     * Ensures the {@code getPlayerName} method throws an exception when the
     * network is not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName_Disconnected()
    {
        remoteTableGateway_.getPlayerName();
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when the
     * network is not connected.
     */
    @Test( expected = IllegalStateException.class )
    public void testSendMessage_Disconnected()
    {
        synchronized( remoteTableGateway_.getLock() )
        {
            remoteTableGateway_.sendMessage( EasyMock.createMock( IMessage.class ) );
        }
    }

    /**
     * Ensures the {@code sendMessage} method throws an exception when passed a
     * {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testSendMessage_Message_Null()
    {
        synchronized( remoteTableGateway_.getLock() )
        {
            remoteTableGateway_.sendMessage( null );
        }
    }
}
