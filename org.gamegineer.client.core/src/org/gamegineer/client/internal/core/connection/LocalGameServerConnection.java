/*
 * LocalGameServerConnection.java
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
 * Created on Dec 25, 2008 at 9:18:22 PM.
 */

package org.gamegineer.client.internal.core.connection;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Principal;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.common.remoting.RemoteException;
import org.gamegineer.engine.core.extensions.securitymanager.ThreadPrincipals;
import org.gamegineer.server.core.IGameServer;

/**
 * Implementation of
 * {@link org.gamegineer.client.core.connection.IGameServerConnection} that
 * represents a connection to a local game server.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class LocalGameServerConnection
    implements IGameServerConnection
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local game server. */
    @GuardedBy( "m_lock" )
    private IGameServer m_gameServer;

    /** The instance lock. */
    private final Object m_lock;

    /** The connection state. */
    @GuardedBy( "m_lock" )
    private State m_state;

    /** The user principal associated with the connection. */
    private final Principal m_userPrincipal;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalGameServerConnection}
     * class.
     * 
     * @param gameServer
     *        The local game server; must not be {@code null}.
     * @param userPrincipal
     *        The user principal associated with the connection; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameServer} or {@code userPrincipal} is {@code null}.
     */
    public LocalGameServerConnection(
        /* @NonNull */
        final IGameServer gameServer,
        /* @NonNull */
        final Principal userPrincipal )
    {
        assertArgumentNotNull( gameServer, "gameServer" ); //$NON-NLS-1$
        assertArgumentNotNull( userPrincipal, "userPrincipal" ); //$NON-NLS-1$

        m_lock = new Object();
        m_state = State.PRISTINE;
        m_gameServer = createGameServerProxy( gameServer );
        m_userPrincipal = userPrincipal;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#close()
     */
    public void close()
    {
        synchronized( m_lock )
        {
            ThreadPrincipals.clearUserPrincipal();
            m_state = State.CLOSED;
            m_gameServer = null;
        }
    }

    /**
     * Creates a proxy for the specified game server that throws a
     * {@code RemoteException} if any game server method is invoked while this
     * connection is not open.
     * 
     * @param gameServer
     *        The game server; must not be {@code null}.
     * 
     * @return The game server proxy; never {@code null}.
     */
    /* @NonNull */
    private IGameServer createGameServerProxy(
        /* @NonNull */
        final IGameServer gameServer )
    {
        assert gameServer != null;

        final ClassLoader loader = IGameServer.class.getClassLoader();
        final Class<?>[] interfaces = new Class<?>[] {
            IGameServer.class
        };
        final InvocationHandler handler = new InvocationHandler()
        {
            @SuppressWarnings( "synthetic-access" )
            public Object invoke(
                @SuppressWarnings( "unused" )
                final Object proxy,
                final Method method,
                final Object[] args )
                throws Throwable
            {
                synchronized( m_lock )
                {
                    if( m_state != State.OPEN )
                    {
                        throw new RemoteException( Messages.LocalGameServerConnection_createGameServerProxy_connectionNotOpen );
                    }
                }

                try
                {
                    return method.invoke( gameServer, args );
                }
                catch( final InvocationTargetException e )
                {
                    throw e.getCause();
                }
            }
        };
        return (IGameServer)Proxy.newProxyInstance( loader, interfaces, handler );
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getGameServer()
     */
    public IGameServer getGameServer()
    {
        synchronized( m_lock )
        {
            assertStateLegal( m_state == State.OPEN, Messages.LocalGameServerConnection_getGameServer_connectionNotOpen );

            return m_gameServer;
        }
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getName()
     */
    public String getName()
    {
        return Messages.LocalGameServerConnection_name;
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#getUserPrincipal()
     */
    public Principal getUserPrincipal()
    {
        return m_userPrincipal;
    }

    /*
     * @see org.gamegineer.client.core.connection.IGameServerConnection#open()
     */
    public void open()
        throws IOException
    {
        synchronized( m_lock )
        {
            if( m_state == State.CLOSED )
            {
                throw new IOException( Messages.LocalGameServerConnection_open_connectionClosed );
            }

            ThreadPrincipals.setUserPrincipal( m_userPrincipal );
            m_state = State.OPEN;
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The state of the connection.
     */
    private enum State
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The connection has never been opened. */
        PRISTINE,

        /** The connection is open. */
        OPEN,

        /** The connection is closed. */
        CLOSED,
    }
}
