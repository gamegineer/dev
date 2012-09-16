/*
 * FramePreferencesTest.java
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
 * Created on Aug 14, 2010 at 9:28:40 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertEquals;
import java.awt.Dimension;
import java.awt.Point;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.FramePreferences} class.
 */
public final class FramePreferencesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The frame preferences under test in the fixture. */
    private FramePreferences framePreferences_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FramePreferencesTest} class.
     */
    public FramePreferencesTest()
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
        framePreferences_ = new FramePreferences();
    }

    /**
     * Ensures the {@link FramePreferences#getLocation} method returns a copy of
     * the frame location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final Point expectedLocation = new Point( 0, 0 );
        framePreferences_.setLocation( expectedLocation );

        final Point location = framePreferences_.getLocation();
        location.x = location.y = 1;
        final Point actualLocation = framePreferences_.getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@link FramePreferences#getSize} method returns a copy of the
     * frame size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension expectedSize = new Dimension( 0, 0 );
        framePreferences_.setSize( expectedSize );

        final Dimension size = framePreferences_.getSize();
        size.width = size.height = 1;
        final Dimension actualSize = framePreferences_.getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@link FramePreferences#setLocation} method makes a copy of
     * the frame location.
     */
    @Test
    public void testSetLocation_Location_Copy()
    {
        final Point location = new Point( 0, 0 );
        final Point expectedLocation = new Point( location );

        framePreferences_.setLocation( location );
        location.x = location.y = 1;
        final Point actualLocation = framePreferences_.getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@link FramePreferences#setSize} method makes a copy of the
     * frame size.
     */
    @Test
    public void testSetSize_Size_Copy()
    {
        final Dimension size = new Dimension( 0, 0 );
        final Dimension expectedSize = new Dimension( size );

        framePreferences_.setSize( size );
        size.width = size.height = 1;
        final Dimension actualSize = framePreferences_.getSize();

        assertEquals( expectedSize, actualSize );
    }
}
