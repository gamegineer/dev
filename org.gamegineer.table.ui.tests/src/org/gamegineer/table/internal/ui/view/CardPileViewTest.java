/*
 * CardPileViewTest.java
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
 * Created on Jan 26, 2010 at 11:48:09 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.awt.Rectangle;
import javax.swing.DebugGraphics;
import org.gamegineer.table.core.CardPileDesigns;
import org.gamegineer.table.core.CardPileFactory;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileDesign;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.ui.model.CardPileModel;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.ui.CardPileDesignUIs;
import org.gamegineer.table.ui.ICardPileDesignUI;
import org.junit.After;
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

    /** The card pile design user interface. */
    private ICardPileDesignUI cardPileDesignUI_;

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
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        cardPileDesignUI_ = CardPileDesignUIs.createCardPileDesignUI( cardPileDesign );
        final ICardPile cardPile = CardPileFactory.createCardPile( cardPileDesign );
        cardPileModel_ = new CardPileModel( cardPile );
        cardPileView_ = new CardPileView( cardPileModel_, cardPileDesignUI_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        cardPileDesignUI_ = null;
        cardPileModel_ = null;
        cardPileView_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card pile design user interface.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_CardPileDesignUI_Null()
    {
        new CardPileView( cardPileModel_, null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * model.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Model_Null()
    {
        new CardPileView( null, cardPileDesignUI_ );
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

    /**
     * Ensures the {@code initialize} method throws an exception when the card
     * pile view is already initialized.
     */
    @Test( expected = AssertionError.class )
    public void testInitialize_Initialized()
    {
        final TableView tableView = new TableView( new TableModel( TableFactory.createTable() ) );
        cardPileView_.initialize( tableView );

        cardPileView_.initialize( tableView );
    }

    /**
     * Ensures the {@code initialize} method throws an exception when passed a
     * {@code null} table view.
     */
    @Test( expected = AssertionError.class )
    public void testInitialize_TableView_Null()
    {
        cardPileView_.initialize( null );
    }

    /**
     * Ensures the {@code paint} method throws an exception when passed a
     * {@code null} graphics context.
     */
    @Test( expected = AssertionError.class )
    public void testPaint_Graphics_Null()
    {
        cardPileView_.paint( null );
    }

    /**
     * Ensures the {@code paint} method throws an exception when the card pile
     * view is uninitialized.
     */
    @Test( expected = AssertionError.class )
    public void testPaint_Uninitialized()
    {
        cardPileView_.paint( new DebugGraphics() );
    }

    /**
     * Ensures the {@code uninitialize} method throws an exception when the card
     * pile view is uninitialized.
     */
    @Test( expected = AssertionError.class )
    public void testUninitialize_Uninitialized()
    {
        cardPileView_.uninitialize();
    }
}
