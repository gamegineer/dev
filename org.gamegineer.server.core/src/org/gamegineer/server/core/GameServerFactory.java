/*
 * GameServerFactory.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Dec 13, 2008 at 9:41:54 PM.
 */

package org.gamegineer.server.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.internal.core.NullGameServer;

/**
 * A factory for creating game servers.
 * 
 * <p>
 * In order to be accessible to this factory, a game server implementation must
 * provide an OSGi service that implements
 * {@link org.gamegineer.common.core.services.component.IComponentFactory} which
 * publishes the
 * {@link org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute}
 * attribute with a value that contains the element
 * {@code org.gamegineer.server.core.IGameServer}. The component creation
 * context passed to the factory will publish the
 * {@link GameServerFactory.GameServerConfigurationAttribute} attribute whose
 * value contains an instance of
 * {@code org.gamegineer.server.core.config.IGameServerConfiguration}.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class GameServerFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The null game server. */
    private static final IGameServer NULL_GAME_SERVER = new NullGameServer();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerFactory} class.
     */
    private GameServerFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game server.
     * 
     * @param gameServerConfig
     *        The game server configuration; must not be {@code null}.
     * 
     * @return A new game server; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameServerConfig} is {@code null}.
     * @throws org.gamegineer.server.core.GameServerConfigurationException
     *         If a game server that satisfies the requested configuration could
     *         not be created or {@code gameServerConfig} represents an illegal
     *         configuration.
     */
    /* @NonNull */
    public static IGameServer createGameServer(
        /* @NonNull */
        final IGameServerConfiguration gameServerConfig )
        throws GameServerConfigurationException
    {
        assertArgumentNotNull( gameServerConfig, "gameServerConfig" ); //$NON-NLS-1$

        final String className = IGameServer.class.getName();
        final IComponentSpecification specification = new ClassNameComponentSpecification( className );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, className );
        GameServerConfigurationAttribute.INSTANCE.setValue( builder, gameServerConfig );

        try
        {
            return (IGameServer)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
        }
        catch( final ComponentException e )
        {
            throw new GameServerConfigurationException( Messages.GameServerFactory_createGameServer_unsupportedConfiguration, e );
        }
    }

    /**
     * Creates a null game server.
     * 
     * <p>
     * A null game server is not configurable and is immutable.
     * </p>
     * 
     * @return A null game server; never {@code null}.
     */
    /* @NonNull */
    public static IGameServer createNullGameServer()
    {
        return NULL_GAME_SERVER;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An attribute used to specify the game server configuration when a client
     * requests a game server instance to be created.
     */
    public static final class GameServerConfigurationAttribute
        extends AbstractAttribute<IGameServerConfiguration>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The singleton attribute instance. */
        public static final GameServerConfigurationAttribute INSTANCE = new GameServerConfigurationAttribute();

        /** The attribute name. */
        private static final String NAME = "org.gamegineer.server.core.GameServerFactory.gameServerConfiguration"; //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code GameServerConfigurationAttribute} class.
         */
        private GameServerConfigurationAttribute()
        {
            super( NAME, IGameServerConfiguration.class );
        }
    }
}
