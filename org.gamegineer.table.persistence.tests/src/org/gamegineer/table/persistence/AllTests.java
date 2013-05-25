/*
 * AllTests.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Mar 13, 2012 at 8:58:53 PM.
 */

package org.gamegineer.table.persistence;

import org.gamegineer.table.internal.persistence.Activator;
import org.gamegineer.table.internal.persistence.TestsFragmentConstants;
import org.gamegineer.test.core.AllFragmentTests;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

/**
 * Defines a test suite for running all tests in the fragment.
 */
@RunWith( AllFragmentTests.class )
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
     * Gets the fragment name.
     * 
     * @return The fragment name; never {@code null}.
     */
    /* @NonNull */
    public static String getFragmentName()
    {
        return TestsFragmentConstants.SYMBOLIC_NAME;
    }

    /**
     * Gets the host bundle of the fragment.
     * 
     * @return The host bundle of the fragment; never {@code null}.
     */
    /* @NonNull */
    public static Bundle getHostBundle()
    {
        return Activator.getDefault().getBundleContext().getBundle();
    }
}
