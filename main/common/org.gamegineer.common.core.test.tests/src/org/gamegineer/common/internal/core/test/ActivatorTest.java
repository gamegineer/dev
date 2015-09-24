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
 * Created on Sep 26, 2013 at 10:34:50 PM.
 */

package org.gamegineer.common.internal.core.test;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Activator} class.
 */
public final class ActivatorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The activator under test in the fixture. */
    private Optional<Activator> activator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActivatorTest} class.
     */
    public ActivatorTest()
    {
        activator_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the activator under test in the fixture.
     * 
     * @return The activator under test in the fixture.
     */
    private Activator getActivator()
    {
        return activator_.get();
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
        activator_ = Optional.of( new Activator() );
    }

    /**
     * Ensures the {@link Activator#start} method throws an exception when
     * passed a {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testStart_BundleContext_Null()
    {
        getActivator().start( null );
    }

    /**
     * Ensures the {@link Activator#stop} method throws an exception when passed
     * a {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testStop_BundleContext_Null()
    {
        getActivator().stop( null );
    }
}
