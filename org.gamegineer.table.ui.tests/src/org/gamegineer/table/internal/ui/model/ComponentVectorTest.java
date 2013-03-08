/*
 * ComponentVectorTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jan 16, 2013 at 8:45:24 PM.
 */

package org.gamegineer.table.internal.ui.model;

import org.easymock.EasyMock;
import org.gamegineer.table.core.IComponent;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.ComponentVector} class.
 */
public final class ComponentVectorTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentVectorTest} class.
     */
    public ComponentVectorTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentVector#ComponentVector} constructor throws an
     * exception when passed a {@code null} direction.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Direction_Null()
    {
        new ComponentVector( EasyMock.createMock( IComponent.class ), null );
    }

    /**
     * Ensures the {@link ComponentVector#ComponentVector} constructor throws an
     * exception when passed a {@code null} origin.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Origin_Null()
    {
        new ComponentVector( null, ComponentAxis.PRECEDING );
    }
}
