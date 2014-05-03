/*
 * Converters.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Oct 21, 2010 at 10:27:44 PM.
 */

package org.gamegineer.table.internal.ui.impl.databinding.conversion;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of useful methods for working with data binding converters.
 */
@NotThreadSafe
public final class Converters
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A data binding converter from values of type {@link Integer#TYPE} to
     * values of type {@link String}.
     */
    @Nullable
    private static IConverter primitiveIntegerToStringConverter_ = null;

    /**
     * A data binding converter from values of type {@link String} to values of
     * type {@link Integer#TYPE}
     */
    @Nullable
    private static IConverter stringToPrimitiveIntegerConverter_ = null;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Converters} class.
     */
    private Converters()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a data binding converter from values of type {@link Integer#TYPE} to
     * values of type {@link String}.
     * 
     * @return A data binding converter from values of type {@link Integer#TYPE}
     *         to values of type {@link String}; never {@code null}.
     */
    public static IConverter getPrimitiveIntegerToStringConverter()
    {
        if( primitiveIntegerToStringConverter_ == null )
        {
            primitiveIntegerToStringConverter_ = new PrimitiveIntegerToStringConverter();
        }

        assert primitiveIntegerToStringConverter_ != null;
        return primitiveIntegerToStringConverter_;
    }

    /**
     * Gets a data binding converter from values of type {@link String} to
     * values of type {@link Integer#TYPE}.
     * 
     * @return A data binding converter from values of type {@link String} to
     *         values of type {@link Integer#TYPE}; never {@code null}.
     */
    public static IConverter getStringToPrimitiveIntegerConverter()
    {
        if( stringToPrimitiveIntegerConverter_ == null )
        {
            stringToPrimitiveIntegerConverter_ = new StringToPrimitiveIntegerConverter();
        }

        assert stringToPrimitiveIntegerConverter_ != null;
        return stringToPrimitiveIntegerConverter_;
    }

    /**
     * Decorates the specified converter such that any exception thrown during
     * the conversion process will be replaced with an
     * {@link IllegalArgumentException} having the specified message.
     * 
     * @param converter
     *        The converter to be decorated; must not be {@code null}.
     * @param exceptionMessage
     *        The replacement exception message; must not be {@code null}.
     * 
     * @return The decorated converter; never {@code null}.
     */
    public static IConverter withExceptionMessage(
        final IConverter converter,
        final String exceptionMessage )
    {
        @SuppressWarnings( "null" )
        final IConverter decorator = new Converter( converter.getFromType(), converter.getToType() )
        {
            @Nullable
            @Override
            public Object convert(
                @Nullable
                final Object fromObject )
            {
                try
                {
                    return converter.convert( fromObject );
                }
                catch( final RuntimeException e )
                {
                    throw new IllegalArgumentException( exceptionMessage, e );
                }
            }
        };
        return decorator;
    }
}
