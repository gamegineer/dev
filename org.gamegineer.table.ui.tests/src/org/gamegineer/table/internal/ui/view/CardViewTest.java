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

import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.CardFactory;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardDesign;
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
}
