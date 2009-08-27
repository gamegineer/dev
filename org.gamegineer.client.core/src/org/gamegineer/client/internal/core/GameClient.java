/*
 * GameClient.java
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
 * Created on Dec 27, 2008 at 9:29:06 PM.
 */

package org.gamegineer.client.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.core.GameClientConfigurationException;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.core.connection.GameServerConnectionFactory;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.client.internal.core.config.ConfigurationUtils;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * Implementation of {@link org.gamegineer.client.core.IGameClient}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class GameClient
    implements IGameClient
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game client configuration. */
    private final IGameClientConfiguration m_config;

    /** The game server connection. */
    @GuardedBy( "m_lock" )
    private IGameServerConnection m_connection;

    /** The instance lock. */
    private final Object m_lock;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClient} class.
     * 
     * @param gameClientConfig
     *        The game client configuration; must not be {@code null}.
     */
    private GameClient(
        final IGameClientConfiguration gameClientConfig )
    {
        assert gameClientConfig != null;

        m_lock = new Object();
        m_config = gameClientConfig;
        resetConnection();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.IGameClient#connect(org.gamegineer.client.core.connection.IGameServerConnection)
     */
    public void connect(
        final IGameServerConnection connection )
        throws IOException
    {
        assertArgumentNotNull( connection, "connection" ); //$NON-NLS-1$

        synchronized( m_lock )
        {
            disconnect();
            connection.open();
            m_connection = connection;
        }
    }

    /**
     * Creates a new instance of the {@code GameClient} class.
     * 
     * @param gameClientConfig
     *        The game client configuration; must not be {@code null}.
     * 
     * @return A new instance of the {@code GameClient} class; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.client.core.GameClientConfigurationException
     *         If an error occurs while creating the game client.
     */
    /* @NonNull */
    public static GameClient createGameClient(
        /* @NonNull */
        final IGameClientConfiguration gameClientConfig )
        throws GameClientConfigurationException
    {
        assertArgumentNotNull( gameClientConfig, "gameClientConfig" ); //$NON-NLS-1$

        try
        {
            ConfigurationUtils.assertGameClientConfigurationLegal( gameClientConfig );
        }
        catch( final IllegalArgumentException e )
        {
            throw new GameClientConfigurationException( Messages.GameClient_gameClientConfig_illegal, e );
        }

        return new GameClient( gameClientConfig );
    }

    /*
     * @see org.gamegineer.client.core.IGameClient#disconnect()
     */
    public void disconnect()
    {
        synchronized( m_lock )
        {
            try
            {
                m_connection.close();
            }
            catch( final IOException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.GameClient_disconnect_connectionCloseError, e );
            }

            resetConnection();
        }
    }

    /*
     * @see org.gamegineer.client.core.IGameClient#getGameServerConnection()
     */
    public IGameServerConnection getGameServerConnection()
    {
        synchronized( m_lock )
        {
            return m_connection;
        }
    }

    /*
     * @see org.gamegineer.client.core.IGameClient#getGameSystemUi(java.lang.String)
     */
    public IGameSystemUi getGameSystemUi(
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        for( final IGameSystemUi gameSystemUi : getGameSystemUis() )
        {
            if( id.equals( gameSystemUi.getId() ) )
            {
                return gameSystemUi;
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.client.core.IGameClient#getGameSystemUis()
     */
    public Collection<IGameSystemUi> getGameSystemUis()
    {
        return m_config.getGameSystemUiSource().getGameSystemUis();
    }

    /**
     * Resets the server connection to a default value.
     */
    private void resetConnection()
    {
        m_connection = GameServerConnectionFactory.createNullGameServerConnection();
        try
        {
            m_connection.open();
        }
        catch( final IOException e )
        {
            Loggers.DEFAULT.log( Level.SEVERE, Messages.GameClient_resetConnection_connectionOpenError, e );
        }
    }
}
