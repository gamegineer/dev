/*
 * SquareState.java
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
 * Created on Jul 11, 2009 at 9:26:46 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.tictactoe.core.SquareId;

/**
 * Represents the mutable state of a square on the game board.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class SquareState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The square identifier. */
    private final SquareId id_;

    /** The role identifier of the square owner. */
    private final String ownerRoleId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SquareState} class with a clean
     * state.
     * 
     * @param id
     *        The square identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public SquareState(
        /* @NonNull */
        final SquareId id )
    {
        this( id, null );
    }

    /**
     * Initializes a new instance of the {@code SquareState} class with the
     * specified state.
     * 
     * @param id
     *        The square identifier; must not be {@code null}.
     * @param ownerRoleId
     *        The role identifier of the square owner; may be {@code null} if
     *        the square is not owned.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    private SquareState(
        /* @NonNull */
        final SquareId id,
        /* @Nullable */
        final String ownerRoleId )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        id_ = id;
        ownerRoleId_ = ownerRoleId;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Changes the square owner.
     * 
     * @param ownerRoleId
     *        The role identifier of the square owner; may be {@code null} if
     *        the square is not owned.
     * 
     * @return The modified square state; never {@code null}.
     */
    /* @NonNull */
    public SquareState changeOwner(
        /* @Nullable */
        final String ownerRoleId )
    {
        return new SquareState( id_, ownerRoleId );
    }

    /**
     * Gets the square identifier.
     * 
     * @return The square identifier; never {@code null}.
     */
    /* @NonNull */
    public SquareId getId()
    {
        return id_;
    }

    /**
     * Gets the role identifier of the square owner.
     * 
     * @return The role identifier of the square owner or {@code null} if the
     *         square is not owned.
     */
    /* @Nullable */
    public String getOwnerRoleId()
    {
        return ownerRoleId_;
    }
}
