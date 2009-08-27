/*
 * Configurations.java
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
 * Created on Dec 20, 2008 at 9:15:13 PM.
 */

package org.gamegineer.server.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.server.core.system.FakeGameSystemSource;

/**
 * A factory for creating various types of game server configuration objects
 * suitable for testing.
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class Configurations
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique game server identifier. */
    private static final AtomicLong c_nextGameServerId = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Configurations} class.
     */
    private Configurations()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game server configuration.
     * 
     * <p>
     * The returned game server configuration contains legal values for all
     * fields.
     * </p>
     * 
     * @return A new game server configuration; never {@code null}.
     */
    /* @NonNull */
    public static IGameServerConfiguration createGameServerConfiguration()
    {
        return createGameServerConfigurationBuilder().toGameServerConfiguration();
    }

    /**
     * Creates a new game server configuration builder.
     * 
     * <p>
     * The returned game server configuration builder contains legal values for
     * all fields.
     * </p>
     * 
     * @return A new game server configuration builder; never {@code null}.
     */
    /* @NonNull */
    public static GameServerConfigurationBuilder createGameServerConfigurationBuilder()
    {
        return createIncompleteGameServerConfigurationBuilder( EnumSet.noneOf( GameServerConfigurationAttribute.class ) );
    }

    /**
     * Creates an incomplete game server configuration builder that does not
     * have a value set for the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game server configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static GameServerConfigurationBuilder createIncompleteGameServerConfigurationBuilder(
        /* @NonNull */
        final GameServerConfigurationAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteGameServerConfigurationBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete game server configuration builder that does not
     * have a value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete game server configuration builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game server configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static GameServerConfigurationBuilder createIncompleteGameServerConfigurationBuilder(
        /* @NonNull */
        final EnumSet<GameServerConfigurationAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final GameServerConfigurationBuilder builder = new GameServerConfigurationBuilder();

        if( !missingAttributes.contains( GameServerConfigurationAttribute.GAME_SYSTEM_SOURCE ) )
        {
            builder.setGameSystemSource( new FakeGameSystemSource() );
        }
        if( !missingAttributes.contains( GameServerConfigurationAttribute.NAME ) )
        {
            builder.setName( getUniqueGameServerIdentifier() + "-name" ); //$NON-NLS-1$
        }

        return builder;
    }

    /**
     * Gets a unique game server identifier.
     * 
     * @return A unique game server identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueGameServerIdentifier()
    {
        return String.format( "game-server-%1$d", c_nextGameServerId.incrementAndGet() ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Identifies the attributes of a game server configuration.
     */
    public enum GameServerConfigurationAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The game system source attribute. */
        GAME_SYSTEM_SOURCE,

        /** The name attribute. */
        NAME,
    }
}
