/*
 * AbstractDialogPageTestCase.java
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
 * Created on Sep 23, 2010 at 10:33:44 PM.
 */

package org.gamegineer.common.ui.dialog;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.ui.dialog.IDialogPage} class.
 */
public abstract class AbstractDialogPageTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog page under test in the fixture. */
    private IDialogPage dialogPage_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDialogPageTestCase}
     * class.
     */
    protected AbstractDialogPageTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the dialog page to be tested.
     * 
     * @return The dialog page to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IDialogPage createDialogPage()
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
        dialogPage_ = createDialogPage();
        assertNotNull( dialogPage_ );
    }

    /**
     * Ensures the {@link AbstractDialogPage#create} method throws an exception
     * when passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreate_Parent_Null()
    {
        dialogPage_.create( null );
    }
}
