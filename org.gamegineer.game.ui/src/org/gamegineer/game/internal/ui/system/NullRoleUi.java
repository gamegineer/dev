/*
 * NullRoleUi.java
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
 * Created on Mar 11, 2009 at 11:02:39 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * Null implementation of {@link org.gamegineer.game.ui.system.IRoleUi}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NullRoleUi
    implements IRoleUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private final String m_id;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullRoleUi} class.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public NullRoleUi(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        return Messages.NullRoleUi_name( m_id );
    }
}
