/*
 * CardTest.java
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
 * Created on Oct 11, 2009 at 10:00:09 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.Card}
 * class.
 */
public final class CardTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardTest} class.
     */
    public CardTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * back design.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_BackDesign_Null()
    {
        new Card( null, CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * face design.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_FaceDesign_Null()
    {
        new Card( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a face design
     * that has a size different from the back design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_FaceDesign_SizeNotEqual()
    {
        final int width = 10;
        final int height = 20;
        final ICardSurfaceDesign backDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( width, height );
        final ICardSurfaceDesign faceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( 2 * width, 2 * height );

        new Card( backDesign, faceDesign );
    }

    /**
     * Ensures the {@code fromMemento} method throws an exception when passed a
     * {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testFromMemento_Memento_Null()
        throws Exception
    {
        Card.fromMemento( null );
    }
}
