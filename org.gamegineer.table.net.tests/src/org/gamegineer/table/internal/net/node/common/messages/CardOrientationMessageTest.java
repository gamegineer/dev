/*
 * CardOrientationMessageTest.java
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
 * Created on Jun 30, 2011 at 11:31:15 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.CardOrientationMessage}
 * class.
 */
public final class CardOrientationMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card orientation message under test in the fixture. */
    private CardOrientationMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardOrientationMessageTest}
     * class.
     */
    public CardOrientationMessageTest()
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
        message_ = new CardOrientationMessage();
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
        message_ = null;
    }

    /**
     * Ensures the {@code setCardIndex} method throws an exception when passed
     * an illegal card index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetCardIndex_CardIndex_Null()
    {
        message_.setCardIndex( -1 );
    }

    /**
     * Ensures the {@code setCardOrientation} method throws an exception when
     * passed a {@code null} card orientation.
     */
    @Test( expected = NullPointerException.class )
    public void testSetCardOrientation_CardOrientation_Null()
    {
        message_.setCardOrientation( null );
    }

    /**
     * Ensures the {@code setCardPileIndex} method throws an exception when
     * passed an illegal card pile index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetCardPileIndex_CardPileIndex_Null()
    {
        message_.setCardPileIndex( -1 );
    }
}
