/*
 * AbstractCardPileDesignUITestCase.java
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
 * Created on Jan 23, 2010 at 8:56:55 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.ICardPileDesignUI} interface.
 */
public abstract class AbstractCardPileDesignUITestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile design user interface under test in the fixture. */
    private ICardPileDesignUI cardPileDesignUI_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileDesignUITestCase} class.
     */
    protected AbstractCardPileDesignUITestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile design user interface to be tested.
     * 
     * @return The card pile design user interface to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileDesignUI createCardPileDesignUI()
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
        cardPileDesignUI_ = createCardPileDesignUI();
        assertNotNull( cardPileDesignUI_ );
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
        cardPileDesignUI_ = null;
    }

    /**
     * Ensures the {@code getIcon} method does not return {@code null}.
     */
    @Test
    public void testGetIcon_ReturnValue_NonNull()
    {
        assertNotNull( cardPileDesignUI_.getIcon() );
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( cardPileDesignUI_.getId() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( cardPileDesignUI_.getName() );
    }
}
