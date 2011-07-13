/*
 * CardIncrementTest.java
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
 * Created on Jul 9, 2011 at 10:10:15 PM.
 */

package org.gamegineer.table.internal.net.node;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.CardIncrement} class.
 */
public final class CardIncrementTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card increment under test in the fixture. */
    private CardIncrement cardIncrement_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardIncrementTest} class.
     */
    public CardIncrementTest()
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
        cardIncrement_ = new CardIncrement();
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method throws an exception when
     * passed a non-{@code null} back design and a {@code null} face design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesigns_BackDesign_NotNull_FaceDesign_Null()
    {
        cardIncrement_.setSurfaceDesigns( EasyMock.createMock( ICardSurfaceDesign.class ), null );
    }

    /**
     * Ensures the {@code setSurfaceDesigns} method throws an exception when
     * passed a {@code null} back design and a non-{@code null} face design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSurfaceDesigns_BackDesign_Null_FaceDesign_NotNull()
    {
        cardIncrement_.setSurfaceDesigns( null, EasyMock.createMock( ICardSurfaceDesign.class ) );
    }
}
