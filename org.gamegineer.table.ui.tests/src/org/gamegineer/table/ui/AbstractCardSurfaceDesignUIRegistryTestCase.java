/*
 * AbstractCardSurfaceDesignUIRegistryTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Nov 20, 2009 at 11:43:50 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry} interface.
 */
public abstract class AbstractCardSurfaceDesignUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The card surface design user interface registry under test in the
     * fixture.
     */
    private ICardSurfaceDesignUIRegistry cardSurfaceDesignUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardSurfaceDesignUIRegistryTestCase} class.
     */
    protected AbstractCardSurfaceDesignUIRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card surface design user interface registry to be tested.
     * 
     * @return The card surface design user interface registry to be tested;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardSurfaceDesignUIRegistry createCardSurfaceDesignUIRegistry()
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
        cardSurfaceDesignUIRegistry_ = createCardSurfaceDesignUIRegistry();
        assertNotNull( cardSurfaceDesignUIRegistry_ );
    }

    /**
     * Ensures the {@code getCardSurfaceDesignUI} method returns the correct
     * value when passed an identifier that is absent.
     */
    @Test
    public void testGetCardSurfaceDesignUI_Id_Absent()
    {
        assertNull( cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUI( CardSurfaceDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardSurfaceDesignUI} method throws an exception
     * when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardSurfaceDesignUI_Id_Null()
    {
        cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUI( null );
    }

    /**
     * Ensures the {@code getCardSurfaceDesignUI} method returns the correct
     * value when passed an identifier that is present.
     */
    @Test
    public void testGetCardSurfaceDesignUI_Id_Present()
    {
        final ICardSurfaceDesignUI expectedCardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( expectedCardSurfaceDesignUI );

        final ICardSurfaceDesignUI actualCardSurfaceDesignUI = cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUI( expectedCardSurfaceDesignUI.getId() );

        assertSame( expectedCardSurfaceDesignUI, actualCardSurfaceDesignUI );
    }

    /**
     * Ensures the {@code getCardSurfaceDesignUIs} method returns a copy of the
     * registered card surface design user interface collection.
     */
    @Test
    public void testGetCardSurfaceDesignUIs_ReturnValue_Copy()
    {
        final Collection<ICardSurfaceDesignUI> cardSurfaceDesignUIs = cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs();
        final int expectedCardSurfaceDesignUIsSize = cardSurfaceDesignUIs.size();

        cardSurfaceDesignUIs.add( CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI() );

        assertEquals( expectedCardSurfaceDesignUIsSize, cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the {@code getCardSurfaceDesignUIs} method returns a snapshot of
     * the registered card surface design user interface collection.
     */
    @Test
    public void testGetCardSurfaceDesignUIs_ReturnValue_Snapshot()
    {
        final Collection<ICardSurfaceDesignUI> cardSurfaceDesignUIs = cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs();
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI() );

        assertTrue( cardSurfaceDesignUIs.size() != cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesignUI} method throws an
     * exception when passed a {@code null} card surface design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardSurfaceDesignUI_CardSurfaceDesignUI_Null()
    {
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( null );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesignUI} method throws an
     * exception when a card surface design user interface with the same
     * identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterCardSurfaceDesignUI_CardSurfaceDesignUI_Registered()
    {
        final ICardSurfaceDesignUI cardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( cardSurfaceDesignUI );

        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( CardSurfaceDesignUIs.cloneCardSurfaceDesignUI( cardSurfaceDesignUI ) );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesignUI} method registers an
     * unregistered card surface design user interface.
     */
    @Test
    public void testRegisterCardSurfaceDesignUI_CardSurfaceDesignUI_Unregistered()
    {
        final ICardSurfaceDesignUI cardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();

        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( cardSurfaceDesignUI );

        assertTrue( cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().contains( cardSurfaceDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesignUI} method throws an
     * exception when passed a {@code null} card surface design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardSurfaceDesignUI_CardSurfaceDesignUI_Null()
    {
        cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( null );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesignUI} method throws an
     * exception when passed a card surface design user interface whose
     * identifier was previously registered but by a different card surface
     * design user interface instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardSurfaceDesignUI_CardSurfaceDesignUI_Registered_DifferentInstance()
    {
        final ICardSurfaceDesignUI cardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();
        final int originalCardSurfaceDesignUIsSize = cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size();
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( cardSurfaceDesignUI );
        assertEquals( originalCardSurfaceDesignUIsSize + 1, cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size() );

        cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( CardSurfaceDesignUIs.cloneCardSurfaceDesignUI( cardSurfaceDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesignUI} method unregisters a
     * previously registered card surface design user interface.
     */
    @Test
    public void testUnregisterCardSurfaceDesignUI_CardSurfaceDesignUI_Registered_SameInstance()
    {
        final ICardSurfaceDesignUI cardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();
        final int originalCardSurfaceDesignUIsSize = cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size();
        cardSurfaceDesignUIRegistry_.registerCardSurfaceDesignUI( cardSurfaceDesignUI );
        assertEquals( originalCardSurfaceDesignUIsSize + 1, cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size() );

        cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( cardSurfaceDesignUI );

        assertEquals( originalCardSurfaceDesignUIsSize, cardSurfaceDesignUIRegistry_.getCardSurfaceDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesignUI} method throws an
     * exception when passed a card surface design user interface that was not
     * previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardSurfaceDesignUI_CardSurfaceDesignUI_Unregistered()
    {
        final ICardSurfaceDesignUI cardSurfaceDesignUI = CardSurfaceDesignUIs.createUniqueCardSurfaceDesignUI();

        cardSurfaceDesignUIRegistry_.unregisterCardSurfaceDesignUI( cardSurfaceDesignUI );
    }
}
