/*
 * AbstractHelpSystemTestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Jan 4, 2012 at 8:30:56 PM.
 */

package org.gamegineer.common.ui.help;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.ui.help.IHelpSystem} interface.
 */
public abstract class AbstractHelpSystemTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The help system under test in the fixture. */
    private IHelpSystem helpSystem_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractHelpSystemTestCase}
     * class.
     */
    protected AbstractHelpSystemTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the help system to be tested.
     * 
     * @return The help system to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IHelpSystem createHelpSystem()
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
        helpSystem_ = createHelpSystem();
        assertNotNull( helpSystem_ );
    }

    /**
     * A dummy test until testable functionality is added to the interface.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
