/*
 * FakeGameSystemSource.java
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
 * Created on Dec 12, 2008 at 10:04:56 PM.
 */

package org.gamegineer.server.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * Fake implementation of
 * {@link org.gamegineer.server.core.system.IGameSystemSource}.
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
public class FakeGameSystemSource
    implements IGameSystemSource
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of game systems available from this source. */
    private final Collection<IGameSystem> gameSystems_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeGameSystemSource} class
     * using an empty collection of game systems.
     */
    public FakeGameSystemSource()
    {
        this( Collections.<IGameSystem>emptyList() );
    }

    /**
     * Initializes a new instance of the {@code FakeGameSystemSource} class
     * using the specified collection of game systems.
     * 
     * @param gameSystems
     *        The collection of game systems available from this source; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystems} is {@code null}.
     */
    public FakeGameSystemSource(
        /* @NonNull */
        final Collection<IGameSystem> gameSystems )
    {
        assertArgumentNotNull( gameSystems, "gameSystems" ); //$NON-NLS-1$

        gameSystems_ = new ArrayList<IGameSystem>( gameSystems );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.config.IGameSystemSource#getGameSystems()
     */
    public Collection<IGameSystem> getGameSystems()
    {
        return new ArrayList<IGameSystem>( gameSystems_ );
    }
}
