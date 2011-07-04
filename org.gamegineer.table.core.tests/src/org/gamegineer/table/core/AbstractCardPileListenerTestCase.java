/*
 * AbstractCardPileListenerTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jan 10, 2010 at 10:22:59 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ICardPileListener} interface.
 */
public abstract class AbstractCardPileListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile listener under test in the fixture. */
    private ICardPileListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileListenerTestCase} class.
     */
    protected AbstractCardPileListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile listener to be tested.
     * 
     * @return The card pile listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileListener createCardPileListener()
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
        listener_ = createCardPileListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@code cardAdded} method throws an exception when passed a
     * {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardAdded_Event_Null()
    {
        listener_.cardAdded( null );
    }

    /**
     * Ensures the {@code cardPileBaseDesignChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileBaseDesignChanged_Event_Null()
    {
        listener_.cardPileBaseDesignChanged( null );
    }

    /**
     * Ensures the {@code cardPileBoundsChanged} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileBoundsChanged_Event_Null()
    {
        listener_.cardPileBoundsChanged( null );
    }

    /**
     * Ensures the {@code cardRemoved} method throws an exception when passed a
     * {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardRemoved_Event_Null()
    {
        listener_.cardRemoved( null );
    }
}
