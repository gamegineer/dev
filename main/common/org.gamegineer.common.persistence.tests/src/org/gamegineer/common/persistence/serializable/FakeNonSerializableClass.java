/*
 * FakeNonSerializableClass.java
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
 * Created on Jun 21, 2008 at 9:44:49 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.util.Objects;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A fake non-serializable class used for testing the object serialization
 * streams.
 * 
 * <p>
 * This class is non-serializable because a) it does not implement
 * {@code Serializable}, b) it is immutable, and c) it does not define a default
 * constructor.
 * </p>
 */
@Immutable
public final class FakeNonSerializableClass
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The integer field. */
    private final int intField_;

    /** The string field. */
    private final @Nullable String stringField_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeNonSerializableClass} class.
     * 
     * @param intField
     *        The integer field.
     * @param stringField
     *        The string field.
     */
    public FakeNonSerializableClass(
        final int intField,
        final @Nullable String stringField )
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
        final @Nullable Object obj )
    {
        if( obj == this )
        {
            return true;
        }

        if( !(obj instanceof FakeNonSerializableClass) )
        {
            return false;
        }

        final FakeNonSerializableClass other = (FakeNonSerializableClass)obj;
        return (intField_ == other.intField_) && Objects.equals( stringField_, other.stringField_ );
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
     * @return The string field.
     */
    public @Nullable String getStringField()
    {
        return stringField_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    @SuppressWarnings( "boxing" )
    public int hashCode()
    {
        return Objects.hash( intField_, stringField_ );
    }
}
