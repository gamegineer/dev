/*
 * UserPrincipalAsPrincipalTest.java
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
 * Created on Apr 10, 2009 at 9:29:31 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.securitymanager.UserPrincipal}
 * class to ensure it does not violate the contract of the
 * {@link java.security.Principal} interface.
 */
public final class UserPrincipalAsPrincipalTest
    extends AbstractPrincipalTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code UserPrincipalAsPrincipalTest}
     * class.
     */
    public UserPrincipalAsPrincipalTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.securitymanager.AbstractPrincipalTestCase#createPrincipal(java.lang.String)
     */
    @Override
    protected Principal createPrincipal(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return new UserPrincipal( name );
    }
}
