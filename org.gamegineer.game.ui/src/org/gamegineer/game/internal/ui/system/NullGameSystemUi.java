/*
 * NullGameSystemUi.java
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
 * Created on Mar 11, 2009 at 11:02:32 PM.
 */

package org.gamegineer.game.internal.ui.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * Null implementation of {@link org.gamegineer.game.ui.system.IGameSystemUi}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NullGameSystemUi
    implements IGameSystemUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system identifier. */
    private final String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullGameSystemUi} class.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public NullGameSystemUi(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        return Messages.NullGameSystemUi_name( id_ );
    }

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getRoles()
     */
    public List<IRoleUi> getRoles()
    {
        return new ArrayList<IRoleUi>();
    }
}
