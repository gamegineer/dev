/*
 * MainModelEventTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 13, 2010 at 10:06:26 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.easymock.EasyMock;
import org.gamegineer.table.ui.ITableAdvisor;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModelEvent} class.
 */
public final class MainModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model event under test in the fixture. */
    private MainModelEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelEventTest} class.
     */
    public MainModelEventTest()
    {
        super();
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
        event_ = new MainModelEvent( new MainModel( EasyMock.createMock( ITableAdvisor.class ) ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new MainModelEvent( null );
    }

    /**
     * Ensures the {@code getMainModel} method does not return {@code null}.
     */
    @Test
    public void testGetMainModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getMainModel() );
    }

    /**
     * Ensures the {@code getSource} method returns the same instance as the
     * {@code getMainModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameMainModel()
    {
        assertSame( event_.getMainModel(), event_.getSource() );
    }
}
