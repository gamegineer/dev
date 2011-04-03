/*
 * AuthenticatorTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 2, 2011 at 9:18:45 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.Authenticator} class.
 */
public final class AuthenticatorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The authenticator under test in the fixture. */
    private Authenticator authenticator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AuthenticatorTest} class.
     */
    public AuthenticatorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Simulates an authentication using the specified expected and actual
     * passwords.
     * 
     * @param expectedPassword
     *        The expected password; must not be {@code null}.
     * @param actualPassword
     *        The actual password; must not be {@code null}.
     * 
     * @return {@code true} if authentication is successful; otherwise {@code
     *         false}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    private boolean authenticate(
        /* @NonNull */
        final String expectedPassword,
        /* @NonNull */
        final String actualPassword )
        throws NetworkTableException
    {
        assert expectedPassword != null;
        assert actualPassword != null;

        final byte[] challenge = authenticator_.createChallenge();
        final byte[] salt = authenticator_.createSalt();
        final byte[] expectedResponse = authenticator_.createResponse( challenge, new SecureString( expectedPassword.toCharArray() ), salt );

        final byte[] actualResponse = authenticator_.createResponse( challenge, new SecureString( actualPassword.toCharArray() ), salt );

        return Arrays.equals( expectedResponse, actualResponse );
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        authenticator_ = new Authenticator();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        authenticator_ = null;
    }

    /**
     * Ensures the authenticator correctly detects a failed authentication.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAuthentication_Failure()
        throws Exception
    {
        assertFalse( authenticate( "correct_password", "wrong_password" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the authenticator correctly detects a successful authentication.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAuthentication_Success()
        throws Exception
    {
        assertTrue( authenticate( "correct_password", "correct_password" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the authenticator correctly detects a successful authentication
     * with the password is an empty string.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAuthentication_Success_EmptyPassword()
        throws Exception
    {
        assertTrue( authenticate( "", "" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
