/*
 * RoleBuilder.java
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
 * Created on Jan 17, 2009 at 8:59:42 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.game.internal.core.system.Role;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.IRole}.
 * 
 * <p>
 * Each role built by an instance of this class is immutable and thus guaranteed
 * to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class RoleBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private String m_id;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleBuilder} class with an empty
     * role.
     */
    public RoleBuilder()
    {
        m_id = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the role identifier.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public RoleBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Creates a new role based on the current state of this builder.
     * 
     * @return A new role; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         role.
     */
    /* @NonNull */
    public IRole toRole()
    {
        assertStateLegal( m_id != null, Messages.RoleBuilder_id_notSet );

        try
        {
            return Role.createRole( m_id );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.RoleBuilder_state_illegal, e );
        }
    }
}
