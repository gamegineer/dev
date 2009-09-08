/*
 * MockNonSerializableClass.java
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
 * Created on Jun 21, 2008 at 9:44:49 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

/**
 * A mock non-serializable class used for testing the object serialization
 * streams.
 * 
 * <p>
 * This class is non-serializable because a) it does not implement {@code
 * Serializable}, b) it is immutable, and c) it does not define a default
 * constructor.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class MockNonSerializableClass
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The integer field. */
    private final int intField_;

    /** The string field. */
    private final String stringField_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockNonSerializableClass} class.
     * 
     * @param intField
     *        The integer field.
     * @param stringField
     *        The string field; may be {@code null}.
     */
    public MockNonSerializableClass(
        final int intField,
        /* @Nullable */
        final String stringField )
    {
        intField_ = intField;
        stringField_ = stringField;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( obj == this )
        {
            return true;
        }

        if( !(obj instanceof MockNonSerializableClass) )
        {
            return false;
        }

        final MockNonSerializableClass other = (MockNonSerializableClass)obj;
        return (intField_ == other.intField_) && stringField_.equals( other.stringField_ );
    }

    /**
     * Gets the integer field.
     * 
     * @return The integer field.
     */
    public int getIntField()
    {
        return intField_;
    }

    /**
     * Gets the string field.
     * 
     * @return The string field; may be {@code null}.
     */
    /* @Nullable */
    public String getStringField()
    {
        return stringField_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + intField_;
        result = result * 31 + stringField_.hashCode();
        return result;
    }
}
