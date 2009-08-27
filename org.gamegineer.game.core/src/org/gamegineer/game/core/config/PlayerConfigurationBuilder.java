/*
 * PlayerConfigurationBuilder.java
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
 * Created on Jan 17, 2009 at 10:29:14 PM.
 */

package org.gamegineer.game.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.game.internal.core.config.PlayerConfiguration;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.config.IPlayerConfiguration}.
 * 
 * <p>
 * Each player configuration built by an instance of this class is immutable and
 * thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class PlayerConfigurationBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player role identifier. */
    private String m_roleId;

    /** The player user identifier. */
    private String m_userId;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerConfigurationBuilder}
     * class with an empty player configuration.
     */
    public PlayerConfigurationBuilder()
    {
        m_roleId = null;
        m_userId = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the player role identifier.
     * 
     * @param roleId
     *        The player role identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleId} is {@code null}.
     */
    /* @NonNull */
    public PlayerConfigurationBuilder setRoleId(
        /* @NonNull */
        final String roleId )
    {
        assertArgumentNotNull( roleId, "roleId" ); //$NON-NLS-1$

        m_roleId = roleId;

        return this;
    }

    /**
     * Sets the player user identifier.
     * 
     * @param userId
     *        The player user identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code userId} is {@code null}.
     */
    /* @NonNull */
    public PlayerConfigurationBuilder setUserId(
        /* @NonNull */
        final String userId )
    {
        assertArgumentNotNull( userId, "userId" ); //$NON-NLS-1$

        m_userId = userId;

        return this;
    }

    /**
     * Creates a new player configuration based on the current state of this
     * builder.
     * 
     * @return A new player configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         player configuration.
     */
    /* @NonNull */
    public IPlayerConfiguration toPlayerConfiguration()
    {
        assertStateLegal( m_roleId != null, Messages.PlayerConfigurationBuilder_roleId_notSet );
        assertStateLegal( m_userId != null, Messages.PlayerConfigurationBuilder_userId_notSet );

        try
        {
            return PlayerConfiguration.createPlayerConfiguration( m_roleId, m_userId );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.PlayerConfigurationBuilder_state_illegal, e );
        }
    }
}
