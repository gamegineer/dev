/*
 * RoleUiBuilder.java
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
 * Created on Feb 27, 2009 at 9:51:03 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.game.internal.ui.system.RoleUi;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.ui.system.IRoleUi}.
 * 
 * <p>
 * Each role user interface built by an instance of this class is immutable and
 * thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class RoleUiBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private String m_id;

    /** The role name. */
    private String m_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleUiBuilder} class with an
     * empty role user interface.
     */
    public RoleUiBuilder()
    {
        m_id = null;
        m_name = null;
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
    public RoleUiBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Sets the role name.
     * 
     * @param name
     *        The role name; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public RoleUiBuilder setName(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        m_name = name;

        return this;
    }

    /**
     * Creates a new role user interface based on the current state of this
     * builder.
     * 
     * @return A new role user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         role user interface.
     */
    /* @NonNull */
    public IRoleUi toRoleUi()
    {
        assertStateLegal( m_id != null, Messages.RoleUiBuilder_id_notSet );
        assertStateLegal( m_name != null, Messages.RoleUiBuilder_name_notSet );

        try
        {
            return RoleUi.createRoleUi( m_id, m_name );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.RoleUiBuilder_state_illegal, e );
        }
    }
}
