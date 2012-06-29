/*
 * AbstractTableEnvironmentTestCase.java
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
 * {@link org.gamegineer.table.core.ITableEnvironment} interface.
 */
public abstract class AbstractTableEnvironmentTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment under test in the fixture. */
    private ITableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableEnvironmentTestCase} class.
     */
    protected AbstractTableEnvironmentTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table environment to be tested.
     * 
     * @return The table environment to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableEnvironment createTableEnvironment()
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
        tableEnvironment_ = createTableEnvironment();
        assertNotNull( tableEnvironment_ );
    }

    /**
     * Ensures the {@code createCard} method returns a card that is associated
     * with the table environment.
     */
    @Test
    public void testCreateCard_ReturnValue_AssociatedWithTableEnvironment()
    {
        final ICard card = tableEnvironment_.createCard();

        assertNotNull( card );
        assertEquals( tableEnvironment_, card.getTableEnvironment() );
    }

    /**
     * Ensures the {@code createCard} method does not return {@code null}.
     */
    @Test
    public void testCreateCard_ReturnValue_NonNull()
    {
        assertNotNull( tableEnvironment_.createCard() );
    }

    /**
     * Ensures the {@code createCardPile} method returns a card pile that is
     * associated with the table environment.
     */
    @Test
    public void testCreateCardPile_ReturnValue_AssociatedWithTableEnvironment()
    {
        final ICardPile cardPile = tableEnvironment_.createCardPile();

        assertNotNull( cardPile );
        assertEquals( tableEnvironment_, cardPile.getTableEnvironment() );
    }

    /**
     * Ensures the {@code createCardPile} method does not return {@code null}.
     */
    @Test
    public void testCreateCardPile_ReturnValue_NonNull()
    {
        assertNotNull( tableEnvironment_.createCardPile() );
    }

    /**
     * Ensures the {@code createNullComponent} method returns a null component
     * that is associated with the table environment.
     */
    @Test
    public void testCreateNullComponent_ReturnValue_AssociatedWithTableEnvironment()
    {
        final IComponent component = tableEnvironment_.createNullComponent();

        assertNotNull( component );
        assertEquals( tableEnvironment_, component.getTableEnvironment() );
    }

    /**
     * Ensures the {@code createNullComponent} method does not return
     * {@code null}.
     */
    @Test
    public void testCreateNullComponent_ReturnValue_NonNull()
    {
        assertNotNull( tableEnvironment_.createNullComponent() );
    }

    /**
     * Ensures the {@code createTable} method returns a table that is associated
     * with the table environment.
     */
    @Test
    public void testCreateTable_ReturnValue_AssociatedWithTableEnvironment()
    {
        final ITable table = tableEnvironment_.createTable();

        assertNotNull( table );
        assertEquals( tableEnvironment_, table.getTableEnvironment() );
    }

    /**
     * Ensures the {@code getLock} method does not return {@code null}.
     */
    @Test
    public void testGetLock_ReturnValue_NonNull()
    {
        assertNotNull( tableEnvironment_.getLock() );
    }
}
