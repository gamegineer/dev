/*
 * AccordianContainerLayoutTest.java
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
 * Created on May 5, 2012 at 10:00:47 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import java.awt.Point;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.AccordianContainerLayout} class.
 */
public final class AccordianContainerLayoutTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AccordianContainerLayoutTest}
     * class.
     */
    public AccordianContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor does not throw an exception when passed an offset
     * in the x-direction that is non-zero and an offset in the y-direction that
     * is zero.
     */
    @Test
    public void testConstructor_OffsetX_NonZero_OffsetY_Zero()
    {
        new AccordianContainerLayout( 1, 0 );
    }

    /**
     * Ensures the constructor throws an exception when passed an offset in the
     * x-direction that is zero and an offset in the y-direction that is zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_OffsetX_Zero_OffsetY_Zero()
    {
        new AccordianContainerLayout( 0, 0 );
    }

    /**
     * Ensures the constructor does not throw an exception when passed an offset
     * in the x-direction that is zero and an offset in the y-direction that is
     * non-zero.
     */
    @Test
    public void testConstructor_OffsetX_Zero_OffsetY_NonZero()
    {
        new AccordianContainerLayout( 0, 1 );
    }

    /**
     * Ensures the {@code layout} method correctly lays out the child components
     * of the container.
     */
    @Test
    public void testLayout()
    {
        final int originX = 20, originY = 30;
        final int offsetX = 2, offsetY = -3;
        final AccordianContainerLayout layout = new AccordianContainerLayout( offsetX, offsetY );
        final ITable table = TableFactory.createTable();
        final IContainer container = CardPiles.createUniqueCardPile( table );
        container.setOrigin( new Point( originX, originY ) );
        final IComponent component1 = Cards.createUniqueCard( table );
        container.addComponent( component1 );
        final IComponent component2 = Cards.createUniqueCard( table );
        container.addComponent( component2 );
        final IComponent component3 = Cards.createUniqueCard( table );
        container.addComponent( component3 );

        layout.layout( container );

        assertEquals( new Point( originX, originY ), component1.getLocation() );
        assertEquals( new Point( originX + offsetX, originY + offsetY ), component2.getLocation() );
        assertEquals( new Point( originX + 2 * offsetX, originY + 2 * offsetY ), component3.getLocation() );
    }
}
