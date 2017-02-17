/*
 * ContainerContentChangedEventTest.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Mar 29, 2012 at 8:45:15 PM.
 */

package org.gamegineer.table.core;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the {@link ContainerContentChangedEvent} class.
 */
public final class ContainerContentChangedEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerContentChangedEventTest} class.
     */
    public ContainerContentChangedEventTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the
     * {@link ContainerContentChangedEvent#ContainerContentChangedEvent}
     * constructor throws an exception when passed an illegal component index
     * that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_ComponentIndex_Illegal_Negative()
    {
        new ContainerContentChangedEvent( EasyMock.createMock( IContainer.class ), ComponentPath.ROOT, EasyMock.createMock( IComponent.class ), -1 );
    }
}
