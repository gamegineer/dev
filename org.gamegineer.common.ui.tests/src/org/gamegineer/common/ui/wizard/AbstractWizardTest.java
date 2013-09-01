/*
 * AbstractWizardTest.java
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
 * Created on Sep 24, 2010 at 8:43:28 PM.
 */

package org.gamegineer.common.ui.wizard;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.wizard.AbstractWizard} class.
 */
public final class AbstractWizardTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The wizard under test in the fixture. */
    private AbstractWizard wizard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizardTest} class.
     */
    public AbstractWizardTest()
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
        wizard_ = new AbstractWizard()
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@link AbstractWizard#addPage} method throws an exception
     * when passed a {@code null} page.
     */
    @Test( expected = NullPointerException.class )
    public void testAddPage_Page_Null()
    {
        wizard_.addPage( null );
    }
}
