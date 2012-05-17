/*
 * CardPileIncrement.java
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
 * Created on Jul 9, 2011 at 10:29:55 PM.
 */

package org.gamegineer.table.internal.net.node;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.IComponentSurfaceDesign;
import org.gamegineer.table.core.IContainerLayout;

/**
 * An incremental change to the state of a card pile.
 */
@NotThreadSafe
public final class CardPileIncrement
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8032659310899173988L;

    /**
     * The collection of mementos representing the cards added to the top of the
     * card pile ordered from bottom to top or {@code null} if no cards were
     * added.
     * 
     * @serial The collection of mementos representing the cards added to the
     *         top of the card pile.
     */
    private List<Object> addedCardMementos_;

    /**
     * The new design of the card pile base or {@code null} if unchanged.
     * 
     * @serial The new design of the card pile base.
     */
    private IComponentSurfaceDesign baseDesign_;

    /**
     * The new card pile layout or {@code null} if unchanged.
     * 
     * @serial The new card pile layout.
     */
    private IContainerLayout layout_;

    /**
     * The new card pile origin or {@code null} if unchanged.
     * 
     * @serial The new card pile origin.
     */
    private Point origin_;

    /**
     * The count of cards removed from the top of the card pile or {@code null}
     * if no cards were removed.
     * 
     * @serial The count of cards removed from the top of the card pile.
     */
    private Integer removedCardCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileIncrement} class.
     */
    public CardPileIncrement()
    {
        addedCardMementos_ = null;
        baseDesign_ = null;
        layout_ = null;
        origin_ = null;
        removedCardCount_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the collection of mementos representing the cards added to the top
     * of the card pile.
     * 
     * @return The collection of mementos representing the cards added to the
     *         top of the card pile ordered from bottom to top or {@code null}
     *         if no cards were added. The returned value is not a copy and must
     *         not be modified.
     */
    /* @Nullable */
    public List<Object> getAddedCardMementos()
    {
        return addedCardMementos_;
    }

    /**
     * Gets the new design of the card pile base.
     * 
     * @return The new design of the card pile base or {@code null} if
     *         unchanged.
     */
    /* @Nullable */
    public IComponentSurfaceDesign getBaseDesign()
    {
        return baseDesign_;
    }

    /**
     * Gets the new card pile layout.
     * 
     * @return The new card pile layout or {@code null} if unchanged.
     */
    /* @Nullable */
    public IContainerLayout getLayout()
    {
        return layout_;
    }

    /**
     * Gets the new card pile origin.
     * 
     * @return The new card pile origin or {@code null} if unchanged. The
     *         returned value is not a copy and must not be modified.
     */
    /* @Nullable */
    public Point getOrigin()
    {
        return origin_;
    }

    /**
     * Gets the count of cards removed from the top of the card pile.
     * 
     * @return The count of cards removed from the top of the card pile or
     *         {@code null} if no cards were removed.
     */
    /* @Nullable */
    public Integer getRemovedCardCount()
    {
        return removedCardCount_;
    }

    /**
     * Sets the collection of mementos representing the cards added to the top
     * of the card pile.
     * 
     * @param addedCardMementos
     *        The collection of mementos representing the cards added to the top
     *        of the card pile ordered from bottom to top or {@code null} if no
     *        cards were added. No copy is made of the specified value and it
     *        must not be modified after calling this method.
     */
    public void setAddedCardMementos(
        /* @Nullable */
        final List<Object> addedCardMementos )
    {
        addedCardMementos_ = addedCardMementos;
    }

    /**
     * Sets the new design of the card pile base.
     * 
     * @param baseDesign
     *        The new design of the card pile base or {@code null} if unchanged.
     */
    public void setBaseDesign(
        /* @Nullable */
        final IComponentSurfaceDesign baseDesign )
    {
        baseDesign_ = baseDesign;
    }

    /**
     * Sets the new card pile layout.
     * 
     * @param layout
     *        The new card pile layout or {@code null} if unchanged.
     */
    public void setLayout(
        /* @Nullable */
        final IContainerLayout layout )
    {
        layout_ = layout;
    }

    /**
     * Sets the new card pile origin.
     * 
     * @param origin
     *        The new card pile origin or {@code null} if unchanged. No copy is
     *        made of the specified value and it must not be modified after
     *        calling this method.
     */
    public void setOrigin(
        /* @Nullable */
        final Point origin )
    {
        origin_ = origin;
    }

    /**
     * Sets the count of cards removed from the top of the card pile.
     * 
     * @param removedCardCount
     *        The count of cards removed from the top of the card pile or
     *        {@code null} if no cards were removed.
     */
    public void setRemovedCardCount(
        /* @Nullable */
        final Integer removedCardCount )
    {
        removedCardCount_ = removedCardCount;
    }
}
