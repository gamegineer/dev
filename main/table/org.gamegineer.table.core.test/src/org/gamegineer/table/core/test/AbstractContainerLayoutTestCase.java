/*
 * AbstractContainerLayoutTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

import static org.junit.Assert.assertNotNull;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.IContainerLayout;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IContainerLayout} interface.
 */
@NonNullByDefault( false )
public abstract class AbstractContainerLayoutTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container layout under test in the fixture. */
    private IContainerLayout layout_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayoutTestCase}
     * class.
     */
    protected AbstractContainerLayoutTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container layout to be tested.
     * 
     * @return The container layout to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract IContainerLayout createContainerLayout()
        throws Exception;

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
        layout_ = createContainerLayout();
        assertNotNull( layout_ );
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
