/*
 * StringToPrimitiveIntegerConverter.java
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
 * Created on Oct 21, 2010 at 11:16:46 PM.
 */

package org.gamegineer.table.internal.ui.impl.databinding.conversion;

import net.jcip.annotations.Immutable;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A data binding converter from values of type {@link String} to values of type
 * {@link Integer#TYPE}.
 */
@Immutable
public final class StringToPrimitiveIntegerConverter
    extends Converter
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StringToPrimitiveIntegerConverter} class.
     */
    public StringToPrimitiveIntegerConverter()
    {
        super( String.class, int.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.Object)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public @Nullable Object convert(
        final @Nullable Object fromObject )
    {
        return Integer.parseInt( (String)fromObject );
    }
}
