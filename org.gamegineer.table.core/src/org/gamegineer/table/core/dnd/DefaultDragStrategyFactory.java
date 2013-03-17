/*
 * DefaultDragStrategyFactory.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Mar 9, 2013 at 8:56:50 PM.
 */

package org.gamegineer.table.core.dnd;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponent;

/**
 * Implementation of {@link IDragStrategyFactory} for creating instances of
 * {@link DefaultDragStrategy}.
 */
@Immutable
public final class DefaultDragStrategyFactory
    implements IDragStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDragStrategyFactory}
     * class.
     */
    public DefaultDragStrategyFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategyFactory#createDragStrategy(org.gamegineer.table.core.IComponent)
     */
    @Override
    public IDragStrategy createDragStrategy(
        final IComponent component )
    {
        return new DefaultDragStrategy( component );
    }
}
