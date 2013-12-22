/*
 * AllTests.java
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
 * Created on Dec 21, 2013 at 9:00:15 PM.
 */

package org.gamegineer.cards.core.impl;

import junit.framework.Test;
import org.gamegineer.cards.internal.core.impl.Activator;
import org.gamegineer.cards.internal.core.impl.TestsFragmentConstants;
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
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a test suite consisting of all tests in the fragment.
     * 
     * @return A test suite consisting of all tests in the fragment; never
     *         {@code null}.
     */
    /* @NonNull */
    public static Test suite()
    {
        return BundleSuiteBuilder.suite( Activator.getDefault().getBundleContext().getBundle(), TestsFragmentConstants.SYMBOLIC_NAME );
    }
}