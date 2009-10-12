/*
 * Card.java
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
 * Created on Oct 11, 2009 at 9:53:36 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardDesign;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ICard;

/**
 * Implementation of {@link org.gamegineer.table.core.ICard}.
 */
@ThreadSafe
public final class Card
    implements ICard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The design on the back of the card. */
    private final CardDesign backDesign_;

    /** The design on the face of the card. */
    private final CardDesign faceDesign_;

    /** The instance lock. */
    private final Object lock_;

    /** The card orientation. */
    @GuardedBy( "lock_" )
    private CardOrientation orientation_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param backDesign
     *        The design on the back of the card; must not be {@code null}.
     * @param faceDesign
     *        The design on the face of the card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code backDesign} or {@code faceDesign} is {@code null}.
     */
    public Card(
        /* @NonNull */
        final CardDesign backDesign,
        /* @NonNull */
        final CardDesign faceDesign )
    {
        assertArgumentNotNull( backDesign, "backDesign" ); //$NON-NLS-1$
        assertArgumentNotNull( faceDesign, "faceDesign" ); //$NON-NLS-1$

        lock_ = new Object();
        backDesign_ = backDesign;
        faceDesign_ = faceDesign;
        orientation_ = CardOrientation.FACE_UP;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICard#flip()
     */
    public void flip()
    {
        synchronized( lock_ )
        {
            orientation_ = orientation_.inverse();
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#getBackDesign()
     */
    public CardDesign getBackDesign()
    {
        return backDesign_;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getFaceDesign()
     */
    public CardDesign getFaceDesign()
    {
        return faceDesign_;
    }

    /*
     * @see org.gamegineer.table.core.ICard#getOrientation()
     */
    public CardOrientation getOrientation()
    {
        synchronized( lock_ )
        {
            return orientation_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ICard#setOrientation(org.gamegineer.table.core.CardOrientation)
     */
    public void setOrientation(
        final CardOrientation orientation )
    {
        assertArgumentNotNull( orientation, "orientation" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            orientation_ = orientation;
        }
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format( "Card[backDesign_='%1$s', faceDesign_='%2$s', orientation_='%3$s'", backDesign_, faceDesign_, getOrientation() ); //$NON-NLS-1$
    }
}
