/*
 * AbstractCardPileDesignRegistryTestCase.java
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
 * Created on Jan 19, 2010 at 11:15:57 PM.
 */

package org.gamegineer.table.core.services.cardpiledesignregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardPileDesignId;
import org.gamegineer.table.core.CardPileDesigns;
import org.gamegineer.table.core.ICardPileDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.services.cardpiledesignregistry.ICardPileDesignRegistry}
 * interface.
 */
public abstract class AbstractCardPileDesignRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile design registry under test in the fixture. */
    private ICardPileDesignRegistry cardPileDesignRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileDesignRegistryTestCase} class.
     */
    protected AbstractCardPileDesignRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile design registry to be tested.
     * 
     * @return The card pile design registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileDesignRegistry createCardPileDesignRegistry()
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
        cardPileDesignRegistry_ = createCardPileDesignRegistry();
        assertNotNull( cardPileDesignRegistry_ );
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
        cardPileDesignRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardPileDesign} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetCardPileDesign_Id_Absent()
    {
        assertNull( cardPileDesignRegistry_.getCardPileDesign( CardPileDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardPileDesign} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileDesign_Id_Null()
    {
        cardPileDesignRegistry_.getCardPileDesign( null );
    }

    /**
     * Ensures the {@code getCardPileDesign} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetCardPileDesign_Id_Present()
    {
        final ICardPileDesign expectedCardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        cardPileDesignRegistry_.registerCardPileDesign( expectedCardPileDesign );

        final ICardPileDesign actualCardPileDesign = cardPileDesignRegistry_.getCardPileDesign( expectedCardPileDesign.getId() );

        assertSame( expectedCardPileDesign, actualCardPileDesign );
    }

    /**
     * Ensures the {@code getCardPileDesigns} method returns a copy of the
     * registered card pile design collection.
     */
    @Test
    public void testGetCardPileDesigns_ReturnValue_Copy()
    {
        final Collection<ICardPileDesign> cardPileDesigns = cardPileDesignRegistry_.getCardPileDesigns();
        final int expectedCardPileDesignsSize = cardPileDesigns.size();

        cardPileDesigns.add( CardPileDesigns.createUniqueCardPileDesign() );

        assertEquals( expectedCardPileDesignsSize, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code getCardPileDesigns} method returns a snapshot of the
     * registered card pile design collection.
     */
    @Test
    public void testGetCardPileDesigns_ReturnValue_Snapshot()
    {
        final Collection<ICardPileDesign> cardPileDesigns = cardPileDesignRegistry_.getCardPileDesigns();
        cardPileDesignRegistry_.registerCardPileDesign( CardPileDesigns.createUniqueCardPileDesign() );

        assertTrue( cardPileDesigns.size() != cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesign} method throws an exception
     * when passed a {@code null} card pile design.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardPileDesign_CardPileDesign_Null()
    {
        cardPileDesignRegistry_.registerCardPileDesign( null );
    }

    /**
     * Ensures the {@code registerCardPileDesign} method properly ignores a card
     * pile design whose identifier has already been registered.
     */
    @Test
    public void testRegisterCardPileDesign_CardPileDesign_Registered_DifferentInstance()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final int originalCardPileDesignsSize = cardPileDesignRegistry_.getCardPileDesigns().size();
        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );

        cardPileDesignRegistry_.registerCardPileDesign( CardPileDesigns.cloneCardPileDesign( cardPileDesign ) );

        assertTrue( cardPileDesignRegistry_.getCardPileDesigns().contains( cardPileDesign ) );
        assertEquals( originalCardPileDesignsSize + 1, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesign} method properly ignores a card
     * pile design instance that has already been registered.
     */
    @Test
    public void testRegisterCardPileDesign_CardPileDesign_Registered_SameInstance()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final int originalCardPileDesignsSize = cardPileDesignRegistry_.getCardPileDesigns().size();
        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );

        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );

        assertTrue( cardPileDesignRegistry_.getCardPileDesigns().contains( cardPileDesign ) );
        assertEquals( originalCardPileDesignsSize + 1, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileDesign} method registers an
     * unregistered card pile design.
     */
    @Test
    public void testRegisterCardPileDesign_CardPileDesign_Unregistered()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();

        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );

        assertTrue( cardPileDesignRegistry_.getCardPileDesigns().contains( cardPileDesign ) );
    }

    /**
     * Ensures the {@code unregisterCardPileDesign} method throws an exception
     * when passed a {@code null} card pile design.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardPileDesign_CardPileDesign_Null()
    {
        cardPileDesignRegistry_.unregisterCardPileDesign( null );
    }

    /**
     * Ensures the {@code unregisterCardPileDesign} method ignores a card pile
     * design whose identifier was previously registered but by a different card
     * pile design instance.
     */
    @Test
    public void testUnregisterCardPileDesign_CardPuleDesign_Registered_DifferentInstance()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final int originalCardPileDesignsSize = cardPileDesignRegistry_.getCardPileDesigns().size();
        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );
        assertEquals( originalCardPileDesignsSize + 1, cardPileDesignRegistry_.getCardPileDesigns().size() );

        cardPileDesignRegistry_.unregisterCardPileDesign( CardPileDesigns.cloneCardPileDesign( cardPileDesign ) );

        assertEquals( originalCardPileDesignsSize + 1, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileDesign} method unregisters a
     * previously registered card pile design.
     */
    @Test
    public void testUnregisterCardPileDesign_CardPileDesign_Registered_SameInstance()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final int originalCardPileDesignsSize = cardPileDesignRegistry_.getCardPileDesigns().size();
        cardPileDesignRegistry_.registerCardPileDesign( cardPileDesign );
        assertEquals( originalCardPileDesignsSize + 1, cardPileDesignRegistry_.getCardPileDesigns().size() );

        cardPileDesignRegistry_.unregisterCardPileDesign( cardPileDesign );

        assertEquals( originalCardPileDesignsSize, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileDesign} properly ignores a card pile
     * design that was not previously registered.
     */
    @Test
    public void testUnregisterCardPileDesign_CardPileDesign_Unregistered()
    {
        final ICardPileDesign cardPileDesign = CardPileDesigns.createUniqueCardPileDesign();
        final int originalCardPileDesignsSize = cardPileDesignRegistry_.getCardPileDesigns().size();

        cardPileDesignRegistry_.unregisterCardPileDesign( cardPileDesign );

        assertEquals( originalCardPileDesignsSize, cardPileDesignRegistry_.getCardPileDesigns().size() );
    }
}
