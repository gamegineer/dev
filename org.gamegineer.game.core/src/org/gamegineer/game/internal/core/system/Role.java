/*
 * Role.java
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
 * Created on Jan 17, 2009 at 9:04:17 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.IRole;

/**
 * Implementation of {@link org.gamegineer.game.core.system.IRole}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Role
    implements IRole
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private final String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Role} class.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     */
    private Role(
        /* @NonNull */
        final String id )
    {
        assert id != null;

        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code Role} class.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new role; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal role.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static Role createRole(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final Role role = new Role( id );
        GameSystemUtils.assertRoleLegal( role );
        return role;
    }

    /*
     * @see org.gamegineer.game.core.system.IRole#getId()
     */
    public String getId()
    {
        return id_;
    }
}
