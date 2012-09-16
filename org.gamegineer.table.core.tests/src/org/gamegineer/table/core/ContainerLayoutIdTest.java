/*
 * ContainerLayoutIdTest.java
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
 * Created on Aug 8, 2012 at 7:50:20 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.ContainerLayoutId}
 * class.
 */
public final class ContainerLayoutIdTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutIdTest} class.
     */
    public ContainerLayoutIdTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ContainerLayoutId#fromString} method throws an
     * exception when passed a {@code null} underlying identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testFromString_Id_Null()
    {
        ContainerLayoutId.fromString( null );
    }

    /**
     * Ensures the {@link ContainerLayoutId#toString} method does not return
     * {@code null}.
     */
    @Test
    public void testToString_ReturnValue_NonNull()
    {
        final ContainerLayoutId id = ContainerLayoutId.fromString( "id" ); //$NON-NLS-1$

        assertNotNull( id.toString() );
    }

    /**
     * Ensures the {@link ContainerLayoutId#toString} method returns the same
     * underlying identifier that was used to construct the container layout
     * identifier.
     */
    @Test
    public void testToString_ReturnValue_SameId()
    {
        final String expectedId = "id"; //$NON-NLS-1$
        final ContainerLayoutId id = ContainerLayoutId.fromString( expectedId );

        final String actualId = id.toString();

        assertEquals( expectedId, actualId );
    }
}
