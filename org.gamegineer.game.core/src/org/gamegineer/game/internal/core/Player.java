/*
 * Player.java
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
 * Created on Jul 21, 2008 at 10:19:23 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.IPlayer;
import org.gamegineer.game.core.system.IRole;

/**
 * Implementation of {@link org.gamegineer.game.core.IPlayer}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Player
    implements IPlayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player role identifier. */
    private final String m_roleId;

    /** The player user identifier. */
    private final String m_userId;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Player} class.
     * 
     * @param role
     *        The player role; must not be {@code null}.
     * @param userId
     *        The player user identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} or {@code userId} is {@code null}.
     */
    public Player(
        /* @NonNull */
        final IRole role,
        /* @NonNull */
        final String userId )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$
        assertArgumentNotNull( userId, "userId" ); //$NON-NLS-1$

        m_roleId = role.getId();
        m_userId = userId;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.IPlayer#getRoleId()
     */
    public String getRoleId()
    {
        return m_roleId;
    }

    /*
     * @see org.gamegineer.game.core.IPlayer#getUserId()
     */
    public String getUserId()
    {
        return m_userId;
    }
}
