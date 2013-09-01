/*
 * AbstractComponentListenerTestCase.java
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
 * Created on Mar 27, 2012 at 9:00:34 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponentListener} interface.
 */
public abstract class AbstractComponentListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component listener under test in the fixture. */
    private IComponentListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentListenerTestCase} class.
     */
    protected AbstractComponentListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component listener to be tested.
     * 
     * @return The component listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentListener createComponentListener()
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
        listener_ = createComponentListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@link IComponentListener#componentBoundsChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentBoundsChanged_Event_Null()
    {
        listener_.componentBoundsChanged( null );
    }

    /**
     * Ensures the {@link IComponentListener#componentOrientationChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentOrientationChanged_Event_Null()
    {
        listener_.componentOrientationChanged( null );
    }

    /**
     * Ensures the {@link IComponentListener#componentSurfaceDesignChanged}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentSurfaceDesignChanged_Event_Null()
    {
        listener_.componentSurfaceDesignChanged( null );
    }
}
