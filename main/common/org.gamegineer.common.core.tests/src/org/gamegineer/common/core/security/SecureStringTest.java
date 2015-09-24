/*
 * SecureStringTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Oct 30, 2010 at 4:14:41 PM.
 */

package org.gamegineer.common.core.security;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link SecureString} class.
 */
public final class SecureStringTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The secure string under test in the fixture. */
    private Optional<SecureString> secureString_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecureStringTest} class.
     */
    public SecureStringTest()
    {
        secureString_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the secure string under test in the fixture.
     * 
     * @return The secure string under test in the fixture.
     */
    private SecureString getSecureString()
    {
        return secureString_.get();
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
        secureString_ = Optional.of( new SecureString( "password".toCharArray() ) ); //$NON-NLS-1$
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
        getSecureString().dispose();
    }

    /**
     * Ensures the {@link SecureString#charAt} method throws an exception when
     * the index is out of bounds.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testCharAt_Index_OutOfBounds()
    {
        getSecureString().charAt( -1 );
    }

    /**
     * Ensures the {@link SecureString#clearCharArray} clears the value.
     */
    @Test
    public void testClearCharArray_Value_Cleared()
    {
        final char[] value = "password".toCharArray(); //$NON-NLS-1$
        final char[] expectedValue = "\0\0\0\0\0\0\0\0".toCharArray(); //$NON-NLS-1$

        SecureString.clearCharArray( value );

        assertTrue( Arrays.equals( expectedValue, value ) );
    }

    /**
     * Ensures the {@link SecureString#SecureString(char[], int, int)}
     * constructor throws an exception when passed a length that is out of
     * bounds.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testConstructorFromValueAndOffsetAndLength_Length_OutOfBounds()
    {
        new SecureString( "password".toCharArray(), 0, 100 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link SecureString#SecureString(char[], int, int)}
     * constructor throws an exception when passed an offset that is out of
     * bounds.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testConstructorFromValueAndOffsetAndLength_Offset_OutOfBounds()
    {
        new SecureString( "password".toCharArray(), -1, 1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link SecureString#subSequence} method throws an exception
     * when the end index is out of bounds.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testSubSequence_End_OutOfBounds()
    {
        final SecureString secureString = getSecureString();

        secureString.subSequence( 0, secureString.length() + 1 );
    }

    /**
     * Ensures the {@link SecureString#subSequence} method throws an exception
     * when the start index is out of bounds.
     */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testSubSequence_Start_OutOfBounds()
    {
        final SecureString secureString = getSecureString();

        secureString.subSequence( -1, secureString.length() + 1 );
    }
}
