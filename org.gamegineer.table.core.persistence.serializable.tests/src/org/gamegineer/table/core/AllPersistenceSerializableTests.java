/*
 * AllPersistenceSerializableTests.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Sep 15, 2009 at 11:30:37 PM.
 */

package org.gamegineer.table.core;

import junit.framework.Test;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.PersistenceSerializableTestsFragmentConstants;
import org.gamegineer.test.core.BundleSuiteBuilder;

/**
 * Defines a test suite for running all tests in the fragment.
 */
public final class AllPersistenceSerializableTests
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AllPersistenceSerializableTests}
     * class.
     */
    public AllPersistenceSerializableTests()
    {
        super();
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
        return BundleSuiteBuilder.suite( Activator.getDefault().getBundleContext().getBundle(), PersistenceSerializableTestsFragmentConstants.SYMBOLIC_NAME );
    }
}
