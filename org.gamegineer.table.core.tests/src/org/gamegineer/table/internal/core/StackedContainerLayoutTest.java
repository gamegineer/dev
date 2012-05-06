/*
 * StackedContainerLayoutTest.java
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
 * Created on May 5, 2012 at 10:15:41 PM.
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
 * {@link org.gamegineer.table.internal.core.StackedContainerLayout} class.
 */
public final class StackedContainerLayoutTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StackedContainerLayoutTest}
     * class.
     */
    public StackedContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed an illegal
     * components per stack level count that is zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_ComponentsPerStackLevel_Zero()
    {
        new StackedContainerLayout( 0, 1, 1 );
    }

    /**
     * Ensures the constructor throws an exception when passed an illegal stack
     * level offset in the x-direction that is zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_StackLevelOffsetX_Zero()
    {
        new StackedContainerLayout( 1, 0, 1 );
    }

    /**
     * Ensures the constructor throws an exception when passed an illegal stack
     * level offset in the y-direction that is zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_StackLevelOffsetY_Zero()
    {
        new StackedContainerLayout( 1, 1, 0 );
    }

    /**
     * Ensures the {@code layout} method correctly lays out the child components
     * of the container.
     */
    @Test
    public void testLayout()
    {
        final int originX = 20, originY = 30;
        final int stackLevelOffsetX = 2, stackLevelOffsetY = 3;
        final StackedContainerLayout layout = new StackedContainerLayout( 2, stackLevelOffsetX, stackLevelOffsetY );
        final ITable table = TableFactory.createTable();
        final IContainer container = CardPiles.createUniqueCardPile( table );
        container.setOrigin( new Point( originX, originY ) );
        final IComponent component1 = Cards.createUniqueCard( table );
        container.addComponent( component1 );
        final IComponent component2 = Cards.createUniqueCard( table );
        container.addComponent( component2 );
        final IComponent component3 = Cards.createUniqueCard( table );
        container.addComponent( component3 );
        final IComponent component4 = Cards.createUniqueCard( table );
        container.addComponent( component4 );
        final IComponent component5 = Cards.createUniqueCard( table );
        container.addComponent( component5 );

        layout.layout( container );

        assertEquals( new Point( originX, originY ), component1.getLocation() );
        assertEquals( new Point( originX, originY ), component2.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component3.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component4.getLocation() );
        assertEquals( new Point( originX + 2 * stackLevelOffsetX, originY + 2 * stackLevelOffsetY ), component5.getLocation() );
    }
}
