/*
 * AllTests.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 5, 2008 at 10:02:30 PM.
 */

package org.gamegineer.game.core;

import junit.framework.Test;
import org.gamegineer.game.internal.core.Activator;
import org.gamegineer.test.core.BundleSuiteBuilder;

/**
 * Defines a test suite for running all tests in the fragment.
 */
public final class AllTests
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AllTests} class.
     */
    public AllTests()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a test suite consisting of all tests in the fragment.
     * 
     * @return A test suite consisting of all tests in the fragment.
     */
    public static Test suite()
    {
        return BundleSuiteBuilder.suite( Activator.getDefault().getBundleContext().getBundle(), "org.gamegineer.game.core.tests" ); //$NON-NLS-1$
    }
}
