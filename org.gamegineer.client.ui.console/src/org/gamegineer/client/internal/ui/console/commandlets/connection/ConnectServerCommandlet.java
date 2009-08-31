/*
 * ConnectServerCommandlet.java
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
 * Created on Dec 29, 2008 at 9:06:48 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.connection;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.connection.GameServerConnectionFactory;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletArguments;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.internal.ui.console.commandlets.farm.FarmAttributes;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.engine.core.extensions.securitymanager.Principals;
import org.gamegineer.server.core.IGameServer;

/**
 * A commandlet that connects the client to a game server.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class ConnectServerCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The connection property name for the local server identifier. */
    private static final String CONNECTION_PROPERTY_LOCAL_ID = "id"; //$NON-NLS-1$

    /** The connection type specifier for a local connection. */
    private static final String CONNECTION_TYPE_LOCAL = "local"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConnectServerCommandlet} class.
     */
    public ConnectServerCommandlet()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a local game server connection using the specified connection
     * descriptor.
     * 
     * @param context
     *        The commandlet context; must not be {@code null}.
     * @param connectionDescriptor
     *        The game server connection descriptor; must not be {@code null}.
     * @param userPrincipal
     *        The user principal associated with the connection; must not be
     *        {@code null}.
     * 
     * @return A local game server connection; never {@code null}.
     * 
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If the connection cannot be created.
     */
    /* @NonNull */
    private static IGameServerConnection createLocalGameServerConnection(
        /* @NonNull */
        final ICommandletContext context,
        /* @NonNull */
        final ConnectionDescriptor connectionDescriptor,
        /* @NonNull */
        final Principal userPrincipal )
        throws CommandletException
    {
        assert context != null;
        assert connectionDescriptor != null;
        assert userPrincipal != null;

        final String gameServerId = connectionDescriptor.getProperty( CONNECTION_PROPERTY_LOCAL_ID );
        if( gameServerId == null )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_createLocalGameServerConnection_noGameServerId, Messages.ConnectServerCommandlet_output_noGameServerId );
        }

        final Map<String, IGameServer> gameServers = FarmAttributes.GAME_SERVERS.ensureGetValue( context.getStatelet() );
        final IGameServer gameServer = gameServers.get( gameServerId );
        if( gameServer == null )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_createLocalGameServerConnection_gameServerIdUnregistered( gameServerId ), Messages.ConnectServerCommandlet_output_gameServerIdUnregistered( gameServerId ) );
        }

        return GameServerConnectionFactory.createLocalGameServerConnection( gameServer, userPrincipal );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandlet#execute(org.gamegineer.client.ui.console.commandlet.ICommandletContext)
     */
    public void execute(
        final ICommandletContext context )
        throws CommandletException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final int argCount = context.getArguments().size();
        if( argCount == 0 )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_execute_noGameServerArg, Messages.ConnectServerCommandlet_output_noGameServerArg );
        }
        else if( argCount == 1 )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_execute_noUserIdArg, Messages.ConnectServerCommandlet_output_noUserIdArg );
        }
        else if( argCount > 2 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        // TODO: This commandlet must be generalized to support arbitrary server connections.

        final IGameServerConnection connection;
        final ConnectionDescriptor connectionDescriptor = parseConnectionString( context.getArguments().get( 0 ) );
        final Principal userPrincipal = Principals.createUserPrincipal( context.getArguments().get( 1 ) );
        if( CONNECTION_TYPE_LOCAL.equals( connectionDescriptor.getType() ) )
        {
            connection = createLocalGameServerConnection( context, connectionDescriptor, userPrincipal );
        }
        else
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_execute_unknownConnectionType( connectionDescriptor.getType() ), Messages.ConnectServerCommandlet_output_unknownConnectionType( connectionDescriptor.getType() ) );
        }

        try
        {
            context.getGameClient().connect( connection );
        }
        catch( final IOException e )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_execute_connectGameServerError, Messages.ConnectServerCommandlet_output_connectGameServerError, e );
        }

        context.getConsole().getDisplay().getWriter().println( Messages.ConnectServerCommandlet_output_connected( connection ) );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.ConnectServerCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.ConnectServerCommandlet_help_synopsis;
    }

    /**
     * Parses the specified game server connection string.
     * 
     * @param connectionString
     *        The game server connection string; must not be {@code null}.
     * 
     * @return A game server connection descriptor; never {@code null}.
     * 
     * @throws org.gamegineer.client.ui.console.commandlet.CommandletException
     *         If an error occurs while parsing the connection string.
     */
    /* @NonNull */
    private static ConnectionDescriptor parseConnectionString(
        /* @NonNull */
        final String connectionString )
        throws CommandletException
    {
        assert connectionString != null;

        final int colonIndex = connectionString.indexOf( ':' );
        final String connectionType = (colonIndex != -1) ? connectionString.substring( 0, colonIndex ) : connectionString;
        final String connectionPropertiesString = (colonIndex != -1) ? connectionString.substring( colonIndex + 1 ) : ""; //$NON-NLS-1$
        final Map<String, String> connectionProperties;
        try
        {
            connectionProperties = CommandletArguments.parseNamedValueList( connectionPropertiesString );
        }
        catch( final IllegalArgumentException e )
        {
            throw new CommandletException( Messages.ConnectServerCommandlet_parseConnectionString_parseConnectionPropertiesError, Messages.ConnectServerCommandlet_output_parseConnectionPropertiesError, e );
        }

        return new ConnectionDescriptor( connectionType, connectionProperties );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A game server connection descriptor.
     */
    private static final class ConnectionDescriptor
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The connection properties. */
        private final Map<String, String> m_properties;

        /** The connection type. */
        private final String m_type;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ConnectionDescriptor} class.
         * 
         * @param type
         *        The connection type; must not be {@code null}.
         * @param properties
         *        The connection properties; must not be {@code null}. Note
         *        that no copy of this collection is made.
         */
        ConnectionDescriptor(
            /* @NonNull */
            final String type,
            /* @NonNull */
            final Map<String, String> properties )
        {
            assert type != null;
            assert properties != null;

            m_type = type;
            m_properties = properties;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the connection property with the specified name.
         * 
         * @param name
         *        The property name; must not be {@code null}.
         * 
         * @return The value of the connection property with the specified name
         *         or {@code null} if no such property exists.
         */
        /* @Nullable */
        String getProperty(
            /* @NonNull */
            final String name )
        {
            assert name != null;

            return m_properties.get( name );
        }

        /**
         * Gets the connection type.
         * 
         * @return The connection type; never {@code null}.
         */
        /* @NonNull */
        String getType()
        {
            return m_type;
        }
    }
}
