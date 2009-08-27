/*
 * RoleUi.java
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
 * Created on Feb 27, 2009 at 10:00:51 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * Implementation of {@link org.gamegineer.game.ui.system.IRoleUi}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class RoleUi
    implements IRoleUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private final String m_id;

    /** The role name. */
    private final String m_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleUi} class.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * @param name
     *        The role name; must not be {@code null}.
     */
    private RoleUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name )
    {
        assert id != null;
        assert name != null;

        m_id = id;
        m_name = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code RoleUi} class.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * @param name
     *        The role name; must not be {@code null}.
     * 
     * @return A new role user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal role user interface.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code name} is {@code null}.
     */
    /* @NonNull */
    public static RoleUi createRoleUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        final RoleUi roleUi = new RoleUi( id, name );
        GameSystemUiUtils.assertRoleUiLegal( roleUi );
        return roleUi;
    }

    /*
     * @see org.gamegineer.game.ui.system.IRoleUi#getId()
     */
    public String getId()
    {
        return m_id;
    }

    /*
     * @see org.gamegineer.game.ui.system.IRoleUi#getName()
     */
    public String getName()
    {
        return m_name;
    }
}
