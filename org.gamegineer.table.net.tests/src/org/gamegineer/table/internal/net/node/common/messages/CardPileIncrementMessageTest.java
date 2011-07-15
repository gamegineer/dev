/*
 * CardPileIncrementMessageTest.java
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
 * Created on Jul 12, 2011 at 8:46:00 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.CardPileIncrementMessage}
 * class.
 */
public final class CardPileIncrementMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile increment message under test in the fixture. */
    private CardPileIncrementMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileIncrementMessageTest}
     * class.
     */
    public CardPileIncrementMessageTest()
    {
        super();
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
        message_ = new CardPileIncrementMessage();
    }

    /**
     * Ensures the {@code setIncrement} method throws an exception when passed a
     * {@code null} increment.
     */
    @Test( expected = NullPointerException.class )
    public void testSetIncrement_Increment_Null()
    {
        message_.setIncrement( null );
    }

    /**
     * Ensures the {@code setIndex} method throws an exception when passed an
     * illegal index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetIndex_Index_Null()
    {
        message_.setIndex( -1 );
    }
}
