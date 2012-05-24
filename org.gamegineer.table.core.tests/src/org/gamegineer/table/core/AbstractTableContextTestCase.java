/*
 * AbstractTableContextTestCase.java
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
 * Created on May 19, 2012 at 9:34:42 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITableContext} interface.
 */
public abstract class AbstractTableContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table context under test in the fixture. */
    private ITableContext tableContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableContextTestCase}
     * class.
     */
    protected AbstractTableContextTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table context to be tested.
     * 
     * @return The table context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableContext createTableContext()
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
        tableContext_ = createTableContext();
        assertNotNull( tableContext_ );
    }

    /**
     * Ensures the {@code createCard} method returns a card that is associated
     * with the table context.
     */
    @Test
    public void testCreateCard_ReturnValue_AssociatedWithTableContext()
    {
        final ICard card = tableContext_.createCard();

        assertNotNull( card );
        assertEquals( tableContext_, card.getTableContext() );
    }

    /**
     * Ensures the {@code createCardPile} method returns a card pile that is
     * associated with the table context.
     */
    @Test
    public void testCreateCardPile_ReturnValue_AssociatedWithTableContext()
    {
        final ICardPile cardPile = tableContext_.createCardPile();

        assertNotNull( cardPile );
        assertEquals( tableContext_, cardPile.getTableContext() );
    }

    /**
     * Ensures the {@code createTable} method returns a table that is associated
     * with the table context.
     */
    @Test
    public void testCreateTable_ReturnValue_AssociatedWithTableContext()
    {
        final ITable table = tableContext_.createTable();

        assertNotNull( table );
        assertEquals( tableContext_, table.getTableContext() );
    }
}
