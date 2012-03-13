/*
 * AbstractCardPileBaseDesignUIRegistryTestCase.java
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
 * Created on Jan 23, 2010 at 9:31:39 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.ICardPileBaseDesignUIRegistry} interface.
 */
public abstract class AbstractCardPileBaseDesignUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card pile base design user interface registry under test in the
     * fixture.
     */
    private ICardPileBaseDesignUIRegistry cardPileBaseDesignUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractCardPileBaseDesignUIRegistryTestCase} class.
     */
    protected AbstractCardPileBaseDesignUIRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile base design user interface registry to be tested.
     * 
     * @return The card pile base design user interface registry to be tested;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileBaseDesignUIRegistry createCardPileBaseDesignUIRegistry()
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
        cardPileBaseDesignUIRegistry_ = createCardPileBaseDesignUIRegistry();
        assertNotNull( cardPileBaseDesignUIRegistry_ );
    }

    /**
     * Ensures the {@code getCardPileBaseDesignUI} method returns the correct
     * value when passed an identifier that is absent.
     */
    @Test
    public void testGetCardPileBaseDesignUI_Id_Absent()
    {
        assertNull( cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUI( CardPileBaseDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardPileBaseDesignUI} method throws an exception
     * when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileBaseDesignUI_Id_Null()
    {
        cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUI( null );
    }

    /**
     * Ensures the {@code getCardPileBaseDesignUI} method returns the correct
     * value when passed an identifier that is present.
     */
    @Test
    public void testGetCardPileBaseDesignUI_Id_Present()
    {
        final ICardPileBaseDesignUI expectedCardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( expectedCardPileBaseDesignUI );

        final ICardPileBaseDesignUI actualCardPileBaseDesignUI = cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUI( expectedCardPileBaseDesignUI.getId() );

        assertSame( expectedCardPileBaseDesignUI, actualCardPileBaseDesignUI );
    }

    /**
     * Ensures the {@code getCardPileBaseDesignUIs} method returns a copy of the
     * registered card pile base design user interface collection.
     */
    @Test
    public void testGetCardPileBaseDesignUIs_ReturnValue_Copy()
    {
        final Collection<ICardPileBaseDesignUI> cardPileBaseDesignUIs = cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs();
        final int expectedCardPileBaseDesignUIsSize = cardPileBaseDesignUIs.size();

        cardPileBaseDesignUIs.add( CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI() );

        assertEquals( expectedCardPileBaseDesignUIsSize, cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size() );
    }

    /**
     * Ensures the {@code getCardPileBaseDesignUIs} method returns a snapshot of
     * the registered card pile base design user interface collection.
     */
    @Test
    public void testGetCardPileBaseDesignUIs_ReturnValue_Snapshot()
    {
        final Collection<ICardPileBaseDesignUI> cardPileBaseDesignUIs = cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs();
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI() );

        assertTrue( cardPileBaseDesignUIs.size() != cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesignUI} method throws an
     * exception when passed a {@code null} card pile base design user
     * interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardPileBaseDesignUI_CardPileBaseDesignUI_Null()
    {
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( null );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesignUI} method throws an
     * exception when a card pile base design user interface with the same
     * identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterCardPileBaseDesignUI_CardPileBaseDesignUI_Registered()
    {
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( cardPileBaseDesignUI );

        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( CardPileBaseDesignUIs.cloneCardPileBaseDesignUI( cardPileBaseDesignUI ) );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesignUI} method registers an
     * unregistered card pile base design user interface.
     */
    @Test
    public void testRegisterCardPileBaseDesignUI_CardPileBaseDesignUI_Unregistered()
    {
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();

        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( cardPileBaseDesignUI );

        assertTrue( cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().contains( cardPileBaseDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesignUI} method throws an
     * exception when passed a {@code null} card pile base design user
     * interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardPileBaseDesignUI_CardPileBaseDesignUI_Null()
    {
        cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( null );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesignUI} method throws an
     * exception when passed a card pile base design user interface whose
     * identifier was previously registered but by a different card pile base
     * design user interface instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardPileBaseDesignUI_CardPileBaseDesignUI_Registered_DifferentInstance()
    {
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();
        final int originalCardPileBaseDesignUIsSize = cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size();
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( cardPileBaseDesignUI );
        assertEquals( originalCardPileBaseDesignUIsSize + 1, cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size() );

        cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( CardPileBaseDesignUIs.cloneCardPileBaseDesignUI( cardPileBaseDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesignUI} method unregisters a
     * previously registered card pile base design user interface.
     */
    @Test
    public void testUnregisterCardPileBaseDesignUI_CardPileBaseDesignUI_Registered_SameInstance()
    {
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();
        final int originalCardPileBaseDesignUIsSize = cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size();
        cardPileBaseDesignUIRegistry_.registerCardPileBaseDesignUI( cardPileBaseDesignUI );
        assertEquals( originalCardPileBaseDesignUIsSize + 1, cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size() );

        cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( cardPileBaseDesignUI );

        assertEquals( originalCardPileBaseDesignUIsSize, cardPileBaseDesignUIRegistry_.getCardPileBaseDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesignUI} method throws an
     * exception when passed a card pile base design user interface that was not
     * previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardPileBaseDesignUI_CardPileBaseDesignUI_Unregistered()
    {
        final ICardPileBaseDesignUI cardPileBaseDesignUI = CardPileBaseDesignUIs.createUniqueCardPileBaseDesignUI();

        cardPileBaseDesignUIRegistry_.unregisterCardPileBaseDesignUI( cardPileBaseDesignUI );
    }
}
