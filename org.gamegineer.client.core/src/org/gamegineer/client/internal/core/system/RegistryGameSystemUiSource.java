/*
 * RegistryGameSystemUiSource.java
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
 * Created on Mar 7, 2009 at 9:05:16 PM.
 */

package org.gamegineer.client.internal.core.system;

import java.util.Collection;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.system.IGameSystemUiSource;
import org.gamegineer.client.internal.core.Services;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * Implementation of
 * {@link org.gamegineer.client.core.system.IGameSystemUiSource} that uses the
 * game system user interface registry as its source.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class RegistryGameSystemUiSource
    implements IGameSystemUiSource
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RegistryGameSystemUiSource}
     * class.
     */
    public RegistryGameSystemUiSource()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.system.IGameSystemUiSource#getGameSystemUis()
     */
    public Collection<IGameSystemUi> getGameSystemUis()
    {
        return Services.getDefault().getGameSystemUiRegistry().getGameSystemUis();
    }
}
