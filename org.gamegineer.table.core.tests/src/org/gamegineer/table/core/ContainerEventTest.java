/*
 * ContainerEventTest.java
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
 * Created on May 3, 2013 at 8:36:21 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.ContainerEvent}
 * class.
 */
public final class ContainerEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container event under test in the fixture. */
    private ContainerEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerEventTest} class.
     */
    public ContainerEventTest()
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
        event_ = new ContainerEvent( EasyMock.createMock( IContainer.class ), new ComponentPath( null, 0 ) );
    }

    /**
     * Ensures the {@link ContainerEvent#ContainerEvent} constructor throws an
     * exception when passed a {@code null} source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new ContainerEvent( null, new ComponentPath( null, 0 ) );
    }

    /**
     * Ensures the {@link ContainerEvent#getContainer} method does not return
     * {@code null}.
     */
    @Test
    public void testGetContainer_ReturnValue_NonNull()
    {
        assertNotNull( event_.getContainer() );
    }

    /**
     * Ensures the {@link ContainerEvent#getSource} method returns the same
     * instance as the {@link ContainerEvent#getContainer} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameContainer()
    {
        assertSame( event_.getContainer(), event_.getSource() );
    }
}
