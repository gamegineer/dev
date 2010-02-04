/*
 * AbstractCardSurfaceDesignUITestCase.java
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
 * Created on Nov 20, 2009 at 11:34:44 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.ICardSurfaceDesignUI} interface.
 */
public abstract class AbstractCardSurfaceDesignUITestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design user interface under test in the fixture. */
    private ICardSurfaceDesignUI cardSurfaceDesignUI_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardSurfaceDesignUITestCase} class.
     */
    protected AbstractCardSurfaceDesignUITestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card surface design user interface to be tested.
     * 
     * @return The card surface design user interface to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardSurfaceDesignUI createCardSurfaceDesignUI()
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
        cardSurfaceDesignUI_ = createCardSurfaceDesignUI();
        assertNotNull( cardSurfaceDesignUI_ );
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
        cardSurfaceDesignUI_ = null;
    }

    /**
     * Ensures the {@code getIcon} method does not return {@code null}.
     */
    @Test
    public void testGetIcon_ReturnValue_NonNull()
    {
        assertNotNull( cardSurfaceDesignUI_.getIcon() );
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( cardSurfaceDesignUI_.getId() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( cardSurfaceDesignUI_.getName() );
    }
}
