/*
 * AbstractCardPileDesignTestCase.java
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
 * Created on Jan 18, 2010 at 10:16:46 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPileDesign} interface.
 */
public abstract class AbstractCardPileDesignTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile design under test in the fixture. */
    private ICardPileDesign cardPileDesign_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCardPileDesignTestCase}
     * class.
     */
    protected AbstractCardPileDesignTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile design to be tested.
     * 
     * @return The card pile design to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileDesign createCardPileDesign()
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
        cardPileDesign_ = createCardPileDesign();
        assertNotNull( cardPileDesign_ );
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
        cardPileDesign_ = null;
    }

    /**
     * Ensures the {@code getId} method does not return {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( cardPileDesign_.getId() );
    }

    /**
     * Ensures the {@code getSize} method returns a copy of the size.
     */
    @Test
    public void testGetSize_ReturnValue_Copy()
    {
        final Dimension size = cardPileDesign_.getSize();
        final Dimension expectedSize = new Dimension( size );

        size.setSize( 101, 202 );

        assertEquals( expectedSize, cardPileDesign_.getSize() );
    }

    /**
     * Ensures the {@code getSize} method does not return {@code null}.
     */
    @Test
    public void testGetSize_ReturnValue_NonNull()
    {
        assertNotNull( cardPileDesign_.getSize() );
    }
}
