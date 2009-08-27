/*
 * AllTests.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Mar 4, 2009 at 11:10:27 PM.
 */

package org.gamegineer.tictactoe.ui;

import junit.framework.Test;
import org.gamegineer.test.core.BundleSuiteBuilder;
import org.gamegineer.tictactoe.internal.ui.Activator;

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
        return BundleSuiteBuilder.suite( Activator.getDefault().getBundleContext().getBundle(), "org.gamegineer.tictactoe.ui.tests" ); //$NON-NLS-1$
    }
}
