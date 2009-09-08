/*
 * PlayerConfiguration.java
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
 * Created on Jan 17, 2009 at 10:54:52 PM.
 */

package org.gamegineer.game.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.config.IPlayerConfiguration;

/**
 * Implementation of
 * {@link org.gamegineer.game.core.config.IPlayerConfiguration}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class PlayerConfiguration
    implements IPlayerConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player role identifier. */
    private final String roleId_;

    /** The player user identifier. */
    private final String userId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerConfiguration} class.
     * 
     * @param roleId
     *        The player role identifier; must not be {@code null}.
     * @param userId
     *        The player user identifier; must not be {@code null}.
     */
    private PlayerConfiguration(
        /* @NonNull */
        final String roleId,
        /* @NonNull */
        final String userId )
    {
        assert roleId != null;
        assert userId != null;

        roleId_ = roleId;
        userId_ = userId;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code PlayerConfiguration} class.
     * 
     * @param roleId
     *        The player role identifier; must not be {@code null}.
     * @param userId
     *        The player user identifier; must not be {@code null}.
     * 
     * @return A new player configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal player configuration.
     * @throws java.lang.NullPointerException
     *         If {@code roleId} or {@code userId} is {@code null}.
     */
    /* @NonNull */
    public static PlayerConfiguration createPlayerConfiguration(
        /* @NonNull */
        final String roleId,
        /* @NonNull */
        final String userId )
    {
        assertArgumentNotNull( roleId, "roleId" ); //$NON-NLS-1$
        assertArgumentNotNull( userId, "userId" ); //$NON-NLS-1$

        final PlayerConfiguration playerConfig = new PlayerConfiguration( roleId, userId );
        ConfigurationUtils.assertPlayerConfigurationLegal( playerConfig );
        return playerConfig;
    }

    /*
     * @see org.gamegineer.game.core.config.IPlayerConfiguration#getRoleId()
     */
    public String getRoleId()
    {
        return roleId_;
    }

    /*
     * @see org.gamegineer.game.core.config.IPlayerConfiguration#getUserId()
     */
    public String getUserId()
    {
        return userId_;
    }
}
