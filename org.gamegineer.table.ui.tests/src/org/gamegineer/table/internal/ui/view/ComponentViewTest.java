/*
 * ComponentViewTest.java
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
 * Created on Oct 15, 2009 at 10:44:54 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Rectangle;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.ComponentView} class.
 */
public final class ComponentViewTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A component model for use in the test fixture. */
    private ComponentModel componentModel_;

    /** The component view under test in the fixture. */
    private ComponentView componentView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentViewTest} class.
     */
    public ComponentViewTest()
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
        componentModel_ = new ComponentModel( TableEnvironmentFactory.createTableEnvironment().createCard() );
        componentView_ = new ComponentView( componentModel_ );
    }

    /**
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = componentView_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, componentView_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( componentView_.getBounds() );
    }
}
