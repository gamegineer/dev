/*
 * AbstractDialogPageTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Sep 23, 2010 at 10:43:59 PM.
 */

package org.gamegineer.common.ui.dialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.dialog.AbstractDialogPage} class.
 */
public final class AbstractDialogPageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog page under test in the fixture. */
    private AbstractDialogPage dialogPage_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDialogPageTest} class.
     */
    public AbstractDialogPageTest()
    {
        super();
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
        dialogPage_ = new AbstractDialogPage()
        {
            // no overrides
        };
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        dialogPage_ = null;
    }

    /**
     * Ensures the {@code createDialogPageArea} method throws an exception when
     * passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateDialogPageArea_Parent_Null()
    {
        dialogPage_.createDialogPageArea( null );
    }

    /**
     * Ensures the {@code setButtonLayoutData} method throws an exception when
     * passed a {@code null} button.
     */
    @Test( expected = NullPointerException.class )
    public void testSetButtonLayoutData_Button_Null()
    {
        dialogPage_.setButtonLayoutData( null );
    }
}
