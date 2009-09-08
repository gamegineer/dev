/*
 * NonValidatingGameSystemUi.java
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
 * Created on Feb 28, 2009 at 9:55:53 PM.
 */

package org.gamegineer.game.ui.system;

import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * An implementation of {@link org.gamegineer.game.ui.system.IGameSystemUi} that
 * does not validate its attributes.
 * 
 * <p>
 * This implementation allows malformed game system user interfaces and thus may
 * violate the contract of {@code IGameSystemUi}. It is only intended to be used
 * during testing.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NonValidatingGameSystemUi
    implements IGameSystemUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private final String id_;

    /** The game system name. */
    private final String name_;

    /** The role user interface list. */
    private final List<IRoleUi> roleUis_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingGameSystemUi}
     * class.
     * 
     * @param id
     *        The game system identifier; may be {@code null}.
     * @param name
     *        The game system name; may be {@code null}.
     * @param roleUis
     *        The role user interface list; may be {@code null}.
     */
    public NonValidatingGameSystemUi(
        /* @Nullable */
        final String id,
        /* @Nullable */
        final String name,
        /* @Nullable */
        final List<IRoleUi> roleUis )
    {
        id_ = id;
        name_ = name;
        roleUis_ = (roleUis != null) ? new ArrayList<IRoleUi>( roleUis ) : null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @return The game system identifier; may be {@code null}.
     * 
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getId()
     */
    /* @Nullable */
    public String getId()
    {
        return id_;
    }

    /**
     * @return The game system name; may be {@code null}.
     * 
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getName()
     */
    /* @Nullable */
    public String getName()
    {
        return name_;
    }

    /**
     * @return A list view of the game roles; may be {@code null}.
     * 
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getRoles()
     */
    /* @Nullable */
    public List<IRoleUi> getRoles()
    {
        return (roleUis_ != null) ? new ArrayList<IRoleUi>( roleUis_ ) : null;
    }
}
