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
import static org.junit.Assert.assertTrue;
import java.awt.Dimension;
import java.awt.Point;
import org.gamegineer.table.core.CardPiles;
import org.gamegineer.table.core.Cards;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.TableEnvironmentFactory;
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
     * Ensures the {@code getComponentIndex} method returns {@code -1} when a
     * component is present at the specified location but the component is not
     * the top-most component.
     */
    @Test
    public void testGetComponentIndex_Location_ComponentPresent_NotTopComponent()
    {
        final StackedContainerLayout layout = new StackedContainerLayout( 1, 10, 10 );
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment();
        final IContainer container = CardPiles.createUniqueCardPile( tableEnvironment );
        container.setOrigin( new Point( 0, 0 ) );
        container.setLayout( layout );
        final IComponent component1 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component1 );
        final IComponent component2 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component2 );

        final Point location = new Point( 0, 0 );
        final int actualIndex = layout.getComponentIndex( container, location );

        assertTrue( container.getBounds().contains( location ) );
        assertEquals( -1, actualIndex );
    }

    /**
     * Ensures the {@code getComponentIndex} method returns the correct index
     * when a component is present at the specified location and the component
     * is the top-most component.
     */
    @Test
    public void testGetComponentIndex_Location_ComponentPresent_TopComponent()
    {
        final StackedContainerLayout layout = new StackedContainerLayout( 1, 10, 10 );
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment();
        final IContainer container = CardPiles.createUniqueCardPile( tableEnvironment );
        container.setOrigin( new Point( 0, 0 ) );
        container.setLayout( layout );
        final IComponent component1 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component1 );
        final IComponent component2 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component2 );

        final Point location = new Point( 10, 10 );
        final int actualIndex = layout.getComponentIndex( container, location );

        assertEquals( 1, actualIndex );
    }

    /**
     * Ensures the {@code getStackLevelOffset} method returns a copy of the
     * stack level offset.
     */
    @Test
    public void testGetStackLevelOffset_ReturnValue_Copy()
    {
        final StackedContainerLayout layout = new StackedContainerLayout( 1, 1, 1 );
        final Dimension stackLevelOffset = layout.getStackLevelOffset();
        final Dimension expectedValue = new Dimension( stackLevelOffset );

        stackLevelOffset.setSize( 1000, 1000 );
        final Dimension actualValue = layout.getStackLevelOffset();

        assertEquals( expectedValue, actualValue );
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
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment();
        final IContainer container = CardPiles.createUniqueCardPile( tableEnvironment );
        container.setOrigin( new Point( originX, originY ) );
        final IComponent component1 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component1 );
        final IComponent component2 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component2 );
        final IComponent component3 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component3 );
        final IComponent component4 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component4 );
        final IComponent component5 = Cards.createUniqueCard( tableEnvironment );
        container.addComponent( component5 );

        layout.layout( container );

        assertEquals( new Point( originX, originY ), component1.getLocation() );
        assertEquals( new Point( originX, originY ), component2.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component3.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component4.getLocation() );
        assertEquals( new Point( originX + 2 * stackLevelOffsetX, originY + 2 * stackLevelOffsetY ), component5.getLocation() );
    }
}
