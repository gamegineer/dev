/*
 * RoleUiTest.java
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
 * Created on Feb 27, 2009 at 10:19:34 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.system.RoleUi} class.
 */
public final class RoleUiTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleUiTest} class.
     */
    public RoleUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createRoleUi} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateRoleUi_Id_Null()
    {
        RoleUi.createRoleUi( null, "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createRoleUi} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateRoleUi_Name_Null()
    {
        RoleUi.createRoleUi( "id", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createRoleUi} method throws an exception when one or
     * more of its arguments results in an illegal role user interface.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal role user interface attributes is
     * located in the {@code GameSystemUiUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal role user interface." )
    @Test( expected = IllegalArgumentException.class )
    public void testCreateRoleUi_RoleUi_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }
}
