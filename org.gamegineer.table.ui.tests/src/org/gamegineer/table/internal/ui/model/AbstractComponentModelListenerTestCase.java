/*
 * AbstractComponentModelListenerTestCase.java
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
 * Created on Dec 25, 2009 at 11:00:07 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.ui.model.IComponentModelListener}
 * interface.
 */
public abstract class AbstractComponentModelListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component model listener under test in the fixture. */
    private IComponentModelListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentModelListenerTestCase} class.
     */
    protected AbstractComponentModelListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component model listener to be tested.
     * 
     * @return The component model listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentModelListener createComponentModelListener()
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
        listener_ = createComponentModelListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@link IComponentModelListener#componentBoundsChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentBoundsChanged_Event_Null()
    {
        listener_.componentBoundsChanged( null );
    }

    /**
     * Ensures the {@link IComponentModelListener#componentChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentChanged_Event_Null()
    {
        listener_.componentChanged( null );
    }

    /**
     * Ensures the {@link IComponentModelListener#componentModelFocusChanged}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentModelFocusChanged_Event_Null()
    {
        listener_.componentModelFocusChanged( null );
    }

    /**
     * Ensures the {@link IComponentModelListener#componentOrientationChanged}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentOrientationChanged_Event_Null()
    {
        listener_.componentOrientationChanged( null );
    }

    /**
     * Ensures the {@link IComponentModelListener#componentSurfaceDesignChanged}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentSurfaceDesignChanged_Event_Null()
    {
        listener_.componentSurfaceDesignChanged( null );
    }
}
