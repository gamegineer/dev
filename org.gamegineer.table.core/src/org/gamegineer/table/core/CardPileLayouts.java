/*
 * CardPileLayouts.java
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
 * Created on May 5, 2012 at 9:56:34 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.AccordianLayout;
import org.gamegineer.table.internal.core.StackedLayout;

/**
 * A collection of layouts for card piles.
 */
@ThreadSafe
public final class CardPileLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately below it.
     */
    public static final IContainerLayout ACCORDIAN_DOWN = new AccordianLayout( 0, 18 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the left of it.
     */
    public static final IContainerLayout ACCORDIAN_LEFT = new AccordianLayout( -16, 0 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the right of it.
     */
    public static final IContainerLayout ACCORDIAN_RIGHT = new AccordianLayout( 16, 0 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately above it.
     */
    public static final IContainerLayout ACCORDIAN_UP = new AccordianLayout( 0, -18 );

    /**
     * Indicates the card pile is laid out with one card placed on top of the
     * other with no offset.
     */
    public static final IContainerLayout STACKED = new StackedLayout( 10, 2, 1 );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileLayouts} class.
     */
    private CardPileLayouts()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile layout associated with the specified identifier.
     * 
     * @param id
     *        The card pile layout identifier; must not be {@code null}.
     * 
     * @return The card pile layout associated with the specified identifier;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code id} is not associated with a card pile layout.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout fromId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        try
        {
            return (IContainerLayout)CardPileLayouts.class.getField( id ).get( null );
        }
        catch( final NoSuchFieldException e )
        {
            throw new IllegalArgumentException( "id", e ); //$NON-NLS-1$
        }
        catch( final IllegalAccessException e )
        {
            throw new IllegalArgumentException( "id", e ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the identifier of the specified card pile layout.
     * 
     * @param layout
     *        The card pile layout; must not be {@code null}.
     * 
     * @return The identifier of the specified card pile layout; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code layout} is not a card pile layout.
     * @throws java.lang.NullPointerException
     *         If {@code layout} is {@code null}.
     */
    /* @NonNull */
    public static String getId(
        /* @NonNull */
        final IContainerLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        if( layout == ACCORDIAN_DOWN )
        {
            return "ACCORDIAN_DOWN"; //$NON-NLS-1$
        }
        else if( layout == ACCORDIAN_LEFT )
        {
            return "ACCORDIAN_LEFT"; //$NON-NLS-1$
        }
        else if( layout == ACCORDIAN_RIGHT )
        {
            return "ACCORDIAN_RIGHT"; //$NON-NLS-1$
        }
        else if( layout == ACCORDIAN_UP )
        {
            return "ACCORDIAN_UP"; //$NON-NLS-1$
        }
        else if( layout == STACKED )
        {
            return "STACKED"; //$NON-NLS-1$
        }

        throw new IllegalArgumentException( "layout" ); //$NON-NLS-1$
    }
}
