/*
 * CardModelEventTest.java
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
 * Created on Dec 25, 2009 at 10:25:12 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.CardModelEvent} class.
 */
public final class CardModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card model event under test in the fixture. */
    private CardModelEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardModelEventTest} class.
     */
    public CardModelEventTest()
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
        event_ = new CardModelEvent( new CardModel( Cards.createUniqueCard( TableEnvironmentFactory.createTableEnvironment().createTable() ) ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new CardModelEvent( null );
    }

    /**
     * Ensures the {@code getCardModel} method does not return {@code null}.
     */
    @Test
    public void testGetCardModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getCardModel() );
    }

    /**
     * Ensures the {@code getSource} method returns the same instance as the
     * {@code getCardModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameCardModel()
    {
        assertSame( event_.getCardModel(), event_.getSource() );
    }
}
