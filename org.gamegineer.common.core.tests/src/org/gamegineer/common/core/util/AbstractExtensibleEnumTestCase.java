/*
 * AbstractExtensibleEnumTestCase.java
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
 * Created on Apr 3, 2012 at 9:03:42 PM.
 */

package org.gamegineer.common.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.core.util.ExtensibleEnum} class.
 */
public abstract class AbstractExtensibleEnumTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractExtensibleEnumTestCase}
     * class.
     */
    protected AbstractExtensibleEnumTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the enum type under test in the fixture.
     * 
     * @return The enum type under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected abstract Class<? extends ExtensibleEnum> getType();

    /**
     * Ensures the ordinal of each enum constant matches the order in which it
     * was declared in the subclass.
     */
    @Test
    public void testOrdinals()
    {
        final ExtensibleEnum[] values = ExtensibleEnum.values( getType() );
        for( int expectedOrdinal = 0; expectedOrdinal < values.length; ++expectedOrdinal )
        {
            assertEquals( expectedOrdinal, values[ expectedOrdinal ].ordinal() );
        }
    }

    /**
     * Ensures the {@code valueOf} method returns the correct value for each
     * name supported by the enum type.
     */
    @Test
    public void testValueOf()
    {
        final ExtensibleEnum[] values = ExtensibleEnum.values( getType() );
        for( int index = 0; index < values.length; ++index )
        {
            assertSame( values[ index ], ExtensibleEnum.valueOf( getType(), values[ index ].name() ) );
        }
    }
}
