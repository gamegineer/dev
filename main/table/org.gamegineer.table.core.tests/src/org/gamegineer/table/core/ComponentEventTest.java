/*
 * ComponentEventTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Mar 27, 2012 at 8:57:23 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertSame;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentEvent} class.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
public final class ComponentEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component event under test in the fixture. */
    private ComponentEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentEventTest} class.
     */
    public ComponentEventTest()
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
        event_ = new ComponentEvent( EasyMock.createMock( IComponent.class ), new ComponentPath( null, 0 ) );
    }

    /**
     * Ensures the {@link ComponentEvent#getSource} method returns the same
     * instance as the {@link ComponentEvent#getComponent} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameComponent()
    {
        assertSame( event_.getComponent(), event_.getSource() );
    }
}
