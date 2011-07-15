/*
 * TableIncrement.java
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
 * Created on Jul 9, 2011 at 10:50:51 PM.
 */

package org.gamegineer.table.internal.net.node;

import java.io.Serializable;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;

/**
 * An incremental change to the state of the table.
 */
@NotThreadSafe
public final class TableIncrement
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8590870576222840677L;

    /**
     * The collection of mementos representing the card piles added to the table
     * ordered from oldest to newest or {@code null} if no card piles were
     * added.
     * 
     * @serial The collection of mementos representing the card piles added to
     *         the table.
     */
    private List<Object> addedCardPileMementos_;

    /**
     * The collection of indexes of card piles removed from the table ordered
     * from newest to oldest or {@code null} if no card piles were removed.
     * 
     * @serial The collection of indexes of card piles removed from the table.
     */
    private List<Integer> removedCardPileIndexes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableIncrement} class.
     */
    public TableIncrement()
    {
        addedCardPileMementos_ = null;
        removedCardPileIndexes_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the collection of mementos representing the card piles added to the
     * table.
     * 
     * @return The collection of mementos representing the card piles added to
     *         the table ordered from oldest to newest or {@code null} if no
     *         card piles were added. The returned value is not a copy and must
     *         not be modified.
     */
    /* @Nullable */
    public List<Object> getAddedCardPileMementos()
    {
        return addedCardPileMementos_;
    }

    /**
     * Gets the collection of indexes of card piles removed from the table.
     * 
     * @return The collection of indexes of card piles removed from the table
     *         ordered from newest to oldest or {@code null} if no card piles
     *         were removed. The returned value is not a copy and must not be
     *         modified.
     */
    /* @Nullable */
    public List<Integer> getRemovedCardPileIndexes()
    {
        return removedCardPileIndexes_;
    }

    /**
     * Sets the collection of mementos representing the card piles added to the
     * table.
     * 
     * @param addedCardPileMementos
     *        The collection of mementos representing the card piles added to
     *        the table ordered from oldest to newest or {@code null} if no card
     *        piles were added. No copy is made of the specified value and it
     *        must not be modified after calling this method.
     */
    public void setAddedCardPileMementos(
        /* @Nullable */
        final List<Object> addedCardPileMementos )
    {
        addedCardPileMementos_ = addedCardPileMementos;
    }

    /**
     * Sets the collection of card piles removed from the table.
     * 
     * @param removedCardPileIndexes
     *        The collection of indexes of card piles removed from the table
     *        ordered from newest to oldest or {@code null} if no card piles
     *        were removed. No copy is made of the specified value and it must
     *        not be modified after calling this method.
     */
    public void setRemovedCardPileIndexes(
        /* @Nullable */
        final List<Integer> removedCardPileIndexes )
    {
        removedCardPileIndexes_ = removedCardPileIndexes;
    }
}
