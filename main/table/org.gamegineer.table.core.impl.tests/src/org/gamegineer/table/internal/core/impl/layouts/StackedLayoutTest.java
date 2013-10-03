/*
 * StackedLayoutTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.core.impl.layouts;

import static org.junit.Assert.assertEquals;
import java.awt.Point;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestComponents;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.junit.Test;

/**
 * A fixture for testing the {@link StackedLayout} class.
 */
public final class StackedLayoutTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default container layout identifier for use in the fixture. */
    private static final ContainerLayoutId DEFAULT_ID = ContainerLayoutId.fromString( "id" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StackedLayoutTest} class.
     */
    public StackedLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link StackedLayout#StackedLayout} constructor throws an
     * exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new StackedLayout( null, 1, 1, 1 );
    }

    /**
     * Ensures the {@link StackedLayout#layout} method correctly lays out the
     * child components of the container.
     */
    @Test
    public void testLayout()
    {
        final int originX = 20, originY = 30;
        final int stackLevelOffsetX = 2, stackLevelOffsetY = 3;
        final StackedLayout layout = new StackedLayout( DEFAULT_ID, 2, stackLevelOffsetX, stackLevelOffsetY );
        final ITableEnvironment tableEnvironment = TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
        final IContainer container = TestComponents.createUniqueContainer( tableEnvironment );
        container.setOrigin( new Point( originX, originY ) );
        final IComponent component1 = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component1 );
        final IComponent component2 = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component2 );
        final IComponent component3 = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component3 );
        final IComponent component4 = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component4 );
        final IComponent component5 = TestComponents.createUniqueComponent( tableEnvironment );
        container.addComponent( component5 );

        layout.layout( container );

        assertEquals( new Point( originX, originY ), component1.getLocation() );
        assertEquals( new Point( originX, originY ), component2.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component3.getLocation() );
        assertEquals( new Point( originX + stackLevelOffsetX, originY + stackLevelOffsetY ), component4.getLocation() );
        assertEquals( new Point( originX + 2 * stackLevelOffsetX, originY + 2 * stackLevelOffsetY ), component5.getLocation() );
    }
}
