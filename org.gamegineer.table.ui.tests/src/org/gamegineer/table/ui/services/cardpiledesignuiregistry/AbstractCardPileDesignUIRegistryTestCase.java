/*
 * AbstractCardPileDesignUIRegistryTestCase.java
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
 * Created on Jan 23, 2010 at 9:31:39 PM.
 */

package org.gamegineer.table.ui.services.cardpiledesignuiregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.ui.CardPileDesignUIs;
import org.gamegineer.table.ui.ICardPileDesignUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.services.cardpiledesignuiregistry.ICardPileDesignUIRegistry}
 * interface.
 */
public abstract class AbstractCardPileDesignUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile design user interface registry under test in the fixture. */
    private ICardPileDesignUIRegistry cardPileDesignUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileDesignUIRegistryTestCase} class.
     */
    protected AbstractCardPileDesignUIRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile design user interface registry to be tested.
     * 
     * @return The card pile design user interface registry to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileDesignUIRegistry createCardPileDesignUIRegistry()
        throws Exception;

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
        cardPileDesignUIRegistry_ = createCardPileDesignUIRegistry();
        assertNotNull( cardPileDesignUIRegistry_ );
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
        cardPileDesignUIRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardPileDesignUI} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetCardPileDesignUI_Id_Absent()
    {
        assertNull( cardPileDesignUIRegistry_.getCardPileDesignUI( CardPileDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardPileDesignUI} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileDesignUI_Id_Null()
    {
        cardPileDesignUIRegistry_.getCardPileDesignUI( null );
    }

    /**
     * Ensures the {@code getCardPileDesignUI} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetCardPileDesignUI_Id_Present()
    {
        final ICardPileDesignUI expectedCardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( expectedCardPileDesignUI );

        final ICardPileDesignUI actualCardPileDesignUI = cardPileDesignUIRegistry_.getCardPileDesignUI( expectedCardPileDesignUI.getId() );

        assertSame( expectedCardPileDesignUI, actualCardPileDesignUI );
    }

    /**
     * Ensures the {@code getCardPileDesignUIs} method returns a copy of the
     * registered card pile design user interface collection.
     */
    @Test
    public void testGetCardPileDesignUIs_ReturnValue_Copy()
    {
        final Collection<ICardPileDesignUI> cardPileDesignUIs = cardPileDesignUIRegistry_.getCardPileDesignUIs();
        final int expectedCardPileDesignUIsSize = cardPileDesignUIs.size();

        cardPileDesignUIs.add( CardPileDesignUIs.createUniqueCardPileDesignUI() );

        assertEquals( expectedCardPileDesignUIsSize, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code getCardPileDesignUIs} method returns a snapshot of the
     * registered card pile design user interface collection.
     */
    @Test
    public void testGetCardPileDesignUIs_ReturnValue_Snapshot()
    {
        final Collection<ICardPileDesignUI> cardPileDesignUIs = cardPileDesignUIRegistry_.getCardPileDesignUIs();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( CardPileDesignUIs.createUniqueCardPileDesignUI() );

        assertTrue( cardPileDesignUIs.size() != cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesignUI} method throws an exception
     * when passed a {@code null} card pile design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardPileDesignUI_CardPileDesignUI_Null()
    {
        cardPileDesignUIRegistry_.registerCardPileDesignUI( null );
    }

    /**
     * Ensures the {@code registerCardPileDesignUI} method properly ignores a
     * card pile design user interface whose identifier has already been
     * registered.
     */
    @Test
    public void testRegisterCardPileDesignUI_CardPileDesignUI_Registered_DifferentInstance()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        final int originalCardPileDesignUIsSize = cardPileDesignUIRegistry_.getCardPileDesignUIs().size();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );

        cardPileDesignUIRegistry_.registerCardPileDesignUI( CardPileDesignUIs.cloneCardPileDesignUI( cardPileDesignUI ) );

        assertTrue( cardPileDesignUIRegistry_.getCardPileDesignUIs().contains( cardPileDesignUI ) );
        assertEquals( originalCardPileDesignUIsSize + 1, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesignUI} method properly ignores a
     * card pile design user interface instance that has already been
     * registered.
     */
    @Test
    public void testRegisterCardPileDesignUI_CardPileDesignUI_Registered_SameInstance()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        final int originalCardPileDesignUIsSize = cardPileDesignUIRegistry_.getCardPileDesignUIs().size();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );

        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );

        assertTrue( cardPileDesignUIRegistry_.getCardPileDesignUIs().contains( cardPileDesignUI ) );
        assertEquals( originalCardPileDesignUIsSize + 1, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesignUI} method registers an
     * unregistered card pile design user interface.
     */
    @Test
    public void testRegisterCardPileDesignUI_CardPileDesignUI_Unregistered()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();

        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );

        assertTrue( cardPileDesignUIRegistry_.getCardPileDesignUIs().contains( cardPileDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardPileDesignUI} method throws an exception
     * when passed a {@code null} card pile design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardPileDesignUI_CardPileDesignUI_Null()
    {
        cardPileDesignUIRegistry_.unregisterCardPileDesignUI( null );
    }

    /**
     * Ensures the {@code unregisterCardPileDesignUI} method ignores a card pile
     * design user interface whose identifier was previously registered but by a
     * different card pile design user interface instance.
     */
    @Test
    public void testUnregisterCardPileDesignUI_CardPileDesignUI_Registered_DifferentInstance()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        final int originalCardPileDesignUIsSize = cardPileDesignUIRegistry_.getCardPileDesignUIs().size();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );
        assertEquals( originalCardPileDesignUIsSize + 1, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );

        cardPileDesignUIRegistry_.unregisterCardPileDesignUI( CardPileDesignUIs.cloneCardPileDesignUI( cardPileDesignUI ) );

        assertEquals( originalCardPileDesignUIsSize + 1, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileDesignUI} method unregisters a
     * previously registered card pile design user interface.
     */
    @Test
    public void testUnregisterCardPileDesignUI_CardPileDesignUI_Registered_SameInstance()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        final int originalCardPileDesignUIsSize = cardPileDesignUIRegistry_.getCardPileDesignUIs().size();
        cardPileDesignUIRegistry_.registerCardPileDesignUI( cardPileDesignUI );
        assertEquals( originalCardPileDesignUIsSize + 1, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );

        cardPileDesignUIRegistry_.unregisterCardPileDesignUI( cardPileDesignUI );

        assertEquals( originalCardPileDesignUIsSize, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileDesignUI} properly ignores a card
     * pile design user interface that was not previously registered.
     */
    @Test
    public void testUnregisterCardPileDesignUI_CardPileDesignUI_Unregistered()
    {
        final ICardPileDesignUI cardPileDesignUI = CardPileDesignUIs.createUniqueCardPileDesignUI();
        final int originalCardPileDesignUIsSize = cardPileDesignUIRegistry_.getCardPileDesignUIs().size();

        cardPileDesignUIRegistry_.unregisterCardPileDesignUI( cardPileDesignUI );

        assertEquals( originalCardPileDesignUIsSize, cardPileDesignUIRegistry_.getCardPileDesignUIs().size() );
    }
}
