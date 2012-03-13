/*
 * BundleImagesTest.java
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
 * Created on Sep 22, 2011 at 11:17:25 PM.
 */

package org.gamegineer.table.internal.ui;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.BundleImages} class.
 */
public final class BundleImagesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The bundle image manager under test in the fixture. */
    private BundleImages bundleImages_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleImagesTest} class.
     */
    public BundleImagesTest()
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
        bundleImages_ = new BundleImages( EasyMock.createMock( BundleContext.class ) );
    }

    /**
     * Ensures the {@code getIcon} method throws an exception when passed a
     * {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testGetIcon_Path_Null()
    {
        bundleImages_.getIcon( null );
    }

    /**
     * Ensures the {@code getImage} method throws an exception when passed a
     * {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testGetImage_Path_Null()
    {
        bundleImages_.getImage( null );
    }
}
