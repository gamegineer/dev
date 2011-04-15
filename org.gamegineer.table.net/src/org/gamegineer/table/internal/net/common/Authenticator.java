/*
 * Authenticator.java
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
 * Created on Apr 2, 2011 at 9:17:38 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A network table protocol authenticator.
 */
@Immutable
public final class Authenticator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The length in bytes of a challenge buffer. */
    private static final int CHALLENGE_LENGTH = 64;

    /** The default password to use if none was specified in the configuration. */
    private static final String DEFAULT_PASSWORD = "#8v&}KF$NI%Nyu3!{KwdH5Enu)MUp8m'"; //$NON-NLS-1$

    /** The iteration count used to generate a secret key. */
    private static final int ITERATION_COUNT = 1000;

    /** The length in bits of a secret key. */
    private static final int KEY_LENGTH = 256;

    /** The length in bytes of a salt buffer. */
    private static final int SALT_LENGTH = 16;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Authenticator} class.
     */
    public Authenticator()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new challenge buffer.
     * 
     * @return A new challenge buffer; never {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    /* @NonNull */
    public byte[] createChallenge()
        throws NetworkTableException
    {
        return createSecureRandomBytes( CHALLENGE_LENGTH );
    }

    /**
     * Creates a new response buffer for the specified challenge and secret key
     * material.
     * 
     * @param challenge
     *        The challenge buffer; must not be {@code null}.
     * @param password
     *        The plain-text password used to generate the secret key; must not
     *        be {@code null}.
     * @param salt
     *        The salt buffer used to generate the secret key; must not be
     *        {@code null}.
     * 
     * @return A new response buffer; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code challenge}, {@code password}, or {@code salt} is
     *         {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    /* @NonNull */
    public byte[] createResponse(
        /* @NonNull */
        final byte[] challenge,
        /* @NonNull */
        final SecureString password,
        /* @NonNull */
        final byte[] salt )
        throws NetworkTableException
    {
        assertArgumentNotNull( challenge, "challenge" ); //$NON-NLS-1$
        assertArgumentNotNull( password, "password" ); //$NON-NLS-1$
        assertArgumentNotNull( salt, "salt" ); //$NON-NLS-1$

        try
        {
            final Mac mac = Mac.getInstance( "HmacSHA1" ); //$NON-NLS-1$
            mac.init( createSecretKey( password, salt ) );
            mac.update( challenge );
            return mac.doFinal();
        }
        catch( final GeneralSecurityException e )
        {
            throw new NetworkTableException( Messages.Authenticator_createResponse_failed, e );
        }
    }

    /**
     * Creates a new salt buffer used to generate a secret key from a plain-text
     * password.
     * 
     * @return A new salt buffer used to generate a secret key from a plain-text
     *         password; never {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    /* @NonNull */
    public byte[] createSalt()
        throws NetworkTableException
    {
        return createSecureRandomBytes( SALT_LENGTH );
    }

    /**
     * Creates a secret key for the specified key material.
     * 
     * @param password
     *        The plain-text password used to generate the secret key; must not
     *        be {@code null}.
     * @param salt
     *        The salt buffer used to generate the secret key; must not be
     *        {@code null}.
     * 
     * @return The secret key; never {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    /* @NonNull */
    private static SecretKey createSecretKey(
        /* @NonNull */
        final SecureString password,
        /* @NonNull */
        final byte[] salt )
        throws NetworkTableException
    {
        assert password != null;
        assert salt != null;

        final char[] passwordChars = getPasswordChars( password );
        try
        {
            final KeySpec keySpec = new PBEKeySpec( passwordChars, salt, ITERATION_COUNT, KEY_LENGTH );
            final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA1" ); //$NON-NLS-1$
            return secretKeyFactory.generateSecret( keySpec );
        }
        catch( final GeneralSecurityException e )
        {
            throw new NetworkTableException( Messages.Authenticator_createSecretKey_failed, e );
        }
        finally
        {
            SecureString.clearCharArray( passwordChars );
        }
    }

    /**
     * Creates a buffer filled with securely-generated random bytes.
     * 
     * @param length
     *        The buffer length; must not be negative.
     * 
     * @return A buffer filled with securely-generated random bytes; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    /* @NonNull */
    private static byte[] createSecureRandomBytes(
        final int length )
        throws NetworkTableException
    {
        assert length >= 0;

        try
        {
            final SecureRandom rng = SecureRandom.getInstance( "SHA1PRNG" ); //$NON-NLS-1$
            final byte[] buffer = new byte[ length ];
            rng.nextBytes( buffer );
            return buffer;
        }
        catch( final GeneralSecurityException e )
        {
            throw new NetworkTableException( Messages.Authenticator_createSecureRandomBytes_failed, e );
        }
    }

    /**
     * Gets the password as a character array.
     * 
     * @param password
     *        The password; must not be {@code null}.
     * 
     * @return The password as a character array; never {@code null}.
     */
    /* @NonNull */
    private static char[] getPasswordChars(
        /* @NonNull */
        final SecureString password )
    {
        assert password != null;

        return (password.length() > 0) ? password.toCharArray() : DEFAULT_PASSWORD.toCharArray();
    }
}
