/*
 * AbstractCardDesignRegistryTestCase.java
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
 * Created on Nov 14, 2009 at 11:55:12 PM.
 */

package org.gamegineer.table.core.services.carddesignregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardDesignId;
import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.ICardDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.services.carddesignregistry.ICardDesignRegistry}
 * interface.
 */
public abstract class AbstractCardDesignRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card design registry under test in the fixture. */
    private ICardDesignRegistry cardDesignRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardDesignRegistryTestCase} class.
     */
    protected AbstractCardDesignRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card design registry to be tested.
     * 
     * @return The card design registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardDesignRegistry createCardDesignRegistry()
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
        cardDesignRegistry_ = createCardDesignRegistry();
        assertNotNull( cardDesignRegistry_ );
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
        cardDesignRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardDesign} method returns the correct value when
     * passed an identifier that is absent.
     */
    @Test
    public void testGetCardDesign_Id_Absent()
    {
        assertNull( cardDesignRegistry_.getCardDesign( CardDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardDesign} method throws an exception when passed
     * a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardDesign_Id_Null()
    {
        cardDesignRegistry_.getCardDesign( null );
    }

    /**
     * Ensures the {@code getCardDesign} method returns the correct value when
     * passed an identifier that is present.
     */
    @Test
    public void testGetCardDesign_Id_Present()
    {
        final ICardDesign expectedCardDesign = CardDesigns.createUniqueCardDesign();
        cardDesignRegistry_.registerCardDesign( expectedCardDesign );

        final ICardDesign actualCardDesign = cardDesignRegistry_.getCardDesign( expectedCardDesign.getId() );

        assertSame( expectedCardDesign, actualCardDesign );
    }

    /**
     * Ensures the {@code getCardDesigns} method returns a copy of the
     * registered card design collection.
     */
    @Test
    public void testGetCardDesigns_ReturnValue_Copy()
    {
        final Collection<ICardDesign> cardDesigns = cardDesignRegistry_.getCardDesigns();
        final int expectedCardDesignsSize = cardDesigns.size();

        cardDesigns.add( CardDesigns.createUniqueCardDesign() );

        assertEquals( expectedCardDesignsSize, cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code getCardDesigns} method returns a snapshot of the
     * registered card design collection.
     */
    @Test
    public void testGetCardDesigns_ReturnValue_Snapshot()
    {
        final Collection<ICardDesign> cardDesigns = cardDesignRegistry_.getCardDesigns();
        cardDesignRegistry_.registerCardDesign( CardDesigns.createUniqueCardDesign() );

        assertTrue( cardDesigns.size() != cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardDesign} method throws an exception when
     * passed a {@code null} card design.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardDesign_CardDesign_Null()
    {
        cardDesignRegistry_.registerCardDesign( null );
    }

    /**
     * Ensures the {@code registerCardDesign} method properly ignores a card
     * design whose identifier has already been registered.
     */
    @Test
    public void testRegisterCardDesign_CardDesign_Registered_DifferentInstance()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        final int originalCardDesignsSize = cardDesignRegistry_.getCardDesigns().size();
        cardDesignRegistry_.registerCardDesign( cardDesign );

        cardDesignRegistry_.registerCardDesign( CardDesigns.cloneCardDesign( cardDesign ) );

        assertTrue( cardDesignRegistry_.getCardDesigns().contains( cardDesign ) );
        assertEquals( originalCardDesignsSize + 1, cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardDesign} method properly ignores a card
     * design instance that has already been registered.
     */
    @Test
    public void testRegisterCardDesign_CardDesign_Registered_SameInstance()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        final int originalCardDesignsSize = cardDesignRegistry_.getCardDesigns().size();
        cardDesignRegistry_.registerCardDesign( cardDesign );

        cardDesignRegistry_.registerCardDesign( cardDesign );

        assertTrue( cardDesignRegistry_.getCardDesigns().contains( cardDesign ) );
        assertEquals( originalCardDesignsSize + 1, cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardDesign} method registers an unregistered
     * card design.
     */
    @Test
    public void testRegisterCardDesign_CardDesign_Unregistered()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();

        cardDesignRegistry_.registerCardDesign( cardDesign );

        assertTrue( cardDesignRegistry_.getCardDesigns().contains( cardDesign ) );
    }

    /**
     * Ensures the {@code unregisterCardDesign} method throws an exception when
     * passed a {@code null} card design.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardDesign_CardDesign_Null()
    {
        cardDesignRegistry_.unregisterCardDesign( null );
    }

    /**
     * Ensures the {@code unregisterCardDesign} method ignores a card design
     * whose identifier was previously registered but by a different card design
     * instance.
     */
    @Test
    public void testUnregisterCardDesign_CardDesign_Registered_DifferentInstance()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        final int originalCardDesignsSize = cardDesignRegistry_.getCardDesigns().size();
        cardDesignRegistry_.registerCardDesign( cardDesign );
        assertEquals( originalCardDesignsSize + 1, cardDesignRegistry_.getCardDesigns().size() );

        cardDesignRegistry_.unregisterCardDesign( CardDesigns.cloneCardDesign( cardDesign ) );

        assertEquals( originalCardDesignsSize + 1, cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardDesign} method unregisters a previously
     * registered card design.
     */
    @Test
    public void testUnregisterCardDesign_CardDesign_Registered_SameInstance()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        final int originalCardDesignsSize = cardDesignRegistry_.getCardDesigns().size();
        cardDesignRegistry_.registerCardDesign( cardDesign );
        assertEquals( originalCardDesignsSize + 1, cardDesignRegistry_.getCardDesigns().size() );

        cardDesignRegistry_.unregisterCardDesign( cardDesign );

        assertEquals( originalCardDesignsSize, cardDesignRegistry_.getCardDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardDesign} properly ignores a card design
     * that was not previously registered.
     */
    @Test
    public void testUnregisterCardDesign_CardDesign_Unregistered()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        final int originalCardDesignsSize = cardDesignRegistry_.getCardDesigns().size();

        cardDesignRegistry_.unregisterCardDesign( cardDesign );

        assertEquals( originalCardDesignsSize, cardDesignRegistry_.getCardDesigns().size() );
    }
}
