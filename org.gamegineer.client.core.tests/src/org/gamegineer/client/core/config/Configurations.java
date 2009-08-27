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
 * Created on Mar 7, 2009 at 8:40:05 PM.
 */

package org.gamegineer.client.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.EnumSet;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.core.system.FakeGameSystemUiSource;

/**
 * A factory for creating various types of game client configuration objects
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
     * Creates a new game client configuration.
     * 
     * <p>
     * The returned game client configuration contains legal values for all
     * fields.
     * </p>
     * 
     * @return A new game client configuration; never {@code null}.
     */
    /* @NonNull */
    public static IGameClientConfiguration createGameClientConfiguration()
    {
        return createGameClientConfigurationBuilder().toGameClientConfiguration();
    }

    /**
     * Creates a new game client configuration builder.
     * 
     * <p>
     * The returned game client configuration builder contains legal values for
     * all fields.
     * </p>
     * 
     * @return A new game client configuration builder; never {@code null}.
     */
    /* @NonNull */
    public static GameClientConfigurationBuilder createGameClientConfigurationBuilder()
    {
        return createIncompleteGameClientConfigurationBuilder( EnumSet.noneOf( GameClientConfigurationAttribute.class ) );
    }

    /**
     * Creates an incomplete game client configuration builder that does not
     * have a value set for the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game client configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static GameClientConfigurationBuilder createIncompleteGameClientConfigurationBuilder(
        /* @NonNull */
        final GameClientConfigurationAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteGameClientConfigurationBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete game client configuration builder that does not
     * have a value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete game client configuration builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game client configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static GameClientConfigurationBuilder createIncompleteGameClientConfigurationBuilder(
        /* @NonNull */
        final EnumSet<GameClientConfigurationAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final GameClientConfigurationBuilder builder = new GameClientConfigurationBuilder();

        if( !missingAttributes.contains( GameClientConfigurationAttribute.GAME_SYSTEM_UI_SOURCE ) )
        {
            builder.setGameSystemUiSource( new FakeGameSystemUiSource() );
        }

        return builder;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Identifies the attributes of a game client configuration.
     */
    public enum GameClientConfigurationAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The game system user interface source attribute. */
        GAME_SYSTEM_UI_SOURCE,
    }
}
