/*
 * CardPileViewTest.java
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
 * Created on Jan 26, 2010 at 11:48:09 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Rectangle;
import org.gamegineer.table.core.CardPileOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesigns;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.ComponentSurfaceDesignUIs;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardPileView} class.
 */
public final class CardPileViewTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component surface design user interface for the card pile base. */
    private ComponentSurfaceDesignUI baseDesignUI_;

    /** A card pile model for use in the test fixture. */
    private CardPileModel cardPileModel_;

    /** The card pile view under test in the fixture. */
    private CardPileView cardPileView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileViewTest} class.
     */
    public CardPileViewTest()
    {
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
        final ITable table = TableEnvironmentFactory.createTableEnvironment().createTable();
        final ComponentSurfaceDesign baseDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        baseDesignUI_ = ComponentSurfaceDesignUIs.createComponentSurfaceDesignUI( baseDesign );
        final ICardPile cardPile = table.createCardPile();
        cardPile.setSurfaceDesign( CardPileOrientation.BASE, baseDesign );
        cardPileModel_ = new CardPileModel( cardPile );
        cardPileView_ = new CardPileView( cardPileModel_, baseDesignUI_ );
    }

    /**
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = cardPileView_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, cardPileView_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( cardPileView_.getBounds() );
    }
}
