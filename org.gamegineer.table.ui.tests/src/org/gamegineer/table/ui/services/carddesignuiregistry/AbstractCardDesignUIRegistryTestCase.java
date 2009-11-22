/*
 * AbstractCardDesignUIRegistryTestCase.java
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
 * Created on Nov 20, 2009 at 11:43:50 PM.
 */

package org.gamegineer.table.ui.services.carddesignuiregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.ui.CardDesignUIs;
import org.gamegineer.table.ui.ICardDesignUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.services.carddesignuiregistry.ICardDesignUIRegistry}
 * interface.
 */
public abstract class AbstractCardDesignUIRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card design user interface registry under test in the fixture. */
    private ICardDesignUIRegistry cardDesignUIRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardDesignUIRegistryTestCase} class.
     */
    protected AbstractCardDesignUIRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card design user interface registry to be tested.
     * 
     * @return The card design user interface registry to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardDesignUIRegistry createCardDesignUIRegistry()
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
        cardDesignUIRegistry_ = createCardDesignUIRegistry();
        assertNotNull( cardDesignUIRegistry_ );
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
        cardDesignUIRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardDesignUI} method returns the correct value when
     * passed an identifier that is absent.
     */
    @Test
    public void testGetCardDesignUI_Id_Absent()
    {
        assertNull( cardDesignUIRegistry_.getCardDesignUI( CardDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardDesignUI} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardDesignUI_Id_Null()
    {
        cardDesignUIRegistry_.getCardDesignUI( null );
    }

    /**
     * Ensures the {@code getCardDesignUI} method returns the correct value when
     * passed an identifier that is present.
     */
    @Test
    public void testGetCardDesignUI_Id_Present()
    {
        final ICardDesignUI expectedCardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        cardDesignUIRegistry_.registerCardDesignUI( expectedCardDesignUI );

        final ICardDesignUI actualCardDesignUI = cardDesignUIRegistry_.getCardDesignUI( expectedCardDesignUI.getId() );

        assertSame( expectedCardDesignUI, actualCardDesignUI );
    }

    /**
     * Ensures the {@code getCardDesignUIs} method returns a copy of the
     * registered card design user interface collection.
     */
    @Test
    public void testGetCardDesignUIs_ReturnValue_Copy()
    {
        final Collection<ICardDesignUI> cardDesignUIs = cardDesignUIRegistry_.getCardDesignUIs();
        final int expectedCardDesignUIsSize = cardDesignUIs.size();

        cardDesignUIs.add( CardDesignUIs.createUniqueCardDesignUI() );

        assertEquals( expectedCardDesignUIsSize, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code getCardDesignUIs} method returns a snapshot of the
     * registered card design user interface collection.
     */
    @Test
    public void testGetCardDesignUIs_ReturnValue_Snapshot()
    {
        final Collection<ICardDesignUI> cardDesignUIs = cardDesignUIRegistry_.getCardDesignUIs();
        cardDesignUIRegistry_.registerCardDesignUI( CardDesignUIs.createUniqueCardDesignUI() );

        assertTrue( cardDesignUIs.size() != cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardDesignUI} method throws an exception when
     * passed a {@code null} card design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardDesignUI_CardDesignUI_Null()
    {
        cardDesignUIRegistry_.registerCardDesignUI( null );
    }

    /**
     * Ensures the {@code registerCardDesignUI} method properly ignores a card
     * design user interface whose identifier has already been registered.
     */
    @Test
    public void testRegisterCardDesignUI_CardDesignUI_Registered_DifferentInstance()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        final int originalCardDesignUIsSize = cardDesignUIRegistry_.getCardDesignUIs().size();
        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );

        cardDesignUIRegistry_.registerCardDesignUI( CardDesignUIs.cloneCardDesignUI( cardDesignUI ) );

        assertTrue( cardDesignUIRegistry_.getCardDesignUIs().contains( cardDesignUI ) );
        assertEquals( originalCardDesignUIsSize + 1, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardDesignUI} method properly ignores a card
     * design user interface instance that has already been registered.
     */
    @Test
    public void testRegisterCardDesignUI_CardDesignUI_Registered_SameInstance()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        final int originalCardDesignUIsSize = cardDesignUIRegistry_.getCardDesignUIs().size();
        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );

        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );

        assertTrue( cardDesignUIRegistry_.getCardDesignUIs().contains( cardDesignUI ) );
        assertEquals( originalCardDesignUIsSize + 1, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code registerCardDesignUI} method registers an unregistered
     * card design user interface.
     */
    @Test
    public void testRegisterCardDesignUI_CardDesignUI_Unregistered()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();

        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );

        assertTrue( cardDesignUIRegistry_.getCardDesignUIs().contains( cardDesignUI ) );
    }

    /**
     * Ensures the {@code unregisterCardDesignUI} method throws an exception
     * when passed a {@code null} card design user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardDesignUI_CardDesignUI_Null()
    {
        cardDesignUIRegistry_.unregisterCardDesignUI( null );
    }

    /**
     * Ensures the {@code unregisterCardDesignUI} method ignores a card design
     * user interface whose identifier was previously registered but by a
     * different card design user interface instance.
     */
    @Test
    public void testUnregisterCardDesignUI_CardDesignUI_Registered_DifferentInstance()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        final int originalCardDesignUIsSize = cardDesignUIRegistry_.getCardDesignUIs().size();
        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );
        assertEquals( originalCardDesignUIsSize + 1, cardDesignUIRegistry_.getCardDesignUIs().size() );

        cardDesignUIRegistry_.unregisterCardDesignUI( CardDesignUIs.cloneCardDesignUI( cardDesignUI ) );

        assertEquals( originalCardDesignUIsSize + 1, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardDesignUI} method unregisters a
     * previously registered card design user interface.
     */
    @Test
    public void testUnregisterCardDesignUI_CardDesignUI_Registered_SameInstance()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        final int originalCardDesignUIsSize = cardDesignUIRegistry_.getCardDesignUIs().size();
        cardDesignUIRegistry_.registerCardDesignUI( cardDesignUI );
        assertEquals( originalCardDesignUIsSize + 1, cardDesignUIRegistry_.getCardDesignUIs().size() );

        cardDesignUIRegistry_.unregisterCardDesignUI( cardDesignUI );

        assertEquals( originalCardDesignUIsSize, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }

    /**
     * Ensures the {@code unregisterCardDesignUI} properly ignores a card design
     * user interface that was not previously registered.
     */
    @Test
    public void testUnregisterCardDesignUI_CardDesignUI_Unregistered()
    {
        final ICardDesignUI cardDesignUI = CardDesignUIs.createUniqueCardDesignUI();
        final int originalCardDesignUIsSize = cardDesignUIRegistry_.getCardDesignUIs().size();

        cardDesignUIRegistry_.unregisterCardDesignUI( cardDesignUI );

        assertEquals( originalCardDesignUIsSize, cardDesignUIRegistry_.getCardDesignUIs().size() );
    }
}
