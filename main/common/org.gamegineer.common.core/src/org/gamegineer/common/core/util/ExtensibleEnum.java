/*
 * ExtensibleEnum.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Apr 3, 2012 at 8:17:25 PM.
 */

package org.gamegineer.common.core.util;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An extensible type-safe enumeration.
 */
@Immutable
public abstract class ExtensibleEnum
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 307302906593482145L;

    /** The name of the enum constant. */
    private final String name_;

    /** The ordinal of the enum constant. */
    private final int ordinal_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensibleEnum} class.
     * 
     * @param name
     *        The name of the enum constant.
     * @param ordinal
     *        The ordinal of the enum constant.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code ordinal} is negative.
     */
    protected ExtensibleEnum(
        final String name,
        final int ordinal )
    {
        assertArgumentLegal( ordinal >= 0, "ordinal", NonNlsMessages.ExtensibleEnum_ordinal_outOfRange( ordinal ) ); //$NON-NLS-1$

        name_ = name;
        ordinal_ = ordinal;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#clone()
     */
    @Override
    protected final Object clone()
        throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(
        final @Nullable Object obj )
    {
        return super.equals( obj );
    }

    /*
     * @see java.lang.Object#finalize()
     */
    @Override
    protected final void finalize()
    {
        // do nothing
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        return super.hashCode();
    }

    /**
     * Gets the name of the enum constant.
     * 
     * @return The name of the enum constant.
     */
    public final String name()
    {
        return name_;
    }

    /**
     * Gets the ordinal of the enum constant.
     * 
     * @return The ordinal of the enum constant.
     */
    public final int ordinal()
    {
        return ordinal_;
    }

    /**
     * Allows the persistence framework to resolve this object with an
     * equivalent object after it is deserialized.
     * 
     * <p>
     * The purpose of this method is to ensure every enum constant deserialized
     * is resolved to a singleton instance.
     * </p>
     * 
     * @return The alternate object that resolves this object.
     * 
     * @throws java.io.ObjectStreamException
     *         If an error occurs.
     */
    protected final Object readResolve()
        throws ObjectStreamException
    {
        final List<@NonNull ?> values = ExtensibleEnum.values( getClass() );
        final int ordinal = ordinal();
        if( (ordinal < 0) || (ordinal >= values.size()) )
        {
            throw new InvalidObjectException( NonNlsMessages.ExtensibleEnum_ordinal_outOfRange( ordinal ) );
        }

        return values.get( ordinal );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name_;
    }

    /**
     * Gets the enum constant of the specified type with the specified name.
     * 
     * @param <T>
     *        The enum type.
     * 
     * @param type
     *        The enum type.
     * @param name
     *        The name of the enum constant.
     * 
     * @return The enum constant of the specified type with the specified name.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the specified enum type has no constant with the specified
     *         name.
     */
    public static <T extends ExtensibleEnum> T valueOf(
        final Class<T> type,
        final String name )
    {
        for( final T value : values( type ) )
        {
            if( name.equals( value.name() ) )
            {
                return value;
            }
        }

        throw new IllegalArgumentException( NonNlsMessages.ExtensibleEnum_valueOf_nameIllegal( type, name ) );
    }

    /**
     * Gets the collection of values associated with the specified enum type.
     * 
     * @param <T>
     *        The enum type.
     * 
     * @param type
     *        The enum type.
     * 
     * @return The collection of values associated with the specified enum type.
     */
    public static <T extends ExtensibleEnum> List<T> values(
        final Class<T> type )
    {
        final List<T> values = new ArrayList<>();

        for( final Field field : type.getDeclaredFields() )
        {
            final int modifiers = field.getModifiers();
            if( Modifier.isStatic( modifiers ) //
                && Modifier.isFinal( modifiers ) //
                && !Modifier.isPrivate( modifiers ) //
                && ExtensibleEnum.class.isAssignableFrom( field.getType() ) )
            {
                field.setAccessible( true );

                try
                {
                    values.add( type.cast( field.get( null ) ) );
                }
                catch( final IllegalAccessException e )
                {
                    throw new AssertionError( e );
                }
            }
        }

        return values;
    }
}
