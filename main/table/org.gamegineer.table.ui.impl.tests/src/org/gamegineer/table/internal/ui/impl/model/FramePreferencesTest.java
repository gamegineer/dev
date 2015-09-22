/*
 * FramePreferencesTest.java
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
 * Created on Aug 14, 2010 at 9:28:40 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link FramePreferences} class.
 */
public final class FramePreferencesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The frame preferences under test in the fixture. */
    private Optional<FramePreferences> framePreferences_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FramePreferencesTest} class.
     */
    public FramePreferencesTest()
    {
        framePreferences_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the frame preferences under test in the fixture.
     * 
     * @return The frame preferences under test in the fixture; never
     *         {@code null}.
     */
    private FramePreferences getFramePreferences()
    {
        return framePreferences_.get();
    }

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
        framePreferences_ = Optional.of( new FramePreferences() );
    }

    /**
     * Ensures the {@link FramePreferences#getLocation} method returns a copy of
     * the frame location.
     */
    @Test
    public void testGetLocation_ReturnValue_Copy()
    {
        final FramePreferences framePreferences = getFramePreferences();
        final Point expectedLocation = new Point( 0, 0 );
        framePreferences.setLocation( expectedLocation );

        final Point location = framePreferences.getLocation();
        assertNotNull( location );
        location.x = location.y = 1;
        final Point actualLocation = framePreferences.getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@link FramePreferences#getSize} method returns a copy of the
     * frame size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final FramePreferences framePreferences = getFramePreferences();
        final Dimension expectedSize = new Dimension( 0, 0 );
        framePreferences.setSize( expectedSize );

        final Dimension size = framePreferences.getSize();
        assertNotNull( size );
        size.width = size.height = 1;
        final Dimension actualSize = framePreferences.getSize();

        assertEquals( expectedSize, actualSize );
    }

    /**
     * Ensures the {@link FramePreferences#setLocation} method makes a copy of
     * the frame location.
     */
    @Test
    public void testSetLocation_Location_Copy()
    {
        final FramePreferences framePreferences = getFramePreferences();
        final Point location = new Point( 0, 0 );
        final Point expectedLocation = new Point( location );

        framePreferences.setLocation( location );
        location.x = location.y = 1;
        final Point actualLocation = framePreferences.getLocation();

        assertEquals( expectedLocation, actualLocation );
    }

    /**
     * Ensures the {@link FramePreferences#setSize} method makes a copy of the
     * frame size.
     */
    @Test
    public void testSetSize_Size_Copy()
    {
        final FramePreferences framePreferences = getFramePreferences();
        final Dimension size = new Dimension( 0, 0 );
        final Dimension expectedSize = new Dimension( size );

        framePreferences.setSize( size );
        size.width = size.height = 1;
        final Dimension actualSize = framePreferences.getSize();

        assertEquals( expectedSize, actualSize );
    }
}
