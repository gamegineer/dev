/*
 * CardViewTest.java
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
 * Created on Oct 15, 2009 at 10:44:54 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Rectangle;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesigns;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableEnvironmentFactory;
import org.gamegineer.table.internal.ui.model.CardModel;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.ComponentSurfaceDesignUIs;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.CardView} class.
 */
public final class CardViewTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component surface design user interface for the card back for use in
     * the test fixture.
     */
    private ComponentSurfaceDesignUI backDesignUI_;

    /** A card model for use in the test fixture. */
    private CardModel cardModel_;

    /** The card view under test in the fixture. */
    private CardView cardView_;

    /**
     * The component surface design user interface for the card face for use in
     * the test fixture.
     */
    private ComponentSurfaceDesignUI faceDesignUI_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardViewTest} class.
     */
    public CardViewTest()
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
        final ITable table = TableEnvironmentFactory.createTable();
        final ComponentSurfaceDesign backDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        backDesignUI_ = ComponentSurfaceDesignUIs.createComponentSurfaceDesignUI( backDesign );
        final ComponentSurfaceDesign faceDesign = ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
        faceDesignUI_ = ComponentSurfaceDesignUIs.createComponentSurfaceDesignUI( faceDesign );
        final ICard card = table.createCard();
        card.setSurfaceDesign( CardOrientation.BACK, backDesign );
        card.setSurfaceDesign( CardOrientation.FACE, faceDesign );
        cardModel_ = new CardModel( card );
        cardView_ = new CardView( cardModel_, backDesignUI_, faceDesignUI_ );
    }

    /**
     * Ensures the {@code getBounds} method returns a copy of the bounds.
     */
    @Test
    public void testGetBounds_ReturnValue_Copy()
    {
        final Rectangle bounds = cardView_.getBounds();
        final Rectangle expectedBounds = new Rectangle( bounds );

        bounds.setBounds( 1010, 2020, 101, 202 );

        assertEquals( expectedBounds, cardView_.getBounds() );
    }

    /**
     * Ensures the {@code getBounds} method does not return {@code null}.
     */
    @Test
    public void testGetBounds_ReturnValue_NonNull()
    {
        assertNotNull( cardView_.getBounds() );
    }
}
