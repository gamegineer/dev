/*
 * ComponentStrategyIdProxyTest.java
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
 * Created on Aug 4, 2012 at 11:03:17 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.ComponentStrategyIdProxy}
 * class.
 */
public final class ComponentStrategyIdProxyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyIdProxyTest}
     * class.
     */
    public ComponentStrategyIdProxyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentStrategyIdProxy#ComponentStrategyIdProxy}
     * constructor throws an exception when passed a {@code null} component
     * strategy identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_ComponentStrategyId_Null()
    {
        new ComponentStrategyIdProxy( null );
    }
}
