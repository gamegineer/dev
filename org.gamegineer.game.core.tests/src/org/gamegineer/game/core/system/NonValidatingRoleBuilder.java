/*
 * NonValidatingRoleBuilder.java
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
 * Created on Jan 17, 2009 at 9:53:35 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.NonValidatingRole}.
 * 
 * <p>
 * Each role built by an instance of this class is immutable and thus guaranteed
 * to be thread-safe. This class does not validate its attributes and thus
 * allows the construction of malformed roles that may violate the contract of
 * {@code IRole}. It is only intended to be used during testing.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class NonValidatingRoleBuilder
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
     * Initializes a new instance of the {@code NonValidatingRoleBuilder} class
     * with an uninitialized role.
     */
    public NonValidatingRoleBuilder()
    {
        m_id = null;
    }

    /**
     * Initializes a new instance of the {@code NonValidatingRoleBuilder} class
     * using the specified role.
     * 
     * @param role
     *        The role used to initialize this builder; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code role} is {@code null}.
     */
    public NonValidatingRoleBuilder(
        /* @NonNull */
        final IRole role )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$

        m_id = role.getId();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the role identifier.
     * 
     * @param id
     *        The role identifier; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingRoleBuilder setId(
        /* @Nullable */
        final String id )
    {
        m_id = id;

        return this;
    }

    /**
     * Creates a new role based on the current state of this builder.
     * 
     * @return A new role; never {@code null}.
     */
    /* @NonNull */
    public IRole toRole()
    {
        return new NonValidatingRole( m_id );
    }
}
