/*
 * ContainerLayoutFactoryTest.java
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
 * Created on May 15, 2012 at 8:29:36 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.ContainerLayoutFactory} class.
 */
public final class ContainerLayoutFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutFactoryTest}
     * class.
     */
    public ContainerLayoutFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code fromId} method throws an exception when passed an
     * illegal identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromId_Id_Illegal()
    {
        ContainerLayoutFactory.fromId( "id" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code fromId} method returns the correct container layout
     * when passed a legal identifier.
     */
    @Test
    public void testFromId_Id_Legal()
    {
        final IContainerLayout[] layouts = new IContainerLayout[] {
            ContainerLayoutFactory.createAbsoluteLayout(), //
            ContainerLayoutFactory.createAccordianDownLayout(), //
            ContainerLayoutFactory.createAccordianLeftLayout(), //
            ContainerLayoutFactory.createAccordianRightLayout(), //
            ContainerLayoutFactory.createAccordianUpLayout(), //
            ContainerLayoutFactory.createStackedLayout()
        };

        for( final IContainerLayout layout : layouts )
        {
            assertEquals( layout, ContainerLayoutFactory.fromId( ContainerLayoutFactory.getId( layout ) ) );
        }
    }

    /**
     * Ensures the {@code fromId} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testFromId_Id_Null()
    {
        ContainerLayoutFactory.fromId( null );
    }

    /**
     * Ensures the {@code getId} method throws an exception when passed an
     * illegal layout.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetId_Layout_Illegal()
    {
        ContainerLayoutFactory.getId( EasyMock.createMock( IContainerLayout.class ) );
    }

    /**
     * Ensures the {@code getId} method throws an exception when passed a
     * {@code null} layout.
     */
    @Test( expected = NullPointerException.class )
    public void testGetId_Layout_Null()
    {
        ContainerLayoutFactory.getId( null );
    }
}
