/*
 * SecureString.java
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
 * Created on Oct 28, 2010 at 11:03:29 PM.
 */

package org.gamegineer.common.core.security;

import java.util.Arrays;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An immutable sequence of characters that can be cleared from memory when they
 * are no longer to slightly improve application security.
 * 
 * <p>
 * Clients should call the {@link #dispose()} method as soon as the string is no
 * longer needed.
 * </p>
 * 
 * <p>
 * Although this class implements the equatable interface, instances of this
 * class should not be used as keys in hash containers.
 * </p>
 */
@ThreadSafe
public final class SecureString
    implements CharSequence
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The empty string value. */
    private static final char[] EMPTY_VALUE = new char[ 0 ];

    /** The value of the string. */
    @GuardedBy( "value_" )
    private final char[] value_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecureString} class with an
     * empty character sequence.
     */
    public SecureString()
    {
        this( EMPTY_VALUE );
    }

    /**
     * Initializes a new instance of the {@code SecureString} class from the
     * specified character array.
     * 
     * @param value
     *        The initial value of the string; must not be {@code null}.
     */
    public SecureString(
        final char[] value )
    {
        this( value, 0, value.length );
    }

    /**
     * Initializes a new instance of the {@code SecureString} class from the
     * specified range of the specified character array.
     * 
     * @param value
     *        The source of the initial value of the string; must not be
     *        {@code null}.
     * @param offset
     *        The offset to the first character in the string value.
     * @param length
     *        The length of the initial value of the string.
     * 
     * @throws java.lang.IndexOutOfBoundsException
     *         If {@code offset} and {@code length} are outside the bounds of
     *         {@code value}.
     */
    public SecureString(
        final char[] value,
        final int offset,
        final int length )
    {
        value_ = new char[ length ];
        System.arraycopy( value, offset, value_, 0, length );
    }

    /**
     * Initializes a new instance of the {@code SecureString} class from the
     * specified secure string.
     * 
     * @param secureString
     *        The secure string whose value will be used as the initial value of
     *        the string; must not be {@code null}.
     */
    public SecureString(
        final SecureString secureString )
    {
        value_ = secureString.toCharArray();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.CharSequence#charAt(int)
     */
    @Override
    public char charAt(
        final int index )
    {
        synchronized( value_ )
        {
            return value_[ index ];
        }
    }

    /**
     * Clears the contents of the specified character array.
     * 
     * @param value
     *        The character array; must not be {@code null}.
     */
    public static void clearCharArray(
        final char[] value )
    {
        Arrays.fill( value, '\0' );
    }

    /**
     * Disposes the secure string when it is no longer needed.
     * 
     * <p>
     * The contents of the secure string will be cleared from memory after this
     * method returns.
     * </p>
     */
    public void dispose()
    {
        synchronized( value_ )
        {
            clearCharArray( value_ );
        }
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final @Nullable Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof SecureString) )
        {
            return false;
        }

        final SecureString other = (SecureString)obj;
        synchronized( value_ )
        {
            synchronized( other.value_ )
            {
                return Arrays.equals( value_, other.value_ );
            }
        }
    }

    /*
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize()
        throws Throwable
    {
        try
        {
            dispose();
        }
        finally
        {
            super.finalize();
        }
    }

    /**
     * Creates a new instance of the {@code SecureString} class from the
     * specified character array.
     * 
     * <p>
     * The contents of the specified character array will be cleared from memory
     * after this method returns.
     * </p>
     * 
     * @param value
     *        The initial value of the string; must not be {@code null}.
     * 
     * @return A new instance of the {@code SecureString} class; never
     *         {@code null}.
     */
    public static SecureString fromCharArray(
        final char[] value )
    {
        final SecureString secureString = new SecureString( value );
        clearCharArray( value );
        return secureString;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        synchronized( value_ )
        {
            return Arrays.hashCode( value_ );
        }
    }

    /*
     * @see java.lang.CharSequence#length()
     */
    @Override
    public int length()
    {
        return value_.length;
    }

    /*
     * @see java.lang.CharSequence#subSequence(int, int)
     */
    @Override
    public CharSequence subSequence(
        final int start,
        final int end )
    {
        synchronized( value_ )
        {
            return new SecureString( value_, start, end - start + 1 );
        }
    }

    /**
     * Converts the contents of the secure string to a new character array.
     * 
     * <p>
     * It is recommended that the returned character array be cleared when it is
     * no longer needed.
     * </p>
     * 
     * @return A new character array that contains the contents of the secure
     *         string; never {@code null}.
     */
    public char[] toCharArray()
    {
        synchronized( value_ )
        {
            return value_.clone();
        }
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        synchronized( value_ )
        {
            return new String( value_ );
        }
    }
}
