/*
 * AbstractCardPileBaseDesignRegistryTestCase.java
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

package org.gamegineer.table.core.services.cardpilebasedesignregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.services.cardpilebasedesignregistry.ICardPileBaseDesignRegistry}
 * interface.
 */
public abstract class AbstractCardPileBaseDesignRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile base design registry under test in the fixture. */
    private ICardPileBaseDesignRegistry cardPileBaseDesignRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardPileBaseDesignRegistryTestCase} class.
     */
    protected AbstractCardPileBaseDesignRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card pile base design registry to be tested.
     * 
     * @return The card pile base design registry to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardPileBaseDesignRegistry createCardPileBaseDesignRegistry()
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
        cardPileBaseDesignRegistry_ = createCardPileBaseDesignRegistry();
        assertNotNull( cardPileBaseDesignRegistry_ );
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
        cardPileBaseDesignRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardPileBaseDesign} method returns the correct
     * value when passed an identifier that is absent.
     */
    @Test
    public void testGetCardPileBaseDesign_Id_Absent()
    {
        assertNull( cardPileBaseDesignRegistry_.getCardPileBaseDesign( CardPileBaseDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardPileBaseDesign} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardPileBaseDesign_Id_Null()
    {
        cardPileBaseDesignRegistry_.getCardPileBaseDesign( null );
    }

    /**
     * Ensures the {@code getCardPileBaseDesign} method returns the correct
     * value when passed an identifier that is present.
     */
    @Test
    public void testGetCardPileBaseDesign_Id_Present()
    {
        final ICardPileBaseDesign expectedCardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( expectedCardPileBaseDesign );

        final ICardPileBaseDesign actualCardPileBaseDesign = cardPileBaseDesignRegistry_.getCardPileBaseDesign( expectedCardPileBaseDesign.getId() );

        assertSame( expectedCardPileBaseDesign, actualCardPileBaseDesign );
    }

    /**
     * Ensures the {@code getCardPileBaseDesigns} method returns a copy of the
     * registered card pile base design collection.
     */
    @Test
    public void testGetCardPileBaseDesigns_ReturnValue_Copy()
    {
        final Collection<ICardPileBaseDesign> cardPileBaseDesigns = cardPileBaseDesignRegistry_.getCardPileBaseDesigns();
        final int expectedCardPileBaseDesignsSize = cardPileBaseDesigns.size();

        cardPileBaseDesigns.add( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );

        assertEquals( expectedCardPileBaseDesignsSize, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code getCardPileBaseDesigns} method returns a snapshot of
     * the registered card pile base design collection.
     */
    @Test
    public void testGetCardPileBaseDesigns_ReturnValue_Snapshot()
    {
        final Collection<ICardPileBaseDesign> cardPileBaseDesigns = cardPileBaseDesignRegistry_.getCardPileBaseDesigns();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );

        assertTrue( cardPileBaseDesigns.size() != cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesign} method throws an exception
     * when passed a {@code null} card pile base design.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardPileBaseDesign_CardPileBaseDesign_Null()
    {
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( null );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesign} method properly ignores a
     * card pile base design whose identifier has already been registered.
     */
    @Test
    public void testRegisterCardPileBaseDesign_CardPileBaseDesign_Registered_DifferentInstance()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final int originalCardPileBaseDesignsSize = cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );

        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( CardPileBaseDesigns.cloneCardPileBaseDesign( cardPileBaseDesign ) );

        assertTrue( cardPileBaseDesignRegistry_.getCardPileBaseDesigns().contains( cardPileBaseDesign ) );
        assertEquals( originalCardPileBaseDesignsSize + 1, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesign} method properly ignores a
     * card pile base design instance that has already been registered.
     */
    @Test
    public void testRegisterCardPileBaseDesign_CardPileBaseDesign_Registered_SameInstance()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final int originalCardPileBaseDesignsSize = cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );

        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );

        assertTrue( cardPileBaseDesignRegistry_.getCardPileBaseDesigns().contains( cardPileBaseDesign ) );
        assertEquals( originalCardPileBaseDesignsSize + 1, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardPileBaseDesign} method registers an
     * unregistered card pile base design.
     */
    @Test
    public void testRegisterCardPileBaseDesign_CardPileBaseDesign_Unregistered()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();

        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );

        assertTrue( cardPileBaseDesignRegistry_.getCardPileBaseDesigns().contains( cardPileBaseDesign ) );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesign} method throws an
     * exception when passed a {@code null} card pile base design.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardPileBaseDesign_CardPileBaseDesign_Null()
    {
        cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( null );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesign} method ignores a card
     * pile base design whose identifier was previously registered but by a
     * different card pile base design instance.
     */
    @Test
    public void testUnregisterCardPileBaseDesign_CardPileBaseDesign_Registered_DifferentInstance()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final int originalCardPileBaseDesignsSize = cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );
        assertEquals( originalCardPileBaseDesignsSize + 1, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );

        cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( CardPileBaseDesigns.cloneCardPileBaseDesign( cardPileBaseDesign ) );

        assertEquals( originalCardPileBaseDesignsSize + 1, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesign} method unregisters a
     * previously registered card pile base design.
     */
    @Test
    public void testUnregisterCardPileBaseDesign_CardPileBaseDesign_Registered_SameInstance()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final int originalCardPileBaseDesignsSize = cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size();
        cardPileBaseDesignRegistry_.registerCardPileBaseDesign( cardPileBaseDesign );
        assertEquals( originalCardPileBaseDesignsSize + 1, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );

        cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesign );

        assertEquals( originalCardPileBaseDesignsSize, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardPileBaseDesign} properly ignores a card
     * pile base design that was not previously registered.
     */
    @Test
    public void testUnregisterCardPileBaseDesign_CardPileBaseDesign_Unregistered()
    {
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        final int originalCardPileBaseDesignsSize = cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size();

        cardPileBaseDesignRegistry_.unregisterCardPileBaseDesign( cardPileBaseDesign );

        assertEquals( originalCardPileBaseDesignsSize, cardPileBaseDesignRegistry_.getCardPileBaseDesigns().size() );
    }
}
