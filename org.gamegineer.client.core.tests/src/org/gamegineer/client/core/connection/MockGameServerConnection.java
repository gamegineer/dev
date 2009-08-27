/*
 * MockGameServerConnection.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 29, 2008 at 12:18:32 AM.
 */

package org.gamegineer.client.core.connection;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import java.io.IOException;
import java.security.Principal;
import org.gamegineer.server.core.IGameServer;

/**
 * Mock implementation of
 * {@link org.gamegineer.client.core.connection.IGameServerConnection}.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public class MockGameServerConnection
    implements IGameServerConnection
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The call count for the {@code close} method. */
    private int m_closeCallCount;

    /** The call count for the {@code open} method. */
    private int m_openCallCount;

    /** Indicates an exception should be thrown when the connection is closed. */
    private boolean m_throwExceptionWhenClosed;

    /** Indicates an exception should be thrown when the connection is opened. */
    private boolean m_throwExceptionWhenOpened;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockGameServerConnection} class.
     */
    public MockGameServerConnection()
    {
        m_closeCallCount = 0;
        m_openCallCount = 0;
        m_throwExceptionWhenClosed = false;
        m_throwExceptionWhenOpened = false;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#close()
     */
    public void close()
        throws IOException
    {
        ++m_closeCallCount;

        if( m_throwExceptionWhenClosed )
        {
            throw new IOException();
        }
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getGameServer()
     */
    public IGameServer getGameServer()
    {
        return createDummy( IGameServer.class );
    }

    /**
     * Gets the call count for the {@code close} method.
     * 
     * @return The call count for the {@code close} method.
     */
    public int getCloseCallCount()
    {
        return m_closeCallCount;
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getName()
     */
    public String getName()
    {
        return getClass().getName();
    }

    /**
     * Gets the call count for the {@code open} method.
     * 
     * @return The call count for the {@code open} method.
     */
    public int getOpenCallCount()
    {
        return m_openCallCount;
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getUserPrincipal()
     */
    public Principal getUserPrincipal()
    {
        return createDummy( Principal.class );
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#open()
     */
    public void open()
        throws IOException
    {
        ++m_openCallCount;

        if( m_throwExceptionWhenOpened )
        {
            throw new IOException();
        }
    }

    /**
     * Sets the exception behavior of the connection when it is closed.
     * 
     * @param throwExceptionWhenClosed
     *        Indicates an exception should be thrown when the connection is
     *        closed.
     * 
     * @return A reference to this mock object; never {@code null}.
     */
    /* @NonNull */
    public MockGameServerConnection setThrowExceptionWhenClosed(
        final boolean throwExceptionWhenClosed )
    {
        m_throwExceptionWhenClosed = throwExceptionWhenClosed;
        return this;
    }

    /**
     * Sets the exception behavior of the connection when it is opened.
     * 
     * @param throwExceptionWhenOpened
     *        Indicates an exception should be thrown when the connection is
     *        opened.
     * 
     * @return A reference to this mock object; never {@code null}.
     */
    /* @NonNull */
    public MockGameServerConnection setThrowExceptionWhenOpened(
        final boolean throwExceptionWhenOpened )
    {
        m_throwExceptionWhenOpened = throwExceptionWhenOpened;
        return this;
    }
}
