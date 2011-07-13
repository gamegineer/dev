/*
 * CardIncrement.java
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
 * Created on Jul 9, 2011 at 10:03:31 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Point;
import java.io.Serializable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICardSurfaceDesign;

/**
 * An incremental change to the state of a card.
 */
@NotThreadSafe
public final class CardIncrement
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 9216681710456705624L;

    /**
     * The new design on the back of the card or {@code null} if unchanged.
     * 
     * @serial The new design on the back of the card.
     */
    private ICardSurfaceDesign backDesign_;

    /**
     * The new design on the face of the card or {@code null} if unchanged.
     * 
     * @serial The new design on the face of the card.
     */
    private ICardSurfaceDesign faceDesign_;

    /**
     * The new card location or {@code null} if unchanged.
     * 
     * @serial The new card location.
     */
    private Point location_;

    /**
     * The new card orientation or {@code null} if unchanged.
     * 
     * @serial The new card orientation.
     */
    private CardOrientation orientation_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardIncrement} class.
     */
    public CardIncrement()
    {
        backDesign_ = null;
        faceDesign_ = null;
        location_ = null;
        orientation_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the new design on the back of the card.
     * 
     * @return The new design on the back of the card or {@code null} if the
     *         back design is unchanged.
     */
    /* @Nullable */
    public ICardSurfaceDesign getBackDesign()
    {
        return backDesign_;
    }

    /**
     * Gets the new design on the face of the card.
     * 
     * @return The new design on the face of the card or {@code null} if the
     *         face design is unchanged.
     */
    /* @Nullable */
    public ICardSurfaceDesign getFaceDesign()
    {
        return faceDesign_;
    }

    /**
     * Gets the new card location.
     * 
     * @return The new card location or {@code null} if the card location is
     *         unchanged. The returned value is not a copy and must not be
     *         modified.
     */
    /* @Nullable */
    public Point getLocation()
    {
        return location_;
    }

    /**
     * Gets the new card orientation.
     * 
     * @return The new card orientation or {@code null} if the card orientation
     *         is unchanged.
     */
    /* @Nullable */
    public CardOrientation getOrientation()
    {
        return orientation_;
    }

    /**
     * Sets the new card location.
     * 
     * @param location
     *        The card location or {@code null} if the card location is
     *        unchanged. No copy is made of the specified value and it must not
     *        be modified after calling this method.
     */
    public void setLocation(
        /* @Nullable */
        final Point location )
    {
        location_ = location;
    }

    /**
     * Sets the new card orientation.
     * 
     * @param orientation
     *        The new card orientation or {@code null} if the card orientation
     *        is unchanged.
     */
    public void setOrientation(
        /* @Nullable */
        final CardOrientation orientation )
    {
        orientation_ = orientation;
    }

    /**
     * Sets the new card surface designs.
     * 
     * @param backDesign
     *        The new design on the back of the card or {@code null} if the back
     *        design is unchanged.
     * @param faceDesign
     *        The new design on the face of the card or {@code null} if the face
     *        design is unchanged.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code backDesign} is {@code null} and {@code faceDesign} is
     *         not {@code null}; or if {@code backDesign} is not {@code null}
     *         and {@code faceDesign} is {@code null}.
     */
    public void setSurfaceDesigns(
        /* @Nullable */
        final ICardSurfaceDesign backDesign,
        /* @Nullable */
        final ICardSurfaceDesign faceDesign )
    {
        assertArgumentLegal( (backDesign != null) ? (faceDesign != null) : (faceDesign == null), "faceDesign" ); //$NON-NLS-1$

        backDesign_ = backDesign;
        faceDesign_ = faceDesign;
    }
}
