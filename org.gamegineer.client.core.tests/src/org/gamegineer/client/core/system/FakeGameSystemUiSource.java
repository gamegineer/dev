/*
 * FakeGameSystemUiSource.java
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
 * Created on Mar 7, 2009 at 12:04:48 AM.
 */

package org.gamegineer.client.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * Fake implementation of
 * {@link org.gamegineer.client.core.system.IGameSystemUiSource}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@Immutable
public class FakeGameSystemUiSource
    implements IGameSystemUiSource
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of game system user interfaces available from this source.
     */
    private final Collection<IGameSystemUi> gameSystemUis_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeGameSystemUiSource} class
     * using an empty collection of game system user interfaces.
     */
    public FakeGameSystemUiSource()
    {
        this( Collections.<IGameSystemUi>emptyList() );
    }

    /**
     * Initializes a new instance of the {@code FakeGameSystemUiSource} class
     * using the specified collection of game system user interfaces.
     * 
     * @param gameSystemUis
     *        The collection of game system user interfaces available from this
     *        source; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUis} is {@code null}.
     */
    public FakeGameSystemUiSource(
        /* @NonNull */
        final Collection<IGameSystemUi> gameSystemUis )
    {
        assertArgumentNotNull( gameSystemUis, "gameSystemUis" ); //$NON-NLS-1$

        gameSystemUis_ = new ArrayList<IGameSystemUi>( gameSystemUis );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.system.IGameSystemUiSource#getGameSystemUis()
     */
    public Collection<IGameSystemUi> getGameSystemUis()
    {
        return new ArrayList<IGameSystemUi>( gameSystemUis_ );
    }
}
