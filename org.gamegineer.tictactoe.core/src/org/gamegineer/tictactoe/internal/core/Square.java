/*
 * Square.java
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
 * Created on Jun 30, 2009 at 11:54:29 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.tictactoe.core.ISquare;
import org.gamegineer.tictactoe.core.SquareId;

/**
 * Implementation of {@link org.gamegineer.tictactoe.core.ISquare}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Square
    implements ISquare
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The square identifier. */
    private final SquareId m_id;

    /** The role identifier of the square owner. */
    private final String m_ownerRoleId;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Square} class.
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
    public Square(
        /* @NonNull */
        final SquareId id,
        /* @Nullable */
        final String ownerRoleId )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;
        m_ownerRoleId = ownerRoleId;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.tictactoe.core.ISquare#getId()
     */
    public SquareId getId()
    {
        return m_id;
    }

    /*
     * @see org.gamegineer.tictactoe.core.ISquare#getOwnerRoleId()
     */
    public String getOwnerRoleId()
    {
        return m_ownerRoleId;
    }
}
