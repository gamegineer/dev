/*
 * GameSystemUiUtils.java
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
 * Created on Mar 10, 2009 at 10:01:23 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.internal.ui.system.NullGameSystemUi;
import org.gamegineer.game.internal.ui.system.NullRoleUi;

/**
 * A collection of useful methods for working with game system user interfaces.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class GameSystemUiUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiUtils} class.
     */
    private GameSystemUiUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system user interface with the specified identifier from
     * the specified factory.
     * 
     * @param gameSystemUiFactory
     *        The game system user interface factory; must not be {@code null}.
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system user interface with the specified identifier;
     *         never {@code null}. If the specified factory cannot provide the
     *         requested game system user interface, an appropriate default
     *         implementation will be returned.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUiFactory} or {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IGameSystemUi getGameSystemUi(
        /* @NonNull */
        final IGameSystemUiFactory gameSystemUiFactory,
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( gameSystemUiFactory, "gameSystemUiFactory" ); //$NON-NLS-1$
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final IGameSystemUi gameSystemUi = gameSystemUiFactory.getGameSystemUi( id );
        if( gameSystemUi != null )
        {
            return gameSystemUi;
        }

        return new NullGameSystemUi( id );
    }

    /**
     * Gets the role user interface with the specified identifier from the
     * specified game system user interface.
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return The role user interface with the specified identifier; never
     *         {@code null}. If the specified game system user interface cannot
     *         provide the requested role user interface, an appropriate default
     *         implementation will be returned.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} or {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IRoleUi getRoleUi(
        /* @NonNull */
        final IGameSystemUi gameSystemUi,
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        for( final IRoleUi roleUi : gameSystemUi.getRoles() )
        {
            if( id.equals( roleUi.getId() ) )
            {
                return roleUi;
            }
        }

        return new NullRoleUi( id );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A factory for obtaining a game system user interface with a specific
     * identifier.
     */
    public interface IGameSystemUiFactory
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the game system user interface with the specified identifier.
         * 
         * @param id
         *        The game system identifier; must not be {@code null}.
         * 
         * @return The game system user interface with the specified identifier
         *         or {@code null} if no such game system is known to this
         *         factory.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code id} is {@code null}.
         */
        /* @Nullable */
        public IGameSystemUi getGameSystemUi(
            /* @NonNull */
            final String id );
    }
}
