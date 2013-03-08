/*
 * ContainerLayoutIdProxyTest.java
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
 * Created on Aug 11, 2012 at 8:27:39 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.ContainerLayoutIdProxy}
 * class.
 */
public final class ContainerLayoutIdProxyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutIdProxyTest}
     * class.
     */
    public ContainerLayoutIdProxyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ContainerLayoutIdProxy#ContainerLayoutIdProxy}
     * constructor throws an exception when passed a {@code null} container
     * layout identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_ContainerLayoutId_Null()
    {
        new ContainerLayoutIdProxy( null );
    }
}
