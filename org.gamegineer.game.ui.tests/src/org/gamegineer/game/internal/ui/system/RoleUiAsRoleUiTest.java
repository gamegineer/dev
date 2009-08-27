/*
 * RoleUiAsRoleUiTest.java
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
 * Created on Feb 27, 2009 at 10:02:04 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.ui.system.AbstractRoleUiTestCase;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.RoleUi} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.game.ui.system.IRoleUi}
 * interface.
 */
public final class RoleUiAsRoleUiTest
    extends AbstractRoleUiTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleUiAsRoleUiTest} class.
     */
    public RoleUiAsRoleUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.system.AbstractRoleUiTestCase#createRoleUi(org.gamegineer.game.core.system.IRole)
     */
    @Override
    protected IRoleUi createRoleUi(
        final IRole role )
    {
        assertArgumentNotNull( role, "role" ); //$NON-NLS-1$

        return RoleUi.createRoleUi( role.getId(), role.getId() + "-name" ); //$NON-NLS-1$
    }
}
