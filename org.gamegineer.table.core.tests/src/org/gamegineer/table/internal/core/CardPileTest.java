/*
 * CardPileTest.java
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
 * Created on Jun 12, 2012 at 8:08:48 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesigns;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class.
 */
public final class CardPileTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile under test in the fixture. */
    private CardPile cardPile_;

    /** The table environment for use in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileTest} class.
     */
    public CardPileTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique attributes using the fixture table
     * environment.
     * 
     * @return A new component; never {@code null}.
     */
    /* @NonNull */
    private Component createUniqueComponent()
    {
        final Component component = new Card( tableEnvironment_ );
        for( final ComponentOrientation orientation : component.getSupportedOrientations() )
        {
            component.setSurfaceDesign( orientation, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        }

        return component;
    }

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
        tableEnvironment_ = new TableEnvironment();
        cardPile_ = new CardPile( tableEnvironment_ );
    }

    /**
     * Ensures the {@code getComponentIndex} method throws an exception when
     * passed a component that is absent from the component collection.
     */
    @Test( expected = AssertionError.class )
    public void testGetComponentIndex_Component_Absent()
    {
        cardPile_.getTableEnvironment().getLock().lock();
        try
        {
            cardPile_.getComponentIndex( createUniqueComponent() );
        }
        finally
        {
            cardPile_.getTableEnvironment().getLock().unlock();
        }
    }

    /**
     * Ensures the {@code getComponentIndex} method returns the correct value
     * when passed a component present in the component collection.
     */
    @Test
    public void testGetComponentIndex_Component_Present()
    {
        final Component component = createUniqueComponent();
        cardPile_.addComponent( createUniqueComponent() );
        cardPile_.addComponent( component );
        cardPile_.addComponent( createUniqueComponent() );

        final int actualValue;
        cardPile_.getTableEnvironment().getLock().lock();
        try
        {
            actualValue = cardPile_.getComponentIndex( component );
        }
        finally
        {
            cardPile_.getTableEnvironment().getLock().unlock();
        }

        assertEquals( 1, actualValue );
    }

    /**
     * Ensures the {@code getPath} method returns the correct value when the
     * card pile is associated with a table.
     */
    @Test
    public void testGetPath()
    {
        final ITableEnvironment tableEnvironment = TableEnvironmentFactory.createTableEnvironment();
        final ITable table = tableEnvironment.createTable();
        final ICardPile cardPile1 = tableEnvironment.createCardPile();
        table.getTabletop().addComponent( cardPile1 );
        final ICardPile cardPile2 = tableEnvironment.createCardPile();
        table.getTabletop().addComponent( cardPile2 );
        final ICardPile cardPile3 = tableEnvironment.createCardPile();
        table.getTabletop().addComponent( cardPile3 );
        final ComponentPath tabletopPath = new ComponentPath( null, 0 );
        final ComponentPath expectedComponentPath1 = new ComponentPath( tabletopPath, 0 );
        final ComponentPath expectedComponentPath2 = new ComponentPath( tabletopPath, 1 );
        final ComponentPath expectedComponentPath3 = new ComponentPath( tabletopPath, 2 );

        final ComponentPath actualComponentPath1 = cardPile1.getPath();
        final ComponentPath actualComponentPath2 = cardPile2.getPath();
        final ComponentPath actualComponentPath3 = cardPile3.getPath();

        assertEquals( expectedComponentPath1, actualComponentPath1 );
        assertEquals( expectedComponentPath2, actualComponentPath2 );
        assertEquals( expectedComponentPath3, actualComponentPath3 );
    }
}
