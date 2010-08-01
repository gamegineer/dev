/*
 * AbstractCardSurfaceDesignRegistryTestCase.java
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
 * Created on Nov 14, 2009 at 11:55:12 PM.
 */

package org.gamegineer.table.core.services.cardsurfacedesignregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.services.cardsurfacedesignregistry.ICardSurfaceDesignRegistry}
 * interface.
 */
public abstract class AbstractCardSurfaceDesignRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design registry under test in the fixture. */
    private ICardSurfaceDesignRegistry cardSurfaceDesignRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardSurfaceDesignRegistryTestCase} class.
     */
    protected AbstractCardSurfaceDesignRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card surface design registry to be tested.
     * 
     * @return The card surface design registry to be tested; never {@code null}
     *         .
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardSurfaceDesignRegistry createCardSurfaceDesignRegistry()
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
        cardSurfaceDesignRegistry_ = createCardSurfaceDesignRegistry();
        assertNotNull( cardSurfaceDesignRegistry_ );
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
        cardSurfaceDesignRegistry_ = null;
    }

    /**
     * Ensures the {@code getCardSurfaceDesign} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetCardSurfaceDesign_Id_Absent()
    {
        assertNull( cardSurfaceDesignRegistry_.getCardSurfaceDesign( CardSurfaceDesignId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getCardSurfaceDesign} method throws an exception when
     * passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetCardSurfaceDesign_Id_Null()
    {
        cardSurfaceDesignRegistry_.getCardSurfaceDesign( null );
    }

    /**
     * Ensures the {@code getCardSurfaceDesign} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetCardSurfaceDesign_Id_Present()
    {
        final ICardSurfaceDesign expectedCardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( expectedCardSurfaceDesign );

        final ICardSurfaceDesign actualCardSurfaceDesign = cardSurfaceDesignRegistry_.getCardSurfaceDesign( expectedCardSurfaceDesign.getId() );

        assertSame( expectedCardSurfaceDesign, actualCardSurfaceDesign );
    }

    /**
     * Ensures the {@code getCardSurfaceDesigns} method returns a copy of the
     * registered card surface design collection.
     */
    @Test
    public void testGetCardSurfaceDesigns_ReturnValue_Copy()
    {
        final Collection<ICardSurfaceDesign> cardSurfaceDesigns = cardSurfaceDesignRegistry_.getCardSurfaceDesigns();
        final int expectedCardSurfaceDesignsSize = cardSurfaceDesigns.size();

        cardSurfaceDesigns.add( CardSurfaceDesigns.createUniqueCardSurfaceDesign() );

        assertEquals( expectedCardSurfaceDesignsSize, cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code getCardSurfaceDesigns} method returns a snapshot of
     * the registered card surface design collection.
     */
    @Test
    public void testGetCardSurfaceDesigns_ReturnValue_Snapshot()
    {
        final Collection<ICardSurfaceDesign> cardSurfaceDesigns = cardSurfaceDesignRegistry_.getCardSurfaceDesigns();
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( CardSurfaceDesigns.createUniqueCardSurfaceDesign() );

        assertTrue( cardSurfaceDesigns.size() != cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesign} method throws an exception
     * when passed a {@code null} card surface design.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterCardSurfaceDesign_CardSurfaceDesign_Null()
    {
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( null );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesign} method throws an exception
     * when a card surface design with the same identifier is already
     * registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterCardSurfaceDesign_CardSurfaceDesign_Registered()
    {
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );

        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( CardSurfaceDesigns.cloneCardSurfaceDesign( cardSurfaceDesign ) );
    }

    /**
     * Ensures the {@code registerCardSurfaceDesign} method registers an
     * unregistered card surface design.
     */
    @Test
    public void testRegisterCardSurfaceDesign_CardSurfaceDesign_Unregistered()
    {
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();

        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );

        assertTrue( cardSurfaceDesignRegistry_.getCardSurfaceDesigns().contains( cardSurfaceDesign ) );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesign} method throws an
     * exception when passed a {@code null} card surface design.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterCardSurfaceDesign_CardSurfaceDesign_Null()
    {
        cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( null );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesign} method throws an
     * exception when passed a card surface design whose identifier was
     * previously registered but by a different card surface design instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardSurfaceDesign_CardSurfaceDesign_Registered_DifferentInstance()
    {
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        final int originalCardSurfaceDesignsSize = cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size();
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );
        assertEquals( originalCardSurfaceDesignsSize + 1, cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size() );

        cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( CardSurfaceDesigns.cloneCardSurfaceDesign( cardSurfaceDesign ) );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesign} method unregisters a
     * previously registered card surface design.
     */
    @Test
    public void testUnregisterCardSurfaceDesign_CardSurfaceDesign_Registered_SameInstance()
    {
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        final int originalCardSurfaceDesignsSize = cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size();
        cardSurfaceDesignRegistry_.registerCardSurfaceDesign( cardSurfaceDesign );
        assertEquals( originalCardSurfaceDesignsSize + 1, cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size() );

        cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( cardSurfaceDesign );

        assertEquals( originalCardSurfaceDesignsSize, cardSurfaceDesignRegistry_.getCardSurfaceDesigns().size() );
    }

    /**
     * Ensures the {@code unregisterCardSurfaceDesign} method throws an
     * exception when passed a card surface design that was not previously
     * registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterCardSurfaceDesign_CardSurfaceDesign_Unregistered()
    {
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();

        cardSurfaceDesignRegistry_.unregisterCardSurfaceDesign( cardSurfaceDesign );
    }
}
