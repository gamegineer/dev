/*
 * RoleAsRoleTest.java
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
 * Created on Jan 17, 2009 at 9:05:23 PM.
 */

package org.gamegineer.game.internal.core.system;

import org.gamegineer.game.core.system.AbstractRoleTestCase;
import org.gamegineer.game.core.system.IRole;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.Role} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.game.core.system.IRole}
 * interface.
 */
public final class RoleAsRoleTest
    extends AbstractRoleTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleAsRoleTest} class.
     */
    public RoleAsRoleTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.AbstractRoleTestCase#createRole()
     */
    @Override
    protected IRole createRole()
    {
        return Role.createRole( "id" ); //$NON-NLS-1$
    }
}
