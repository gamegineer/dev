/*
 * AbstractCardPileEventTestCase.java
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
 * Created on Jan 10, 2010 at 8:51:38 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPileEvent} interface.
 * 
 * @param <T>
 *        The type of the card pile event.
 */
public abstract class AbstractCardPileEventTestCase<T extends ICardPileEvent>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile event under test in the fixture. */
    private T event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCardPileEventTestCase}
     * class.
     */
    protected AbstractCardPileEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile event to be tested.
     * 
     * @return The card pile event to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createCardPileEvent()
        throws Exception;

    /**
     * Gets the card pile event under test in the fixture.
     * 
     * @return The card pile event under test in the fixture; never {@code null}
     *         .
     */
    /* @NonNull */
    protected final T getCardPileEvent()
    {
        assertNotNull( event_ );
        return event_;
    }

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
        event_ = createCardPileEvent();
        assertNotNull( event_ );
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
        event_ = null;
    }

    /**
     * Ensures the {@code getCardPile} method does not return {@code null}.
     */
    @Test
    public void testGetCardPile_ReturnValue_NonNull()
    {
        assertNotNull( event_.getCardPile() );
    }
}