/*
 * AbstractWindowTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Sep 12, 2010 at 9:33:40 PM.
 */

package org.gamegineer.common.ui.window;

import java.awt.Window;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.window.AbstractWindow} class.
 */
public final class AbstractWindowTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The window under test in the fixture. */
    private AbstractWindow<Window> window_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWindowTest} class.
     */
    public AbstractWindowTest()
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
        window_ = new AbstractWindow<Window>( null )
        {
            @Override
            protected Window createShell(
                final Window parent )
            {
                return new Window( parent );
            }
        };
    }

    /**
     * Ensures the {@link AbstractWindow#configureShell} method throws an
     * exception when passed a {@code null} shell.
     */
    @Test( expected = NullPointerException.class )
    public void testConfigureShell_Shell_Null()
    {
        window_.configureShell( null );
    }

    /**
     * Ensures the {@link AbstractWindow#createContent} method throws an
     * exception when passed a {@code null} parent.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateContent_Parent_Null()
    {
        window_.createContent( null );
    }

    /**
     * Ensures the {@link AbstractWindow#getInitialLocation} method throws an
     * exception when passed a {@code null} initial size.
     */
    @Test( expected = NullPointerException.class )
    public void testGetInitialLocation_InitialSize_Null()
    {
        window_.getInitialLocation( null );
    }
}
