/*
 * ImageRegistryTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Sep 18, 2010 at 10:53:25 PM.
 */

package org.gamegineer.common.internal.ui;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ImageRegistry} class.
 */
public final class ImageRegistryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The image registry under test in the fixture. */
    private ImageRegistry imageRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ImageRegistryTest} class.
     */
    public ImageRegistryTest()
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
        imageRegistry_ = new ImageRegistry();
    }

    /**
     * Ensures the {@link ImageRegistry#getImage} method throws an exception
     * when passed a {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testGetImage_Path_Null()
    {
        imageRegistry_.getImage( null );
    }
}
