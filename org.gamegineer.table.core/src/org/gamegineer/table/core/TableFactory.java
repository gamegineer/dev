/*
 * TableFactory.java
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
 * Created on Oct 6, 2009 at 11:05:05 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MalformedMementoException;
import org.gamegineer.table.internal.core.Card;
import org.gamegineer.table.internal.core.CardPile;
import org.gamegineer.table.internal.core.CardPileBaseDesign;
import org.gamegineer.table.internal.core.CardSurfaceDesign;
import org.gamegineer.table.internal.core.Table;

/**
 * A factory for creating table components.
 */
@ThreadSafe
public final class TableFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFactory} class.
     */
    private TableFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card with the specified back and face designs.
     * 
     * @param backDesign
     *        The design on the back of the card; must not be {@code null}.
     * @param faceDesign
     *        The design on the face of the card; must not be {@code null}.
     * 
     * @return A new card; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code backDesign} and {@code faceDesign} do not have the same
     *         size.
     * @throws java.lang.NullPointerException
     *         If {@code backDesign} or {@code faceDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICard createCard(
        /* @NonNull */
        final ICardSurfaceDesign backDesign,
        /* @NonNull */
        final ICardSurfaceDesign faceDesign )
    {
        return new Card( backDesign, faceDesign );
    }

    /**
     * Creates a new card whose state is initialized using the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial card state; must not be
     *        {@code null}.
     * 
     * @return A new card; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.core.util.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    public static ICard createCard(
        /* @NonNull */
        final Object memento )
        throws MalformedMementoException
    {
        return Card.fromMemento( memento );
    }

    /**
     * Creates a new card pile.
     * 
     * @param baseDesign
     *        The design of the card pile base; must not be {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPile createCardPile(
        /* @NonNull */
        final ICardPileBaseDesign baseDesign )
    {
        return new CardPile( baseDesign );
    }

    /**
     * Creates a new card pile whose state is initialized using the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial card pile state; must not be
     *        {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.core.util.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    public static ICardPile createCardPile(
        /* @NonNull */
        final Object memento )
        throws MalformedMementoException
    {
        return CardPile.fromMemento( memento );
    }

    /**
     * Creates a new card pile base design.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * @param size
     *        The card pile base design size in table coordinates; no component
     *        may be negative.
     * 
     * @return A new card pile base design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createCardPileBaseDesign(
        /* @NonNull */
        final CardPileBaseDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createCardPileBaseDesign( id, size.width, size.height );
    }

    /**
     * Creates a new card pile base design.
     * 
     * @param id
     *        The card pile base design identifier; must not be {@code null}.
     * @param width
     *        The card pile base design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card pile base design height in table coordinates; must not be
     *        negative.
     * 
     * @return A new card pile base design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesign createCardPileBaseDesign(
        /* @NonNull */
        final CardPileBaseDesignId id,
        final int width,
        final int height )
    {
        return new CardPileBaseDesign( id, width, height );
    }

    /**
     * Creates a new card surface design.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * @param size
     *        The card surface design size in table coordinates; no component
     *        may be negative.
     * 
     * @return A new card surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any component of {@code size} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} or {@code size} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createCardSurfaceDesign(
        /* @NonNull */
        final CardSurfaceDesignId id,
        /* @NonNull */
        final Dimension size )
    {
        assertArgumentNotNull( size, "size" ); //$NON-NLS-1$

        return createCardSurfaceDesign( id, size.width, size.height );
    }

    /**
     * Creates a new card surface design.
     * 
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * @param width
     *        The card surface design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card surface design height in table coordinates; must not be
     *        negative.
     * 
     * @return A new card surface design; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesign createCardSurfaceDesign(
        /* @NonNull */
        final CardSurfaceDesignId id,
        final int width,
        final int height )
    {
        return new CardSurfaceDesign( id, width, height );
    }

    /**
     * Creates a new table.
     * 
     * @return A new table; never {@code null}.
     */
    /* @NonNull */
    public static ITable createTable()
    {
        return new Table();
    }

    /**
     * Creates a new table whose state is initialized using the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial table state; must not be
     *        {@code null}.
     * 
     * @return A new table; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.core.util.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    public static ITable createTable(
        /* @NonNull */
        final Object memento )
        throws MalformedMementoException
    {
        return Table.fromMemento( memento );
    }
}
