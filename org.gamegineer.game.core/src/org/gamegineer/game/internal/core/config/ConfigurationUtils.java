/*
 * ConfigurationUtils.java
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
 * Created on Jul 12, 2008 at 9:42:30 PM.
 */

package org.gamegineer.game.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.IRole;

/**
 * A collection of useful methods for working with configurations.
 */
public final class ConfigurationUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConfigurationUtils} class.
     */
    private ConfigurationUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts a game configuration is legal.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code gameConfig} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code gameConfig} is {@code null}.
     */
    public static void assertGameConfigurationLegal(
        /* @NonNull */
        final IGameConfiguration gameConfig )
    {
        // TODO: Consider having this method, and all methods like it, throw a checked
        // exception instead of an unchecked exception.  This also implies we may want
        // the toXXX() method in all builders to throw the equivalent checked exception.
        //
        // A pattern whereby we throw exceptions at the point where we encounter these
        // types of assertion failures is desirable because it allows us to embed a
        // more descriptive error message, rather than using the assertArgumentLegal()
        // method to test a boolean expression and then using a much-too-generic error
        // message if it fails.

        assertArgumentNotNull( gameConfig, "gameConfig" ); //$NON-NLS-1$

        assertArgumentLegal( containsAllDefinedRoles( gameConfig ), "gameConfig", Messages.ConfigurationUtils_assertGameConfigurationLegal_definedRoleAbsent ); //$NON-NLS-1$
    }

    /**
     * Asserts a player configuration is legal.
     * 
     * @param playerConfig
     *        The player configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code playerConfig} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code playerConfig} is {@code null}.
     */
    public static void assertPlayerConfigurationLegal(
        /* @NonNull */
        final IPlayerConfiguration playerConfig )
    {
        assertArgumentNotNull( playerConfig, "playerConfig" ); //$NON-NLS-1$
    }

    /**
     * Determines if the player configuration list contains an entry for each
     * and only each defined role.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @return {@code true} if the player configuration list contains an entry
     *         for each and only each defined role; otherwise {@code false}.
     */
    private static boolean containsAllDefinedRoles(
        /* @NonNull */
        final IGameConfiguration gameConfig )
    {
        assert gameConfig != null;

        final List<IPlayerConfiguration> playerConfigs = gameConfig.getPlayers();
        final List<IRole> roles = gameConfig.getSystem().getRoles();

        if( playerConfigs.size() != roles.size() )
        {
            return false;
        }

        roleLoop: for( final IRole role : roles )
        {
            for( final IPlayerConfiguration playerConfig : playerConfigs )
            {
                if( role.getId().equals( playerConfig.getRoleId() ) )
                {
                    continue roleLoop;
                }
            }

            return false;
        }

        return true;
    }
}
