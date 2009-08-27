/*
 * ThreadPrincipalsTest.java
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
 * Created on Apr 8, 2009 at 10:25:33 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.security.Principal;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.securitymanager.ThreadPrincipals}
 * class.
 */
public final class ThreadPrincipalsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ThreadPrincipalsTest} class.
     */
    public ThreadPrincipalsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code clearUserPrincipal} method sets the user principal to
     * the anonymous user principal.
     */
    @Test
    public void testClearUserPrincipal_ClearsUserPrincipal()
    {
        ThreadPrincipals.setUserPrincipal( Principals.createUserPrincipal( "name" ) ); //$NON-NLS-1$

        ThreadPrincipals.clearUserPrincipal();

        assertEquals( Principals.getAnonymousUserPrincipal(), ThreadPrincipals.getUserPrincipal() );
    }

    /**
     * Ensures the {@code getUserPrincipal} method does not return {@code null}.
     */
    @Test
    public void testGetUserPrincipal_ReturnValue_NonNull()
    {
        assertNotNull( ThreadPrincipals.getUserPrincipal() );
    }

    /**
     * Ensures the {@code setUserPrincipal} method throws an exception when
     * passed a {@code null} principal.
     */
    @Test( expected = NullPointerException.class )
    public void testSetUserPrincipal_Principal_Null()
    {
        ThreadPrincipals.setUserPrincipal( null );
    }

    /**
     * Ensures the {@code setUserPrincipal} method sets the user principal.
     */
    @Test
    public void testSetUserPrincipal_SetsUserPrincipal()
    {
        final Principal newPrincipal = Principals.createUserPrincipal( "user" ); //$NON-NLS-1$

        ThreadPrincipals.setUserPrincipal( newPrincipal );

        assertEquals( newPrincipal, ThreadPrincipals.getUserPrincipal() );
    }
}
