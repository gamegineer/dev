/*
 * NonValidatingRoleUi.java
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
 * Created on Feb 28, 2009 at 9:49:49 PM.
 */

package org.gamegineer.game.ui.system;

import net.jcip.annotations.Immutable;

/**
 * An implementation of {@link org.gamegineer.game.ui.system.IRoleUi} that does
 * not validate its attributes.
 * 
 * <p>
 * This implementation allows malformed role user interfaces and thus may
 * violate the contract of {@code IRoleUi}. It is only intended to be used
 * during testing.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NonValidatingRoleUi
    implements IRoleUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role identifier. */
    private final String id_;

    /** The role name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingRoleUi} class.
     * 
     * @param id
     *        The role identifier; may be {@code null}.
     * @param name
     *        The role name; may be {@code null}.
     */
    public NonValidatingRoleUi(
        /* @Nullable */
        final String id,
        /* @Nullable */
        final String name )
    {
        id_ = id;
        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @return The role identifier; may be {@code null}.
     * 
     * @see org.gamegineer.game.ui.system.IRoleUi#getId()
     */
    /* @Nullable */
    public String getId()
    {
        return id_;
    }

    /**
     * @return The role name; may be {@code null}.
     * 
     * @see org.gamegineer.game.ui.system.IRoleUi#getName()
     */
    /* @Nullable */
    public String getName()
    {
        return name_;
    }
}
