/*
 * CardFactoryTest.java
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
 * Created on Oct 14, 2009 at 11:29:22 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.CardFactory}
 * class.
 */
public final class CardFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardFactoryTest} class.
     */
    public CardFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCard} method throws an exception when passed a
     * {@code null} back design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCard_BackDesign_Null()
    {
        CardFactory.createCard( null, CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /**
     * Ensures the {@code createCard} method throws an exception when passed a
     * {@code null} face design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCard_FaceDesign_Null()
    {
        CardFactory.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), null );
    }

    /**
     * Ensures the {@code createCard} method throws an exception when passed a
     * face design that has a size different from the back design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCard_FaceDesign_SizeNotEqual()
    {
        final int width = 10;
        final int height = 20;
        final ICardSurfaceDesign backDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( width, height );
        final ICardSurfaceDesign faceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( 2 * width, 2 * height );

        CardFactory.createCard( backDesign, faceDesign );
    }

    /**
     * Ensures the {@code createCard} method does not return {@code null}.
     */
    @Test
    public void testCreateCard_ReturnValue_NonNull()
    {
        assertNotNull( CardFactory.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() ) );
    }
}
