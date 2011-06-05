/*
 * TableFactoryTest.java
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
 * Created on Oct 6, 2009 at 11:07:48 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import org.gamegineer.common.core.util.memento.IMemento;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.TableFactory}
 * class.
 */
public final class TableFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFactoryTest} class.
     */
    public TableFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createCard(ICardSurfaceDesign, ICardSurfaceDesign)}
     * method throws an exception when passed a {@code null} back design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardFromBackDesignAndFaceDesign_BackDesign_Null()
    {
        TableFactory.createCard( null, CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }

    /**
     * Ensures the {@code createCard(ICardSurfaceDesign, ICardSurfaceDesign)}
     * method throws an exception when passed a {@code null} face design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardFromBackDesignAndFaceDesign_FaceDesign_Null()
    {
        TableFactory.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), null );
    }

    /**
     * Ensures the {@code createCard(ICardSurfaceDesign, ICardSurfaceDesign)}
     * method throws an exception when passed a face design that has a size
     * different from the back design.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardFromBackDesignAndFaceDesign_FaceDesign_SizeNotEqual()
    {
        final int width = 10;
        final int height = 20;
        final ICardSurfaceDesign backDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( width, height );
        final ICardSurfaceDesign faceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign( 2 * width, 2 * height );

        TableFactory.createCard( backDesign, faceDesign );
    }

    /**
     * Ensures the {@code createCard(ICardSurfaceDesign, ICardSurfaceDesign)}
     * method does not return {@code null}.
     */
    @Test
    public void testCreateCardFromBackDesignAndFaceDesign_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCard( CardSurfaceDesigns.createUniqueCardSurfaceDesign(), CardSurfaceDesigns.createUniqueCardSurfaceDesign() ) );
    }

    /**
     * Ensures the {@code createCard(IMemento)} method throws an exception when
     * passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardFromMemento_Memento_Null()
        throws Exception
    {
        TableFactory.createCard( null );
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Dimension)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardPileBaseDesignFromSize_Height_Negative()
    {
        TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Dimension)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPileBaseDesignFromSize_Id_Null()
    {
        TableFactory.createCardPileBaseDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Dimension)} method does not return {@code null}.
     */
    @Test
    public void testCreateCardPileBaseDesignFromSize_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), new Dimension( 0, 0 ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Dimension)} method throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPileBaseDesignFromSize_Size_Null()
    {
        TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Dimension)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardPileBaseDesignFromSize_Width_Negative()
    {
        TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Integer, Integer)} method throws an exception when passed a negative
     * height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardPileBaseDesignFromWidthHeight_Height_Negative()
    {
        TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Integer, Integer)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPileBaseDesignFromWidthHeight_Id_Null()
    {
        TableFactory.createCardPileBaseDesign( null, 0, 0 );
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Integer, Integer)} method does not return {@code null}.
     */
    @Test
    public void testCreateCardPileBaseDesignFromWidthHeight_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), 0, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPileBaseDesign(CardPileBaseDesignId,
     * Integer, Integer)} method throws an exception when passed a negative
     * width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardPileBaseDesignFromWidthHeight_Width_Negative()
    {
        TableFactory.createCardPileBaseDesign( CardPileBaseDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardPile(CardPileBaseDesign)} method throws an
     * exception when passed a {@code null} card pile base design.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPileFromBaseDesign_BaseDesign_Null()
    {
        TableFactory.createCardPile( (ICardPileBaseDesign)null );
    }

    /**
     * Ensures the {@code createCardPile(CardPileBaseDesign)} method does not
     * return {@code null}.
     */
    @Test
    public void testCreateCardPileFromBaseDesign_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCardPile( CardPileBaseDesigns.createUniqueCardPileBaseDesign() ) );
    }

    /**
     * Ensures the {@code createCardPile(IMemento)} method throws an exception
     * when passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardPileFromMemento_Memento_Null()
        throws Exception
    {
        TableFactory.createCardPile( (IMemento)null );
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardSurfaceDesignFromSize_Height_Negative()
    {
        TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), new Dimension( 0, -1 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardSurfaceDesignFromSize_Id_Null()
    {
        TableFactory.createCardSurfaceDesign( null, new Dimension( 0, 0 ) );
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId,
     * Dimension)} method does not return {@code null}.
     */
    @Test
    public void testCreateCardSurfaceDesignFromSize_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), new Dimension( 0, 0 ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId,
     * Dimension)} method throws an exception when passed a {@code null} size.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardSurfaceDesignFromSize_Size_Null()
    {
        TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId,
     * Dimension)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardSurfaceDesignFromSize_Width_Negative()
    {
        TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), new Dimension( -1, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative height.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardSurfaceDesignFromWidthHeight_Height_Negative()
    {
        TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), 0, -1 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateCardSurfaceDesignFromWidthHeight_Id_Null()
    {
        TableFactory.createCardSurfaceDesign( null, 0, 0 );
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId, Integer,
     * Integer)} method does not return {@code null}.
     */
    @Test
    public void testCreateCardSurfaceDesignFromWidthHeight_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), 0, 0 ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createCardSurfaceDesign(CardSurfaceDesignId, Integer,
     * Integer)} method throws an exception when passed a negative width.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateCardSurfaceDesignFromWidthHeight_Width_Negative()
    {
        TableFactory.createCardSurfaceDesign( CardSurfaceDesignId.fromString( "id" ), -1, 0 ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createTable()} method does not return {@code null}.
     */
    @Test
    public void testCreateTable_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createTable() );
    }

    /**
     * Ensures the {@code createTable(IMemento)} method throws an exception when
     * passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableFromMemento_Memento_Null()
        throws Exception
    {
        TableFactory.createTable( null );
    }
}
