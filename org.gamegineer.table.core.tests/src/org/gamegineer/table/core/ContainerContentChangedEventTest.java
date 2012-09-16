/*
 * ContainerContentChangedEventTest.java
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
 * Created on Mar 29, 2012 at 8:45:15 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.ContainerContentChangedEvent} class.
 */
public final class ContainerContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container content changed event under test in the fixture. */
    private ContainerContentChangedEvent event_;


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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        event_ = new ContainerContentChangedEvent( EasyMock.createMock( IContainer.class ), EasyMock.createMock( IComponent.class ), 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Component_Null()
    {
        new ContainerContentChangedEvent( EasyMock.createMock( IContainer.class ), null, 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed an illegal
     * component index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_ComponentIndex_Illegal_Negative()
    {
        new ContainerContentChangedEvent( EasyMock.createMock( IContainer.class ), EasyMock.createMock( IComponent.class ), -1 );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new ContainerContentChangedEvent( null, EasyMock.createMock( IComponent.class ), 0 );
    }

    /**
     * Ensures the {@link ContainerContentChangedEvent#getComponent} method does
     * not return {@code null}.
     */
    @Test
    public void testGetComponent_ReturnValue_NonNull()
    {
        assertNotNull( event_.getComponent() );
    }
}
