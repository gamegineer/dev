/*
 * CardViewTest.java
 * Copyright 2008-2009 Gamegineer.org
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
import javax.swing.DebugGraphics;
import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardDesign;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.ui.CardDesignUIs;
import org.gamegineer.table.ui.ICardDesignUI;
import org.junit.After;
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
     * The card design user interface for the card back for use in the test
     * fixture.
     */
    private ICardDesignUI backDesignUI_;

    /** A card for use in the test fixture. */
    private ICard card_;

    /** The card view under test in the fixture. */
    private CardView cardView_;

    /**
     * The card design user interface for the card face for use in the test
     * fixture.
     */
    private ICardDesignUI faceDesignUI_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardViewTest} class.
     */
    public CardViewTest()
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
        final ICardDesign backDesign = CardDesigns.createUniqueCardDesign();
        backDesignUI_ = CardDesignUIs.createCardDesignUI( backDesign );
        final ICardDesign faceDesign = CardDesigns.createUniqueCardDesign();
        faceDesignUI_ = CardDesignUIs.createCardDesignUI( faceDesign );
        card_ = CardFactory.createCard( backDesign, faceDesign );
        cardView_ = new CardView( card_, backDesignUI_, faceDesignUI_ );
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
        backDesignUI_ = null;
        faceDesignUI_ = null;
        card_ = null;
        cardView_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card design user interface for the card back.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_BackDesignUI_Null()
    {
        new CardView( card_, null, faceDesignUI_ );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Card_Null()
    {
        new CardView( null, backDesignUI_, faceDesignUI_ );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * card design user interface for the card face.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_FaceDesignUI_Null()
    {
        new CardView( card_, backDesignUI_, null );
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

    /**
     * Ensures the {@code initialize} method throws an exception when the card
     * view is already initialized.
     */
    @Test( expected = AssertionError.class )
    public void testInitialize_Initialized()
    {
        final TableView tableView = new TableView( TableFactory.createTable() );
        cardView_.initialize( tableView );

        cardView_.initialize( tableView );
    }

    /**
     * Ensures the {@code initialize} method throws an exception when passed a
     * {@code null} table view.
     */
    @Test( expected = AssertionError.class )
    public void testInitialize_TableView_Null()
    {
        cardView_.initialize( null );
    }

    /**
     * Ensures the {@code initialize} method throws an exception when passed a
     * {@code null} graphics context.
     */
    @Test( expected = AssertionError.class )
    public void testPaint_Graphics_Null()
    {
        cardView_.paint( null );
    }

    /**
     * Ensures the {@code paint} method throws an exception when the card view
     * is uninitialized.
     */
    @Test( expected = AssertionError.class )
    public void testPaint_Uninitialized()
    {
        cardView_.paint( new DebugGraphics() );
    }

    /**
     * Ensures the {@code uninitialize} method throws an exception when the card
     * view is uninitialized.
     */
    @Test( expected = AssertionError.class )
    public void testUninitialize_Uninitialized()
    {
        cardView_.uninitialize();
    }
}
