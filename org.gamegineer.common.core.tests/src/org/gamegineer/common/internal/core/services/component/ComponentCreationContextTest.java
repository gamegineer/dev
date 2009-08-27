/*
 * ComponentCreationContextTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Apr 10, 2008 at 10:57:41 PM.
 */

package org.gamegineer.common.internal.core.services.component;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.component.ComponentCreationContext}
 * class.
 */
public final class ComponentCreationContextTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentCreationContextTest}
     * class.
     */
    public ComponentCreationContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * attribute map.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_AttributeMap_Null()
    {
        new ComponentCreationContext( null );
    }

    /**
     * Ensures the constructor throws an exception when passed an attribute map
     * which contains one or more {@code null} values.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_AttributeMap_NullValues()
    {
        final Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributeMap.put( "name2", null ); //$NON-NLS-1$
        attributeMap.put( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        new ComponentCreationContext( attributeMap );
    }
}
