/*
 * ConvertersTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 21, 2010 at 10:38:34 PM.
 */

package org.gamegineer.table.internal.ui.databinding.conversion;

import org.easymock.EasyMock;
import org.eclipse.core.databinding.conversion.IConverter;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.databinding.conversion.Converters}
 * class.
 */
public final class ConvertersTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConvertersTest} class.
     */
    public ConvertersTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code withExceptionMessage} method throws an exception when
     * passed a {@code null} converter.
     */
    @Test( expected = NullPointerException.class )
    public void testWithExceptionMessage_Converter_Null()
    {
        Converters.withExceptionMessage( null, "message" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code withExceptionMessage} method throws an exception when
     * passed a {@code null} exception message.
     */
    @Test( expected = NullPointerException.class )
    public void testWithExceptionMessage_ExceptionMessage_Null()
    {
        Converters.withExceptionMessage( EasyMock.createMock( IConverter.class ), null );
    }
}
