/*
 * ActivatorTest.java
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
 * Created on Nov 23, 2012 at 9:41:56 PM.
 */

package org.gamegineer.cards.internal.persistence;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Activator} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class ActivatorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The activator under test in the fixture. */
    private Activator activator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActivatorTest} class.
     */
    public ActivatorTest()
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
        activator_ = new Activator();
    }

    /**
     * Ensures the {@link Activator#start} method throws an exception when
     * passed a {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testStart_BundleContext_Null()
    {
        activator_.start( null );
    }

    /**
     * Ensures the {@link Activator#stop} method throws an exception when passed
     * a {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testStop_BundleContext_Null()
    {
        activator_.stop( null );
    }
}
