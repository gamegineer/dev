/*
 * AbstractDialogTest.java
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
 * Created on Sep 14, 2010 at 9:43:16 PM.
 */

package org.gamegineer.common.ui.dialog;

import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.dialog.AbstractDialog} class.
 */
public final class AbstractDialogTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog under test in the fixture. */
    private AbstractDialog dialog_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDialogTest} class.
     */
    public AbstractDialogTest()
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
        dialog_ = new AbstractDialog( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code buttonPressed} method throws an exception when passed
     * a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testButtonPressed_Id_Null()
    {
        dialog_.buttonPressed( null );
    }

    /**
     * Ensures the {@code createButton} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateButton_Id_Null()
    {
        dialog_.createButton( new JPanel(), null, "label", false ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createButton} method throws an exception when passed a
     * {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateButton_Parent_Null()
    {
        dialog_.createButton( null, "id", "label", false ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code createButtonBar} method throws an exception when
     * passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateButtonBar_Parent_Null()
    {
        dialog_.createButtonBar( null );
    }

    /**
     * Ensures the {@code createButtonsForButtonBar} method throws an exception
     * when passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateButtonsForButtonBar_Parent_Null()
    {
        dialog_.createButtonsForButtonBar( null );
    }

    /**
     * Ensures the {@code createDialogArea} method throws an exception when
     * passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateDialogArea_Parent_Null()
    {
        dialog_.createDialogArea( null );
    }

    /**
     * Ensures the {@code getButton} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetButton_Id_Null()
    {
        dialog_.getButton( null );
    }

    /**
     * Ensures the {@code setButtonLayoutData} method throws an exception when
     * passed a {@code null} button.
     */
    @Test( expected = NullPointerException.class )
    public void testSetButtonLayoutData_Button_Null()
    {
        dialog_.setButtonLayoutData( null );
    }
}
