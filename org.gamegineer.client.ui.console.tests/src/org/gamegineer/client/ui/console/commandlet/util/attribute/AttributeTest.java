/*
 * AttributeTest.java
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
 * Created on May 18, 2009 at 10:21:39 PM.
 */

package org.gamegineer.client.ui.console.commandlet.util.attribute;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.commandlet.util.attribute.Attribute}
 * class.
 */
public final class AttributeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeTest} class.
     */
    public AttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * attribute name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AttributeName_Null()
    {
        new Attribute<Object>( null )
        {
            @Override
            protected Object getDefaultValue()
            {
                return new Object();
            }
        };
    }
}
