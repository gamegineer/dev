/*
 * GameSystemUi.java
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
 * Created on Feb 27, 2009 at 9:34:40 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * Implementation of {@link org.gamegineer.game.ui.system.IGameSystemUi}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameSystemUi
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
     * Initializes a new instance of the {@code GameSystemUi} class.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param name
     *        The game system name; must not be {@code null}.
     * @param roleUis
     *        The role user interface list; must not be {@code null}.
     */
    private GameSystemUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final List<IRoleUi> roleUis )
    {
        assert id != null;
        assert name != null;
        assert roleUis != null;

        id_ = id;
        name_ = name;
        roleUis_ = new ArrayList<IRoleUi>( roleUis );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code GameSystemUi} class.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param name
     *        The game system name; must not be {@code null}.
     * @param roleUis
     *        The role user interface list; must not be {@code null}.
     * 
     * @return A new game system user interface; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal game system user
     *         interface.
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, or {@code roleUis} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemUi createGameSystemUi(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final List<IRoleUi> roleUis )
    {
        // TODO: See comment in GameConfiguration.createGameConfiguration().

        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( roleUis, "roleUis" ); //$NON-NLS-1$

        final GameSystemUi gameSystemUi = new GameSystemUi( id, name, roleUis );
        GameSystemUiUtils.assertGameSystemUiLegal( gameSystemUi );
        return gameSystemUi;
    }

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getId()
     */
    public String getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getName()
     */
    public String getName()
    {
        return name_;
    }

    public List<IRoleUi> getRoles()
    {
        return new ArrayList<IRoleUi>( roleUis_ );
    }
}
