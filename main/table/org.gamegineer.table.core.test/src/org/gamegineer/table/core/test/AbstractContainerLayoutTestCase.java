/*
 * AbstractContainerLayoutTestCase.java
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
 * Created on May 5, 2012 at 9:04:16 PM.
 */

package org.gamegineer.table.core.test;

import java.util.Optional;
import org.gamegineer.table.core.IContainerLayout;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IContainerLayout} interface.
 */
public abstract class AbstractContainerLayoutTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout under test in the fixture. */
    private Optional<IContainerLayout> containerLayout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayoutTestCase}
     * class.
     */
    protected AbstractContainerLayoutTestCase()
    {
        containerLayout_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container layout to be tested.
     * 
     * @return The container layout to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IContainerLayout createContainerLayout()
        throws Exception;

    /**
     * Gets the container layout under test in the fixture.
     * 
     * @return The container layout under test in the fixture.
     */
    protected final IContainerLayout getContainerLayout()
    {
        return containerLayout_.get();
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
        containerLayout_ = Optional.of( createContainerLayout() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
