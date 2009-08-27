/*
 * PrincipalsTest.java
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
 * Created on Apr 10, 2009 at 9:16:03 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.securitymanager.Principals}
 * class.
 */
public final class PrincipalsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PrincipalsTest} class.
     */
    public PrincipalsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createUserPrincipal} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateUserPrincipal_Name_Null()
    {
        Principals.createUserPrincipal( null );
    }

    /**
     * Ensures the {@code createUserPrincipal} method does not return
     * {@code null}.
     */
    @Test
    public void testCreateUserPrincipal_ReturnValue_NonNull()
    {
        assertNotNull( Principals.createUserPrincipal( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAnonymousUserPrincipal} method does not return
     * {@code null}.
     */
    @Test
    public void testGetAnonymousUserPrincipal_ReturnValue_NonNull()
    {
        assertNotNull( Principals.getAnonymousUserPrincipal() );
    }
}
